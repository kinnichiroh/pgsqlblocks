/*
 * ========================LICENSE_START=================================
 * pgSqlBlocks
 * %
 * Copyright (C) 2017 "Technology" LLC
 * %
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package ru.taximaxim.pgsqlblocks.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.taximaxim.pgsqlblocks.common.models.DBModel;
import ru.taximaxim.pgsqlblocks.utils.SupportedVersion;

import java.util.List;
import java.util.ResourceBundle;

public class AddDatabaseDialog extends Dialog {

    private static final String ATTENTION = "attention";

    protected final ResourceBundle resourceBundle;

    private DBModel createdModel;

    Text nameText;
    Text hostText;
    Text portText;
    ComboViewer versionCombo;
    Text userText;
    Text passwordText;
    Text databaseNameText;
    Button enabledButton;

    private static final String DEFAULT_PORT = "5432";
    private static final int TEXT_WIDTH = 200;

    private List<String> reservedConnectionNames;

    public AddDatabaseDialog(ResourceBundle resourceBundle, Shell shell, List<String> reservedConnectionNames) {
        super(shell);
        this.resourceBundle = resourceBundle;
        this.reservedConnectionNames = reservedConnectionNames;
    }

    public DBModel getCreatedModel() {
        return createdModel;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(resourceBundle.getString("add_new_connection"));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);

        GridLayout layout = new GridLayout(2, false);
        layout.marginRight = 5;
        layout.marginLeft = 10;
        layout.marginTop = 10;
        container.setLayout(layout);

        GridData textGd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        textGd.widthHint = TEXT_WIDTH;

        Label nameLabel = new Label(container, SWT.HORIZONTAL);
        nameLabel.setText(resourceBundle.getString("name"));
        nameText = new Text(container, SWT.BORDER);
        nameText.setLayoutData(textGd);

        Label hostLabel = new Label(container, SWT.HORIZONTAL);
        hostLabel.setText(resourceBundle.getString("host"));
        hostText = new Text(container, SWT.BORDER);
        hostText.setLayoutData(textGd);

        Label portLabel = new Label(container, SWT.HORIZONTAL);
        portLabel.setText(resourceBundle.getString("port"));
        portText = new Text(container, SWT.BORDER);
        portText.setText(DEFAULT_PORT);
        portText.setLayoutData(textGd);

        Label versionLabel = new Label(container, SWT.HORIZONTAL);
        versionLabel.setText(resourceBundle.getString("version"));
        versionCombo = new ComboViewer(container, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
        versionCombo.setContentProvider(ArrayContentProvider.getInstance());
        versionCombo.setInput(SupportedVersion.getValuesNoDefault());
        versionCombo.setSelection(new StructuredSelection(SupportedVersion.VERSION_10));
        versionCombo.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof SupportedVersion) {
                    SupportedVersion version = (SupportedVersion) element;
                    return version.getVersionText();
                } else {
                    return super.getText(element);
                }
            }
        });

        Label userLabel = new Label(container, SWT.HORIZONTAL);
        userLabel.setText(resourceBundle.getString("user"));
        userText = new Text(container, SWT.BORDER);
        userText.setLayoutData(textGd);

        Label passwordLabel = new Label(container, SWT.HORIZONTAL);
        passwordLabel.setText(resourceBundle.getString("password"));
        passwordText = new Text(container, SWT.BORDER);
        passwordText.setLayoutData(textGd);
        passwordText.setEchoChar('•');
        passwordText.addListener(SWT.FocusOut, event -> {
            if (!passwordText.getText().isEmpty()) {
                MessageDialog.openWarning(null,
                        resourceBundle.getString(ATTENTION), resourceBundle.getString("use_pgpass_file"));
            }
        });

        Label databaseNameLabel = new Label(container, SWT.HORIZONTAL);
        databaseNameLabel.setText(resourceBundle.getString("database_name"));
        databaseNameText = new Text(container, SWT.BORDER);
        databaseNameText.setLayoutData(textGd);

        Label enabledLabel = new Label(container, SWT.HORIZONTAL);
        enabledLabel.setText(resourceBundle.getString("connect_automatically"));
        enabledButton = new Button(container, SWT.CHECK);

        return container;
    }

    @Override
    protected void okPressed() {
        String name = nameText.getText();
        String host = hostText.getText();
        String port = portText.getText();
        SupportedVersion version = (SupportedVersion) versionCombo.getStructuredSelection().getFirstElement();
        String databaseName = databaseNameText.getText();
        String user = userText.getText();
        String password = passwordText.getText();
        boolean enabled = enabledButton.getSelection();
        if (name.isEmpty()) {
            displayError("missing_connection_name");
            return;
        } else if (reservedConnectionNames.contains(name)) {
            displayError("already_exists", name);
            return;
        } else if (host.isEmpty() || port.isEmpty()) {
            displayError("missing_host_port");
            return;
        } else if (databaseName.isEmpty() || user.isEmpty()) {
            displayError("missing_database_user");
            return;
        } else if (version == SupportedVersion.VERSION_DEFAULT || version == null){
            displayError("missing_database_version");
            return;
        }

        createdModel = new DBModel(name, host, port, version, databaseName, user, password, enabled);

        super.okPressed();
    }
    
    private void displayError(String msg, String... args) {
        MessageDialog.openError(null, resourceBundle.getString(ATTENTION),
                                String.format(resourceBundle.getString(msg), (Object[]) args));
    }
}
