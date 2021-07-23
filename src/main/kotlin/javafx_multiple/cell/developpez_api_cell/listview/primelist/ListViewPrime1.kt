package javafx_multiple.cell.developpez_api_cell.listview.primelist

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.util.Callback

class ListViewPrime1 : Application() {

    override fun start(primaryStage: Stage?) {
        // populate list.
        val listView = ListView<Int>();
        repeat(3000) {
            listView.items.add(it);
        }

        // factory of cells
        listView.setCellFactory {
            object: ListCell<Int>() {
                override fun updateItem(item: Int?, empty: Boolean) {
                    super.updateItem(item, empty);
                    val text: String? = if (item == null || empty) null else item.toString();
                    setText(text);
                    var style: String? = null;
                    if (!empty && item != null && PrimeUtils.isPrime(item)) {
                        style = "-fx-font-weight: bold; -fx-text-fill: skyblue; -fx-underline: true;";
                    }
                    setStyle(style);
                }
            }
        }

        val root = StackPane();
        root.children.add(listView);
        var scene = Scene(root);

        primaryStage?.scene = scene;
        primaryStage?.setTitle("ListView avec nombres premiers");
        primaryStage?.width = 300.0;
        primaryStage?.height = 350.0;
        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(ListViewPrime1::class.java, *args);
}