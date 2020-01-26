package com.dewarim.server;

import org.eclipse.jetty.http.MimeTypes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * Static Servlet liefert statische (also sich nicht verändernde) Resourcen wie HTML-Seiten und Bilder
 * an den Benutzer / Browser aus.
 */
@WebServlet(name = "Static", urlPatterns = "/")
public class StaticServlet extends HttpServlet {

    /**
     * doGet verarbeitet GET Anfragen, die auf dem Server nichts verändern sollten,
     * weil der Benutzer sie beliebig oft und in beliebiger Reihenfolge stellen kann.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }

        // Wenn jemand nach "http://localhost:7070/" fragt, gibt es die index.html-Seite als Antwort.
        if (pathInfo.isEmpty() || pathInfo.equals("/")) {
            pathInfo = "/index.html";
        }
        // note: pathInfo seems to filter out ../ automatically, but I think it's useful to be sure.
        // otherwise, someone could read every file on the current system.
        if (request.getRequestURI().contains("../")) {
            // Jede Antwort (response) braucht einen int Status, z.B. 200 für OK, 404 für "nicht gefunden"
            response.setStatus(SC_FORBIDDEN);
            return;
        }
        handleStaticContentRequest(pathInfo, response);
    }

    private void handleStaticContentRequest(String pathInfo, HttpServletResponse response) throws IOException {
        // Versuche, die gewünschte Datei aus src/main/resources/static zu laden:
        InputStream inputStream = getClass().getResourceAsStream("/static" + pathInfo);
        if (inputStream == null) {
            // nichts gefunden? Dann sind wir hier fertig:
            response.setStatus(SC_NOT_FOUND);
            return;
        }

        /* Wenn eine Antwort nicht leer ist (wie bei "NOT FOUND" oben), dann ist es korrekt, dem
         * Aufrufer mitzuteilen, was für eine Art von Inhalt (ContentType) es sich hier handelt:
         */
        String defaultMimeByExtension = MimeTypes.getDefaultMimeByExtension(pathInfo);
        response.setContentType(defaultMimeByExtension);
        switch (defaultMimeByExtension) {
            case "application/xml":
            case "text/plain":
            case "text/css":
            case "text/html":
            case "text/javascript":
                /*
                 * Bei Textdateien sollte das Encoding nicht fehlen, sonst sind z.B. die Umlaute kaputt
                 * oder das Euro-Zeichen kann nicht dargestellt werden, weil der Browser den Inhalt falsch
                 * dekodiert.
                 * Unicode kann so ziemlich alle Zeichen kodieren, auch Emojis.
                 * Siehe: https://de.wikipedia.org/wiki/UTF-8
                 * Ob der Browser sie nicht nur dekodieren, sondern auch darstellen kann, liegt dann an den
                 * beim Benutzer vorhandenen Schriften.
                 */
                response.setCharacterEncoding("UTF-8");
                break;
            default: // do not set character encoding.
        }
        response.setStatus(HttpServletResponse.SC_OK);
        inputStream.transferTo(response.getOutputStream());
        inputStream.close();
    }
}
