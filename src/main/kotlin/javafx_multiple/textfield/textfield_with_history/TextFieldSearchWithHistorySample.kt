package javafx_multiple.textfield.textfield_with_history

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx_multiple.ftp_web.style.StyleEffects

class TextFieldSearchWithHistorySample : Application() {
    override fun start(primaryStage: Stage) {

        var scene = Scene(getTextBox());

        primaryStage.scene = scene;
        //  primaryStage.isMaximized = true;
        primaryStage.show();
    }


    private fun getTextBox(): VBox {
        val vbox = VBox();

        with(vbox) {
            padding = Insets(10.0);
            alignment = Pos.CENTER;
            style = StyleEffects.BACKGROUND_NEUTRAL_GREY;
            prefHeight = 400.0;
            prefWidth = 500.0;
        }

        val textFieldSearch = TextFieldSearch("Hello");
        //textFieldSearch.text = "Hi";

        vbox.children.addAll(textFieldSearch);

        return vbox;
    }

    inner class TextFieldSearch : TextField {
        constructor(): super() {}
        constructor(textArg: String = ""): super(textArg) {}

    }
}