package javafx_multiple.controlsfx.popover

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.controlsfx.control.PopOver

class PopOverApp : Application() {
    override fun start(primaryStage: Stage) {

//        //Build PopOver look and feel
//        val lblName = Label("John Doe")
//        val lblStreet = Label("123 Hello Street")
//        val lblCityStateZip = Label("MadeUpCity, XX 55555")
//        val vBox = VBox(lblName, lblStreet, lblCityStateZip)
//        //Create PopOver and add look and feel
//        val popOver = PopOver(vBox)
//        val label = Label("Mouse mouse over me")
//        label.onMouseEntered = EventHandler { mouseEvent: MouseEvent? ->
//            //Show PopOver when mouse enters label
//            popOver.show(label)
//        }
//        label.onMouseExited = EventHandler { mouseEvent: MouseEvent? ->
//            //Hide PopOver when mouse exits label
//            popOver.hide()
//        }

        val button = Button("Click me");
        val label = Label("Hello");
        val checkBox = CheckBox("Show Hidden Files")
        val vbox = VBox(label, checkBox);
        vbox.spacing = 10.0
        vbox.padding = Insets(10.0);
        vbox.setPrefSize(150.0, 90.0);
        val popOver = PopOver(vbox);
        popOver.arrowLocation = PopOver.ArrowLocation.TOP_CENTER;

        button.setOnAction { popOver.show(button); }


        val root = StackPane()
        root.children.add(button)
        val scene = Scene(root, 300.0, 250.0)
        primaryStage.title = "Hello World!"
        primaryStage.scene = scene
        primaryStage.show()
    }
}