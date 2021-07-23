package javafx_multiple.tornado_fx.workspace;

import tornadofx.ItemViewModel
import tornadofx.observable

data class Document(var title: String = "", var text: String = "")
class DocumentViewModel(var document: Document = Document()) : ItemViewModel<Document>() {
    val title = bind { document.observable(Document::title) }
    val text = bind { document.observable(Document::text)}
}