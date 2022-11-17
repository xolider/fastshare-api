FROM openjdk:11-jdk

WORKDIR /build

COPY . .

RUN chmod +x ./gradlew && ./gradlew --no-daemon bootWar

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=0 /build/build/libs/fastshare-api*.war app.war

CMD ["java", "-jar", "/app/app.war"]