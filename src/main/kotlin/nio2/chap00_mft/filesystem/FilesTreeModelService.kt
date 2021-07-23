package nio2.chap00_mft.filesystem

import java.io.File
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

// TODO make it as Singleton or Not ?
// TODO we have DefaultModelTree ---> Json
//  but not DefaultModelTree --> customModel --> Json. good solution?

// TODO /!\ OutOfMemory java heap space when we ask for home.user directory /!\

/**
 * Fetch files as tree (names and paths) with DefaultTreeModel.
 * the DefaultModel receives a DefaultTreeNode as root folder.
 * the root folder does not mean necessarily a root of the system,
 * it can be the beginning of an folder chosen.
 *
 * We can get the <i>treeModel</i> after the class has been instantiated
 * @param dir File path to determine where to start.
 */
class FilesTreeModelService(var dir: File) {
    lateinit var treeModel: DefaultTreeModel;
    //lateinit var root: DefaultMutableTreeNode;

    init {
        treeModel = DefaultTreeModel(addNodes(null, dir));
    }

    /**
     * recursive method for retrieving all childs nodes.
     * 1st time addNodes(null, dir)
     * n times addNodes(parent, dir);
     *
     * @param currentParent parent of dir. 1st time should be null
     * @param dir a file or directory.
     * @return an defaultMutableTreeNode that represents all hierachy tree with dir as root
     */
    private fun addNodes(currentParent: DefaultMutableTreeNode?, dir: File): DefaultMutableTreeNode {
        var currentPath: String = dir.path;
        var currentDir: DefaultMutableTreeNode = DefaultMutableTreeNode(currentPath);

        currentParent?.add(currentDir);

        var ol: MutableList<String> = mutableListOf();
        var tmp: Array<String> = dir.list();

        tmp.forEach { ol.add(it) };
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);

        var f: File;
        var files: MutableList<Any> = mutableListOf();
        ol.forEach {
            var thisObject = it;
            var newPath: String = if (currentPath.equals(".")) {
                thisObject;
            } else {
                "$currentPath${File.separator}$thisObject";
            }

            f = File(newPath);
            if (f.isDirectory) {
                addNodes(currentDir, f);
            }
            else {
                files.add(thisObject);
            }
        }

        files.forEach { currentDir.add(DefaultMutableTreeNode(it)) }
        return currentDir;
    }
}