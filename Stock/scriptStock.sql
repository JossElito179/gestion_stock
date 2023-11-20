create database stock;

\c stock;


CREATE TABLE Type_sortie(
   IdTypeSortie SERIAL,
   nomSortie VARCHAR(50) ,
   PRIMARY KEY(IdTypeSortie)
);

CREATE TABLE Magasin(
   IdMagasin SERIAL,
   Nom VARCHAR(50) ,
   PRIMARY KEY(IdMagasin)
);

CREATE TABLE Article(
   IdArticle SERIAL,
   encodage VARCHAR(50) ,
   unite VARCHAR(50) ,
   IdTypeSortie INT NOT NULL,
   PRIMARY KEY(IdArticle),
   FOREIGN KEY(IdTypeSortie) REFERENCES Type_sortie(IdTypeSortie)
);

CREATE TABLE Entre_stock(
   IdEntreStock SERIAL,
   date_entre Timestamp,
   quantite FLOAT,
   prix_unitaire DECIMAL(10,2)  ,
   prix_total DECIMAL(10,2)  ,
   IdMagasin INT NOT NULL,
   IdArticle INT NOT NULL,
   PRIMARY KEY(IdEntreStock),
   FOREIGN KEY(IdMagasin) REFERENCES Magasin(IdMagasin),
   FOREIGN KEY(IdArticle) REFERENCES Article(IdArticle)
);

CREATE TABLE Sortie_stock(
   IdSortieStock SERIAL,
   date_sortie Timestamp ,
   quantite FLOAT,
   prix_unitaire DECIMAL(10,2)  ,
   prix_total DECIMAL(10,2)  ,
   IdArticle INT NOT NULL,
   IdMagasin INT NOT NULL,
   PRIMARY KEY(IdSortieStock),
   FOREIGN KEY(IdArticle) REFERENCES Article(IdArticle),
   FOREIGN KEY(IdMagasin) REFERENCES Magasin(IdMagasin)
);

CREATE TABLE Sortie_dEntres(
   IdSortie_dentres SERIAL,
   quantite FLOAT,
   IdEntreStock INT NOT NULL,
   IdSortieStock INT NOT NULL,
   PRIMARY KEY(IdSortie_dentres),
   FOREIGN KEY(IdEntreStock) REFERENCES Entre_stock(IdEntreStock),
   FOREIGN KEY(IdSortieStock) REFERENCES Sortie_stock(IdSortieStock)
);

insert into Type_sortie values (default ,'FIFO'),
                               (default ,'LIFO');


insert into Magasin values (default , 'Magasin 1'),
                           (default , 'Magasin 2'),
                           (default ,'Magasin 3');

insert into Article values (default , 'AR1' , 'KG' ,1),
                    (default , 'AR2' , 'KG' ,2),
                    (default , 'AL1' , 'L' ,1);

create view v_quantite_article as select DISTINCT idmagasin,idArticle,sum(entre_stock.quantite) as reste from entre_stock group by idArticle,idmagasin ;

create view sum_per_sortie as select DISTINCT idmagasin, idarticle , sum(quantite) from sortie_stock group by idarticle,idmagasin;

SELECT v_quantite_article.reste - sum_per_sortie.sum as reste from v_quantite_article join sum_per_sortie on sum_per_sortie.idArticle=v_quantite_article.idArticle where v_quantite_article.idArticle=2;


--for FIFO
----------------------------------------------------------------
SELECT DISTINCT
    entre_stock.idArticle,
    entre_stock.IdEntreStock,
    entre_stock.date_entre,
    entre_stock.quantite - Sortie_dEntres.quantite AS reste
FROM entre_stock
JOIN Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.IdEntreStock
ORDER BY entre_stock.date_entre DESC;

--for LIFO
----------------------------------------------------------------
SELECT DISTINCT
    entre_stock.idArticle,
    entre_stock.IdEntreStock,
    entre_stock.date_entre,
    entre_stock.quantite - Sortie_dEntres.quantite AS reste
FROM entre_stock
JOIN Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.IdEntreStock
ORDER BY entre_stock.date_entre ASC;


SELECT
    entre_stock.idArticle,
    entre_stock.identrestock,
    entre_stock.date_entre,
    entre_stock.IdMagasin,
    entre_stock.quantite
FROM
    entre_stock
LEFT JOIN
    Sortie_dEntres ON entre_stock.IdEntreStock = Sortie_dEntres.identrestock
WHERE
    Sortie_dEntres.identrestock IS NULL;
