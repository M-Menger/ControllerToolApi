# Estágio de build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Verifique o nome exato do arquivo JAR no diretório target
COPY --from=build /workspace/app/target/ControllerToolApi-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]