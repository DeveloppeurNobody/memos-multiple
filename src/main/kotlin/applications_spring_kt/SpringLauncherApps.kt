package applications_spring_kt

import applications_spring_kt.aop.AopDemo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringLauncherApps : CommandLineRunner{

	@Autowired
	lateinit var aopDemo: AopDemo;

	override fun run(vararg args: String?) {
		aopDemo.doAspectSpring();
	}
}

fun main(args: Array<String>) {
	runApplication<SpringLauncherApps>(*args)
}
