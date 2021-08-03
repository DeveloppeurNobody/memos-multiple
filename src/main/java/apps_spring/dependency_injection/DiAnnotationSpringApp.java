package apps_spring.dependency_injection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import apps_spring.dependency_injection.app.conf.AppConfig;
import apps_spring.dependency_injection.metier.IMetier;


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
		// put package to scan from java to 'service' or 'dao' [ src/main/java/dependecy_injection/dao ]
		// ApplicationContext ctx = new AnnotationConfigApplicationContext("apps_spring.dependency_injection.dao", "apps_spring.dependency_injection.metier");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		IMetier metier = ctx.getBean(IMetier.class);
		System.out.println("[ Annotation_version ] #metier.calcul: " + metier.calcul());
	}

}
