# Noch ein kleiner Web-Server (Neklewese)

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
    POST /my/count - Counter erhöhen
    GET /my - nix zu sehen
    
