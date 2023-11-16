package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connection.ConnectionBase;

public class SortiedEntre {
    public int getIdMagasin() {
        return idMagasin;
    }
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    private int idArticle;
    private int identrestock;
    private Timestamp date_entre;
    private int idMagasin;
    private float reste;
    private double prix_unitaire;
    

    public SortiedEntre(int idArticle, int identrestock, Timestamp date_entre, int idMagasin, float reste) {
        this.idArticle = idArticle;
        this.identrestock = identrestock;
        this.date_entre = date_entre;
        this.idMagasin = idMagasin;
        this.reste = reste;
    }

    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    public Timestamp getDate_entre() {
        return date_entre;
    }
    public void setDate_entre(Timestamp date_entre) {
        this.date_entre = date_entre;
    }

    public int getIdentrestock() {
        return identrestock;
    }
    public void setIdentrestock(int identrestock) {
        this.identrestock = identrestock;
    }
    public Timestamp getDateentre() {
        return date_entre;
    }
    public void setDateentre(Timestamp dateentre) {
        this.date_entre = dateentre;
    }
    public float getReste() {
        return reste;
    }
    public void setReste(float reste) {
        this.reste = reste;
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }
    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    

    public SortiedEntre(){}

    public static String requestCategorire(int idArticle,int idMagasin,String condition){
        return "SELECT DISTINCT\r\n" + //
                "    entre_stock.idArticle,\r\n" + //
                "    entre_stock.IdEntreStock,\r\n" + //
                "    entre_stock.date_entre,\r\n" + //
                "    entre_stock.idMagasin,\r\n" + //
                "    entre_stock.quantite - Sortie_dEntres.quantite AS reste\r\n" + //
                "FROM entre_stock\r\n" + //
                "JOIN Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.IdEntreStock\r\n" + //
                " where entre_Stock.idMagasin="+idMagasin+" and entre_Stock.idArticle="+idArticle+" \r\n" + //
                " and entre_stock.quantite>0 ORDER BY entre_stock.date_entre "+condition;
    }

    public static ArrayList<SortiedEntre> getAllResteEntre(ConnectionBase connex ,int idArticle,int idMagasin) throws ClassNotFoundException, SQLException{
        Article article = Article.getArticleById(connex, idArticle);
        ArrayList<SortiedEntre> reste_entre=new ArrayList<>();
        Connection dbcon=connex.giveConSql();
        String querysql="";
        System.out.print(article.getIdTypeSortie()+" Type sortie");
        if (article.getIdTypeSortie()==1) {
            querysql+=SortiedEntre.requestCategorire(idArticle, idMagasin,"DESC");
            System.out.println("hitany desc 1");
        }else if (article.getIdTypeSortie()==2){
            querysql+=SortiedEntre.requestCategorire(idArticle,idMagasin,"ASC");
            System.out.println("hitany asc 1");

            System.out.println(querysql);
        }
        PreparedStatement stt=dbcon.prepareStatement(querysql);
        ResultSet rs = stt.executeQuery();
        while (rs.next()) {
            reste_entre.add(new SortiedEntre(rs.getInt(1),rs.getInt(2),rs.getTimestamp(3),rs.getInt(4),rs.getFloat(5)));
        }
        dbcon.close();
        return reste_entre;
    }
    
    public static String finalizeStocksRequest(int idArticle,int IdMagasin,String condition){
        String query="SELECT\r\n" + //
                "    entre_stock.idArticle,\r\n" + //
                "    entre_stock.identrestock,\r\n" + //
                "    entre_stock.date_entre,\r\n" + //
                "    entre_stock.IdMagasin,\r\n" + //
                "    entre_stock.quantite\r\n" + //
                "FROM\r\n" + //
                "    entre_stock\r\n" + //
                "LEFT JOIN\r\n" + //
                "    Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.identrestock\r\n" + //
                "WHERE\r\n" + //
                "    Sortie_dEntres.identrestock IS NULL and idMagasin="+IdMagasin+"and idArticle="+idArticle+"\r\n" + //
                "and entre_stock.quantite>0 ORDER BY entre_stock.date_entre "+condition;

        return query;
    }

