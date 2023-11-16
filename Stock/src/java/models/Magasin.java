package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionBase;

public class Magasin extends ModelDAO {

    private int idMagasin;
    private String nom;

    public Magasin() {}

    public Magasin(int idMagasin, String nom) {
        this.idMagasin = idMagasin;
        this.nom = nom;
    }
    
    public int getIdMagasin() {
        return idMagasin;
    }
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

        public static Magasin getArticleById(ConnectionBase connex,int idMagasin) throws ClassNotFoundException, SQLException {
        Magasin mgs=new Magasin();
        Connection sqlCon=connex.giveConSql(); 
        String query="select * from Magasin where idMagasin="+idMagasin;
        System.out.println(query);
        try {
            PreparedStatement stt=sqlCon.prepareStatement(query);
            ResultSet res=stt.executeQuery();
            while (res.next()) {
                mgs.setIdMagasin(res.getInt(1));
                mgs.setNom(res.getString(2));
            }
            sqlCon.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return mgs;
    } 

    
    
}
