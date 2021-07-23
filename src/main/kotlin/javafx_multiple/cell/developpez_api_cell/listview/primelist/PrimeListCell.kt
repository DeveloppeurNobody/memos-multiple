package javafx_multiple.cell.developpez_api_cell.listview.primelist

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.control.ListCell
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class PrimeListCell : ListCell<Int> {

    var renderer: Node? = null;
    var rendererController: PrimeListCellController? = null;

    constructor(application: Application): super() {
        // loading of fxml
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/PrimeListCell.fxml"));
            renderer = fxmlLoader.load() as Node;
            rendererController = fxmlLoader.getController() as PrimeListCellController;
            rendererController?.setApplication(application);
        } catch (ex: Exception) {
            println("#ex: ${ex.printStackTrace()}")
        }
    }

    override fun updateItem(item: Int?, empty: Boolean) {
        println("PrimeCell.updateItem()");
        super.updateItem(item, empty);
        var text: String? = null;
        var graphic: Node? = null;

        if (!empty && item != null) {
            // if it's a prime number, we use our fxml to render
            if (PrimeUtils.isPrime(item) && renderer != null)  graphic = renderer;
            else text = item.toString();
        }

        setText(text);
        setGraphic(graphic);
    }


}