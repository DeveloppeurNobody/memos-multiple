package javafx_multiple.cell.developpez_api_cell.tableview

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class Truc {
    //=== Properties JavaFX
    // visible
    private val visible: BooleanProperty = SimpleBooleanProperty();
    fun isVisible() = visible.get();
    fun setVisible(value: Boolean) = visible.set(value);
    fun visibleProperty() = visible;
    // name
    private val name: StringProperty = SimpleStringProperty();
    fun getName() = name.get();
    fun setName(value: String) = name.set(value);
    fun nameProperty() = name;

    //=== Properties JavaBeans observables.
    // propertyChangeSupport
    private val propertyChangeSupport = PropertyChangeSupport(this);

    fun addPropertyChangeListener(property: String, listener: PropertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(property, listener);
    }

    fun removePropertyChangeListener(property: String, listener: PropertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(property, listener);
    }

    fun firePropertyChange(property: String, oldValue: Any, newValue: Any) {
        propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
    }

    private var opaque = false;
    fun isOpaque() = opaque;
    fun setOpaque(value: Boolean) {
        val oldValue = opaque;
        opaque = value;
        propertyChangeSupport.firePropertyChange("opaque", oldValue, opaque);
    }

    private var comment = "";
    fun getComment() = comment;
    fun setComment(value: String) {
        val oldValue = comment;
        comment = value;
        propertyChangeSupport.firePropertyChange("comment", oldValue, comment);
    }

    // others
    var administrator = false;
    var email = "";
}