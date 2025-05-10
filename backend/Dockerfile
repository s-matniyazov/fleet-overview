# Faqat JAR ishga tushirish uchun OpenJDK image
FROM openjdk:17-jdk-slim

# Ishchi papkani belgilash
WORKDIR /app

# Tayyorlangan jar-faylni konteynerga nusxalash
COPY target/fleetoverview-1.jar app.jar

# (ixtiyoriy) port ochishni ko‘rsatish
EXPOSE 8088

# Ilovani ishga tushirish
CMD ["java", "-jar", "app.jar"]

