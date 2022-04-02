# Dockerfile/Docker-Compose file based on an initial version authored by Alexander Lontke (ASSE, Fall Semester 2021)

FROM maven as build

COPY . /app

RUN mvn -f app/pom.xml --batch-mode --update-snapshots -Dmaven.test.skip package

FROM openjdk

COPY --from=build /app/target/customerPersistence-0.0.1-SNAPSHOT.jar ./customerPersistence-0.0.1-SNAPSHOT.jar

CMD java -jar customerPersistence-0.0.1-SNAPSHOT.jar