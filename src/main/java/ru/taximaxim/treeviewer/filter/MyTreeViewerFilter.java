package ru.taximaxim.treeviewer.filter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.taximaxim.treeviewer.listeners.AllTextFilterListener;
import ru.taximaxim.treeviewer.listeners.DataUpdateListener;
import ru.taximaxim.treeviewer.listeners.FilterListener;
import ru.taximaxim.treeviewer.models.IColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * графическое представление фильтра
 */
public class MyTreeViewerFilter extends Composite {

    private GridLayout glayout;
    private List<? extends IColumn> filterList = new ArrayList<>();
    private FilterListener filterableListeners;
    private DataUpdateListener dataUpdateListener;
    private AllTextFilterListener allTextFilterListener;

    public MyTreeViewerFilter(Composite parent, int style) {
        super(parent, style);
        glayout = new GridLayout();
        glayout.marginWidth = 0;
        glayout.marginHeight = 0;
        GridData layoutData = new GridData(SWT.FILL, SWT.TOP, true, false);
        setLayout(glayout);
        setLayoutData(layoutData);
    }

    public void setFilterList(List<? extends IColumn> filterList, FilterListener filterableListeners, DataUpdateListener dataUpdateListener,
                              AllTextFilterListener allTextFilterListener){
        this.filterList = filterList;
        this.filterableListeners = filterableListeners;
        this.dataUpdateListener = dataUpdateListener;
        this.allTextFilterListener = allTextFilterListener;
        createContent();
    }

    private void createContent() {
        glayout.numColumns = findColumnNumber();
        createAllTextFilter();
        filterList.forEach(this::createFilterView);
    }

    private void createAllTextFilter() {
        ViewFilter viewFilter = new ViewFilter(null, null, allTextFilterListener, dataUpdateListener);

        Group group = new Group(this, SWT.HORIZONTAL);
        GridLayout layout = new GridLayout(3, false);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        group.setLayout(layout);
        group.setLayoutData(layoutData);
        Label label = new Label(group, SWT.NONE);
        GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        data.horizontalSpan = 1;
        label.setLayoutData(data);
        label.setText("Filter"); //bundle??????
        Text filterText = new Text(group, SWT.FILL | SWT.BORDER);
        GridData textLayoutData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
        textLayoutData.horizontalSpan = 2;
        filterText.setLayoutData(textLayoutData);
        filterText.addModifyListener(e -> {
            String text = filterText.getText();
            viewFilter.onAllTextChanges(text);
        });
    }


    private void createFilterView(IColumn filter) {
        ViewFilter viewFilter = new ViewFilter(filter, filterableListeners, allTextFilterListener, dataUpdateListener);

        Group group = new Group(this, SWT.HORIZONTAL);
        GridLayout layout = new GridLayout(3, false);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        group.setLayout(layout);
        group.setLayoutData(layoutData);

        GridData comboLayoutData = new GridData(SWT.CENTER, SWT.CENTER, false,false);
        comboLayoutData.widthHint = 60;
        GridData textLayoutData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
        textLayoutData.widthHint = 150;
        textLayoutData.minimumWidth = 150;

        Label label = new Label(group, SWT.NONE);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
        label.setText(filter.getColumnName()); //bundle??????

        Combo combo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setLayoutData(comboLayoutData);
        List<FilterValues> filterValues = Arrays.asList(FilterValues.values());
        filterValues.forEach( f -> combo.add(f.toString()));
        combo.addModifyListener(e -> {
            FilterValues value = FilterValues.find(combo.getText());
            viewFilter.onComboChanges(value);
        });

        Text filterText = new Text(group, SWT.FILL | SWT.BORDER);
        filterText.setLayoutData(textLayoutData);
        filterText.addModifyListener(e -> {
            String text = filterText.getText();
            viewFilter.onTextChanges(text);
        });
    }


    private int findColumnNumber() {
        int i = filterList.size();
        if (i == 1) {
            return 1;
        }else if (i % 2 == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    public void show() {
        this.setVisible(true);
        GridData layoutData = (GridData) this.getLayoutData();
        layoutData.exclude = false;
        this.getParent().layout();
    }

    public void hide() {
        this.setVisible(false);
        GridData layoutData = (GridData) this.getLayoutData();
        layoutData.exclude = true;
        this.getParent().layout();
    }
}
