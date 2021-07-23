package nio2.chap00_mft.filesystem.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Deserializer for gson that transforms json to DefaultTreeNode
 */
class DefaultMutableTreeNodeDeserializer : JsonDeserializer<DefaultMutableTreeNode> {
    override fun deserialize(
            json: JsonElement,
            type: Type,
            context: JsonDeserializationContext
    ): DefaultMutableTreeNode {
        return context.deserialize<POJO>(
                json,
                POJO::class.java
        ).toDefaultMutableTreeNode()
    }

    private class POJO {
        private var allowsChildren = false
        private var userObject: Any? = null
        private var children: List<POJO>? = null

        // no need for: POJO parent
        fun toDefaultMutableTreeNode(): DefaultMutableTreeNode {
            val node = DefaultMutableTreeNode()
            node.allowsChildren = allowsChildren
            node.userObject = userObject
            if (children != null) {
                for (child in children!!) {
                    node.add(child.toDefaultMutableTreeNode()) // recursion!
                    // this did also set the parent of the child-node
                }
            }
            return node
        }

        // Following setters needed by Gson's deserialization:
        fun setAllowsChildren(allowsChildren: Boolean) {
            this.allowsChildren = allowsChildren
        }

        fun setUserObject(userObject: Any?) {
            this.userObject = userObject
        }

        fun setChildren(children: List<POJO>?) {
            this.children = children
        }
    }
}