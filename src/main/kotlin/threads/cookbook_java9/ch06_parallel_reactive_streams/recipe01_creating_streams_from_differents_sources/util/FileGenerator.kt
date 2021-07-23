package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

class FileGenerator {
    companion object {
        fun generateFile(size: Int): MutableList<String> {
            val file = mutableListOf<String>();
            repeat(size) {
                file.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi lobortis cursus venenatis. Mauris tempus elit ut malesuada luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus laoreet sapien eu pulvinar rhoncus. Integer vel ultricies leo. Donec vel sagittis nibh. Maecenas eu quam non est hendrerit pu");
            }
            return file;
        }
    }
}