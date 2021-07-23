package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

data class Counter(var value: String = "",
                   var counter: Int = 1) {

    fun increment() {
        counter++
    }

}