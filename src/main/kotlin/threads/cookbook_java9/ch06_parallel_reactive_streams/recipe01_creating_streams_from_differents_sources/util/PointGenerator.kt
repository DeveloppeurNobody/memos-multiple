package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

class PointGenerator {
    companion object {
        fun generatePointList(size: Int): MutableList<Point> {
            val ret = mutableListOf<Point>();
            val randomGenerator = java.util.Random();
            repeat(size) {
                val point = Point();
                point.x = randomGenerator.nextDouble();
                point.y = randomGenerator.nextDouble();
                ret.add(point);
            }

            return ret;
        }
    }
}