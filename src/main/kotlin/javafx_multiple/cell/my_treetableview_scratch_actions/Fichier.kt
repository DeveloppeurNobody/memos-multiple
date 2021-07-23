package javafx_multiple.cell.my_treetableview_scratch_actions

import java.nio.file.Path

data class Fichier(var name: String = "", var path: Path? = null) {
    var currentItem: Fichier = this;
    var isDirectory = false;
}