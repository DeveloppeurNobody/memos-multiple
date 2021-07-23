package javafx_multiple.cell.developpez_api_cell.tableview

import javafx.application.Application
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener


class TableViewPropertiesApp01 : Application() {
    override fun start(primaryStage: Stage?) {
        val tableView = TableView<Truc>();
        tableView.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY;

        // construction of table
        val visibleColumn: TableColumn<Truc, Boolean> = TableColumn("Visible");
        val nameColumn: TableColumn<Truc, String> = TableColumn("Nom");
        val opaqueColumn: TableColumn<Truc, Boolean> = TableColumn("Opaque");
        val commentColumn: TableColumn<Truc, String> = TableColumn("Commentaires");
        val administratorColumn: TableColumn<Truc, Boolean> = TableColumn("Administrateur");
        val emailColumn: TableColumn<Truc, String> = TableColumn("Email");

        // adding columns
        tableView
            .columns
            .setAll(visibleColumn, nameColumn, opaqueColumn, commentColumn, administratorColumn, emailColumn);

        // visible property
        visibleColumn.cellValueFactory = PropertyValueFactory("visible");
        // name property
        nameColumn.cellValueFactory = PropertyValueFactory("name");

        //===== JAVABeans Properties
        // opaque property
        opaqueColumn.setCellValueFactory {
            val truc = it.value;
            try {
                JavaBeanBooleanPropertyBuilder
                    .create()
                    .bean(truc)
                    .name("opaque")
                    .build();
            } catch (ex: Exception) {
                println("Error, #ex: ${ex.message}");
                null;
            }
        }

        commentColumn.setCellValueFactory {
            val truc = it.value;
            try {
                JavaBeanStringPropertyBuilder
                    .create()
                    .bean(truc)
                    .name("comment")
                    .build();
            } catch (ex: Exception) {
                println("Error #ex: ${ex.message}");
                null;
            }
        }

        //=== SIMPLE PROPERTIES
        // administrateur
        administratorColumn.setCellValueFactory {
            val truc = it.value;
            val result = SimpleBooleanProperty(truc.administrator);
            result.addListener { _, _, newValue -> truc.administrator = newValue }
            result;
        }
        // email
        emailColumn.setCellValueFactory {
            val truc = it.value;
            SimpleStringProperty(truc.email);
        }

        //=== CELL FACTORIES
        visibleColumn.cellFactory = CheckBoxTableCell.forTableColumn(visibleColumn);
        opaqueColumn.cellFactory = CheckBoxTableCell.forTableColumn(opaqueColumn);
        administratorColumn.cellFactory = CheckBoxTableCell.forTableColumn(administratorColumn);
        administratorColumn.setOnEditCommit { event: TableColumn.CellEditEvent<Truc, Boolean> ->
            println("Administrator ${event.oldValue} -> ${event.newValue}");
        }
        nameColumn.cellFactory = TextFieldTableCell.forTableColumn();
        commentColumn.cellFactory = TextFieldTableCell.forTableColumn();
        emailColumn.cellFactory = TextFieldTableCell.forTableColumn();
        emailColumn.setOnEditCommit { it: TableColumn.CellEditEvent<Truc, String> ->
            println("Email ${it.oldValue} -> ${it.newValue}")
            val truc = it.rowValue;
            truc.email = it.newValue;
        }

        visibleColumn.isEditable = true;
        nameColumn.isEditable = true;
        opaqueColumn.isEditable = true;
        commentColumn.isEditable = true;
        administratorColumn.isEditable = true;
        emailColumn.isEditable = true;
        tableView.isEditable = true;
        ////////////////////////////////////////////////////////////////////////
        val root = StackPane()
        root.children.add(tableView)
        val scene = Scene(root, 500.0, 150.0)
        primaryStage!!.title = "TableView"
        primaryStage.scene = scene

        val truc = Truc();
        truc.setVisible(true);
        truc.setName("foo");
        truc.setOpaque(true);
        truc.setComment("Foo alors !");
        truc.administrator = false;
        truc.email = "foo@foo.foo";

        tableView.items.add(truc);

        truc.visibleProperty().addListener { observable, oldValue, newValue -> println("Visible $oldValue -> $newValue") }
        truc.nameProperty().addListener { observable, oldValue, newValue -> println("Nom $oldValue -> $newValue") }

        truc.addPropertyChangeListener("opaque", PropertyChangeListener { println("opaque ${it.oldValue} -> ${it.newValue}"); });
        truc.addPropertyChangeListener("comment", PropertyChangeListener { println("comment ${it.oldValue} -> ${it.newValue}"); });


        primaryStage.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(TableViewPropertiesApp01::class.java, *args);
}