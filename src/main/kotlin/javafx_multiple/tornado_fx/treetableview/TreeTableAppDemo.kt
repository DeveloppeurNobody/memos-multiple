package javafx_multiple.tornado_fx.treetableview

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import tornadofx.*

fun main(args: Array<String>) {
    launch<TreeTableAppDemo>(*args)
}

class TreeTableAppDemo : App(TreeTableView::class)

class TreeTableView : View("Tree Table View Demo") {
    override val root = vbox {
        treetableview<Car> {

            val treeTableColumn01 = TreeTableColumn<Car, String>("Brand");
            treeTableColumn01.cellValueFactory = TreeItemPropertyValueFactory<Car, String>("brand")
            treeTableColumn01.minWidth = 100.0

            val treeTableColumn02 = TreeTableColumn<Car, String>("model");
            treeTableColumn02.cellValueFactory = TreeItemPropertyValueFactory<Car, String>("model")
            treeTableColumn02.minWidth = 200.0

            columns.add(treeTableColumn01)
            columns.add(treeTableColumn02)

            //=== DATA ===
            // child mercedes
            val mercedes01 = TreeItem<Car>(Car("Mercedes", "SL500"))
            val mercedes02 = TreeItem<Car>(Car("Mercedes", "SL500 AMG"))
            val mercedes03 = TreeItem<Car>(Car("Mercedes", "CLA 200"))

            // parent mercedes
            val mercedes = TreeItem<Car>(Car("Mercedes", "..."))
            mercedes.children.addAll(mercedes01, mercedes02, mercedes03)

            // child audi
            val audi01 = TreeItem<Car>(Car("Audi", "A1"));
            val audi02 = TreeItem<Car>(Car("Audi", "A5"));
            val audi03 = TreeItem<Car>(Car("Audi", "A7"));

            // parent audi
            val audi = TreeItem<Car>(Car("Audi", "..."));
            audi.children.addAll(audi01, audi02, audi03);

            // parent of mercedes and audi
            val cars = TreeItem<Car>(Car("Cars", ""));
            cars.children.addAll(audi, mercedes);

            root = cars;
        }
    }
}

data class Car(var brand: String = "", var model: String = "")