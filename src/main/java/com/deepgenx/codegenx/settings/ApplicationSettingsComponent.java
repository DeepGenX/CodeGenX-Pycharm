package com.deepgenx.codegenx.settings;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Terry Jia
 */
public class ApplicationSettingsComponent {

    private JPanel myMainPanel;
    private JTextField maxLengthText;
    private JTextField temperatureText;
    private JTextField tokenText;
    private JTextField topPText;

    public ApplicationSettingsComponent() {
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    @NotNull
    public String getMaxLengthText() {
        return maxLengthText.getText();
    }

    public void setMaxLengthText(@NotNull String newText) {
        maxLengthText.setText(newText);
    }

    public String getTemperatureText() {
        return temperatureText.getText();
    }

    public void setTemperatureText(@NotNull String newText) {
        temperatureText.setText(newText);
    }

    public String getTokenText() {
        return tokenText.getText();
    }

    public void setTokenText(@NotNull String newText) {
        tokenText.setText(newText);
    }

    public String getTopPText() {
        return topPText.getText();
    }

    public void setTopPText(@NotNull String newText) {
        topPText.setText(newText);
    }

}
