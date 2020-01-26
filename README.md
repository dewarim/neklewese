# Noch ein kleiner Web-Server (Neklewese)

Start mit der main-Methode der Klasse [com.dewarim.server.Webserver](/src/main/java/com/dewarim/server/WebServer.java)

## Technologie

- Wird gebaut mit [Maven 3](https://maven.apache.org/)
- Java [Version 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)

### Verwendete Bibliotheken

- [Jetty Webserver](https://www.eclipse.org/jetty/)
- [Velocity](https://velocity.apache.org/engine/2.1/user-guide.html)

## URLs

Der Server ist (wenn er läuft) unter [http://localhost:7070/](http://localhost:7070/) zu errreichen.

    GET / = index.html
    GET /static/index.html
    GET /static/main.css
    GET /static/count.js (wird auf localhost nicht verwendet)
    POST /my/count - Counter erhöhen
    GET /my - nix zu sehen
    
