# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем Maven внутри контейнера
RUN apt update && apt install -y maven

# Указываем рабочую директорию
WORKDIR /app

# Копируем файлы проекта в контейнер
COPY . .

# Собираем JAR-файл внутри контейнера
RUN mvn clean package -DskipTests

# Запускаем приложение
CMD ["java", "-jar", "target/fleetoverview-1.jar"]
