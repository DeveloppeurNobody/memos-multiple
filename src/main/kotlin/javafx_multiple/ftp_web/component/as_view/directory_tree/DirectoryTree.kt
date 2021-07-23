package javafx_multiple.ftp_web.component.as_view.directory_tree

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.Region
import java.net.URL
import java.util.*

class DirectoryTree : Initializable {

    companion object {
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("DirectoryTree.fxml"));
        }
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}