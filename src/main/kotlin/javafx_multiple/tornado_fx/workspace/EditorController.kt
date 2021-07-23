package javafx_multiple.tornado_fx.workspace;

import tornadofx.Controller
import tornadofx.observable
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Paths

class EditorController : Controller() {
    // random quotes from resource quotes.txt
    var pathAsUri = Paths.get("/home/ryu/IdeaProjects/netty-mutliple/src/main/resources", "quotes.txt").toUri();


    //val quotes = File(javaClass.getResource("javafx.tornado_fx/workspace/quotes.txt").toURI()).readLines(Charset.forName("UTF-8"))
    val quotes = File(pathAsUri).readLines(Charset.forName("UTF-8"))

    // the list of open text editors
    val editorModeList = mutableListOf<TextEditorFragment>().observable()

    fun newEditor(): TextEditorFragment {
        val newFile = DocumentViewModel();
        with(newFile) {
            title.value = "New file ${editorModeList.size}"
            commit()
        }

        val editor = TextEditorFragment(newFile)
        editorModeList.add(editor)

        return editor;
    }

    fun quote(): String = quotes[(Math.random() * quotes.size).toInt()]
}