FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/Smileapp-0.0.1.jar

COPY ${JAR_FILE} Smileapp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","Smileapp.jar"]
