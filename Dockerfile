FROM eclipse-temurin:17-jre-alpine
# Install curl for connectivity diagnostics
RUN apk add --no-cache curl
WORKDIR /app
COPY target/medisphere-api-gateway-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
