<%-- 
    Document   : EtatStockCondition
    Created on : 14 nov. 2023, 22:48:23
    Author     : joss
--%>

<%@page import="java.sql.Timestamp"%>
<%@page import="models.EtatStock"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/styleEntre.css">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
           EtatStock stocks=(EtatStock)request.getAttribute("stock");
             Timestamp date1=(Timestamp)request.getAttribute("date1");
             Timestamp date2=(Timestamp)request.getAttribute("date2");
%>
        <aside id="sidebar" class="sidebar">

            <ul class="sidebar-nav" id="sidebar-nav">
              <li class="nav-item">
                <a class="nav-link" href="EtatStockCondition.jsp">
                  <i class="bi bi-grid"></i>
                  <span class="titre">Stock</span>
                </a>
              </li>
              
              <li class="nav-item">
                <a class="nav-link collapsed" href="index.jsp">
                  <i class="bi bi-grid"></i>
                  <span>Entre de Sotck</span>
                </a>
              </li>
        
              <li class="nav-item">
                <a class="nav-link collapsed" href="Sortiestock.jsp">
                  <i class="bi bi-grid"></i>
                  <span>Sortie de stock</span>
                </a>
              </li>
      <li class="nav-item">
        <a class="nav-link collapsed" href="ChoixEtatStock.jsp">
          <i class="bi bi-grid"></i>
          <span>Filtre etat de stock</span>
        </a>
      </li>
      
      <li class="nav-item">
        <a class="nav-link collapsed" href="EtatStock.jsp">
          <i class="bi bi-grid"></i>
          <span>Voir etat de stock</span>
        </a>
      </li>
            </ul>
          </aside>

        <div class="card">
        <div class="card-body">
            <h5 class="card-title">Etat de Stock</h5>
            <p><span>Date de debut :<%= date1 %></span></p>
            <p><span>Date de fin:<%= date2 %></span></p>
            <!-- Table with stripped rows -->
            <table border="1" class="table table-striped">
              <thead>
                <tr>
                  <th scope="col">Article</th>
                  <th scope="col">Quantité initial</th>
                  <th scope="col">Quantité restant</th>
                  <th scope="col">Prix unitaire moyen</th>
                  <th scope="col">Prix total</th>
                  <th scope="col">Magasin</th>
                </tr>
              </thead>
              <tbody>
                <th ><%= stocks.getArticle().getEncodage() %></th>
                <th ><%=stocks.getQuantite_initial() %></th>
                <th ><%= stocks.getQuantite_actuel() %></th>
                <th ><%= stocks.getPrix_moyen() %></th>
                <th ><%= stocks.getPrix_total() %></th>
                <th ><%= stocks.getMagasin().getNom() %></th>
              </tbody>
            </table>
            <p class="trans">trans</p>
            <!-- End Table with stripped rows -->
          </div>
        </div>
    </body>
</html>
