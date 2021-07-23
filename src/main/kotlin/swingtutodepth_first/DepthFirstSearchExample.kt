package swingtutodepth_first

import java.util.*

fun main() {

    val node40 = DepthFirstSearchExample.Node(40)
    val node10 = DepthFirstSearchExample.Node(10)
    val node20 = DepthFirstSearchExample.Node(20)
    val node30 = DepthFirstSearchExample.Node(30)
    val node60 = DepthFirstSearchExample.Node(60)
    val node50 = DepthFirstSearchExample.Node(50)
    val node70 = DepthFirstSearchExample.Node(70)

    node40.neighbours.add(node10);
    node40.neighbours.add(node20);
    node10.neighbours.add(node30);
    node20.neighbours.add(node10);
    node20.neighbours.add(node30);
    node20.neighbours.add(node60);
    node20.neighbours.add(node50);
    node30.neighbours.add(node60);
    node60.neighbours.add(node70);
    node50.neighbours.add(node70);

    var dfs: DepthFirstSearchExample = DepthFirstSearchExample();
    println("--- the DFS traversal of the graph using stack");
    dfs.dfsUsingStack(node40);
    println()

    // resetting the visited flag for nodes
    node40.visited=false;
    node10.visited=false;
    node20.visited=false;
    node30.visited=false;
    node60.visited=false;
    node50.visited=false;
    node70.visited=false;

    println("--- The DFS traversal of the graph using recursion");
    dfs.dfs(node40);

}

class DepthFirstSearchExample {
    data class Node(var data: Int) {
        var visited: Boolean = false;
        var neighbours: MutableList<Node> = mutableListOf();
    }

    // recursive DFS
    fun dfs(node: Node) {
        println("${node.data} ");
        var neighbours: MutableList<Node> = node.neighbours;
        node.visited = true;
        neighbours.forEach {
            if (it != null && it.visited) {
                dfs(it);
            }
        }
    }

    // Iterative DFS using stack
    fun dfsUsingStack(node: Node) {
        var mutableStack: Stack<Node> = Stack();
        mutableStack.add(node);

        while (mutableStack.isNotEmpty()) {
            var element: Node = mutableStack.pop();
            if (!element.visited) {
                println("element data ${element.data}");
                element.visited = true;
            }

            element.neighbours
                .forEach {
                    if (it != null && !it.visited) {
                        mutableStack.add(it);
                    }
                }
        }
    }


}