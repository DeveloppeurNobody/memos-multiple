package javafx_multiple.tornado_fx.treeviews.app

import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val wrapper by cssclass()
    }

    init {
        s(wrapper) {
            padding = box(10.px)
            spacing = 10.px
        }
    }
}