package javafx_multiple.dialog

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class ConfirmationDialog : Application() {

    var label: Label = Label();

    private fun showConfirmation() {
        val alert = Alert(Alert.AlertType.CONFIRMATION);
        with (alert) {
            title = "Delete file";
            headerText = "Are you sure want to move this file to the Recycle Bin ?";
            contentText = "C:/MyFile.txt";
        }

        var option = alert.showAndWait();

        when {
            option.get() == null -> label.text = "No selection !";
            option.get() == ButtonType.OK -> label.text = "File deleted !";
            option.get() == ButtonType.CANCEL -> label.text = "Cancelled !";
            else -> label.text = "-";
        }
    }

    override fun start(primaryStage: Stage?) {
        val root = VBox(10.0);
        root.padding = Insets(10.0);

        label = Label();

        val button = Button("Click here to delete file");
        button.setOnAction { showConfirmation(); }

        root.children.addAll(button, label);

        var scene = Scene(root, 450.0, 250.0);
        primaryStage?.title = "JavaFX Confirmation Alert";
        primaryStage?.scene = scene;

        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(ConfirmationDialog::class.java, *args);
}