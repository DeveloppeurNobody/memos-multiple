package dependency_injection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import dependency_injection.app.conf.AppConfig;
import dependency_injection.metier.IMetier;


@SpringBootApplication
public class DiAnnotationSpringApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DiAnnotationSpringApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		startWithAnnotation();
	}


	public void startWithAnnotation() {
		System.out.println("startWithAnnotation");

		// put package scan from java to service or dao [ src/main/java/dependecy_injection/dao ]
		// ApplicationContext ctx = new AnnotationConfigApplicationContext("dependency_injection.dao", "dependency_injection.metier");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		IMetier metier = ctx.getBean(IMetier.class);
		System.out.println("[ Annotation_version ] #metier.calcul: " + metier.calcul());
	}

}
