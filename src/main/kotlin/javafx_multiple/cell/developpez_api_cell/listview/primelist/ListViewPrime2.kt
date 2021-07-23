package javafx_multiple.cell.developpez_api_cell.listview.primelist

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class ListViewPrime2 : Application() {
    override fun start(primaryStage: Stage?) {
        // populate the list
        val listView = ListView<Int>();
        repeat(3000) {
            listView.items.add(it);
        }

        // factory for cells
        listView.setCellFactory {
            PrimeListCell(this);
        }

        val root = StackPane();
        root.children.add(listView);
        var scene = Scene(root);
        primaryStage?.scene = scene;
        primaryStage?.title = "ListView avec nombres premiers";
        primaryStage?.width = 300.0;
        primaryStage?.height = 350.0;
        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(ListViewPrime2::class.java, *args);
}