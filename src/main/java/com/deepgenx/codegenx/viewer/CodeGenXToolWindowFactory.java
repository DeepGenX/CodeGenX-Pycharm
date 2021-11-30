package com.deepgenx.codegenx.viewer;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CodeGenXToolWindowFactory implements ToolWindowFactory {

    private CodeGenXTabViewer codeGenXTabViewer;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        try {
            codeGenXTabViewer = new CodeGenXTabViewer(project);

            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

            Content content = contentFactory.createContent(codeGenXTabViewer.getComponent(), "", false);

            toolWindow.getContentManager().addContent(content);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
