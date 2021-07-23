package javafx_multiple.textfield.textfield_with_contextmenu

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx_multiple.ftp_web.style.StyleEffects

class TextFieldWithContextMenuSample : Application() {
    override fun start(primaryStage: Stage) {

        var scene = Scene(getTextBox());

        primaryStage.scene = scene;
        //  primaryStage.isMaximized = true;
        primaryStage.show();
    }

    private fun getTextBox(): VBox {
        val vboxTextContainer = VBox();

        val textField = TextField();
        val contextMenu = ContextMenu();
        var data = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown"
        )

        textField.setOnKeyPressed { event: KeyEvent ->
            if (event.code == KeyCode.DOWN) {
                data.forEach {
                    val menuItem = MenuItem(it);
                    menuItem.style = "-fx-pref-width: ${textField.width};"
                    menuItem.graphic = MaterialDesignIconView(MaterialDesignIcon.HISTORY);
                    menuItem.setOnAction { textField.text = menuItem.text; }
                    contextMenu.items.add(menuItem);
                }
                contextMenu.show(textField, Side.BOTTOM, 0.0, 0.0);
            }
        }


        println(
            """
            #layoutX: ${textField.layoutX}
            #layoutY: ${textField.layoutY}
        """.trimIndent()
        );


//        textField.setOnKeyPressed { event: KeyEvent ->
//            if (event.code == KeyCode.DOWN) {
//                list.isVisible = true;
//                list.toFront();
//                list.requestFocus();
//            }
//
//        }


        vboxTextContainer.children.addAll(textField);

        val labelBelow = Label("Below");
        val vbox = VBox();

        with(vbox) {
            padding = Insets(10.0);
            alignment = Pos.CENTER;
            style = StyleEffects.BACKGROUND_NEUTRAL_GREY;
            prefHeight = 400.0;
            prefWidth = 500.0;
        }

        vbox.children.addAll(vboxTextContainer, labelBelow);

        return vbox;
    }
}