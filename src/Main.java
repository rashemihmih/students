import api.Plugin;
import form.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import plugin.PluginLoader;
import settings.Settings;

import java.io.File;
import java.util.List;


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
        List<Plugin> plugins = PluginLoader.loadPlugins(new File("plugins"));
        for (Plugin plugin : plugins) {
            System.out.println("Подключаем плагин " + plugin);
            controller.addPlugin(plugin);
        }
        controller.update();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
