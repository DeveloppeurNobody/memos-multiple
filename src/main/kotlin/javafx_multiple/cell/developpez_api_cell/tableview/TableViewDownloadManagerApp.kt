package javafx_multiple.cell.developpez_api_cell.tableview

import javafx.animation.PauseTransition
import javafx.animation.SequentialTransition
import javafx.application.Application
import javafx.beans.binding.DoubleBinding
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.util.Duration
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Test qui simule un gestionnaire de téléchargements.
 */
class TableViewDownloadManagerApp : Application() {
    override fun start(primaryStage: Stage) {
        val tableView = TableView<Download>()
        tableView.isEditable = false
        tableView.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
        tableView.setRowFactory { p: TableView<Download>? -> DownloadTableRow() }
        // Colonne non.
        val nameColumn = TableColumn<Download, String>("Nom")
        nameColumn.setCellValueFactory { p: TableColumn.CellDataFeatures<Download, String> ->
            val download = p.value
            SimpleStringProperty(download.getName())
        }
        // Colonne site.
        val baseCodeColumn = TableColumn<Download, String>("Site")
        baseCodeColumn.setCellValueFactory { p: TableColumn.CellDataFeatures<Download, String> ->
            val download = p.value
            SimpleStringProperty(download.getCodeBase())
        }
        // Colonne fichier local
        val outputColumn = TableColumn<Download, String>("Fichier local")
        outputColumn.setCellValueFactory { p: TableColumn.CellDataFeatures<Download, String> ->
            val download = p.value
            SimpleStringProperty(download.localFile?.getAbsolutePath())
        }
        // Colonne taille.
        val sizeColumn = TableColumn<Download, Number>("Taille")
        sizeColumn.setCellValueFactory { p: TableColumn.CellDataFeatures<Download, Number> ->
            val download = p.value;
            download.sizeProperty;
        }
        sizeColumn.setCellFactory { p: TableColumn<Download, Number>? ->
            object : TableCell<Download?, Number?>() {
                override fun updateItem(value: Number?, empty: Boolean) {
                    super.updateItem(value, empty)
                    var text: String? = null
                    if (value != null && !empty) {
                        text = if (value.toDouble() < 0) "Calcul en cours..." else value.toString()
                    }
                    setText(text)
                }
            }
        }
        // Colonne progression.
        val progressColumn = TableColumn<Download, Number>("Progression")
        progressColumn.setCellValueFactory { p: TableColumn.CellDataFeatures<Download, Number> ->
            val download = p.value
            val progressProperty: DoubleBinding = object : DoubleBinding() {
                override fun dispose() {
                    unbind(download.progressProperty(), download.sizeProperty)
                }

                override fun computeValue(): Double {
                    val progress = download.getProgress()
                    val size = download.getSize()
                    return if (size < 0 || progress < 0) (-1).toDouble() else progress / size.toDouble()
                }

                init {
                    bind(download.progressProperty(), download.sizeProperty)
                }
            }
            progressProperty
        }
        progressColumn.setCellFactory { p: TableColumn<Download, Number>? ->
            object : TableCell<Download?, Number?>() {
                private val progressBar = ProgressBar()
                override fun updateItem(value: Number?, empty: Boolean) {
                    super.updateItem(value, empty)
                    val text: String? = null
                    var graphic: Node? = null
                    if (value != null && !empty) {
                        if (value.toDouble() >= 1) {
                            val label = Label("Terminé !")
                            label.maxWidth = Double.MAX_VALUE
                            HBox.setHgrow(label, Priority.ALWAYS)
                            val button = Button("Ouvrir")
                            button.style = "-fx-padding: 3; -fx-font-size: 0.8em;"
                            button.onAction = EventHandler { t: ActionEvent? ->
                                val download = getTableView().items[index]!!
                                val file: File? = download.localFile;
                                hostServices.showDocument(file?.toURI().toString())
                            }
                            val content = HBox(label, button)
                            content.alignment = Pos.BASELINE_LEFT
                            graphic = content
                        } else {
                            progressBar.progress = value.toDouble()
                            graphic = progressBar
                        }
                    }
                    setText(text)
                    setGraphic(graphic)
                }

                init {
                    progressBar.maxWidth = Double.MAX_VALUE
                }
            }
        }
        // Peuplement des colonnes de la table.
        tableView.columns.setAll(nameColumn, baseCodeColumn, sizeColumn, outputColumn, progressColumn)
        //
        val root = StackPane()
        root.children.add(tableView)
        val scene = Scene(root, 850.0, 300.0)
        primaryStage.title = "TableView - gestionnaire de téléchargements"
        primaryStage.scene = scene
        // Peuplement de la table.
        val values = arrayOf(
            "http://cygwin.com/setup-x86.exe",
            "http://cygwin.com/setup-x86_64.exe",
            "http://cran.r-project.org/src/base/R-3/R-3.1.0.tar.gz"
        )
        val downloadFolder = File(System.getProperty("user.home"), "Downloads")
        for (value in values) {
            try {
                val url = URL(value)
                val download = Download(url, downloadFolder)
                tableView.items.add(download)
            } catch (ex: MalformedURLException) {
                println("Error #ex: ${ex.message}")
            }
        }
        // Simulation du téléchargement.
        val simulatorTimer = PauseTransition(Duration.seconds(5.0))
        simulatorTimer.onFinished = EventHandler { t: ActionEvent? ->
            val download = tableView.items[0]
            download.setSize(300000)
            download.setProgress(0)
            val downloadAnimation = SequentialTransition()
            val downloadAction =
                PauseTransition(Duration.millis(150.0))
            downloadAction.onFinished = EventHandler { actionEvent: ActionEvent? ->
                val progress = download.getProgress()
                val size = download.getSize()
                val increment = 5000
                if (progress + increment >= size) {
                    download.setProgress(size)
                    downloadAnimation.stop()
                } else {
                    download.setProgress(progress + increment)
                }
            }
            downloadAnimation.children.setAll(downloadAction)
            downloadAnimation.cycleCount = PauseTransition.INDEFINITE
            downloadAnimation.play()
        }
        simulatorTimer.play()
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(TableViewDownloadManagerApp::class.java, *args);
}