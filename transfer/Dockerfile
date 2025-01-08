FROM openjdk:17-slim
WORKDIR /app
COPY ./target/*.jar app.jar
EXPOSE 8191
ENTRYPOINT ["java", "-jar", "app.jar"]