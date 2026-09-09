FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/momentum-ingest-api-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
