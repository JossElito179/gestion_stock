package models;

import java.sql.Timestamp;

public class Entre_stock extends ModelDAO {

    private int IDEntreStock;
    private Timestamp Date_entre;
    private float quantite;
    private double prix_unitaire;
    private double prix_total;
    private int IdMagasin;
    private int IdArticle;

    public Entre_stock() {}

    public Entre_stock(int iDEntreStock, Timestamp Date_entre, float quantite, double prix_unitaire, double prix_total,int idMagasin, int idArticle) {
        IDEntreStock = iDEntreStock;
        this.Date_entre = Date_entre;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.prix_total = prix_total;
        IdMagasin = idMagasin;
        IdArticle = idArticle;
    }

    public int getIDEntreStock() {
        return IDEntreStock;
    }
    public void setIDEntreStock(int iDEntreStock) {
        IDEntreStock = iDEntreStock;
    }
    public Timestamp getDate_entre() {
        return Date_entre;
    }
    public void setDate_entre(Timestamp Date_entre) {
        this.Date_entre = Date_entre;
    }
    public float getQuantite() {
        return quantite;
    }
    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }
    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public double getPrix_total() {
        return prix_total;
    }
    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }
    public int getIdMagasin() {
        return IdMagasin;
    }
    public void setIdMagasin(int idMagasin) {
        IdMagasin = idMagasin;
    }
    public int getIdArticle() {
        return IdArticle;
    }
    public void setIdArticle(int idArticle) {
        IdArticle = idArticle;
    }

    

}
