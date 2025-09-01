# Etapa 1: build com Maven (usa cache dos deps)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY . .
RUN mvn -q -DskipTests package

# Etapa 2: runtime leve (só JRE)
FROM eclipse-temurin:17-jre
WORKDIR /app
# Ajuste o nome do JAR conforme o gerado pelo Maven:
ARG JAR=problem1-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/${JAR} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

