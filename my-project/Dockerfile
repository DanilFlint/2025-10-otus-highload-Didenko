FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app
RUN apk update && apk add --no-cache maven

COPY pom.xml .
COPY hw01/pom.xml hw01/
RUN mvn dependency:resolve -f hw01/pom.xml

COPY hw01/src hw01/src
RUN mvn package -DskipTests -f hw01/pom.xml

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/hw01/target/hw01-*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]