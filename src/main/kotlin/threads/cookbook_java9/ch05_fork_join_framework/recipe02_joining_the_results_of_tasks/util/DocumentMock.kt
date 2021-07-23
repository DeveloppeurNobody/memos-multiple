package threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks.util

/**
 * This class will simulate a document generating a String array with a determined number of rows
 * (numLines) and columns(numWords). The content of the document will be generated
 * selecting in a random way words from a String array.
 */
class DocumentMock {
    val words = arrayListOf<String>("the", "hello", "goodbye", "packt", "java", "thread", "pool", "random", "class", "main");

    fun generateDocument(numLines: Int, numWords: Int, word: String): Array<Array<String?>> {
        var counter = 0;
        val document = Array(numLines) { arrayOfNulls<String>(numWords) }
        val random = java.util.Random();
        repeat(numLines) {i ->
            repeat(numWords) {j ->
                val index = random.nextInt(words.size);
                document[i][j] = words[index];
                if (document[i][j].equals(word)) {
                    counter++;
                }
            }
        }
        println("DocumentMock: The word appears $counter times in the document");
        return document;
    }
}