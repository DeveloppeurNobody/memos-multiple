package javafx_multiple.ftp_web.component.as_root.searchbar

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.HBox

class FtpSearchBar : HBox() {

    init {
        try {
            val loader = FXMLLoader(javaClass.getResource("FtpSearchBar.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load<HBox>();

            children.add(Label("FtpSearchBar"));

        } catch (ex: Exception) {
            println("Error loading FtpSearchBar.fxml #ex: ${ex.printStackTrace()}");
        }
    }
}