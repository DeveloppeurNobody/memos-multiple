package apps_spring.dependency_injection.ext;

import apps_spring.dependency_injection.dao.IDao;

/**
 * an update package
 * a new class in this new package
 */
//@Component
public class DaoImplV2 implements IDao {

	@Override
	public double getData() {
		/**
		 * Version WS
		 */
		System.out.println(new String(Character.toChars(0x1F6A9)) + " Version web service");
		double data = 12;
		return data;
	}

	
}
