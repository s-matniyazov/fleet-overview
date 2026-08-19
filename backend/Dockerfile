FROM maven:3.9-eclipse-temurin-21 as builder
WORKDIR fleet/app
COPY pom.xml ./
# Download dependencies required for the build
RUN mvn -B dependency:resolve
COPY /src ./src/
RUN mvn clean package -DskipTests

#Run spring - image for backend
FROM eclipse-temurin:21-jre
RUN mkdir "home/fleet"
COPY --from=builder /fleet/app/target/*.jar /fleet/app.jar
ENTRYPOINT ["java", "-jar", "fleet/app.jar"]