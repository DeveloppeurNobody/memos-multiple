package swingtuto.serializationgson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode


class DefaultMutableTreeNodeTypeAdapter private constructor(private val gson: Gson) :
    TypeAdapter<DefaultMutableTreeNode>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, node: DefaultMutableTreeNode) {
        out.beginObject()
        out.name("allowsChildren")
        out.value(node.allowsChildren)
        out.name("userObject")
        gson.toJson(node.userObject, Any::class.java, out)
        if (node.childCount > 0) {
            out.name("children")
            gson.toJson(
                Collections.list(node.children()),
                MutableList::class.java, out
            ) // recursion!
        }
        // No need to write node.getParent(), it would lead to infinite recursion.
        out.endObject()
    }

    @Throws(IOException::class)
    override fun read(inJson: JsonReader): DefaultMutableTreeNode {
        inJson.beginObject()
        val node = DefaultMutableTreeNode()
        while (inJson.hasNext()) {
            when (inJson.nextName()) {
                "allowsChildren" -> node.allowsChildren = inJson.nextBoolean()
                "userObject" -> node.userObject = gson.fromJson(inJson, Any::class.java)
                "children" -> {
                    inJson.beginArray()
                    while (inJson.hasNext()) {
                        node.add(read(inJson)) // recursion!
                        // this did also set the parent of the child-node
                    }
                    inJson.endArray()
                }
                else -> inJson.skipValue()
            }
        }
        inJson.endObject()
        return node
    }

    companion object {
        val FACTORY: TypeAdapterFactory = object : TypeAdapterFactory {
            // we use a runtime check to make sure the 'T's equal
            override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
                if (type?.rawType is DefaultMutableTreeNode) {
                    return DefaultMutableTreeNodeTypeAdapter(gson!!) as TypeAdapter<T>;
                }
                return null;
            }
        }
    }

}