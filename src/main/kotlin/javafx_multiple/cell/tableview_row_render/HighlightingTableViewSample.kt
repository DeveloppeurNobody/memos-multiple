package javafx_multiple.cell.tableview_row_render

import javafx.application.Application
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.util.Callback


class HighlightingTableViewSample : Application(){
    val table = TableView<Person>();
    val data: ObservableList<Person> = FXCollections.observableArrayList(
        Person("Jacob", "Smith", "jacob.smith@example.com"),
        Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        Person("Ethan", "Williams", "ethan.williams@example.com"),
        Person("Emma", "Jones", "emma.jones@example.com"),
        Person("Michael", "Brown", "michael.brown@example.com")
    );

    override fun start(stage: Stage?) {
        val label = Label("Address Book");
        label.font = Font("Arial", 20.0);

        table.isEditable = true;

        val firstNameColumn = TableColumn<Person, String>("First Name");
        firstNameColumn.minWidth = 100.0;
        firstNameColumn.cellValueFactory = PropertyValueFactory<Person, String>("firstName");

        val lastNameColumn = TableColumn<Person, String>("Last Name");
        lastNameColumn.minWidth = 100.0;
        lastNameColumn.cellValueFactory = PropertyValueFactory<Person, String>("lastName");

        val emailColumn = TableColumn<Person, String>("Email");
        emailColumn.minWidth = 200.0;
        emailColumn.cellValueFactory = PropertyValueFactory<Person, String>("email");

        // columns
        table.columns.addAll(firstNameColumn, lastNameColumn, emailColumn);

        // data
        table.items = data;

        // adding custom factory
        val rowFactory = StyleChangingRowFactory<Person>("highlightRow");
        table.rowFactory = rowFactory;

        table.selectionModel.selectionMode = SelectionMode.MULTIPLE;

        val highlightButton = Button("Highlight");
        highlightButton.disableProperty().bind(Bindings.isEmpty(table.selectionModel.selectedIndices));
        highlightButton.setOnAction { rowFactory.styledRowIndices.setAll(table.selectionModel.selectedIndex); }

        val clearHighlightButton = Button("Clear Highlights")
        clearHighlightButton.disableProperty().bind(Bindings.isEmpty(rowFactory.styledRowIndices))
        clearHighlightButton.setOnAction { rowFactory.styledRowIndices.clear() }

        val buttons: HBox = HBox(5.0)
        buttons.alignment = Pos.CENTER
        buttons.children.addAll(highlightButton, clearHighlightButton)


        val vbox = VBox()
        vbox.spacing = 5.0
        vbox.padding = Insets(10.0, 0.0, 0.0, 10.0)
        vbox.children.addAll(label, table, buttons)

        val scene = Scene(vbox, 450.0, 500.0)
        stage!!.title = "Highlighting Table View Sample"
        scene.stylesheets.add(javaClass.getResource("/styles/highlightingTable.css").toExternalForm())

        stage!!.scene = scene
        stage!!.show()


    }

}

class Person(fName: String, lName: String, email: String) {
    private val firstName: StringProperty
    private val lastName: StringProperty
    private val email: StringProperty
    fun getFirstName(): String {
        return firstName.get()
    }

    fun setFirstName(fName: String) {
        firstName.set(fName)
    }

    fun firstNameProperty(): StringProperty {
        return firstName
    }

    fun getLastName(): String {
        return lastName.get()
    }

    fun setLastName(fName: String) {
        lastName.set(fName)
    }

    fun lastNameProperty(): StringProperty {
        return lastName
    }

    fun getEmail(): String {
        return email.get()
    }

    fun setEmail(fName: String) {
        email.set(fName)
    }

    fun emailProperty(): StringProperty {
        return email
    }

    init {
        firstName = SimpleStringProperty(this, "firstName", fName)
        lastName = SimpleStringProperty(this, "lastName", lName)
        this.email = SimpleStringProperty(this, "email", email)
    }
}

class StyleChangingRowFactory<T>(
    private val styleClass: String,
    private val baseFactory: Callback<TableView<T>, TableRow<T>>? = null
) : Callback<TableView<T>, TableRow<T>> {


    /**
     *
     * @return The list of indices of the rows to which `styleClass` will be applied.
     * Changes to the content of this list will result in the style class being immediately updated
     * on rows whose indices are either added to or removed from this list.
     */
    val styledRowIndices: ObservableList<Int> = FXCollections.observableArrayList()

    override fun call(tableView: TableView<T>): TableRow<T> {
        val row: TableRow<T> = if (baseFactory == null) {
            TableRow()
        } else {
            baseFactory.call(tableView)
        }
        row.indexProperty().addListener { obs, oldValue, newValue -> updateStyleClass(row) }
        styledRowIndices.addListener(ListChangeListener { updateStyleClass(row) })
        return row
    }

    private fun updateStyleClass(row: TableRow<T>) {
        val rowStyleClasses = row.styleClass
        if (styledRowIndices.contains(row.index)) {
            if (!rowStyleClasses.contains(styleClass)) {
                //rowStyleClasses.add(styleClass)
            }
        } else {
            // remove all occurrences of styleClass:
            rowStyleClasses.removeAll(listOf(styleClass));
        }
    }
    /**
     * Construct a `StyleChangingRowFactory`,
     * specifying the name of the style class that will be applied
     * to rows determined by `getStyledRowIndices`
     * and a base factory to create the `TableRow`. If `baseFactory`
     * is `null`, default table rows will be created.
     * @param styleClass The name of the style class that will be applied to specified rows.
     * @param baseFactory A factory for creating the rows. If null, default
     * `TableRow<T>`s will be created using the default `TableRow` constructor.
     */
}
