package threads.cookbook_java9.ch02_basic_synchronization.recipe04_synchronizing_data_access_with_reentrant_read_write_locks

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates an object to store the prices
        val pricesInfo = PricesInfo();

        val readers = arrayOfNulls<Reader>(5);
        var threadsReaders = arrayOfNulls<Thread>(5);

        // Creates five readers and threads to run them
        (0 until 5).forEach {
            readers[it] = Reader(pricesInfo);
            threadsReaders[it] = Thread(readers[it]);
        }

        // Creates a writer and a thread to run it
        val writer = Writer(pricesInfo);
        val threadWriter = Thread(writer);

        // Starts the threads
        (0 until 5).forEach {
            threadsReaders[it]?.start();
        }

        threadWriter.start();
    }
}