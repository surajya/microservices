FROM openjdk:17-jdk-slim
LABEL "org.opencontainers.image.authors"="eazybytes.com"
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "account-0.0.1-SNAPSHOT.jar"]