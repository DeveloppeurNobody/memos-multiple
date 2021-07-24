package apps_spring.dependency_injection.dao.impl;

import org.springframework.stereotype.Component;

import apps_spring.dependency_injection.dao.IDao;


/**
 * ClassImpl on met la casquette de developpeur
 */
@Component
public class DaoImpl implements IDao {

	public DaoImpl() {
		System.out.println("DaoImpl()");
	}
	
	@Override
	public double getData() {
		double data = 98;
		return data;
	}
	
}
