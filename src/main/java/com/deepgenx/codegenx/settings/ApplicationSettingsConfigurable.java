package com.deepgenx.codegenx.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Terry Jia
 */
public class ApplicationSettingsConfigurable implements SearchableConfigurable {

    private ApplicationSettingsComponent mySettingsComponent;

    @Override
    public @NotNull @NonNls String getId() {
        return "com.deepgenx.codegenx.settings.ApplicationSettingsConfigurable";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "CodeGenX";
    }

    @Override
    public @Nullable JComponent createComponent() {
        mySettingsComponent = new ApplicationSettingsComponent();

        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();

        boolean modified = !mySettingsComponent.getMaxLengthText().equals(settings.maxLength);

        modified |= mySettingsComponent.getTemperatureText().equals(settings.temperature);
        modified |= mySettingsComponent.getTokenText().equals(settings.token);
        modified |= mySettingsComponent.getTopPText().equals(settings.topP);

        return modified;
    }

    @Override
    public void apply() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();

        settings.maxLength = mySettingsComponent.getMaxLengthText();
        settings.temperature = mySettingsComponent.getTemperatureText();
        settings.token = mySettingsComponent.getTokenText();
        settings.topP = mySettingsComponent.getTopPText();
    }

    @Override
    public void reset() {
        ApplicationSettingsState settings = ApplicationSettingsState.getInstance();

        mySettingsComponent.setMaxLengthText(settings.maxLength);
        mySettingsComponent.setTemperatureText(settings.temperature);
        mySettingsComponent.setTokenText(settings.token);
        mySettingsComponent.setTopPText(settings.topP);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
