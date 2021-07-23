package javafx_multiple.cell.developpez_api_cell.listview


import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.util.Callback

class ListViewCellWithPrintln : Application() {

    override fun start(primaryStage: Stage?) {
        val listView: ListView<Int> = ListView();

        // populate items of list
        repeat(3000) { listView.items.add(it); }

        var created: Int = 0;

        // factory for cells
        listView.cellFactory = Callback<ListView<Int>, ListCell<Int>> {

            // in Callback.call()
            created++;
            println("========================== Created $created");

            object : ListCell<Int>() {
                var called = 0;

                override fun updateItem(item: Int?, empty: Boolean) {
                    val cellIndex = created;
                    called++;
                    println("Called #cellIndex: $cellIndex #called: $called");

                    super.updateItem(item, empty);
                    val text: String? = if(item == null || empty) null else item.toString();
                    setText(text);
                }
            }
        }

        // prepare view
        val root = StackPane();
        root.children.add(listView);
        var scene = Scene(root);

        primaryStage?.scene = scene;
        primaryStage?.title = "ListView";
        primaryStage?.width = 500.0;
        primaryStage?.height = 500.0;
        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(ListViewCellWithPrintln::class.java, *args);
}