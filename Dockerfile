# Usa una imagen base de OpenJDK 21
FROM openjdk:21

# Expone el puerto 8080
EXPOSE 8080

# Añade el archivo JAR e la aplicación al contenedor
ADD target/java-0.0.1-SNAPSHOT.jar java-0.0.1-SNAPSHOT.jar

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/java-0.0.1-SNAPSHOT.jar"]