package threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.utils



/**
 * this class simulates a text file. It creates a defined number of random lines
 * to process them sequentially
 */
class FileMock(var size: Int,
               var length: Int) {

    private var content = arrayOfNulls<String>(size);
    private var index: Int = 0;

    init {
        (0 until size).forEach { i ->
            val buffer = StringBuilder(length);
            (0 until length).forEach { j ->
                var randomCharacter: Int = (Math.random() * 255).toInt();
                buffer.append(randomCharacter);
            }
            content[i] = buffer.toString();
        }
        index = 0;
    }

    fun hasMoreLines(): Boolean  = index < content.size;

    fun getLine(): String {
        if (this.hasMoreLines()) {
            println("Mock: ${content.size - index}");
            return content[index++].toString();
        }
        return "";
    }


}

