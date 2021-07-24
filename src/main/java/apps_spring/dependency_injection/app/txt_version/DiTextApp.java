package apps_spring.dependency_injection.app.txt_version;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Scanner;

import apps_spring.dependency_injection.dao.IDao;
import apps_spring.dependency_injection.dao.impl.DaoImpl;
import apps_spring.dependency_injection.metier.IMetier;
import apps_spring.dependency_injection.metier.impl.MetierImpl;

public class DiTextApp {

	public static void main(String[] args) {
		DiTextApp app = new DiTextApp();

		 try {
			 
			// app.instatiateAsStaticMethod();
			//
			 app.instantiateAsDynamiqueTechnique();

		} catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Dependencies Injection by static instantiation 
	 * > implementation known at COMPILE TIME
	 */
	public void instatiateAsStaticMethod() {
		/*
		 * Tip 1 ----- with 'new' keyword, the jvm load an object of type <Class> with the
		 * name argument then with the object Class<Name> jvm create a new instance of
		 * object of type<T> that extends Object
		 * 
		 * Tip 2 ----- All classes and Interfaces are loaded in memory as objects of type Class
		 * 
		 */
		DaoImpl dao = new DaoImpl();
		MetierImpl metier = new MetierImpl();
		metier.setDao(dao);
		System.out.println(new String(Character.toChars(0x1F6A9)) + " " + metier.calcul());

	}

	/**
	 * Dependencies Injection by dynamic instantiation 
	 * > implementation known at RUNTIME
	 */
	public void instantiateAsDynamiqueTechnique()
			throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
		{

		File file = Paths.get("src/main/java/apps_spring.dependency_injection/config.txt").toFile();
		Scanner scanner = new Scanner(file);

		// ==================== DAO ================================== 
		// 3 lines alternative to 'new' keyword 
		String daoClassName = scanner.nextLine();
		// ? is a wildcard in java like * in kotlin/unix
		// load object of type CLASS<daoClassName> in memory like JDBC pilot/api
		Class<?> daoClass = Class.forName(daoClassName); // can throws ClassNotFoundException
		IDao dao = (IDao) daoClass.getDeclaredConstructor().newInstance();
		// ===========================================================

		//===================== METIER ===============================
		String metierClasseName = scanner.nextLine();
		Class<?> metierClass = Class.forName(metierClasseName);
		IMetier metier = (IMetier) metierClass.getConstructor().newInstance();
		//============================================================

		//===================== setDao(dao) ===============================
		// Create Method Object to enable a call of method metier.setDao();
		Method method = metierClass.getMethod("setDao", IDao.class);
		// Inject dependency
		method.invoke(metier, dao);
		//=================================================================

		System.out.println(new String(Character.toChars(0x1F680)) + " " + daoClassName);
		System.out.println(new String(Character.toChars(0x1F6A9)) + " " + metier.calcul());

	}


}
