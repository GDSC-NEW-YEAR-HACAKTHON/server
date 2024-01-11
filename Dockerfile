FROM openjdk:17-jdk
LABEL authors="changmin.kim"
EXPOSE 8080

COPY build/libs/newyearserver-0.0.1-SNAPSHOT.jar /app/newyearserver.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "newyearserver.jar"]