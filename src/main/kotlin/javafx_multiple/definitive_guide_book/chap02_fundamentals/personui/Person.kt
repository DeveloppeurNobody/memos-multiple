package javafx_multiple.definitive_guide_book.chap02_fundamentals.personui

import javafx.beans.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.util.Callback
import java.util.*


class Person {
    private val firstname: StringProperty = SimpleStringProperty(this, "fistname", "")
    private val lastname: StringProperty = SimpleStringProperty(this, "lastname", "")
    private val notes: StringProperty = SimpleStringProperty(this, "notes", "sample notes")

    constructor() {}
    constructor(firstname: String = "", lastname: String = "", notes: String = "") {
        this.firstname.set(firstname)
        this.lastname.set(lastname)
        this.notes.set(notes)
    }

    fun getFirstname(): String {
        return firstname.get()
    }

    fun firstnameProperty(): StringProperty {
        return firstname
    }

    fun setFirstname(firstname: String) {
        this.firstname.set(firstname)
    }

    fun getLastname(): String {
        return lastname.get()
    }

    fun lastnameProperty(): StringProperty {
        return lastname
    }

    fun setLastname(lastname: String) {
        this.lastname.set(lastname)
    }

    fun getNotes(): String {
        return notes.get()
    }

    fun notesProperty(): StringProperty {
        return notes
    }

    fun setNotes(notes: String) {
        this.notes.set(notes)
    }

    override fun toString(): String {
        return firstname.get().toString() + " " + lastname.get()
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null || javaClass != obj.javaClass) return false
        val person = obj as Person
        return firstname == person.firstname &&
                lastname == person.lastname &&
                notes == person.notes
    }

    override fun hashCode(): Int {
        return Objects.hash(firstname, lastname, notes)
    }

    companion object {
        var extractor =
            Callback { p: Person ->
                arrayOf<Observable>(
                    p.lastnameProperty(),
                    p.firstnameProperty()
                )
            }
    }
}
