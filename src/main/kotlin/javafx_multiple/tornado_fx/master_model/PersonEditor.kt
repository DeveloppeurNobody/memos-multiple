package javafx_multiple.tornado_fx.master_model

import tornadofx.*

class PersonEditor : View() {

    // /!\ a shared / global model for all classes that use inject for Person /!\
    val model: PersonModel by inject()

    override val root = form {
        fieldset {
            field("First Name") {
                textfield(model.firstName)
            }
            field("Last Name") {
                textfield(model.lastName)
            }
            button("Save") {
                action { model.commit() }
            }
        }
    }
}
