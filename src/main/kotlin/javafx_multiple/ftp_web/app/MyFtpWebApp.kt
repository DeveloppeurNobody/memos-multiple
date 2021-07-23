package javafx_multiple.ftp_web.app

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import de.jensd.fx.glyphs.octicons.OctIcon
import de.jensd.fx.glyphs.octicons.OctIconView
import de.jensd.fx.glyphs.weathericons.WeatherIcon
import de.jensd.fx.glyphs.weathericons.WeatherIconView
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx_multiple.ftp_web.app.ftp.FtpWeb
import javafx_multiple.ftp_web.style.StyleEffects

class MyFtpWebApp : Application() {
    override fun start(primaryStage: Stage) {

        //var scene = Scene(FtpWeb.getView());

        //var scene = Scene(getTextBox());

        var scene = Scene(getPaneForIcons());

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
                    menuItem.setOnAction { textField.text = menuItem.text;  }
                    contextMenu.items.add(menuItem);
                }
                contextMenu.show(textField, Side.BOTTOM, 0.0, 0.0);
            }
        }



        println("""
            #layoutX: ${textField.layoutX}
            #layoutY: ${textField.layoutY}
        """.trimIndent());


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

        with (vbox) {
            padding = Insets(10.0);
            alignment = Pos.CENTER;
            style = StyleEffects.BACKGROUND_NEUTRAL_GREY;
            prefHeight = 400.0;
            prefWidth = 500.0;
        }

        vbox.children.addAll(vboxTextContainer, labelBelow);

        return vbox;
    }

    private fun getListBox(): VBox {
        ////
        var list = ListView<String>();
        var data = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown"
        )


        val box = VBox()

        list.toBack();
        list.isVisible = false;
        box.children.addAll(list)
        VBox.setVgrow(list, Priority.ALWAYS)

        list.setItems(data)
        list.setCellFactory { ColorRectCell() }
        list.selectionModel.selectedItemProperty().addListener { ov, old_val, new_val ->
           // textField.text = new_val;
        }

        return box;
    }

    private fun getPaneForIcons(): ScrollPane {
        val scrollPane = ScrollPane();

        val vbox = VBox();

   //     loadIcons("MaterialDesignIcon", vbox);
//        loadIcons("MaterialIcon", vbox);
        loadIcons("FontAwesomeIcon", vbox);
        //loadIcons("OctIcon", vbox);
        //loadIcons("WeatherIcon", vbox);

        vbox.spacing = 5.0;
        vbox.padding = Insets(10.0);

        scrollPane.content = vbox;


     return scrollPane
    }

    private fun loadIcons(name: String = "", vbox: VBox) {
        when (name) {
            "MaterialDesignIcon" -> {
                MaterialDesignIcon
                    .values()
                    .forEach {
                        val materialDesignIconView = MaterialDesignIconView(it)
                        materialDesignIconView.glyphSize = 18.0;
                        val label = Label(it.name);
                        label.graphic = materialDesignIconView;
                        vbox.children.add(label);
                    }
            }
            "MaterialIcon" -> {
                MaterialIcon
                    .values()
                    .forEach {
                        val materialIconView = MaterialIconView(it)
                        materialIconView.glyphSize = 18.0;
                        val label = Label(it.name);
                        label.graphic = materialIconView;
                        vbox.children.add(label);
                    }
            }
            "FontAwesomeIcon" -> {
                FontAwesomeIcon
                    .values()
                    .forEach {
                        val fontAwesomeIconView = FontAwesomeIconView(it)
                        fontAwesomeIconView.glyphSize = 18.0;
                        val label = Label(it.name);
                        label.graphic = fontAwesomeIconView;
                        vbox.children.add(label);
                    }
            }
            "OctIcon" -> {
                OctIcon
                    .values()
                    .forEach {
                        val octIconView = OctIconView(it)
                        octIconView.glyphSize = 18.0;
                        val label = Label(it.name);
                        label.graphic = octIconView;
                        vbox.children.add(label);
                    }
            }
            "WeatherIcon" -> {
                WeatherIcon
                    .values()
                    .forEach {
                        val weatherIcon = WeatherIconView(it)
                        weatherIcon.glyphSize = 18.0;
                        val label = Label(it.name);
                        label.graphic = weatherIcon;
                        vbox.children.add(label);
                    }
            }

            else -> arrayOf("");
        }
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

