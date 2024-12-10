
FROM openjdk:21


EXPOSE 8080


ADD target/java-0.0.1-SNAPSHOT.jar java-0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar", "/java-0.0.1-SNAPSHOT.jar"]