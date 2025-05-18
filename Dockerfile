FROM eclipse-temurin:23.0.2_7-jre
EXPOSE 8005
ADD target/order-processing-0.0.1-SNAPSHOT.jar order-processing-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/order-processing-0.0.1-SNAPSHOT.jar"]