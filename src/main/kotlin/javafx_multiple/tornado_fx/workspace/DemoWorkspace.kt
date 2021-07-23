package javafx_multiple.tornado_fx.workspace;

import javafx.application.Platform
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import tornadofx.*
import java.io.PrintStream


class DemoWorkspace : Workspace("Editor") {
    val editorController: EditorController by inject()

    init {
        menubar {
            menu("File") {
                item("New").action {
                    // workspace.dock(mainView, true)
                    log.info("Opening text file")
                    workspace.dock(editorController.newEditor(), true)
                }
                separator()
                item("Exit").action {
                    log.info("Leaving workspace")
                    Platform.exit()
                }
            }
            menu("Window") {
                item("Close all").action {
                    editorController.editorModeList.clear();
                    workspace.dock(EmptyView(), true)
                }
                separator()
                openWindowMenuItemsAfter()
            }
            menu("Help") {
                item("About...")
            }
        }

        add(RestProgressBar::class)
        with(bottomDrawer) {
            item("Logs") {
                textarea {
                    addClass("consola")
                    val ps = PrintStream(TextAreaOutputStream(this))
                    System.setErr(ps)
                    System.setOut(ps)
                }
            }
        }
    }

    /**
     * This extension method allows binding the open document's fragment to menu
     */
    private fun Menu.openWindowMenuItemsAfter() {
        editorController.editorModeList.onChange { dvm ->
            dvm.next()
            if (dvm.wasAdded()) {
                dvm.addedSubList.forEach { x ->
                    val item = MenuItem(x.title)
                    item.action { workspace.dock(x, true) }
                    items.add(item)
                }
            } else if (dvm.wasRemoved()) {
                dvm.removed.forEach { x ->
                    workspace.viewStack.remove(x)
                    x.close()
                    println(workspace.dockedComponent)
                    val morituri = items.takeLast(items.size - 2).filter { item -> item.text.equals(x.title) }
                    items.removeAll(morituri)
                }
            }
        }
    }

}