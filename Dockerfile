FROM openjdk:8-jdk-alpine
MAINTAINER madsum@gmail.com
VOLUME /tmp
EXPOSE 8080
ADD target/profile-0.1.jar ProfileEditor.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ProfileEditor.jar"]

