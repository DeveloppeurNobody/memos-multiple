package javafx_multiple.cell.developpez_api_cell.tableview

import javafx.animation.PauseTransition
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.css.PseudoClass
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.control.TableRow
import javafx.util.Duration
import java.net.URI
import java.net.URL
import java.nio.file.Paths
import java.util.*

/**
 * La cellule utilisée pour les lignes dans le gestionnaire de téléchargement.
 */
class DownloadTableRow : TableRow<Download> {
    var value: Optional<Download> = Optional.empty();
    val ctxMenu = ContextMenu();

    constructor(): super() {
        styleClass.add("download-table-row");

        // style paper
        val cssURL: URL = Paths.get("/home/ryu/IdeaProjects/netty-mutliple/src/main/kotlin/javafx_multiple/cell/developpez_api_cell/tableview/DownloadTableRow.css").toUri().toURL();
        stylesheets.add(cssURL.toExternalForm());
        //
        val restartMenu = MenuItem("Relancer");
        restartMenu.id = "restartMenu";
        restartMenu.setOnAction { value.ifPresent { d: Download -> println("Relancer ${d.getName()}") } }

        val pauseItem = MenuItem("Mettre en pause");
        pauseItem.id = "pauseItem";
        pauseItem.setOnAction { value.ifPresent { println("Mise en pause de ${it.getName()}"); } }

        val resumeItem = MenuItem("Reprendre");
        resumeItem.id = "Retirer de la liste";
        resumeItem.setOnAction { value.ifPresent { println("Reprise de ${it.getName()}"); } }

        val deleteItem = MenuItem("deleteItem");
        deleteItem.id = "deleteItem";
        deleteItem.setOnAction { value.ifPresent { println("Suppression de ${it.getName()}") } }

        //
        with (ctxMenu) {
            id = "contextMenu";
            items.addAll(
                restartMenu,
                SeparatorMenuItem(),
                pauseItem,
                resumeItem,
                SeparatorMenuItem(),
                deleteItem
            );
        }
    }

    override fun updateItem(item: Download?, empty: Boolean) {
        super.updateItem(item, empty);
        value.ifPresent {
            // remove listener of oldValue
            it.progressProperty().removeListener(invalidationListener);
            contextMenu = null;
        };
        value = Optional.ofNullable(item);
        value.ifPresent {
            // put listener on the new value
            it.progressProperty().addListener(invalidationListener);
            contextMenu = ctxMenu;
        };
        requestUpdateStyle();
    }

    val invalidationListener = InvalidationListener { requestUpdateStyle(); }

    var styleWaitTimer: PauseTransition? = PauseTransition();
    val styleWaitDuration = Duration.millis(350.toDouble());

    // update
    fun requestUpdateStyle() {
        if (styleWaitTimer == null) {
            styleWaitTimer = PauseTransition(styleWaitDuration);
            styleWaitTimer?.setOnFinished {
                styleWaitTimer = null;
                updateStyle();
            };
            styleWaitTimer?.playFromStart();
        }
    }

    fun updateStyle() {
        println("calling updateStyle");
        val finishedPseudoClass = PseudoClass.getPseudoClass("finished");
        pseudoClassStateChanged(finishedPseudoClass, false);
        value.ifPresent {
            val progress = it.getProgress();
            val size = it.getSize();
            val enableStyle = (size != -1L) && (progress == size);
            pseudoClassStateChanged(finishedPseudoClass, true);
        }
    }
}
