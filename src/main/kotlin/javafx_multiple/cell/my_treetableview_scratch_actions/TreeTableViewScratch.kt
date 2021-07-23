package javafx_multiple.cell.my_treetableview_scratch_actions

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback


class TreeTableViewScratch : Application() {

    override fun start(stage: Stage?) {
        val splitPane = SplitPane()


        splitPane.setPrefSize(800.0, 400.0)

        // create TreeTableView
        val leftTreeTableView =  TreeTableView<Fichier>();
        val rightTreeTableView = TreeTableView<Fichier>();
        //leftTreeTableView.setRowFactory { MyTreeTableRow() }
        leftTreeTableView.rowFactory = MyTreeTableRowFactory();
        //rightTreeTableView.setRowFactory { MyTreeTableRow() }
        rightTreeTableView.rowFactory = MyTreeTableRowFactory();

        // adding column to treeTableView
        addColumns(leftTreeTableView);
        addColumns(rightTreeTableView);


        // Creating the root node and add it to treeTableView
        addingRootNode(leftTreeTableView);
        addingRootNode(rightTreeTableView);


        // adding treeTableView to vbox;
        val leftControl = VBox(leftTreeTableView)
        val rightControl = VBox(rightTreeTableView)

        splitPane.items.addAll(leftControl, rightControl);

        val scene = Scene(splitPane);
        stage?.scene = scene
        stage?.show()
    }

    private fun addingRootNode(targetTreeTableView: TreeTableView<Fichier>) {
        val root = TreeItem<Fichier>(Fichier("/"))
        root.isExpanded = true

        // Creating the tree items that will be the first children of the root node
        // and the parent to the child nodes.
        val homeItem = TreeItem<Fichier>(Fichier("home"));
        homeItem.isExpanded = true;


        // Creating the tree items that will be the children of the parent
        // nodes.
        val childNode1 = TreeItem<Fichier>(Fichier("Desktop"));
        val childNode2 = TreeItem<Fichier>(Fichier("Documents"));
        val childNode3 = TreeItem<Fichier>(Fichier("Downloads"));

        homeItem.children.addAll(childNode1, childNode2, childNode3);

        // Adding tree items to the root
        root.children.add(homeItem)

        // adding root to treeTableView
        targetTreeTableView.root = root;

        // We set show root to false. This will hide the root and only show it's children in the treeview.
        targetTreeTableView.isShowRoot = false

    }


    fun addColumns(targetTreeTableView: TreeTableView<Fichier>) {
        targetTreeTableView.columnResizePolicy = TreeTableView.CONSTRAINED_RESIZE_POLICY;

        // name
        val treeTableColumnForName = TreeTableColumn<Fichier, Fichier>("Name");
        treeTableColumnForName.cellValueFactory = TreeItemPropertyValueFactory<Fichier, Fichier>("currentItem");
        treeTableColumnForName.cellFactory = FichierNameTreeTableCell.forTreeTableColumn();

        val treeTableColumnForActions = TreeTableColumn<Fichier, Fichier>("Actions");
        treeTableColumnForActions.cellValueFactory = TreeItemPropertyValueFactory<Fichier, Fichier>("currentItem");
        treeTableColumnForActions.cellFactory = FichierActionsTreeTableCell.forTreeTableColumn();
        treeTableColumnForActions.prefWidth = 100.0;

        targetTreeTableView.columns.addAll(treeTableColumnForName, treeTableColumnForActions);
    }

    class FichierNameTreeTableCell : TreeTableCell<Fichier, Fichier>() {

        override fun updateItem(item: Fichier?, empty: Boolean) {
            super.updateItem(item, empty);
            var text: String? = "";
            var graphicValue: Node? = null;

            if (item != null && !empty) {
                text = item.name;
                graphicValue = FontAwesomeIconView(FontAwesomeIcon.FILE)
            }

            setText(text);
            graphic = graphicValue;
        }

        companion object {
            fun forTreeTableColumn(): Callback<TreeTableColumn<Fichier, Fichier>, TreeTableCell<Fichier, Fichier>> {
                return Callback {
                    FichierNameTreeTableCell();
                }
            }
        }

    }

    class FichierActionsTreeTableCell : TreeTableCell<Fichier, Fichier>() {
        val downloadBtn = Button();
        val deleteBtn = Button();

        override fun updateItem(item: Fichier?, empty: Boolean) {
            super.updateItem(item, empty);
            var text: String? = "";
            var graphicValue: Node? = null;
            if (item != null && !empty) {

                val fichier = treeTableRow.item;


                val hbox = HBox(5.0);

                downloadBtn.graphic = FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
                deleteBtn.graphic = FontAwesomeIconView(FontAwesomeIcon.REMOVE);
                hbox.alignment = Pos.CENTER_LEFT;
                hbox.children.addAll(downloadBtn, deleteBtn);
                graphicValue = hbox;

                downloadBtn.setOnAction { println("clicked on #item: $item") }
            }

            setText(text);
            graphic = graphicValue;
        }

        companion object {
            fun forTreeTableColumn(): Callback<TreeTableColumn<Fichier, Fichier>, TreeTableCell<Fichier, Fichier>> {
                return Callback {  FichierActionsTreeTableCell(); }
            }
        }

    }
}