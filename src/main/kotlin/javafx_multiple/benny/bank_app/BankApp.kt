package javafx_multiple.benny.bank_app

import com.dukescript.layouts.flexbox.FlexboxLayout
import com.dukescript.layouts.jfxflexbox.FlexBoxPane
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.stage.StageStyle

class BankApp : Application() {
    override fun start(primaryStage: Stage?) {
//        val root = FXMLLoader.load<Pane>(javaClass.getResource("/fxml/BankApp.fxml"));

        val flex = FlexBoxPane();
        flex.children.addAll(Label("left"), Label("center"), Label("right"));
        flex.style = "-fx-background-color: white;"
        flex.minHeight = 300.0
        flex.setJustifyContent(FlexboxLayout.JustifyContent.CENTER);
        flex.setAlignItems(FlexboxLayout.AlignItems.CENTER);


        val vbox = VBox(flex);
        vbox.style = "-fx-background-color: LIGHTBLUE;"
        var scene = Scene(vbox, 600.0, 600.0);

        primaryStage?.scene = scene;

        primaryStage?.show();

    }
}