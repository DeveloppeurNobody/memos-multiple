package javafx_multiple.ftp_web.component.as_view.remote.connect_account

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.Region
import java.net.URL
import java.util.*

class ConnectAccount : Initializable {
    companion object {
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("ConnectAccount.fxml"));
        }
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}