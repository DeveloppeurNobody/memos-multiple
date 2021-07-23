package javafx_multiple.list_view

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage


class ListViewSample : Application() {
    var list = ListView<String>()
    var data = FXCollections.observableArrayList(
        "chocolate", "salmon", "gold", "coral", "darkorchid",
        "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
        "blueviolet", "brown"
    )
    val label = Label()
    override fun start(stage: Stage) {
        val box = VBox()
        val scene = Scene(box, 200.0, 200.0)
        stage.scene = scene
        stage.title = "ListViewSample"
        box.children.addAll(list, label)
        VBox.setVgrow(list, Priority.ALWAYS)
        label.layoutX = 10.0
        label.layoutY = 115.0
        label.font = Font.font("Verdana", 20.0)
        list.setItems(data)
        list.setCellFactory { ColorRectCell() }
        list.selectionModel.selectedItemProperty().addListener { ov, old_val, new_val ->
            label.text = new_val
            label.textFill = Color.web(new_val)
        }
        stage.show()
    }

    internal class ColorRectCell : ListCell<String?>() {
        override fun updateItem(item: String?, empty: Boolean) {
            super.updateItem(item, empty)
            val rect = Rectangle(100.0, 20.0)
            if (item != null) {
                rect.fill = Color.web(item)
                graphic = rect
            }
        }
    }
}