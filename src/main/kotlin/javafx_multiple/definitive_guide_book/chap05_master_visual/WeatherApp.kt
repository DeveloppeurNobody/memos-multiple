package javafx_multiple.definitive_guide_book.chap05_master_visual

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage


class WeatherApp : Application() {

    override fun start(primaryStage: Stage) {
        val rain = WeatherIcon()
        rain.styleClass.add("rain")
        val thunderstorm = WeatherIcon()
        thunderstorm.styleClass.add("thunderstorm")
        val clouds = WeatherIcon(WeatherType.CLOUDY)
        val vbox = VBox();
        with (vbox) {
            spacing = 10.0;
            children.addAll(rain, thunderstorm, clouds)
        }

        val root = vbox
        root.alignment = Pos.CENTER
        val scene = Scene(root)
        scene.stylesheets.add(javaClass.getResource("styles.css").toExternalForm())
        primaryStage.title = "WeatherType Application"
        primaryStage.scene = scene
        primaryStage.show()
    }
}