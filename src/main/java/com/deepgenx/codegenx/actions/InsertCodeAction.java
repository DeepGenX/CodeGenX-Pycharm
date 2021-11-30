package com.deepgenx.codegenx.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;

public class InsertCodeAction extends AnAction {

    public static final String ID = "com.deepgenx.codegenx.actions.InsertCodeAction";

    private String _insertCode = "";

    public void setInsetCode(String insertCode) {
        _insertCode = insertCode;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();

        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);

        final CaretModel caretModel = editor.getCaretModel();

        final Caret primaryCaret = caretModel.getPrimaryCaret();

        int caretOffset = primaryCaret.getOffset();

        final Document document = editor.getDocument();

        Runnable runnable = () -> document.insertString(caretOffset, _insertCode);

        WriteCommandAction.runWriteCommandAction(project, runnable);
    }

}
