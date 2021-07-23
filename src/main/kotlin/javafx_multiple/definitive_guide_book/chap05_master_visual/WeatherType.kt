package javafx_multiple.definitive_guide_book.chap05_master_visual

import javafx.scene.text.Text

enum class WeatherType(private var c: String = "", private var dangerous: Boolean = false) {
    SUNNY("\uf00d", false),
    CLOUDY("\uf013", false),
    RAIN("\uf019", false),
    THUNDERSTORM("\uf033", true);

    fun isDangerous() = dangerous;

    fun buildGraphic(): Text {
        val text = Text(c);
        text.style = "-fx-font-family: 'Weather Icons Regular'; -fx-font-size: 25;"
        return text;
    }
}