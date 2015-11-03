package ru.taximaxim.pgsqlblocks;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.taximaxim.pgsqlblocks.process.Process;

public class ProcessParcer {
    
    private static final Logger LOG = Logger.getLogger(ProcessParcer.class);

    private static final String PID = "pid";
    private static final String APPLICATIONNAME = "applicationName";
    private static final String DATNAME = "datname";
    private static final String USENAME = "usename";
    private static final String CLIENT = "client";
    private static final String BACKENDSTART = "backendStart";
    private static final String QUERYSTART = "queryStart";
    private static final String XACTSTART = "xactStart";
    private static final String STATE = "state";
    private static final String STATECHANGE = "stateChange";
    private static final String BLOCKED = "blocked";
    private static final String WAITING = "waiting";
    private static final String QUERY = "query";
    private static final String SLOWQUERY = "slowQuery";
    private static final String PROCESS = "process";
    private static final String CHILDREN = "children";
    
    public ProcessParcer() {}

    public Element createProcessElement(Document doc, Process process) {
        Element procEl = doc.createElement(PROCESS);
        createElement(procEl, doc.createElement(PID), String.valueOf(process.getPid()));
        createElement(procEl, doc.createElement(APPLICATIONNAME), process.getApplicationName());
        createElement(procEl, doc.createElement(DATNAME), process.getDatname());
        createElement(procEl, doc.createElement(USENAME), process.getUsename());
        createElement(procEl, doc.createElement(CLIENT), process.getClient());
        createElement(procEl, doc.createElement(BACKENDSTART), process.getBackendStart());
        createElement(procEl, doc.createElement(QUERYSTART), process.getQueryStart());
        createElement(procEl, doc.createElement(XACTSTART), process.getXactStart());
        createElement(procEl, doc.createElement(STATE), process.getState());
        createElement(procEl, doc.createElement(STATECHANGE), process.getStateChange());
        createElement(procEl, doc.createElement(BLOCKED), String.valueOf(process.getBlockedBy()));
        createElement(procEl, doc.createElement(WAITING), String.valueOf(process.getBlockingLocks()));
        createElement(procEl, doc.createElement(QUERY), process.getQuery());
        createElement(procEl, doc.createElement(SLOWQUERY), String.valueOf(process.isSlowQuery()));
        
        Element children = doc.createElement(CHILDREN);
        for(Process childProcess : process.getChildren()) {
            children.appendChild(createProcessElement(doc, childProcess));
        }
        procEl.appendChild(children);
        return procEl;
    }
    
    public Process parseProcess(Element el) {
        Process process = new Process(
                Integer.parseInt(getNodeValue(el, PID)),
                getNodeValue(el, APPLICATIONNAME),
                getNodeValue(el, DATNAME),
                getNodeValue(el, USENAME),
                getNodeValue(el, CLIENT),
                getNodeValue(el, BACKENDSTART),
                getNodeValue(el, QUERYSTART),
                getNodeValue(el, XACTSTART),
                getNodeValue(el, STATE),
                getNodeValue(el, STATECHANGE),
                Integer.parseInt(getNodeValue(el, BLOCKED)),
                Integer.parseInt(getNodeValue(el, WAITING)),
                getNodeValue(el, QUERY),
                Boolean.parseBoolean(getNodeValue(el, SLOWQUERY))
                );
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        NodeList children = null;
        try {
            children = (NodeList)xp.evaluate("children/process", el, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            LOG.error("Ошибка XPathExpressionException: " + e.getMessage());
        }
        for(int i=0;i<children.getLength();i++) {
            Node processNode = children.item(i);
            if (processNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element procEl = (Element)processNode;
            Process proc = parseProcess(procEl);
            proc.setParent(process);
            process.addChildren(proc);
        }
        return process;
    }
    
    private void createElement(Element procEl, Element rows, String textContent){
        rows.setTextContent(textContent);
        procEl.appendChild(rows);
    }
    
    private String getNodeValue(Element el, String nodeName) {
        Node node = el.getElementsByTagName(nodeName).item(0).getFirstChild();
        if(node == null) {
            return "";
        }
        return node.getNodeValue();
    }
}
