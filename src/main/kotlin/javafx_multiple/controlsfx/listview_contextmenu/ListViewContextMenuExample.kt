package javafx_multiple.controlsfx.listview_contextmenu

import javafx.application.Application
import javafx.beans.binding.Bindings
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.layout.BorderPane
import javafx.stage.Stage


class ListViewContextMenuExample : Application() {
    override fun start(primaryStage: Stage) {
        val listView = ListView<String>()
        listView.items.addAll("One", "Two", "Three")
        listView.setCellFactory { lv: ListView<String>? ->
            val cell = ListCell<String>()
            val contextMenu = ContextMenu()
            val editItem = MenuItem()
            editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()))
            editItem.onAction = EventHandler { event: ActionEvent? ->
                val item = cell.item
            }
            val deleteItem = MenuItem()
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()))
            deleteItem.onAction = EventHandler { event: ActionEvent? ->
                listView.items.remove(cell.item)
            }
            contextMenu.items.addAll(editItem, deleteItem)
            cell.textProperty().bind(cell.itemProperty())
            cell.emptyProperty()
                .addListener { obs: ObservableValue<out Boolean>?, wasEmpty: Boolean?, isNowEmpty: Boolean ->
                    if (isNowEmpty) {
                        cell.setContextMenu(null)
                    } else {
                        cell.setContextMenu(contextMenu)
                    }
                }
            cell
        }
        val root = BorderPane(listView)
        primaryStage.scene = Scene(root, 250.0, 400.0)
        primaryStage.show()
    }


}