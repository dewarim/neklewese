package com.dewarim.server;

import org.eclipse.jetty.annotations.AnnotationDecorator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * WebServer: ein einfacher Webserver, der auf statische und dynamische Anfragen antworten kann.
 */
public class WebServer {

    private int           port;
    private Server        server;
    private WebAppContext webAppContext = new WebAppContext();

    public WebServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {

        webAppContext.setContextPath("/");
        webAppContext.setResourceBase(".");
        webAppContext.getObjectFactory().addDecorator(new AnnotationDecorator(webAppContext));

        addFilters(webAppContext);
        addServlets(webAppContext);
        server = new Server(port);
        server.setHandler(webAppContext);
        server.start();

        addSingletons();
    }

    private void addSingletons() {
        // hier kann man einmalig die Datenbank-Verbindung herstellen etc.
    }

    private void addFilters(WebAppContext handler) {
        // hier kann man Filter definieren, die _vor_ oder _nach_ dem eigentlichen Aufruf durchlaufen werden.
        // handler.addFilter(AuthenticationFilter.class, "/api/*", EnumSet.of(DispatcherType.REQUEST));
    }

    private void addServlets(WebAppContext handler) {
        /*
         * hier können Servlets hinzugefügt werden, also Klassen, die unter einem bestimmten Pfad
         * wie http://localhost:8080/static aufgerufen werden können.
         */
        handler.addServlet(StaticServlet.class, "/*");
        handler.addServlet(StaticServlet.class, "/static/*");
        handler.addServlet(MyServlet.class, "/my/*");
    }

    public static void main(String[] args) throws Exception {
        int port = 7070;
        WebServer server = new WebServer(port);
        server.start();
        System.out.println("Server läuft unter http://localhost:"+port+"/");
        server.getServer().join();
    }


    public Server getServer() {
        return server;
    }




}