    public static void arangeTable(ArrayList<SortiedEntre> resteEntres, int idTypesortie){
        SortiedEntre place;
        if (idTypesortie==1) {
            for (int i = 0; i < resteEntres.size(); i++) {
                for (int j = i+1; j < resteEntres.size(); j++) {
                    if (resteEntres.get(i).getDate_entre().after(resteEntres.get(j).getDate_entre()) ) {
                        place=resteEntres.get(i);
                        resteEntres.set(i,resteEntres.get(j));
                        resteEntres.set(j, place);
                    }
                }
            }
        }else if(idTypesortie==2){
            for (int i = 0; i < resteEntres.size(); i++) {
                for (int j = i+1; j < resteEntres.size(); j++) {
                    if (resteEntres.get(i).getDate_entre().after(resteEntres.get(j).getDate_entre()) ) {
                        place=resteEntres.get(j);
                        resteEntres.set(j,resteEntres.get(i));
                        resteEntres.set(i, place);
                    }
                }
            }
        }
    }

    public static int getIDMaxSortie(ConnectionBase connex) throws ClassNotFoundException, SQLException{
        Connection condb=connex.giveConSql();
        int rep=0;
        try {
            PreparedStatement prstt=condb.prepareStatement("select max(idSortiestock) from sortie_stock");
            ResultSet res=prstt.executeQuery();
            while (res.next()) {
                rep=res.getInt(1);
            }
            condb.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return rep;
    }

    public static ArrayList<SortiedEntre> getAllresteByEntreStock(ConnectionBase connex,int idArtcile,int idMagasin) throws ClassNotFoundException, SQLException{
        ArrayList<SortiedEntre> reste_entre= SortiedEntre.getAllResteEntre(connex, idArtcile, idMagasin);
        Article arc=Article.getArticleById(connex, idArtcile);
        String query="";
        if (arc.getIdTypeSortie()==1) {
           query+=SortiedEntre.finalizeStocksRequest(idArtcile, idMagasin,"DESC");
           System.out.println("hitany desc 2");
        }else if (arc.getIdTypeSortie()==2){
           query+=SortiedEntre.finalizeStocksRequest(idArtcile, idMagasin,"ASC");
            System.out.println("hitany  asc 2");
        }
        System.out.println(query);
        Connection dbcon=connex.giveConSql();
        PreparedStatement stt=dbcon.prepareStatement(query);
        ResultSet rs = stt.executeQuery();
        while (rs.next()) {
            reste_entre.add(new SortiedEntre(rs.getInt(1),rs.getInt(2),rs.getTimestamp(3),rs.getInt(4),rs.getFloat(5)));
        }
        dbcon.close();
        SortiedEntre.arangeTable(reste_entre,arc.getIdTypeSortie());
        return reste_entre;
    }

    public static void InsertionSortieDentre(ConnectionBase connex,int idArtcile,float quantite,double prix_u,double prix_t,Timestamp dateSortie,int idMagasin) throws Exception{
        float quantiteR =EtatStock.getResteActuel(connex, idArtcile, idMagasin);
        if (quantite<quantiteR) {
            Sortie_stock sortie=new Sortie_stock(0,dateSortie,quantite,prix_u,prix_t,idMagasin,idArtcile);
            sortie.insertDAO(connex, "*");
            ArrayList<SortiedEntre> sortie_DEntres=SortiedEntre.getAllresteByEntreStock(connex, idArtcile, idMagasin);
            int idSortie=SortiedEntre.getIDMaxSortie(connex);
            System.out.println("idSortie"+idSortie);
            System.out.println("indexReste"+sortie_DEntres.size());
            int indexReste=sortie_DEntres.size();
            float restant=quantite;
            int i=0;
            while (indexReste>0) {
                if (sortie_DEntres.get(i).getReste()>=restant) {
                    System.out.println("Yeeee");
                    Sortie_dEntres str=new Sortie_dEntres(0,restant,sortie_DEntres.get(i).getIdentrestock(),idSortie);
                    str.insertDAO(connex, "*");
                    indexReste=0;
                }else if(sortie_DEntres.get(i).getReste()<restant) {
                    System.out.println("Mbola ee");
                    Sortie_dEntres str=new Sortie_dEntres(0,sortie_DEntres.get(i).getReste(),sortie_DEntres.get(i).getIdentrestock(),idSortie);                    
                    restant=restant-sortie_DEntres.get(i).getReste();
                    str.insertDAO(connex, "*");
                    i++;
                }
            }
        }else{
            throw new Exception("Quantite trop grande");
        }
    }

    public static String requestCategorie2(int idArticle,int idMagasin,Timestamp date1, Timestamp date2){
        return "SELECT DISTINCT\r\n" + //
                "    entre_stock.idArticle,\r\n" + //
                "    entre_stock.IdEntreStock,\r\n" + //
                "    entre_stock.date_entre,\r\n" + //
                "    entre_stock.idMagasin,\r\n" + //
                "    entre_stock.quantite - Sortie_dEntres.quantite AS reste\r\n" + //
                "FROM entre_stock\r\n" + //
                "JOIN Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.IdEntreStock\r\n" + //
                "JOIN Sortie_stock ON sortie_stock.IdsortieStock = Sortie_dEntres.IdsortieStock\r\n" + //
                " where entre_Stock.idMagasin="+idMagasin+" and entre_Stock.idArticle="+idArticle+" \r\n" + //
                " and entre_stock.quantite - Sortie_dEntres.quantite >0 and entre_stock.date_entre<='"+date2+"'\r\n" +
                "and sortie_stock.date_sortie<='"+date2+"'";
    }

    public static String finalizeStockperEntre(int idArticle,int idMagasin,Timestamp date1, Timestamp date2){
        String query="SELECT\r\n" + //
                "    entre_stock.idArticle,\r\n" + //
                "    entre_stock.identrestock,\r\n" + //
                "    entre_stock.date_entre,\r\n" + //
                "    entre_stock.IdMagasin,\r\n" + //
                "    entre_stock.quantite\r\n" + //
                "FROM\r\n" + //
                "    entre_stock\r\n" + //
                "LEFT JOIN\r\n" + //
                "    Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.identrestock\r\n" + //
                "WHERE\r\n" + //
                "    Sortie_dEntres.identrestock IS NULL and idMagasin="+idMagasin+"and idArticle="+idArticle+"\r\n" + //
                "and entre_stock.quantite>0 and entre_stock.date_entre<='"+date2+"'";

        return query;
    }

    public static String getQuantiteInter(int idArticle,int idMagasin,Timestamp date1, Timestamp date2){
       return "SELECT DISTINCT\r\n" + //
                "    entre_stock.idArticle,\r\n" + //
                "    entre_stock.IdEntreStock,\r\n" + //
                "    entre_stock.date_entre,\r\n" + //
                "    entre_stock.idMagasin,\r\n" + //
                "    entre_stock.quantite reste\r\n" + //
                "FROM entre_stock\r\n" + //
                " where entre_Stock.idMagasin="+idMagasin+" and entre_Stock.idArticle="+idArticle+" \r\n" + //
                " and entre_stock.quantite>0 and entre_stock.date_entre<='"+date2+"'";
    }


    public static ArrayList<SortiedEntre> sortiedEntresCondList(ConnectionBase connex,int idArticled,int idMagasin,Timestamp date1, Timestamp date2) throws ClassNotFoundException, SQLException{
        ArrayList<SortiedEntre> reste_entre= SortiedEntre.getAllEntresResteCond(connex, idArticled, idMagasin,date1,date2);
        Article arc=Article.getArticleById(connex, idArticled);
        String query=SortiedEntre.finalizeStockperEntre(idArticled, idMagasin,date1,date2);
        System.out.println(query);
        Connection dbcon=connex.giveConSql();
        PreparedStatement stt=dbcon.prepareStatement(query);
        ResultSet rs = stt.executeQuery();
        while (rs.next()) {
            reste_entre.add(new SortiedEntre(rs.getInt(1),rs.getInt(2),rs.getTimestamp(3),rs.getInt(4),rs.getFloat(5)));
        }
        dbcon.close();
        SortiedEntre.arangeTable(reste_entre,arc.getIdTypeSortie());
        return reste_entre;
    }
    
    public static ArrayList<SortiedEntre> getAllEntresResteCond(ConnectionBase connex ,int idArticle,int idMagasin,Timestamp date1,Timestamp date2) throws ClassNotFoundException, SQLException{
        ArrayList<SortiedEntre> reste_entre=new ArrayList<>();
        Connection dbcon=connex.giveConSql();
        
        String querysql=SortiedEntre.requestCategorie2(idArticle,idMagasin,date1,date2);
        System.out.println(querysql);
        PreparedStatement stt=dbcon.prepareStatement(querysql);
        ResultSet rs = stt.executeQuery();
        while (rs.next()) {
            reste_entre.add(new SortiedEntre(rs.getInt(1),rs.getInt(2),rs.getTimestamp(3),rs.getInt(4),rs.getFloat(5)));
        }
        dbcon.close();
        if (reste_entre.size()==0) {
            System.out.println("tintintintin");
            if (SortiedEntre.getSum_perSortie2(connex,idArticle,idMagasin,date2)==0) {
                Connection dbcon2=connex.giveConSql();
                String querysql2=SortiedEntre.getQuantiteInter(idArticle, idMagasin, date1, date2);
                System.out.println(querysql2+"tenametyee"); 
                PreparedStatement stt2=dbcon2.prepareStatement(querysql2);
                ResultSet rs2 = stt2.executeQuery();
                while (rs2.next()) {
                    System.out.println("tafiditra");
                    reste_entre.add(new SortiedEntre(rs2.getInt(1),rs2.getInt(2),rs2.getTimestamp(3),rs2.getInt(4),rs2.getFloat(5)));
                }
                dbcon2.close();
            }    
        }
        return reste_entre;
    }

    public static void getAllEntreStockPrixUnitaire(ConnectionBase condb,ArrayList<SortiedEntre> sortiedEntres) throws ClassNotFoundException, SQLException{
        Connection connex=condb.giveConSql();
        for (int i = 0; i < sortiedEntres.size(); i++) {
            String query="select prix_unitaire from entre_stock where identrestock="+sortiedEntres.get(i).getIdentrestock();
            PreparedStatement stts=connex.prepareStatement(query);
            ResultSet res=stts.executeQuery();
            while (res.next()) {
                sortiedEntres.get(i).setPrix_unitaire(res.getDouble(1));
            }
        }
        connex.close();
    }
    
    public static float getFinalquantite(ConnectionBase connex,int idArtcile,int idMagasin,Timestamp date1) throws ClassNotFoundException, SQLException{
        Connection connection=connex.giveConSql();
        float rep=0;
        String query="select sum(entre_stock.quantite)-sum(sortie_stock.quantite) as reste\r\n" + //
        " from entre_stock join sortie_stock on entre_stock.idArticle=sortie_stock.idArticle \r\n" + //
        " where entre_stock.idArticle="+idArtcile+ "\r\n" + //
        "and entre_stock.idMagasin="+idMagasin+"and sortie_stock.date_sortie<'"+date1+"'";
        PreparedStatement stts=connection.prepareStatement(query);
        System.out.println(query);
        ResultSet res=stts.executeQuery();
        while (res.next()) {
            rep=res.getFloat("reste");                    
        }
        connection.close();
        return rep;
    }

    public static float getSum_perSortie(ConnectionBase connex,int idArtcile,int idMagasin,Timestamp date1) throws ClassNotFoundException, SQLException{
        Connection connection=connex.giveConSql();
        float rep=0;
        String query="select sum \r\n" + //
        " from sum_per_sortie join sortie_stock on sum_per_sortie.idArticle=sortie_stock.idArticle \r\n" + //
        " where sortie_stock.idArticle="+idArtcile+ "\r\n" + //
        "and sum_per_sortie.idMagasin="+idMagasin+"and sortie_stock.date_sortie<'"+date1+"'";
        PreparedStatement stts=connection.prepareStatement(query);
        System.out.println(query);
        ResultSet res=stts.executeQuery();
        while (res.next()) {
            rep=res.getFloat("sum");                    
        }
        connection.close();
        return rep;
    }

        public static float getSum_perSortie2(ConnectionBase connex,int idArtcile,int idMagasin,Timestamp date2) throws ClassNotFoundException, SQLException{
        Connection connection=connex.giveConSql();
        float rep=0;
        String query="select sum \r\n" + //
        " from sum_per_sortie join sortie_stock on sum_per_sortie.idArticle=sortie_stock.idArticle \r\n" + //
        " where sortie_stock.idArticle="+idArtcile+ "\r\n" + //
        "and sum_per_sortie.idMagasin="+idMagasin+"and sortie_stock.date_sortie<'"+date2+"'";
        PreparedStatement stts=connection.prepareStatement(query);
        System.out.println(query);
        ResultSet res=stts.executeQuery();
        while (res.next()) {
            rep=res.getFloat("sum");                    
        }
        connection.close();
        return rep;
    }


    public static float getFinalFibalizequantite(ConnectionBase connex,int idArtcile,int idMagasin,Timestamp date1) throws ClassNotFoundException, SQLException{
        Connection connection=connex.giveConSql();
        float rest=SortiedEntre.getFinalquantite(connex, idArtcile, idMagasin, date1);
        float sortie=SortiedEntre.getSum_perSortie(connex, idArtcile, idMagasin, date1);
        if (rest==0 && sortie==0) {
        String query="select sum(entre_stock.quantite) as reste\r\n" + //
            " from entre_stock \r\n" + //
            " where idArticle="+idArtcile+ "\r\n" + //
            "and idMagasin="+idMagasin+"and date_entre<'"+date1+"'";
            PreparedStatement stts=connection.prepareStatement(query);
            System.out.println(query);
            ResultSet res=stts.executeQuery();
            while (res.next()) {
                rest=res.getFloat("reste");                    
            }
            connection.close();   
        }
        return rest;
    }

    public static EtatStock createEtatStock(ConnectionBase connex,int idArtcile,int idMagasin,Timestamp date1,Timestamp date2) throws ClassNotFoundException, SQLException{
        EtatStock stocks=new EtatStock();
        Article arc=Article.getArticleById(connex, idArtcile);
        Magasin mgs=Magasin.getArticleById(connex, idMagasin);
        Double prix_total=new Double("0");
        float quantite_actuel=0;
        ArrayList<SortiedEntre> sortie_dEntres=SortiedEntre.sortiedEntresCondList(connex, idArtcile, idMagasin, date1, date2);
        SortiedEntre.getAllEntreStockPrixUnitaire(connex, sortie_dEntres);
        for (int i = 0; i < sortie_dEntres.size(); i++) {
            prix_total+=sortie_dEntres.get(i).getPrix_unitaire()*sortie_dEntres.get(i).getReste();
            quantite_actuel+=sortie_dEntres.get(i).getReste();
        }
        stocks.setIdArtcle(idArtcile);
        stocks.setQuantite_initial(SortiedEntre.getFinalFibalizequantite(connex, idArtcile, idMagasin, date1));
        stocks.setQuantite_actuel(quantite_actuel);
        stocks.setPrix_moyen(Float.valueOf(prix_total.toString())/quantite_actuel);
        stocks.setPrix_total(Float.valueOf(prix_total.toString()));
        stocks.setIdMagasin(idMagasin);
        stocks.setArticle(arc);
        stocks.setMagasin(mgs);
        return stocks;
    }

    // not conditionner

    public static ArrayList<CoupleID> getAllEntresResteCond(ConnectionBase connex ,Timestamp date1,Timestamp date2) throws ClassNotFoundException, SQLException{
        ArrayList<CoupleID> reste_entre=new ArrayList<>();
        String querysql="select distinct idArticle , idMagasin from entre_stock ";
        Connection dbcon=connex.giveConSql();
        PreparedStatement stt=dbcon.prepareStatement(querysql);
        ResultSet rs = stt.executeQuery();
        while (rs.next()) {
            reste_entre.add(new CoupleID(rs.getInt(1), rs.getInt(2)));
        }
        dbcon.close();
        return reste_entre;
    }


    public static ArrayList<EtatStock> createAlEtatStocks(ConnectionBase connex,Timestamp date1,Timestamp date2) throws ClassNotFoundException, SQLException{
        ArrayList<EtatStock> stocks = new ArrayList<EtatStock>();
        ArrayList<CoupleID> sortiedEntres=SortiedEntre.getAllEntresResteCond(connex, date1, date2);
        for (CoupleID sortiedEntre : sortiedEntres) {
            stocks.add(SortiedEntre.createEtatStock(connex, sortiedEntre.getIdArtcile(), sortiedEntre.getIdMagasin(), date1, date2));
        }
        return stocks;
    }

    public static float sum_total(ArrayList<EtatStock> stocks){
        float sum_total=0;
        for (EtatStock etatStock : stocks) {
            sum_total+=etatStock.getPrix_total();
        }
        return sum_total;
    }

}