package models;

public class Sortie_dEntres extends ModelDAO {
    
    private int idsortie_dentres;
    private float quantite;
    private int identrestock;
    private int idsortiestock;

    public Sortie_dEntres(int idsortie_dentres, float quantite, int identrestock, int idsortiestock) {
        this.idsortie_dentres = idsortie_dentres;
        this.quantite = quantite;
        this.identrestock = identrestock;
        this.idsortiestock = idsortiestock;
    }

    public int getIdsortie_dentres() {
        return idsortie_dentres;
    }

    public void setIdsortie_dentres(int idsortie_dentres) {
        this.idsortie_dentres = idsortie_dentres;
    }

    public float getQuantite() {
        return quantite;
    }

    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }

    public int getIdentrestock() {
        return identrestock;
    }

    public void setIdentrestock(int identrestock) {
        this.identrestock = identrestock;
    }

    public int getIdsortiestock() {
        return idsortiestock;
    }

    public void setIdsortiestock(int idsortiestock) {
        this.idsortiestock = idsortiestock;
    }

    public Sortie_dEntres() {}

}
