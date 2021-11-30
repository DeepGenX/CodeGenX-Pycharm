package com.deepgenx.codegenx.actions;

import com.deepgenx.codegenx.settings.ApplicationSettingsState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

public class FetchCodeAction extends AnAction {

    public static final String ID = "com.deepgenx.codegenx.actions.FetchCodeAction";

    private String _input;
    private JTextPane _generatedCode;

    public void setGeneratedCode(JTextPane generatedCode) {
        _generatedCode = generatedCode;
    }

    public void setInput(String input) {
        _input = input;
    }

    private JsonArray _getSuggestions() {
        JsonArray suggestions = null;

        try {
            ApplicationSettingsState settings = ApplicationSettingsState.getInstance();

            Map<String, Object> map = new HashMap<>();

            map.put("maxLength", settings.maxLength);
            map.put("temperature", settings.temperature);
            map.put("token", settings.token);
            map.put("input", _input);

            String json = JSONObject.toJSONString(map);

            StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String url = "https://api.deepgenx.com:5700/generate";

            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(requestEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            HttpEntity responseEntity = response.getEntity();

            String result =  EntityUtils.toString(responseEntity);

            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();

            suggestions = jsonObject.getAsJsonArray("message");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return suggestions;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Application application = ApplicationManager.getApplication();

        application.executeOnPooledThread(() -> application.runReadAction(() -> {
            @Nullable Project project = e.getProject();

            JsonArray suggestions = _getSuggestions();

            _generatedCode.setText("");

            StyledDocument doc = _generatedCode.getStyledDocument();

            SimpleAttributeSet attr = new SimpleAttributeSet();

            for (int i = 0; i < suggestions.size(); i++) {
                String suggestion = suggestions.get(i).getAsString();

                try {
                    JButton clickButton = new JButton("Insert");

                    clickButton.addActionListener(e1 -> {
                        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

                        DataContext dataContext = DataManager.getInstance().getDataContext(editor.getContentComponent());

                        ActionManager actionManager = ActionManager.getInstance();
                        InsertCodeAction insertCodeAction = (InsertCodeAction)actionManager.getAction(InsertCodeAction.ID);

                        insertCodeAction.setInsetCode(suggestion);

                        Presentation presentation = insertCodeAction.getTemplatePresentation();

                        @NotNull AnActionEvent event = AnActionEvent.createFromDataContext("", presentation, dataContext);

                        insertCodeAction.actionPerformed(event);
                    });

                    _generatedCode.insertComponent(clickButton);

                    doc.insertString(doc.getLength(), "\n", attr);

                    doc.insertString(doc.getLength(), suggestion, attr);

                    _generatedCode.setCaretPosition(_generatedCode.getDocument().getLength());

                    JSeparator jSeparator = new JSeparator();

                    doc.insertString(doc.getLength(), "\n", attr);

                    _generatedCode.setCaretPosition(_generatedCode.getDocument().getLength());

                    _generatedCode.insertComponent(jSeparator);

                    doc.insertString(doc.getLength(), "\n", attr);

                    _generatedCode.setCaretPosition(_generatedCode.getDocument().getLength());
                }
                catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }));
    }

}
