package form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import settings.Settings;

public class SettingsForm {
    private static SettingsForm instance;
    @FXML
    private TextField tfFontSize;

    public SettingsForm() {
        instance = this;
    }

    public static SettingsForm getInstance() {
        return instance;
    }

    public void init() {
        tfFontSize.setText(String.valueOf(Settings.getFontSize()));
    }

    @FXML
    private void save() {
        try {
            Settings.setFontSize(Integer.parseInt(tfFontSize.getText()));
            Settings.save();
        } catch (NumberFormatException ignore) {
        }
        Controller controller = Controller.getInstance();
        controller.getSettingsStage().hide();
        controller.updateSettings();
    }
}
