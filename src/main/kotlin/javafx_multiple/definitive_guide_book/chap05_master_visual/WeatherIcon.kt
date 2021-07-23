package javafx_multiple.definitive_guide_book.chap05_master_visual

import javafx.beans.property.BooleanProperty
import javafx.beans.property.BooleanPropertyBase
import javafx.css.*
import javafx.scene.control.Label
import javafx.scene.layout.Region
import java.util.function.Function


class WeatherIcon() : Label() {
    constructor(weatherType: WeatherType) : this() {
        weather = weatherType
    }

    private val dangerous: BooleanProperty = object : BooleanPropertyBase(false) {
        public override fun invalidated() {
            pseudoClassStateChanged(DANGEROUS_PSEUDO_CLASS, get())
        }

        override fun getBean(): Any {
            return this@WeatherIcon
        }

        override fun getName(): String {
            return PSEUDO_CLASS_NAME
        }
    }


    private val weatherTypeProperty: StyleableObjectProperty<WeatherType> =
        object : StyleableObjectProperty<WeatherType>(WeatherType.SUNNY) {
            override fun getCssMetaData(): CssMetaData<WeatherIcon?, WeatherType>? {
                return WEATHER_TYPE_METADATA
            }

            override fun getBean(): Any {
                return this@WeatherIcon
            }

            override fun getName(): String {
                return WEATHER_PROP_NAME
            }

            override fun invalidated() {
                val weatherType = get()
                dangerous.set(weatherType!!.isDangerous())
                graphic = weatherType.buildGraphic()
                text = get().toString()
            }
        }


    override fun getControlCssMetaData(): MutableList<CssMetaData<WeatherIcon?, WeatherType>?>? {
        return java.util.List.of(WEATHER_TYPE_METADATA)
    }

    fun weatherProperty(): WeatherType {
        return weatherTypeProperty.get()
    }

    var weather: WeatherType
        get() = weatherTypeProperty.get()
        set(weather) {
            weatherTypeProperty.set(weather)
        }

    companion object {
        private const val STYLE_CLASS = "weather-icon"
        private const val WEATHER_PROP_NAME = "-fx-weather"
        private const val PSEUDO_CLASS_NAME = "dangerous"
        private val DANGEROUS_PSEUDO_CLASS = PseudoClass.getPseudoClass(PSEUDO_CLASS_NAME)
        private val STYLEABLE_PROPERTY_FACTORY = StyleablePropertyFactory<WeatherIcon?>(Region.getClassCssMetaData())
        private val WEATHER_TYPE_METADATA: CssMetaData<WeatherIcon?, WeatherType>? =
            STYLEABLE_PROPERTY_FACTORY.createEnumCssMetaData(
                WeatherType::class.java, WEATHER_PROP_NAME,
                Function<WeatherIcon?, StyleableProperty<WeatherType>> { x: WeatherIcon? -> x!!.weatherTypeProperty })
    }

    init {
        styleClass.setAll(STYLE_CLASS)
    }
}

