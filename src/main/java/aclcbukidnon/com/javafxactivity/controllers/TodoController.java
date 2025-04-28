package aclcbukidnon.com.javafxactivity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TodoController {

    @FXML
    private ListView<String> todoList;

    private ObservableList<String> todoItems;

    @FXML
    public void initialize() {
        todoItems = FXCollections.observableArrayList("Remove Me");
        todoList.setItems(todoItems);
        todoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        onListEdit(newVal);
                    }
                }
        );
    }

    private void onListEdit(String selectedItem) {
        TextInputDialog dialog = new TextInputDialog(selectedItem);
        dialog.setTitle("Update Todo");
        dialog.setHeaderText("Edit your todo item:");
        dialog.setContentText("New name:");

        var result = dialog.showAndWait();
        result.ifPresent(newText -> {
            if (!newText.isEmpty()) {
                int index = todoItems.indexOf(selectedItem);
                if (index >= 0) {
                    todoItems.set(index, newText); // Update the list
                }
            }
        });
    }

    @FXML
    protected void onCreateClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Todo");
        dialog.setHeaderText("Add a new todo item:");
        dialog.setContentText("Enter your task:");

        var result = dialog.showAndWait();
        result.ifPresent(text -> {
            if (!text.isEmpty()) {
                todoItems.add(text); // Add new item
            }
        });
    }

    @FXML
    protected void onDeleteClick() {
        String selectedItem = todoList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("No Item Selected", "Please select a todo to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Confirmation");
        confirm.setHeaderText("Are you sure you want to delete this todo?");
        confirm.setContentText(selectedItem);

        var result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            todoItems.remove(selectedItem); // Remove the item from the list
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}