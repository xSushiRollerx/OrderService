#
# Package stage
#
FROM openjdk:8
COPY target/orderservice-0.0.1-SNAPSHOT.jar .
EXPOSE 8041
ENTRYPOINT ["java", "-jar", "/app.jar"]