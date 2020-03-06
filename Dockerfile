FROM openjdk:8
WORKDIR app
ADD target/profile-0.1.jar ProfileEditor.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ProfileEditor.jar"]

