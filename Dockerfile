FROM maven:3.9.5 AS build
WORKDIR /app
RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/PaBoS98/cargo_reporting_service.git
WORKDIR /app/cargo_reporting_service
RUN mvn clean install

FROM openjdk:17-jdk-slim
ARG PORT=8080
ARG KAFKA_URL="localhost"
ARG KAFKA_PORT="9092"
ARG CARGO_TRACKING_URL="localhost:8081"

ENV PORT=${PORT}
ENV KAFKA_URL=${KAFKA_URL}
ENV KAFKA_PORT=${KAFKA_PORT}
ENV CARGO_TRACKING_URL=${CARGO_TRACKING_URL}

WORKDIR /app/cargo_reporting_service
COPY --from=build /app/cargo_reporting_service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}",
            "--kafka.server=${KAFKA_URL}:${KAFKA_PORT}",
            "--cargo.tracking.service.url=${CARGO_TRACKING_URL}"]