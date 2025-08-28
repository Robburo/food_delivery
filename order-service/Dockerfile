# Stage 1: Build
FROM gradle:8.7-jdk21 AS builder
WORKDIR /home/gradle/project


# Copy Gradle wrapper and build files first (cached unless these change)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle


# Pre-download dependencies (cached if no dependency changes)
RUN gradle build -x test --no-daemon || return 0


# Copy only the source code (faster rebuilds)
COPY src ./src


# Build Spring Boot executable JAR
RUN gradle clean bootJar -x test --no-daemon


# Stage 2: Run
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]