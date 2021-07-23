package javafx_multiple.tornado_fx.fragments

import tornadofx.*

fun main(args: Array<String>) {
    launch<FragmentExampleApp>(*args)
}

class FragmentExampleApp : App(FragmentExampleView::class)

class FragmentExampleView : View("FragmentViewExample") {
    override val root = vbox {
        this += find(CustomFragment::class)
        label("I am in mainView")
    }
}

class CustomFragment : Fragment() {
    override val root = vbox {
        label("I am in a fragment")
    }
}