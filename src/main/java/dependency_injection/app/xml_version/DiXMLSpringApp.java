package dependency_injection.app.xml_version;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dependency_injection.metier.IMetier;

@SpringBootApplication
public class DiXMLSpringApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DiXMLSpringApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		startWithXML();
	}

	public void startWithXML() {
		/* at startup read file config xml
		 * instantiate classes and inject dependencies
		 */
		System.out.println("startWithXML");
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		//IMetier metier = (IMetier) ctx.getBean("metier");

		// get a bean by its interface class
		// but can not work if we got multiple implementation
		IMetier metier = ctx.getBean(IMetier.class);
		System.out.println("#bean => metier.calcul: " + metier.calcul());
	}

}
