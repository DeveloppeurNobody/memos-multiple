package swingtuto.projava8

import java.awt.Container
import java.util.*
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTree

fun main() {
    var stt: JtreeApp01 = JtreeApp01();
    with(stt) {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        setSize(250, 250);
        isVisible = true;
    }


}

class JtreeApp01 : JFrame() {
    init {
        var genealogy: Array<Any> = arrayOf("Jeff", "Joseph", "Pearl", "Owen", "Sarah", "John");

        // without name
        // genealogy[0] = arrayOf("Jerry", "Selma", "Joe", "Evelyn");

        // with a name
        var v: Vector<Any> = object : java.util.Vector<Any>() {
            override fun toString(): String {
                return "Jeff";
            }
        }

        v.addElement("Jerry");
        v.addElement("Selma");
        v.addElement("Joe");
        v.addElement("Evelyn");

        genealogy[0] = v;

        var tree: JTree = JTree(genealogy);
        tree.isRootVisible = true;
        var jsp: JScrollPane = JScrollPane(tree);
        this.contentPane.add(jsp);
    }
}