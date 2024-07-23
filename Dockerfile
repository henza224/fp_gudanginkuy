#Base image
FROM openjdk:17-jdk-alpine
#Tambah jar file yang ada ke dalam target image
# Perintah build: mvn clean install -DskipTests
ADD target/gudanginkuy-0.0.1-SNAPSHOT.jar gudanginkuy.jar
#Entry Point
CMD ["java", "-jar", "/gudanginkuy.jar"]