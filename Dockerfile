# Use a lightweight OpenJDK image (Corretto 23 Alpine)
FROM amazoncorretto:21-alpine

# Set metadata about the image author
LABEL authors="rana"

# Set the working directory
WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml ./
COPY src ./src/

# Pre-fetch dependencies (uses dependency-goal to cache layers better)
RUN apk add --no-cache maven \
  && mvn dependency:go-offline

# Build the application (skip tests to speed up)
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
# CMD ["java", "-jar", "target/*.jar"]
CMD ["java", "-jar", "target/surgeries-0.0.1-SNAPSHOT.jar"]
