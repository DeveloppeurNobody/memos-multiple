package javafx_multiple.drag_and_drop

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.Stage

class SimpleDragDropFeatureApp : Application() {

    override fun start(primaryStage: Stage?) {
        primaryStage?.title = "Hello Drag and Drop";
        val root = Group();
        var scene = Scene(root, 400.0, 200.0);
        scene.fill = Color.LIGHTGREEN;

        val source = Text(50.0, 100.0, "DRAG ME");
        source.scaleX = 2.0;
        source.scaleY = 2.0;

        val target = Text(250.0, 100.0, "DROP HERE");
        target.scaleX = 2.0;
        target.scaleY = 2.0;

        source.setOnDragDetected {
            // drag was detected, start drag-and-drop gesture
            println("onDragDetected");

            // allow any transfer mode
            val dragboard = source.startDragAndDrop(TransferMode.MOVE);

            // put a string on dragboard
            val content = ClipboardContent();
            content.putString(source.text);
            dragboard.setContent(content);

            // update status of event
            it.consume();
        }

        target.setOnDragOver {
            // data is dragged over the target
            println("onDragOver");

            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (it.gestureSource != target && it.dragboard.hasString()) { it.acceptTransferModes(*TransferMode.COPY_OR_MOVE); }

            it.consume();
        }

        target.setOnDragEntered {
            // the drag-and-drop gesture entered the target
            println("onDragEntered");
            // show to the user that it is an actual gesture target
            if (it.gestureSource != target && it.dragboard.hasString()) { target.fill = Color.GREEN; }
            it.consume();
        }

        target.setOnDragExited {
            // mouse moved away, remove the graphical cues
            target.fill = Color.BLACK;

            it.consume();
        }

        target.setOnDragDropped {
            // data dropped
            println("onDragDropped");
            // if there is a string data on dragboard, read it and use it
            val dragboard = it.dragboard;
            var success = false;
            if (dragboard.hasString()) {
                target.text = dragboard.string;
                success = true;
            }

            // let the source know whether the string was successfully transferred and used
            it.isDropCompleted = success;

            it.consume();
        }

        source.setOnDragDone {
            // the drag-and-drop gesture ended
            println("onDragDone");
            // if the data was successfully moved, clear it
            if (it.transferMode == TransferMode.MOVE) { source.text = ""; }

            it.consume();
        }

        root.children.addAll(source, target);
        primaryStage?.scene = scene
        primaryStage?.show();

    }
}

fun main(args: Array<String>) {
    Application.launch(SimpleDragDropFeatureApp::class.java, *args);
}