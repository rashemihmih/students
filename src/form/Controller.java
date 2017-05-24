package form;

import api.Plugin;
import api.PluginApi;
import api.entity.Entity;
import api.entity.Group;
import api.entity.Root;
import api.entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import settings.Settings;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Controller implements PluginApi {
    public static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#.###");
    private static Controller instance;
    private Model model;
    private List<Plugin> plugins = new ArrayList<>();
    private Parent root;
    private Stage settingsStage = new Stage();
    @FXML
    private TreeView<Entity> treeView;
    @FXML
    private TabPane rootTabPane;
    @FXML
    private TabPane groupTabPane;
    @FXML
    private TabPane studentTabPane;
    @FXML
    private TextField tfAddGroupName;
    @FXML
    private TextField tfGroupInfoName;
    @FXML
    private TextField tfGroupInfoStudents;
    @FXML
    private TextField tfGroupMaxRating;
    @FXML
    private TextField tfGroupInfoAvgRating;
    @FXML
    private TextField tfGroupInfoMinRating;
    @FXML
    private TextField tfAddStudentLastName;
    @FXML
    private TextField tfAddStudentFirstName;
    @FXML
    private TextField tfAddStudentMiddleName;
    @FXML
    private TextField tfAddStudentRating;
    @FXML
    private TextField tfGroupNewName;
    @FXML
    private TextField tfStudentInfoLastName;
    @FXML
    private TextField tfStudentInfoFirstName;
    @FXML
    private TextField tfStudentInfoMiddleName;
    @FXML
    private TextField tfStudentInfoGroup;
    @FXML
    private TextField tfStudentInfoRating;
    @FXML
    private TextField tfEditStudentLastName;
    @FXML
    private TextField tfEditStudentFirstName;
    @FXML
    private TextField tfEditStudentMiddleName;
    @FXML
    private TextField tfEditStudentGroup;
    @FXML
    private TextField tfEditStudentRating;
    @FXML
    private Label headLabel;
    @FXML
    private Button buttonToggleHead;
    @FXML
    private Button buttonUndo;
    @FXML
    private Button buttonRedo;

    public Controller() {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Stage getSettingsStage() {
        return settingsStage;
    }

    @FXML
    private void initialize() {
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            Entity value = newValue.getValue();
            processSelect(value);
        });
        buttonUndo.setGraphic(new ImageView(new Image("/res/undo.png")));
        buttonRedo.setGraphic(new ImageView(new Image("/res/redo.png")));
        try {
            Parent settingsForm = FXMLLoader.load(getClass().getResource("/form/settings.fxml"));
            settingsStage.setTitle("Настройки");
            settingsStage.setScene(new Scene(settingsForm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSelect(Entity value) {
        switch (value.getType()) {
            case ROOT:
                plugins.forEach(plugin -> plugin.onRootSelected((Root) value));
                showRootPanel();
                break;
            case GROUP:
                Group group = (Group) value;
                plugins.forEach(plugin -> plugin.onGroupSelected(group));
                showGroupPanel();
                showGroupInfo(group);
                break;
            case STUDENT:
                Student student = (Student) value;
                plugins.forEach(plugin -> plugin.onStudentSelected(student));
                showStudentPanel();
                showStudentInfo(student);
                break;
        }
    }

    private void showRootPanel() {
        rootTabPane.setVisible(true);
        groupTabPane.setVisible(false);
        studentTabPane.setVisible(false);
    }

    private void showGroupPanel() {
        rootTabPane.setVisible(false);
        groupTabPane.setVisible(true);
        studentTabPane.setVisible(false);
    }

    private void showStudentPanel() {
        rootTabPane.setVisible(false);
        groupTabPane.setVisible(false);
        studentTabPane.setVisible(true);
    }

    private void showGroupInfo(Group group) {
        tfGroupInfoName.setText(group.getName());
        tfGroupInfoStudents.setText(String.valueOf(group.getStudentsCount()));
        tfGroupMaxRating.setText(String.valueOf(group.getMaxRating()));
        tfGroupInfoAvgRating.setText(NUMBER_FORMAT.format(group.getAvgRating()));
        tfGroupInfoMinRating.setText(String.valueOf(group.getMinRating()));
    }

    private void showStudentInfo(Student student) {
        tfStudentInfoLastName.setText(student.getLastName());
        tfStudentInfoFirstName.setText(student.getFirstName());
        tfStudentInfoMiddleName.setText(student.getMiddleName());
        tfStudentInfoGroup.setText(student.getGroup().getName());
        tfStudentInfoRating.setText(String.valueOf(student.getRating()));
        tfEditStudentLastName.setText(student.getLastName());
        tfEditStudentFirstName.setText(student.getFirstName());
        tfEditStudentMiddleName.setText(student.getMiddleName());
        tfEditStudentGroup.setText(student.getGroup().getName());
        tfEditStudentRating.setText(String.valueOf(student.getRating()));
        if (student.isHead()) {
            headLabel.setText("Студент является старостой");
            buttonToggleHead.setText("Убрать статус старосты");
        } else {
            headLabel.setText("");
            buttonToggleHead.setText("Сделать старостой");
        }
    }

    @FXML
    private void loadData() {
        File file = new FileChooser().showOpenDialog(null);
        if (file == null) {
            showDialog("Загрузка данных отменена", "Файл не выбран");
            return;
        }
        model.loadData(file);
    }

    @FXML
    private void saveData() {
        File file = new FileChooser().showSaveDialog(null);
        if (file == null) {
            showDialog("Сохранение данных отменено", "Файл не выбран");
            return;
        }
        model.saveData(file);
    }

    @FXML
    private void settings() {
        SettingsForm.getInstance().init();
        settingsStage.show();
    }

    @FXML
    private void undo() {
        model.undo();
    }

    @FXML
    private void redo() {
        model.redo();
    }

    @FXML
    private void addGroup() {
        String name = tfAddGroupName.getText();
        tfAddGroupName.clear();
        model.addGroup(name);
    }

    @FXML
    private void addStudent() {
        Group group = getSelectedGroup();
        if (group == null) {
            showDialog("Ошибка", "Группа не выбрана");
            return;
        }
        String lastName = tfAddStudentLastName.getText();
        tfAddStudentLastName.clear();
        String firstName = tfAddStudentFirstName.getText();
        tfAddStudentFirstName.clear();
        String middleName = tfAddStudentMiddleName.getText();
        tfAddStudentMiddleName.clear();
        String rating = tfAddStudentRating.getText();
        tfAddStudentRating.clear();
        try {
            model.addStudent(group, firstName, lastName, middleName, Integer.parseInt(rating));
        } catch (NumberFormatException e) {
            showDialog("Ошибка", "Рейтинг должен быть целым числом");
        }
    }

    @FXML
    private void renameGroup() {
        Group group = getSelectedGroup();
        if (group == null) {
            showDialog("Ошибка", "Группа не выбрана");
            return;
        }
        String name = tfGroupNewName.getText();
        tfGroupNewName.clear();
        model.renameGroup(group, name);
    }

    @FXML
    private void deleteGroup() {
        Group group = getSelectedGroup();
        if (group == null) {
            showDialog("Ошибка", "Группа не выбрана");
            return;
        }
        model.deleteGroup(group);
    }

    @FXML
    private void editStudent() {
        Student student = getSelectedStudent();
        if (student == null) {
            showDialog("Ошибка", "Студент не выбран");
            return;
        }
        String lastName = tfEditStudentLastName.getText();
        String firstName = tfEditStudentFirstName.getText();
        String middleName = tfEditStudentMiddleName.getText();
        String group = tfEditStudentGroup.getText();
        String rating = tfEditStudentRating.getText();
        try {
            model.editStudent(student, lastName, firstName, middleName, group, Integer.parseInt(rating));
        } catch (NumberFormatException e) {
            showDialog("Ошибка", "Рейтинг должен быть целым числом");
        }
        showStudentInfo(student);
    }

    @FXML
    private void toggleHead() {
        Student student = getSelectedStudent();
        if (student == null) {
            showDialog("Ошибка", "Студент не выбран");
            return;
        }
        model.toggleHead(student);
    }

    @FXML
    private void deleteStudent() {
        Student student = getSelectedStudent();
        if (student == null) {
            showDialog("Ошибка", "Студент не выбран");
            return;
        }
        model.deleteStudent(student);
    }

    public Group getSelectedGroup() {
        TreeItem<Entity> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return null;
        }
        try {
            return (Group) selectedItem.getValue();
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Student getSelectedStudent() {
        TreeItem<Entity> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return null;
        }
        try {
            return (Student) selectedItem.getValue();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public void showDialog(String header, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Сообщение");
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.show();
    }

    @Override
    public void update() {
        TreeItem<Entity> selectedItem = treeView.getSelectionModel().getSelectedItem();
        Root root = model.getRoot();
        TreeItem<Entity> rootItem = new TreeItem<>(root);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        for (Group group : root.getGroups()) {
            TreeItem<Entity> groupItem = new TreeItem<>(group);
            groupItem.setExpanded(true);
            rootItem.getChildren().add(groupItem);
            for (Student student : group.getStudents()) {
                groupItem.getChildren().add(new TreeItem<>(student));
            }
        }
        treeView.getSelectionModel().select(selectedItem);
        plugins.forEach(Plugin::onUpdate);
        checkUndoRedo();
    }

    private void checkUndoRedo() {
        buttonUndo.setDisable(!model.canUndo());
        buttonRedo.setDisable(!model.canRedo());
    }

    @Override
    public void addRootTab(Tab tab) {
        rootTabPane.getTabs().add(tab);
    }

    @Override
    public void addGroupTab(Tab tab) {
        groupTabPane.getTabs().add(tab);
    }

    @Override
    public void addStudentTab(Tab tab) {
        studentTabPane.getTabs().add(tab);
    }

    public void addPlugin(Plugin plugin) {
        plugin.plugIn(this);
        plugins.add(plugin);
    }

    public void updateSettings() {
        root.styleProperty().set("-fx-font-size: " + Settings.getFontSize());
    }
}
