package model;

import data.loader.DataLoaderFactory;
import data.loader.FileDataLoader;
import data.saver.JsonDataSaver;
import form.Controller;
import model.command.*;
import model.tree.Group;
import model.tree.Root;
import model.tree.Student;

import java.io.File;
import java.io.IOException;

public class Model {
    private Controller controller;
    private Root root = new Root();
    private Invoker invoker = new Invoker();

    public Model(Controller controller) {
        this.controller = controller;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public void loadData(File file) {
        FileDataLoader dataLoader = new DataLoaderFactory().getDataLoader(file);
        if (dataLoader == null) {
            controller.showDialog("Ошибка", "Данный тип файла не поддерживается");
            return;
        }
        try {
            root = dataLoader.loadData();
            controller.update();
        } catch (IOException e) {
            controller.showDialog("Не удалось прочитать файл", e.getMessage());
        } catch (RuntimeException e) {
            controller.showDialog("Ошибка при чтении данных", e.getMessage());
        }
    }

    public void saveData(File file) {
        try {
            new JsonDataSaver().saveData(root, file);
            controller.showDialog("Данные сохранены", "");
        } catch (IOException e) {
            controller.showDialog("Не удалось сохранить данные", e.getMessage());
        }
    }

    public void addGroup(String name) {
        if (root.containsGroup(name)) {
            controller.showDialog("Не удалось добавить группу", "Группа с таким названием уже существует");
            return;
        }
        try {
            Group group = new Group(name);
            invoker.execute(new AddGroupCommand(root, group));
            controller.update();
        } catch (IllegalArgumentException e) {
            controller.showDialog("Не удалось добавить группу", e.getMessage());
        }
    }

    public void addStudent(Group group, String firstName, String lastName, String middleName, int rating) {
        try {
            Student student = new Student(firstName, lastName, middleName, rating, group, false);
            invoker.execute(new AddStudentCommand(student));
            controller.update();
        } catch (IllegalArgumentException e) {
            controller.showDialog("Не удалось добавить студента", e.getMessage());
        }
    }

    public void renameGroup(Group group, String name) {
        if (root.containsGroup(name)) {
            controller.showDialog("Не удалось переименовать группу", "Группа с таким названием уже есть");
            return;
        }
        try {
            invoker.execute(new RenameGroupCommand(group, name));
            controller.update();
        } catch (IllegalArgumentException e) {
            controller.showDialog("Не удалось переименовать группу", e.getMessage());
        }
    }

    public void deleteGroup(Group group) {
        if (!root.containsGroup(group)) {
            controller.showDialog("Не удалось удалить группу", "Группы не существует");
            return;
        }
        invoker.execute(new DeleteGroupCommand(root, group));
        controller.update();
    }

    public void editStudent(Student student, String lastName, String firstName, String middleName, String group, int rating) {
        try {
            invoker.execute(new EditStudentCommand(root, student, firstName, lastName, middleName, rating, group));
            controller.update();
        } catch (IllegalArgumentException e) {
            controller.showDialog("Не удалось изменить данные студента", e.getMessage());
        }
    }

    public void toggleHead(Student student) {
        invoker.execute(new ToggleHeadCommand(student));
        controller.update();
    }

    public void deleteStudent(Student student) {
        invoker.execute(new DeleteStudentCommand(student));
        controller.update();
    }

    public void undo() {
        if (!canUndo()) {
            controller.showDialog("Невозможно откатить последнее действие", "");
            return;
        }
        invoker.undo();
        controller.update();
    }

    public void redo() {
        if (!canRedo()) {
            controller.showDialog("Невозможно повторно выполнить отмененное действие", "");
            return;
        }
        invoker.redo();
        controller.update();
    }

    public boolean canUndo() {
        return invoker.canUndo();
    }

    public boolean canRedo() {
        return invoker.canRedo();
    }
}
