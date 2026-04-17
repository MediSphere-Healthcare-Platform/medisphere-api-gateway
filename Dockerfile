FROM eclipse-temurin:17-jre-alpine
# Install curl for connectivity diagnostics
RUN apk add --no-cache curl
WORKDIR /app
# This makes the build flexible! It defaults to target, but can be changed.
ARG JAR_FILE=target/medisphere-api-gateway-*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
