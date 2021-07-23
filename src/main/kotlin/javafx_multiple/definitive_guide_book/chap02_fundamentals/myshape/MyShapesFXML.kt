package javafx_multiple.definitive_guide_book.chap02_fundamentals.myshape

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage


class MyShapesFXML : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
       val root = FXMLLoader.load<Parent>(javaClass.getResource("/fxml/ShapeScene.fxml"))
        val scene = Scene(root, Color.LIGHTYELLOW)
       scene.stylesheets.add(javaClass.getResource("/styles/ShapeStyle.css").toExternalForm());
        stage.title = "MyShapesApp with JavaFX"
        stage.scene = scene
        stage.show()
//
        println("""
            #javaClass() ${javaClass}
            #getResource(): ${javaClass.classLoader.getResource("")}
        """.trimIndent())
    }
}

