/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connection.ConnectionBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Entre_stock;

/**
 *
 * @author joss
 */
public class EntrerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String produit=request.getParameter("produits");
            String quantite=request.getParameter("quantite");
            String prix_unitaire=request.getParameter("prix_unitaire");
            String date_entre=request.getParameter("date");
            String magasin=request.getParameter("magasin");
            double prixtotal=Float.valueOf(quantite)*Float.valueOf(prix_unitaire);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            
            LocalDateTime st=LocalDateTime.parse(date_entre, formatter);
            Timestamp t=Timestamp.valueOf(st);
            Entre_stock entre_stock = new Entre_stock(0,t, Float.valueOf(quantite), Float.valueOf(prix_unitaire),prixtotal, Integer.valueOf(magasin),Integer.valueOf(produit));
            ConnectionBase connection = new ConnectionBase();
            entre_stock.insertDAO(connection, "*");
            out.println("<p>"+produit+"</p>");
            out.println("<p>"+quantite+"</p>");
            out.println("<p>"+prix_unitaire+"</p>");
            out.println("<p>"+t+"</p>");
            out.println("<p>"+magasin+"</p>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(EntrerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(EntrerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
