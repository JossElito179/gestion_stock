<%@page import="models.Magasin"%>
<%@page import="connection.ConnectionBase"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Article"%>
<!DOCTYPE html>
<html>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./css/styleEntre.css">
</head>
<body>

  <aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
      <li class="nav-item">
        <a class="nav-link" href="#">
          <i class="bi bi-grid"></i>
          <span class="titre">Stock</span>
        </a>
      </li>

    <ul class="sidebar-nav" id="sidebar-nav">
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
        <a class="nav-link" href="EtatStock.jsp">
          <i class="bi bi-grid"></i>
          <span>Voir etat de stock</span>
        </a>
      </li>

    </ul>
  </aside>

    <div class="login-title">
        <h4 class="title">Voir les stock</h4>
        <div id="loginbox" class="loginbox">
              <form action="AllEtatStock" method="get">
                <p>
                    <label for="modlgn_passwd">Date de debut</label>
                    <input id="input" type="datetime-local" name="date1" class="inputbox" size="18" autocomplete="off">
                </p>
                <p>
                    <label for="modlgn_passwd">Date de fin</label>
                    <input id="input" type="datetime-local" name="date2" class="inputbox" size="18" autocomplete="off">
                </p>
                <input type="submit" name="Submit" class="button" value="Valider">
              </div>
            </form>
        </div>
    </div>
    <a href="Sortiestock.jsp">Ici</a>
</body>
</html>
