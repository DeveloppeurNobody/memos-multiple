package javafx_multiple.definitive_guide_book.chap02_fundamentals.personui

import javafx.collections.ObservableList

object SampleData {

    fun fillSampleData(backingList: ObservableList<Person>) {
        with(backingList) {
            add(Person("Waldo", "Soller", "random notes 1"))
            add(Person("Herb", "Dinapoli", "random notes 2"))
            add(Person("Shawanna", "Goehring", "random notes 3"))
            add(Person("Flossie", "Goehring", "random notes 4"))
            add(Person("Magdalen", "Meadors", "random notes 5"))
            add(Person("Marylou", "Berube", "random notes 6"))
            add(Person("Ethan", "Nieto", "random notes 7"))
            add(Person("Elli", "Combes", "random notes 8"))
            add(Person("Andy", "Toupin", "random notes 9"))
            add(Person("Zenia", "Linwood", "random notes 10"))
        }
    }
}