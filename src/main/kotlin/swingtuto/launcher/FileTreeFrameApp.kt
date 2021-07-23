package swingtuto.launcher

import com.google.gson.GsonBuilder
import swingtuto.defaultmutabletree.FileTreeWithPathsOnDirs

import swingtuto.serializationgson.DefaultMutableTreeNodeDeserializer
import swingtuto.serializationgson.DefaultMutableTreeNodeSerializer
import swingtuto.treemodel.FileTreeWithDetailsOnItRight
import java.nio.file.Paths
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Launcher for treeModel
 */
object FileTreeFrameApp {
    @JvmStatic
    fun main(args: Array<String>) {
        //var f = FileTreeFrame("/home/ryu");
        var f = FileTreeWithDetailsOnItRight(Paths.get("/home/ryu/raf").toFile().toString());
        var topNode = f;

        // var f = MyFileSystemModel(Paths.get("/home/ryu").toFile());
        //var topNode = f.treeModel;
       // println(topNode.root.toString());
//        var topNode: Any = f.fileTree
//            .model
//            .root;
//
//        var gson = GsonBuilder().create();
//        println(gson.toJsonTree(topNode));


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

        var jsonString = gson.toJson(topNode);
        println(jsonString);


        println("done!");

        var jsonFromString: String = """
            {
              "allowsChildren": true,
              "userObject": "JTree",
              "children": [
                {
                  "allowsChildren": true,
                  "userObject": "colors",
                  "children": [
                    {
                      "allowsChildren": true,
                      "userObject": "blue"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "violet"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "red"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "yellow"
                    }
                  ]
                },
                {
                  "allowsChildren": true,
                  "userObject": "sports",
                  "children": [
                    {
                      "allowsChildren": true,
                      "userObject": "basketball"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "soccer"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "football"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "hockey"
                    }
                  ]
                },
                {
                  "allowsChildren": true,
                  "userObject": "food",
                  "children": [
                    {
                      "allowsChildren": true,
                      "userObject": "hot dogs"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "pizza"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "ravioli"
                    },
                    {
                      "allowsChildren": true,
                      "userObject": "bananas"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()
//
//        var jsonO: Any = gson.fromJson(jsonFromString, Any::class.java);
//        println(jsonO);

    }
}