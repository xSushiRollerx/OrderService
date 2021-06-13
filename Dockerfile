#
# Package stage
#
FROM openjdk:1.8-jre-slim
COPY target/order-service-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]