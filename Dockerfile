FROM openjdk:11-jre-slim
VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
EXPOSE 5432
ADD build/libs/bank-sara-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

