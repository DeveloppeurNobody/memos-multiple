package javafx_multiple.ftp_web.component.as_view.path_finder

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import java.net.URL
import java.util.*

class PathFinder : Initializable {
    companion object {
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("PathFinder.fxml"));
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}