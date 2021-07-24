package apps_spring.aop;

import apps_spring.aop.metier.Compte;
import apps_spring.aop.metier.IMetierBanque;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import apps_spring.aop.message.MyMessage;
import apps_spring.aop.metier_spring.IMetier;
import apps_spring.aop.metier_spring.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class DemoAopApplication implements CommandLineRunner {

	@Autowired
	MyMessage myMessage;

	@Autowired
	IMetierBanque metierBanque;

	@Autowired
	IMetier metier;

	    
	public static void main(String[] args) {
		SpringApplication.run(DemoAopApplication.class, args);
		//ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		// IMetier metier = applicationContext.getBean(IMetier.class);
		
	}
	
	
	@Override
	public void run(String...args) throws Exception {
		// runMessage();
		// start();
		try {
			SecurityContext.authenticate("root", "1234", new String[]{"USER", "ADMIN"});
			doAspectSpring();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public void doAspectSpring() {
		metier.process();
		System.out.println(metier.compute());
	}
	
	public void runMessage() {
		myMessage.showMessage();
		myMessage.showMessageSecond();		
	}

	public void start() {
		System.out.println("Démarrage de l'application");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Donner le code du compte");
		Long code = scanner.nextLong();
		System.out.println("vous avez entrez #code: " + code);
		System.out.println("Donner le solde initial du compte: ");
		double solde = scanner.nextDouble();
		
		// if call is used in Aspect, here the pointCut is added 
		metierBanque.addCompte(new Compte(code, solde));

		boolean appFlag = true;
		while (appFlag) {
			try {
				System.out.println("Type Opération: ");
				String type = scanner.next();

				if (type.equals("q")) {
					System.out.println("quitter en cours");
					System.exit(0);		
				}

				System.out.println("Montant: ");
				double montant = scanner.nextDouble();
				
				if (type.equals("v")) 
					metierBanque.verser(code, montant);
				else if (type.equals("r"))
					metierBanque.retirer(code, montant);
				
				Compte compte = metierBanque.consulter(code);
				System.out.println("Solde = " + compte.toString());

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
