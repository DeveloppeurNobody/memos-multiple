package swingtuto.launcher

import com.google.gson.GsonBuilder
import swingtuto.defaultmutabletree.runnable.FileBrowser
import swingtuto.serializationgson.DefaultMutableTreeNodeDeserializer
import swingtuto.serializationgson.DefaultMutableTreeNodeSerializer
import javax.swing.SwingUtilities
import javax.swing.tree.DefaultMutableTreeNode

/**
 * launcher for FileBrowser that have multiple runnable interfaces
 */
object FileBrowserApp {
    @JvmStatic
    fun main(args: Array<String>) {
        var f: FileBrowser = FileBrowser();
        SwingUtilities.invokeLater(f);
        val gson = GsonBuilder()
            .registerTypeAdapter(DefaultMutableTreeNode::class.java,
                DefaultMutableTreeNodeSerializer()
            )
            .registerTypeAdapter(DefaultMutableTreeNode::class.java,
                DefaultMutableTreeNodeDeserializer()
            )
            .setPrettyPrinting()
            .create();

//        val gson = GsonBuilder()
//            .registerTypeAdapterFactory(DefaultMutableTreeNodeTypeAdapter.FACTORY)
//            .setPrettyPrinting()
//            .create()

        while (f.tree == null) {
            Thread.sleep(1000);
            println("wainting ...");
        }

        var topNode = f?.tree?.model?.root;



        var jsonString = gson.toJson(topNode);
        println(jsonString);



        println("done!");

    }
}