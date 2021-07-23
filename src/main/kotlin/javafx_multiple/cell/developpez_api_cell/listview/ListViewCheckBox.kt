package javafx_multiple.cell.developpez_api_cell.listview

import javafx.application.Application
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.stage.Stage
import javafx.util.Callback
import java.util.*
import java.util.prefs.Preferences

class ListViewCheckBox : Application() {
    override fun start(primaryStage: Stage?) {
        // access to preferences of user
        val prefs = Preferences.userNodeForPackage(this::class.java);

        // keys of preferences
        val bundle = ResourceBundle.getBundle("javafx_multiple.cell.developpez_api_cell.listview.string");
        val keys = arrayOf<String>(
            "file.overwrite.confirm",
            "file.save.auto",
            "load.last.file.on.open"
        );

        // properties associated to each key
        val properties: MutableMap<String, ObservableValue<Boolean>> = mutableMapOf();
        keys.forEach {
            val property = SimpleBooleanProperty();
            // initial value
            val value = prefs.getBoolean(it, false);
            property.set(value);
            properties.put(it, property);

            // save out value in case of modification
            property.addListener { observable, oldValue, newValue -> prefs.putBoolean(it, newValue) }
        }

        // getting value associated to the key
        val propertyAccessor = Callback<String, ObservableValue<Boolean>?> { key: String? -> properties[key] }
    }
}