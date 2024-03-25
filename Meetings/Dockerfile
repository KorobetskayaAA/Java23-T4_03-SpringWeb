# Родительский образ - компактная сборка Java17 от
FROM openjdk:17-jdk-oracle
# Метаданные
LABEL maintainer=teamscore.ru title="Пример дл курса Java"
# Переменная с именем JAR-файла
ARG JAR_FILE=target/meetings-0.0.1-SNAPSHOT.jar
# Рабочая директория (куда копировать, откуда запускать)
WORKDIR /opt/app
# Копируем JAR в рабочую директорию с переименованием
COPY ${JAR_FILE} app.jar
# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
