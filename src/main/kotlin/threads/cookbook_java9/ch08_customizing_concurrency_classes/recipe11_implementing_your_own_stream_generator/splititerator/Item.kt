package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe11_implementing_your_own_stream_generator.splititerator

data class Item (
    var name: String = "",
    var row: Int = 0,
    var column: Int = 0)