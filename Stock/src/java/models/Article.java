
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionBase;

public class Article extends ModelDAO{
    private int idArticle;
    private String encodage;
    private String unite;
    private int idTypeSortie;


    
    public Article() {}
    
    public Article(int idArticle, String encodage, String unite, int idTypeSortie) {
        this.idArticle = idArticle;
        this.encodage = encodage;
        this.unite = unite;
        this.idTypeSortie = idTypeSortie;
    }
    public int getIdArticle() {
        return idArticle;
    }
    public String getEncodage() {
        return encodage;
    }
    public String getUnite() { 
        return unite;
    }
    public int getIdTypeSortie() {
        return idTypeSortie;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setEncodage(String encodage) {
        this.encodage = encodage;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setIdTypeSortie(int idTypeSortie) {
        this.idTypeSortie = idTypeSortie;
    }

    public static Article getArticleById(ConnectionBase connex,int idArticle2) throws ClassNotFoundException, SQLException {
        Article arc=new Article();
        Connection sqlCon=connex.giveConSql(); 
        String query="select * from Article where idArticle="+idArticle2;
        System.out.println(query);
        try {
            PreparedStatement stt=sqlCon.prepareStatement(query);
            ResultSet res=stt.executeQuery();
            while (res.next()) {
                arc.setIdArticle(res.getInt(1));
                arc.setEncodage(res.getString(2));
                arc.setUnite(res.getString(3));
                arc.setIdTypeSortie(res.getInt(4));
                System.out.println(res.getInt(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getString(4));
            }
            sqlCon.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arc;
    }    

    

}
