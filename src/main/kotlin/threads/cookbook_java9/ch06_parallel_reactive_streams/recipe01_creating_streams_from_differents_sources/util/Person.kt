package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

import java.util.*

data class Person(var id: Int = 0,
                  var firstName: String = "",
                  var lastName: String = "",
                  var birthDate: Date = Date(),
                  var salary: Int = 0,
                  var coeficient: Double = 0.0) : Comparable<Person> {


    override fun compareTo(other: Person): Int {
        var compareLastNames = this.lastName.compareTo(other.lastName);
        return if (compareLastNames != 0) {
            compareLastNames;
        } else {
            this.firstName.compareTo(other.firstName);
        }
    }

    override fun equals(other: Any?): Boolean {
        val otherPerson = other as Person;
        val compareLastNames = this.lastName.compareTo(otherPerson.lastName);
        val compareFirstNames = this.firstName.compareTo(otherPerson.lastName);
        return ((compareLastNames == 0) && (compareFirstNames == 0));
    }

    override fun hashCode(): Int {
        val sequence = "${lastName}${firstName}";
        return sequence.hashCode();
    }
}