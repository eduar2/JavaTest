#pichincha test

FROM openjdk:17-oracle
EXPOSE 8080
RUN mkdir -p /app/

COPY build/libs/PichinchaTest-0.0.1-SNAPSHOT.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "/app/myapp.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]]