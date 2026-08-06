FROM maven:3.8.3-eclipse-temurin-17 as builder
WORKDIR fleet/app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY /src ./src/
RUN mvn clean package -DskipTests

#Run spring - image for backend
FROM eclipse-temurin:17-jdk
RUN mkdir "home/fleet"
COPY --from=builder /fleet/app/target/*.jar /fleet/app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "fleet/app.jar"]