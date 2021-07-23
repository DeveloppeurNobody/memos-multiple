package javafx_multiple.ftp_web.component.as_view.navigation_bar

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.Region
import java.net.URL
import java.util.*

class NavigationBar : Initializable {
    companion object {
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("NavigationBar.fxml"));
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}