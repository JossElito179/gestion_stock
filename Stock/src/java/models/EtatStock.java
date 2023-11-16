package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionBase;

public class EtatStock extends ModelDAO{
    
    private int idArtcle;
    private float quantite_initial;
    private float quantite_actuel;
    private float prix_moyen;
    private float prix_total;
    private int idMagasin;
    private Article article;
    private Magasin magasin;
    public int getIdArtcle() {
        return idArtcle;
    }
    public void setIdArtcle(int idArtcle) {
        this.idArtcle = idArtcle;
    }
    public float getQuantite_initial() {
        return quantite_initial;
    }
    public void setQuantite_initial(float quantite_initial) {
        this.quantite_initial = quantite_initial;
    }
    public float getQuantite_actuel() {
        return quantite_actuel;
    }
    public void setQuantite_actuel(float quantite_actuel) {
        this.quantite_actuel = quantite_actuel;
    }
    public float getPrix_moyen() {
        return prix_moyen;
    }
    public void setPrix_moyen(float prix_moyen) {
        this.prix_moyen = prix_moyen;
    }
    public float getPrix_total() {
        return prix_total;
    }
    public void setPrix_total(float prix_total) {
        this.prix_total = prix_total;
    }
    public int getIdMagasin() {
        return idMagasin;
    }
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public Magasin getMagasin() {
        return magasin;
    }
    
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
    public EtatStock(int idArtcle, float quantite_initial, float quantite_actuel, float prix_moyen, float prix_total,
            int idMagasin) {
        this.idArtcle = idArtcle;
        this.quantite_initial = quantite_initial;
        this.quantite_actuel = quantite_actuel;
        this.prix_moyen = prix_moyen;
        this.prix_total = prix_total;
        this.idMagasin = idMagasin;
    }
    public EtatStock() {
    }

    public static float getResteActuel(ConnectionBase connex,int idartcile,int idMagasin) throws Exception{
        Connection dbc=connex.giveConSql();
        String sqlQuery ="SELECT v_quantite_article.reste - sum_per_sortie.sum as reste from v_quantite_article join sum_per_sortie on sum_per_sortie.idArticle=v_quantite_article.idArticle where v_quantite_article.idArticle="+idartcile+" and v_quantite_article.idMagasin="+idMagasin;
        String altersql="SELECT reste from v_quantite_article  where idArticle="+idartcile+" and idMagasin="+idMagasin; 
        System.out.println(sqlQuery);
        System.out.println(altersql);
        ;Float reste=Float.valueOf("1");
        String s="0s";
        try {
            PreparedStatement ps=dbc.prepareStatement(sqlQuery);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                s=resultSet.getString(1);
                System.out.println(s);
            }        
            if ("0s".equals(s)) {
                PreparedStatement pst2=dbc.prepareStatement(altersql);
                ResultSet resultSet2 = pst2.executeQuery();
                while (resultSet2.next()) {
                s=resultSet2.getString(1);
                }
            }
            dbc.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (s.equals("0s")) {
            throw new Exception("Quantite inseree trop grande");
        }
        reste=Float.valueOf(s);
        return reste;
    }

}


// Infos:   SELECT v_quantite_article.reste - sum_per_sortie.sum as reste from v_quantite_article join sum_per_sortie on sum_per_sortie.idArticle=v_quantite_article.idArticle where v_quantite_article.idArticle=1and where idMagasin=1
// SELECT v_quantite_article.reste - sum_per_sortie.sum as reste from v_quantite_article join sum_per_sortie on sum_per_sortie.idArticle=v_quantite_article.idArticle where v_quantite_article.idArticle=1 and v_quantite_article.idMagasin=1;