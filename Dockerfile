FROM maven:3.9.1 AS MAVEN_BUILD
COPY . /build
WORKDIR /build
RUN mvn clean package

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/*.jar /app/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]