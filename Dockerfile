FROM openjdk:11
MAINTAINER Ashish Khedekar
EXPOSE 8080
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]