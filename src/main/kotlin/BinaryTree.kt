import org.irsal.app.client.GenerateLogger
import java.util.*

object BinaryTree {
    val LOG = GenerateLogger.getLoggerForClass(this);

    var root: TreeNode? = null;

    @JvmStatic
    fun main(args: Array<String>) {
        createBinaryTree();
        //preOrder(root);
        levelOrder();
    }

    private fun createBinaryTree() {
        val first = TreeNode(1);
        val second = TreeNode(2);
        var third = TreeNode(3);
        var fourth = TreeNode(4);
        var fifth = TreeNode(5);
        var sixth = TreeNode(6);
        var seventh = TreeNode(7);

        root = first; // root ---> first
        first.left = second;
        first.right = third; // second <--- first ---> third

        second.left = fourth;
        second.right = fifth;

        third.left = sixth;
        third.right = seventh;
    }

    private fun preOrder(root: TreeNode? = null) {
        if (root == null) {
            return;
        }

        LOG.info("data: ${root.data}");
        preOrder(root.left);
        preOrder(root.right);
    }

    fun levelOrder() {
        if (root == null) {
            return;
        }

        val queue: Queue<TreeNode> = LinkedList();
        queue.offer(root);

        while (!queue.isEmpty()) {
            val tmp: TreeNode = queue.poll();
            LOG.info("temp.data: ${tmp.data}");
            if (tmp.left != null) {
                queue.offer(tmp.left);
            }
            if (tmp.right != null) {
                queue.offer(tmp.right);
            }
        }
    }

    class TreeNode(var data: Int = 0) {
        var left: TreeNode? = null;
        var right: TreeNode? = null;
    }

}