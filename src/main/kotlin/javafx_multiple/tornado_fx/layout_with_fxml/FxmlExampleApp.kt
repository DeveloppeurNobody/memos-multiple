package javafx_multiple.tornado_fx.layout_with_fxml

import tornadofx.App
import tornadofx.launch

class FxmlExampleApp : App(CounterView::class)

fun main(args: Array<String>) {
    launch<FxmlExampleApp>(*args)
}