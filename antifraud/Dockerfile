FROM openjdk:17-slim
WORKDIR /app
COPY ./target/*.jar app.jar
EXPOSE 8186
ENTRYPOINT ["java", "-jar", "app.jar"]