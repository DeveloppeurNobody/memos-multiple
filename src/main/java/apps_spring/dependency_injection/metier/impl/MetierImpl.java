package apps_spring.dependency_injection.metier.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import apps_spring.dependency_injection.dao.IDao;
import apps_spring.dependency_injection.metier.IMetier;

@Component
public class MetierImpl implements IMetier {
	
	/**
	 * Couplage faible
	 */
	@Autowired
	private IDao dao;

	public MetierImpl() {
		System.out.println("MetierImpl()");
	}

	@Override
	public double calcul() {
		double d = dao.getData();
		double res = d * 23;
		return d;
	}

	// Setter pour permettre le couplage faible
	public void setDao(IDao dao) {
		System.out.println("Inject Dao");
		this.dao = dao;
	}
}
