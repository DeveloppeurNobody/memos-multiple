package javafx_multiple.ftp_web.app.ftp

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TreeItem
import javafx.scene.layout.*
import javafx_multiple.ftp_web.component.as_root.searchbar.FtpSearchBar
import javafx_multiple.ftp_web.component.as_view.remote.connect_account.ConnectAccount
import javafx_multiple.ftp_web.component.as_view.directory_tree.DirectoryTree
import javafx_multiple.ftp_web.component.as_view.main_view.MainView
import javafx_multiple.ftp_web.component.as_view.navigation_bar.NavigationBar
import javafx_multiple.ftp_web.component.as_view.path_finder.PathFinder
import org.controlsfx.control.BreadCrumbBar
import java.net.URL
import java.util.*


class FtpWeb : Initializable {
    
    companion object {
        /**
         * Loader of fxml to avoid external classes
         * to know and load manually path of fxml file
         */
        fun getView(): Region? {
            return FXMLLoader.load<Region>(javaClass.getResource("FtpWeb.fxml"));
        }
    }
    
    
    @FXML
    private var stackPane = StackPane();

    @FXML
    private var borderPane = BorderPane();

    @FXML
    private var sidenav = VBox();

    @FXML
    private var menuBtn = Button();

    @FXML
    private var localBreadCrumbBar = BreadCrumbBar<String>();

    @FXML
    private var containerLocal = HBox();

    @FXML
    private var anchorPaneRemote = AnchorPane();

    @FXML
    private var gridPaneLocal = GridPane();

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        menuBtn.onAction = EventHandler { changeTop() }

        val item11 = TreeItem("Item 1.1")
        localBreadCrumbBar.selectedCrumb = item11;

        containerLocal.children.add(0, FtpSearchBar());

        // main view
        val mainView = MainView.getView();
        BorderPane.setAlignment(mainView, Pos.BASELINE_CENTER);

        // adding navigationBar
        //containerLocalHBox.children.add(0, NavigationBar.getView());

        // components
        val navigationBar = NavigationBar.getView();
        val pathFinder = PathFinder.getView();
        val directoryTree = DirectoryTree.getView();
        val connectAccount = ConnectAccount.getView();



        // gridPane constraints
        GridPane.setRowSpan(navigationBar, 2);
        GridPane.setColumnIndex(pathFinder, 1);
        GridPane.setColumnIndex(directoryTree, 1);
        GridPane.setRowIndex(directoryTree, 1);

        // anchorPane constraints for remote server
        AnchorPane.setBottomAnchor(connectAccount,10.0)
        AnchorPane.setLeftAnchor(connectAccount,10.0)
        AnchorPane.setRightAnchor(connectAccount,10.0)
        AnchorPane.setTopAnchor(connectAccount,10.0);



        // adding components for gridPane
        gridPaneLocal.children.addAll(navigationBar, pathFinder, directoryTree);

        // adding components for anchorPane
        anchorPaneRemote.children.add(connectAccount);

        // main view
        borderPane.center = mainView;
    }

    private fun changeTop() {
        val childs = stackPane.children;
        if (childs.size > 1) {
            val topNode = childs.get(childs.size - 1);
            topNode.toBack();
        }
    }
}