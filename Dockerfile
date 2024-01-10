FROM openjdk:17
ADD target/aftas.jar aftas
ENTRYPOINT ["java", "-jar","aftas"]