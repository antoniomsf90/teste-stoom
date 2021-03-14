FROM openjdk:11
ADD ./target/adress-1.0.0.jar /usr/src/adress.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "adress.jar"]