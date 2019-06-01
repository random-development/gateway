FROM maven:3.5-jdk-8-alpine AS build 
COPY /src /usr/src/gateway/src
COPY pom.xml /usr/src/gateway
COPY Dockerfile /usr/src/gateway
RUN mvn -f /usr/src/gateway/pom.xml clean install

FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/gateway/target/Gateway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]