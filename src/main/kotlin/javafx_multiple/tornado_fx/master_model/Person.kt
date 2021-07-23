package javafx_multiple.tornado_fx.master_model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

class Person(firstName: String, lastName: String) {
    var firstName by property<String>(firstName)
    fun firstNameProperty() = getProperty(Person::firstName)

    var lastName by property<String>(lastName)
    fun lastNameProperty() = getProperty(Person::lastName)
}

class PersonModel : ItemViewModel<Person>() {
    var firstName = bind { item?.firstNameProperty() }
    var lastName = bind { item?.lastNameProperty() }
}