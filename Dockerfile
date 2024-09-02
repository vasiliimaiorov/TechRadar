# Build stage
FROM maven:3.9.2-eclipse-temurin-17-alpine AS build

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-alpine

# Install PostgreSQL client tools in the runtime container
RUN apk --no-cache add postgresql-client

WORKDIR /app

# Copy the compiled application JAR from the build stage
COPY --from=build /build/target/t1-0.0.1-SNAPSHOT.jar /app/app.jar

# Copy the SQL script into the container
COPY script/init/insert_tech.sql /app/insert_tech.sql

EXPOSE 8080

# Wait for the database to be ready, then run the application
CMD java -jar /app/app.jar & \
    echo "Waiting for PostgreSQL to be ready..." && \
    until pg_isready -h "database" -p 5432 -U "$SPRING_DATASOURCE_USERNAME"; do \
        sleep 1; \
    done && \
    echo "PostgreSQL is ready. Running SQL script." && \
    PGPASSWORD=$SPRING_DATASOURCE_PASSWORD psql -h "database" -p 5432 -U "$SPRING_DATASOURCE_USERNAME" -d t1 -f /app/insert_tech.sql && \
    wait
