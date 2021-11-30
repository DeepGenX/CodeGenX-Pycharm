package com.deepgenx.codegenx.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Terry Jia
 */
@State(
    name = "com.deepgenx.codegenx.settings.ApplicationSettingsState",
    storages = @Storage("CodeGenXSettingsPlugin.xml")
)
public class ApplicationSettingsState implements PersistentStateComponent<ApplicationSettingsState> {

    public String maxLength = "128";
    public String temperature = "0.9";
    public String token = "";
    public String topP = "1";

    public static ApplicationSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ApplicationSettingsState.class);
    }

    @Override
    public @Nullable ApplicationSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ApplicationSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
