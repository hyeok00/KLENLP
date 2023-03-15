ARG RUN_ENV=dev

FROM openjdk:11-jdk-slim AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:11-jdk-slim
# local, dev, prod
ARG RUN_ENV
ENV PROFILE=$RUN_ENV
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar -Dspring.profiles.active=$(echo ${PROFILE}) /app.jar