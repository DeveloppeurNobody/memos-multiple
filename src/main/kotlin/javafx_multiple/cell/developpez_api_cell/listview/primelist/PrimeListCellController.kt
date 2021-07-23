package javafx_multiple.cell.developpez_api_cell.listview.primelist

import javafx.application.Application
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import tornadofx.ChangeListener
import java.lang.String
import java.net.URL
import java.util.*

class PrimeListCellController : Initializable {
    @FXML
    private var rootPane: AnchorPane? = null;

    @FXML
    private var gridPane: GridPane? = null;

    @FXML
    private var numberLabel: Label? = null;

    @FXML
    private var descriptionLabel: Label? = null;

    @FXML
    private var otherLabel: Label? = null;

    @FXML
    private var hyperLink: Hyperlink? = null;

    // value to display
    private var value: IntegerProperty = SimpleIntegerProperty(this, "value");

    fun setValue(value: Int) {
        this.value.set(value);
    }

    fun getValue(): Int = this.value.get();

    fun valueProperty(): IntegerProperty = value;

    // listener called when value changed
    val valueChangeListener = ChangeListener<Number> { observable, oldValue, newValue -> updateUI(newValue) }

    constructor() {
        value.addListener(valueChangeListener);
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        numberLabel?.text = null;
        descriptionLabel?.text = null;
        otherLabel?.text = null;
        hyperLink?.text = "En savoir plus...";
        hyperLink?.disableProperty()?.bind(applicationProperty().isNull);
        hyperLink?.visitedProperty()?.bind(valueProperty().lessThanOrEqualTo(500));
        hyperLink?.managedProperty()?.bind(hyperLink?.visibleProperty());
    }

    private fun updateUI(newValue: Number) {
        val vall = newValue.toInt();
        val numberText = vall.toString();
        val descriptionText = String.format(
            "Le nombre %d est un nombre premier.Il ne peut être divisé que par lui-même et par le nombre 1.",
            vall
        );
        val otherText = kotlin.String.format(
            "En binaire : %s\nEn octal : 0%s\nEn hexadécimal: 0x%s",
            Integer.toBinaryString(vall),
            Integer.toOctalString(vall),
            Integer.toHexString(vall)
        );
        numberLabel?.text = numberText;
        descriptionLabel?.text = descriptionText;
        otherLabel?.text = otherText;
    }

    @FXML
    private fun handleLink(actionEvent: ActionEvent) {
        val application = getApplication();
        val value = getValue();
        if (application != null && value != null) {
            val vall = getValue();
            val url = String.format("http://fr.wikipedia.org/wiki/%d (nombre)", vall);
            application.hostServices.showDocument(url);
        }
    }

    // reference to parent application
    val application: ObjectProperty<Application> = SimpleObjectProperty(this, "application");
    fun getApplication(): Application = application.get();
    fun setApplication(value: Application) = application.set(value);
    fun applicationProperty() = application;

}