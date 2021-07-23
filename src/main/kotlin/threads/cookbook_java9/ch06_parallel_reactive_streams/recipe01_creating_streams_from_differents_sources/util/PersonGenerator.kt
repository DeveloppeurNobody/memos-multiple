package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

import java.util.*

class PersonGenerator {
    companion object {
        fun generatePersonList(size: Int): MutableList<Person> {
            val ret = mutableListOf<Person>();

            val firstNames = arrayOf<String>("Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "James", "John", "Robert", "Michael", "William");
            val lastNames = arrayOf<String>("Smith","Jones","Taylor","Williams","Brown","Davies","Evans","Wilson","Thomas","Roberts");

            val randomGenerator = java.util.Random();
            repeat(size) {
                val person = Person();
                with(person) {
                    id = it;
                    firstName = firstNames[randomGenerator.nextInt(10)];
                    lastName = lastNames[randomGenerator.nextInt(10)];
                    salary = randomGenerator.nextInt(100000);
                    coeficient = randomGenerator.nextDouble() * 10;
                    val calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, -randomGenerator.nextInt(30));
                    val birthD = calendar.time;
                    birthDate = birthD;
                }
                ret.add(person);
            }
            return ret;
        }
    }
}