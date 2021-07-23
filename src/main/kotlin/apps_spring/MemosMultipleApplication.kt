package apps_spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemosMultipleApplication

fun main(args: Array<String>) {
	runApplication<MemosMultipleApplication>(*args)
}
