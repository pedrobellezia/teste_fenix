# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package
RUN set -e; JAR_FILE=$(ls -1 target/*.jar | grep -v '.jar.original' | head -n 1); cp "$JAR_FILE" /app/app.jar

FROM eclipse-temurin:17-jre

WORKDIR /app

RUN apt-get update \
  && apt-get install -y --no-install-recommends curl \
  && rm -rf /var/lib/apt/lists/*

RUN groupadd -r app && useradd -r -g app -u 10001 app

COPY --from=build /app/app.jar /app/app.jar
RUN chown app:app /app/app.jar

ENV JAVA_OPTS=""
ENV SERVER_PORT=8080

EXPOSE 8080

USER app

HEALTHCHECK --interval=10s --timeout=3s --retries=10 CMD curl -fsS http://localhost:${SERVER_PORT}/actuator/health || exit 1

ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]
