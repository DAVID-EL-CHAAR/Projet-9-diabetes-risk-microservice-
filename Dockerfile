# Étape 1 : Phase de build avec Maven
FROM maven:latest AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier tout le contenu du projet dans le répertoire de travail
COPY . .

# Lancer la commande Maven pour construire l'application (en ignorant les tests)
RUN mvn clean package -DskipTests

# Étape 2 : Phase finale (Image pour exécution)
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail pour l'image finale
WORKDIR /app

# Copier le fichier JAR depuis l'étape de build Maven
COPY --from=build /app/target/diabetes-risk-microservice-0.0.1-SNAPSHOT.jar /app/diabetes-risk-microservice.jar

# Exposer le port sur lequel l'application sera accessible
EXPOSE 8084

# Lancer l'application
ENTRYPOINT ["java", "-jar", "diabetes-risk-microservice.jar"]
