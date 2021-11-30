package com.deepgenx.codegenx.viewer;

import com.deepgenx.codegenx.actions.FetchCodeAction;
import com.deepgenx.codegenx.actions.InsertCodeAction;
import com.deepgenx.codegenx.settings.ApplicationSettingsState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.io.JsonObjectBuilder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CodeGenXTabViewer {

    private JPanel mainPanel;
    private JTextField contentText;
    private JButton generateButton;
    private JTextPane generatedCode;

    public CodeGenXTabViewer(@NotNull Project project) throws IOException {
        generateButton.addActionListener(e -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

            DataContext dataContext = DataManager.getInstance().getDataContext(editor.getContentComponent());

            ActionManager actionManager = ActionManager.getInstance();

            FetchCodeAction fetchCodeAction = (FetchCodeAction)actionManager.getAction(FetchCodeAction.ID);

            fetchCodeAction.setGeneratedCode(generatedCode);

            fetchCodeAction.setInput(contentText.getText());

            Presentation presentation = fetchCodeAction.getTemplatePresentation();

            @NotNull AnActionEvent event = AnActionEvent.createFromDataContext("", presentation, dataContext);

            fetchCodeAction.actionPerformed(event);
        });
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}
