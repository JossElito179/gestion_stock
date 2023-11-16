package models;


import java.sql.Timestamp;

public class Sortie_stock extends ModelDAO {
    
    private int IdSortieStock;
    private Timestamp date_sortie;
    private float quantite;
    private double prix_unitaire;
    private double prix_total;
    private int IdMagasin;
    private int IdArticle;

    public Sortie_stock() {}

    public Sortie_stock(int IdSortieStock,java.sql.Timestamp dateSortie, float quantite, double prix_unitaire, double prix_total,int idMagasin, int idArticle) {
        this.IdSortieStock = IdSortieStock;
        this.date_sortie = dateSortie;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.prix_total = prix_total;
        this.IdMagasin = idMagasin;
        this.IdArticle = idArticle;
    }

    public int getIdSortieStock() {
        return IdSortieStock;
    }
    public void setIdSortieStock(int IdSortieStock) {
        this.IdSortieStock = IdSortieStock;
    }
    public Timestamp getdate_sortie() {
        return date_sortie;
    }
    public void setdate_sortie(Timestamp date_sortie) {
        this.date_sortie = date_sortie;
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
