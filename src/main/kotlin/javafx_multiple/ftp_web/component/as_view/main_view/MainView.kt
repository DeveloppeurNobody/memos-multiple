package javafx_multiple.ftp_web.component.as_view.main_view

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.Region
import java.net.URL
import java.util.*

class MainView : Initializable {
    companion object {
        /**
         * Loader of fxml to avoid external classes
         * to know and load manually path of fxml file
         */
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("MainView.fxml"));
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}