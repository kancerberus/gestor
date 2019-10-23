/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author franj
 */

@WebServlet("/pdfPreviewServlet")
public class PdfPreviewServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    byte[] pdfBytesArray = (byte[]) request.getSession().getAttribute("pdfBytesArray");
    request.getSession().removeAttribute("pdfBytesArray");
    response.setContentType("application/pdf");
    response.setContentLength(pdfBytesArray.length);
    response.getOutputStream().write(pdfBytesArray);
} }