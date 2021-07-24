package apps_spring.aop.metier;

public interface IMetierBanque {
    public void addCompte(Compte compte);
    public void verser(Long code, double montant);
    public void retirer(Long code, double montant);

    public Compte consulter(Long code);
}
