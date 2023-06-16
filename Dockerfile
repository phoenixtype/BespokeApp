FROM openjdk:17-jdk
WORKDIR /app
COPY target/BespokeApp-0.0.1-SNAPSHOT.jar /app/BespokeApp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "BespokeApp-0.0.1-SNAPSHOT.jar"]
