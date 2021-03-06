package javafx_multiple.tornado_fx.workspace;

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.TextArea
import tornadofx.*
import java.io.IOException
import java.io.OutputStream
import java.lang.UnsupportedOperationException
import java.nio.charset.Charset
import java.util.*

class TextEditorFragment(val documentViewModel: DocumentViewModel) : Fragment() {
    override val root = pane {
        title = documentViewModel.title.value
        textarea(documentViewModel.text) {
            this.prefWidthProperty().bind(this@pane.widthProperty())
            this.prefHeightProperty().bind(this@pane.heightProperty())
        }
    }

    init {
        documentViewModel.title.addListener { w, o, n ->
            this.title = n;
        }
    }

    override val deletable = SimpleBooleanProperty(false);
    override val closeable = SimpleBooleanProperty(true)
    override val savable = documentViewModel.dirty
    override val refreshable = documentViewModel.dirty

    override fun onSave() {
        documentViewModel.commit()
    }

    override fun onRefresh() {
        documentViewModel.rollback()
    }
}

class EmptyView : View() {
    val editorController: EditorController by inject()
    override val root = label(editorController.quote())
}

/**
 * TextAreaOutputStream
 *
 * Binds an output stream to a textarea
 */
class TextAreaOutputStream(val textArea: TextArea): OutputStream() {
    @Throws(IOException::class)
    override fun write(b: Int) {
        throw UnsupportedOperationException();
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        // redirects data to the text area
        textArea.appendText(String(Arrays.copyOf(b, len), Charset.defaultCharset()))
        // scrolls the text area to the end of data
        textArea.scrollTop = java.lang.Double.MAX_VALUE
    }
}