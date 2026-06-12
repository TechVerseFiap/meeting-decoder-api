FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

# Copy Maven wrapper and pom first for dependency caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

# Download dependencies first (better layer caching)
RUN ./mvnw dependency:go-offline

# Copy source only after dependencies
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

# Security: run as non-root
RUN useradd -r -u 1001 spring

WORKDIR /app

# Copy the fat jar
COPY --from=builder /build/target/*.jar app.jar

# Ownership
RUN chown -R spring:spring /app

USER spring

EXPOSE 8080

# Better container awareness
ENV JAVA_OPTS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+UseG1GC \
-XX:+UseStringDeduplication \
-Djava.security.egd=file:/dev/./urandom"

# Optional healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD curl --fail http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
