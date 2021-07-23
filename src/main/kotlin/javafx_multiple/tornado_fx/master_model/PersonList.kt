package javafx_multiple.tornado_fx.master_model

import tornadofx.*

class PersonList : View("My View") {
    val controller: PersonController by inject()

    // /!\ a shared / global model for all classes that use inject for Person /!\
    val model: PersonModel by inject()

    override val root = tableview(controller.persons) {
        column("First Name", Person::firstNameProperty)
        column("Last Name", Person::lastNameProperty)
        columnResizePolicy = SmartResize.POLICY

        // shortcut for model.item
        bindSelected(model)
    }
}
