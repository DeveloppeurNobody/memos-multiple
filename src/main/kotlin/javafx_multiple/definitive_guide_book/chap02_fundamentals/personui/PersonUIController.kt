package javafx_multiple.definitive_guide_book.chap02_fundamentals.personui

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import java.net.URL
import java.util.*

class PersonUIController : Initializable {

    @FXML
    var firstnameTextfield = TextField();
    @FXML
    var lastnameTextField = TextField();
    @FXML
    var notesTextArea = TextArea();
    @FXML
    var removeButton = Button();
    @FXML
    var createButton = Button();
    @FXML
    var updateButton = Button();
    @FXML
    var listView = ListView<Person>();

    val personList: ObservableList<Person> = FXCollections.observableArrayList(Person.extractor);

    private var selectedPerson: Person = Person();
    private val modifiedProperty = SimpleBooleanProperty(false);
    private var personChangeListener: ChangeListener<Person>? = null;

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        removeButton
            .disableProperty()
            .bind(listView.selectionModel.selectedItemProperty().isNull);

        updateButton
            .disableProperty()
            .bind(
                listView.selectionModel.selectedItemProperty()
                    .isNull
                    .or(modifiedProperty.not())
                    .or(firstnameTextfield.textProperty().isEmpty)
            );

        createButton
            .disableProperty()
            .bind(
                listView.selectionModel.selectedItemProperty()
                    .isNull
                    .or(
                        firstnameTextfield.textProperty()
                            .isEmpty
                            .or(lastnameTextField.textProperty().isEmpty)
                    )
            );

        SampleData.fillSampleData(personList);

        // Use a sorted list; sort by lastname; then by firstname
        val sortedList = SortedList<Person>(personList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.comparator = Comparator { p1: Person, p2: Person ->
            var result = p1.getLastname().compareTo(p2.getLastname(), ignoreCase = true)
            if (result == 0) {
                result = p1.getFirstname().compareTo(p2.getFirstname(), ignoreCase = true)
            }
            result
        }

        listView.items = sortedList;

        //
        listView
            .selectionModel
            .selectedItemProperty()
            .addListener(
                ChangeListener<Person> { observable, oldValue, newValue ->
                    println("Selected item: $newValue");
                    // new value can be null if nothing is selected
                    val selectedPerson = newValue;
                    modifiedProperty.set(false);

                    if (newValue != null) {
                        // Populate controls with selected Person
                        firstnameTextfield.text = selectedPerson.getFirstname();
                        lastnameTextField.text = selectedPerson.getLastname();
                        notesTextArea.text = selectedPerson.getNotes()
                    } else {
                        firstnameTextfield.text = "";
                        lastnameTextField.text = "";
                        notesTextArea.text = "";
                    }

                }.also { personChangeListener = it }
            );

        // Pre-select the first item
        listView.selectionModel.selectFirst();
    }

    @FXML
    private fun handleKeyAction(keyEvent: KeyEvent) {
        modifiedProperty.set(true)
    }

    @FXML
    private fun createButtonAction(actionEvent: ActionEvent) {
        println("Create");
        val person = Person(firstnameTextfield.text, lastnameTextField.text, notesTextArea.text)
        personList.add(person)

        // and select it
        listView.selectionModel.select(person);
    }

    @FXML
    private fun removeButtonAction(actionEvent: ActionEvent) {
        println("Remove: $selectedPerson");
        personList.remove(selectedPerson);
    }

    @FXML
    private fun updateButtonAction(actionEvent: ActionEvent) {
        println("Update $selectedPerson");
        val person = listView.selectionModel.selectedItem;
        listView
            .selectionModel
            .selectedItemProperty()
            .removeListener(personChangeListener)

        with(person) {
            setFirstname(firstnameTextfield.text)
            setLastname(lastnameTextField.text)
            setNotes(notesTextArea.text)
        }

        listView
            .selectionModel
            .selectedItemProperty()
            .addListener(personChangeListener)

        modifiedProperty.set(false);
    }


}