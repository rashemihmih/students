import api.Plugin;
import form.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import settings.Settings;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("form/form.fxml"));
        primaryStage.setTitle("Студенты");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Controller controller = Controller.getInstance();
        Model model = new Model(controller);
        controller.setModel(model);
        controller.setRoot(root);
        Settings.load();
        controller.updateSettings();
        for (Plugin plugin : PluginLoader.loadPlugins(new File("plugins"))) {
            System.out.println("Подключаем плагин " + plugin);
            plugin.plugIn(root, model.getRoot(), controller);
        }
        controller.update();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
