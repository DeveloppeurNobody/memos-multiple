package apps_spring.aop.metier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MetierBanqueImpl implements IMetierBanque{
    private Map<Long, Compte> comptesMap = new HashMap<>();

    @Override
    public void addCompte(Compte compte) {
       comptesMap.put(compte.getCode(), compte);
    }

    @Override
    public void verser(Long code, double montant) {
        Compte compte = comptesMap.get(code);
        compte.setSolde(compte.getSolde() + montant);
    }

    @Override
    public void retirer(Long code, double montant) {
        Compte compte = comptesMap.get(code);
        compte.setSolde(compte.getSolde() - montant);
    }

    @Override
    public Compte consulter(Long code) {
        return comptesMap.get(code);
    }

    
}
