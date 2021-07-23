package javafx_multiple.cell.my_treetableview_scratch_actions

import javafx.scene.control.TreeTableRow
import javafx.scene.control.TreeTableView
import javafx.scene.control.cell.TextFieldTreeTableCell
import javafx.util.Callback

class MyTreeTableRowFactory : Callback<TreeTableView<Fichier>, TreeTableRow<Fichier>> {

    var treeTableView: TreeTableView<Fichier>? = null;

    override fun call(treeTableView: TreeTableView<Fichier>?): TreeTableRow<Fichier> {
        println("call() #treeTableView: ${treeTableView?.root?.value?.name}")
        this.treeTableView = treeTableView;
        return MyTreeTableRow(treeTableView);
    }

    override fun toString(): String {
        return super.toString();
    }
}