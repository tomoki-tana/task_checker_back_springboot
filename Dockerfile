FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21-alpine
COPY --from=build /app/build/libs/task_checker_back-0.0.1-SNAPSHOT.jar task_checker_back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task_checker_back.jar", "--spring.profiles.active=prod", "--debug"]
