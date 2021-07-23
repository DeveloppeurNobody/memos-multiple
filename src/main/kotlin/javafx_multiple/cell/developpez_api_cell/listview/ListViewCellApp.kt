package javafx_multiple.cell.developpez_api_cell.listview

import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import javafx.util.Callback

class ListViewCellApp : Application() {

    override fun start(primaryStage: Stage?) {
        val listView: ListView<Int> = ListView();

        // populate items of list
        repeat(6) { listView.items.add(it); }

        // factory for cells
        listView.cellFactory = Callback<ListView<Int>, ListCell<Int>> {
            object : ListCell<Int>() {
                override fun updateItem(item: Int?, empty: Boolean) {
                    super.updateItem(item, empty);
                    val text: String? = if (item == null || empty) null else item.toString();
                    setText(text);
                    val bdColor = if (empty) "gray" else { if (index % 2 == 0) "red" else "blue" }
                    val bgColor = if (empty) "lightgray" else { if (index % 2 == 0) "tomato" else "cadetblue" }
                    val style = String.format("-fx-background-color: %s; -fx-border-color: %s;", bgColor, bdColor);
                    setStyle(style);
                }
            }
        }

        // control of orientation
        val toolbar = ToolBar();
        val toggleGroup = ToggleGroup();
        Orientation
            .values()
            .forEach {
                val button: RadioButton = RadioButton(it.name);
                with (button) {
                    properties.put("orientation", it);
                    isSelected = it == listView.orientation;
                    setToggleGroup(toggleGroup);
                }
                toolbar.items.add(button);
            }
        toggleGroup.selectedToggleProperty().addListener { observable, oldValue, newValue ->
            val button: RadioButton = toggleGroup.selectedToggle as RadioButton;
            val orientation: Orientation = button.properties.get("orientation") as Orientation;
            listView.orientation = orientation;
        }

        // prepare view
        val root = BorderPane();
        root.center = listView;
        root.top = toolbar;
        var scene = Scene(root);

            primaryStage?.title = "ListView";
            primaryStage?.width = 300.0;
            primaryStage?.height = 350.0;
            primaryStage?.scene = scene;
            primaryStage?.show();

    }
}

fun main(args: Array<String>) {
    Application.launch(ListViewCellApp::class.java, *args);
}