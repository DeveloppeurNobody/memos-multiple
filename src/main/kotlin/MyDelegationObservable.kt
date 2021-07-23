import kotlin.properties.Delegates

object MyDelegationObservable {
    @JvmStatic
    fun main(args: Array<String>) {

        //=================================================
        // First Example
        //=================================================
        var observed = false;
        var max: Int by Delegates.observable(0) {
            property, oldValue, newValue -> observed = true;
        }

        println(max);
        println("observed is $observed");

        max = 10;
        println(max);
        println("observed is $observed");


        //===============================================
        // Second Example
        //===============================================

        var observableData: String by Delegates.observable("Initial Value") {
            property, oldValue, newValue ->
            println("${property.name}: $oldValue -> $newValue");
        }

        observableData = "NNew value";
        observableData = "Another value";
    }
}