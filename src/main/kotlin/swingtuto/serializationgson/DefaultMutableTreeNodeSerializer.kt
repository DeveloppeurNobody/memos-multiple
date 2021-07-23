package swingtuto.serializationgson

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode


class DefaultMutableTreeNodeSerializer : JsonSerializer<DefaultMutableTreeNode?> {

    override fun serialize(
        src: DefaultMutableTreeNode?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("allowsChildren", src!!.allowsChildren)
        jsonObject.add("userObject", context!!.serialize(src!!.userObject))
        if (src!!.childCount > 0) {
            jsonObject.add("children", context!!.serialize(Collections.list(src!!.children())))
        }
        return jsonObject
    }
}