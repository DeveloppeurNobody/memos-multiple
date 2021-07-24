package apps_spring.aop.metier;

public class Compte {
    private Long code;
    private double solde;


    public Compte() {
    }

    public Compte(Long code, double solde) {
        this.code = code;
        this.solde = solde;
    }

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Compte code(Long code) {
        setCode(code);
        return this;
    }

    public Compte solde(double solde) {
        setSolde(solde);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + getCode() + "'" +
            ", solde='" + getSolde() + "'" +
            "}";
    }

}
