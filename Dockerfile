FROM amazoncorretto:17-alpine-jdk
LABEL mentainer="axmedovkamran19@gmail.com"
WORKDIR /app
COPY build/libs/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]