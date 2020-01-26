package com.dewarim.server;

import org.eclipse.jetty.http.MimeTypes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(name = "MyServlet", urlPatterns = "/")
public class MyServlet extends HttpServlet {

    private AtomicLong counter = new AtomicLong(0);

    /**
     * POST-Anfragen verändern etwas auf dem Server bzw. geben Resourcen zurück,
     * die dynamisch erstellt werden. Antworten auf POST-Anfragen werden nicht vom Browser gecacht
     * (darum die Nachfrage bei Formularen: "wirklich nochmal abschicken?")
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }
        switch (pathInfo) {
            case "/count":
                countUp(response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

    }

    private void countUp(HttpServletResponse response) throws IOException {
        long zahl = counter.incrementAndGet();
        response.setStatus(SC_OK);
        String plainText = MimeTypes.Type.TEXT_PLAIN_UTF_8.asString();
        response.setContentType(plainText);
        response.getWriter().println("Zahl: "+zahl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // hier könnten wir einfach nur die aktuelle Zahl zurückgeben.
        response.setStatus(SC_OK);
        String htmlContent = MimeTypes.Type.TEXT_HTML_UTF_8.asString();
        response.setContentType(htmlContent);
        response.getWriter().println("<!DOCTYPE html><html><body>Nix zu sehen.</body>");
    }

}
