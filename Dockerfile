#FROM eclipse-temurin:17
#LABEL mentainer="axmedovkamran19@gmail.com"
#WORKDIR /app
#EXPOSE 8089
#COPY build/libs/TeamProjectTracker-0.0.1-SNAPSHOT.jar application.jar
#ENTRYPOINT ["java", "-jar", "application.jar"]