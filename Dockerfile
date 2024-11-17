FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY target/fractals-site.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
