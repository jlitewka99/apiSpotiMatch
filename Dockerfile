FROM openjdk:11
EXPOSE 8081
ADD target/spotimatch-api-0.1.jar spotimatch-api-0.1.jar
ENTRYPOINT ["java", "-jar", "/spotimatch-api-0.1.jar", "--spring.profiles.active=prod"]