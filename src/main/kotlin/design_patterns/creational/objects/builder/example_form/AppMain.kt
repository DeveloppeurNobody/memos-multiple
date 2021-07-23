package design_patterns.creational.objects.builder.example_form

object AppMain {
    @JvmStatic
    fun main(args: Array<String>) {
        //println("you entered: ${args[0]}");


        sum(args[0] as Int, args[1] as Int)


    }


    fun sum(first: Int, second: Int) {
        println("first + second = ${first + second}");
    }
}