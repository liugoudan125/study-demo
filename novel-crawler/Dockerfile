FROM maven:3.9.6-eclipse-temurin-21-alpine AS build-stage
WORKDIR /app
COPY ./ ./
RUN --mount=type=cache,target=/root/.m2,id=local_repository,sharing=locked \
    mvn clean -U package -Dmaven.test.skip=true -T 1.5C


FROM eclipse-temurin:21-jdk-alpine AS prod-stage
WORKDIR /app
COPY --from=build-stage /app/target/spider.jar /app/spider.jar
COPY selenium ./selenium/
RUN chmod -R 777 /app/selenium/linux64
EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=8080","-jar", "spider.jar"]
CMD ["--add-opens=java.base/java.lang=ALL-UNNAMED","--add-opens=java.base/java.lang.reflect=ALL-UNNAMED","--add-opens=java.base/java.lang.invoke=ALL-UNNAMED","--add-opens=java.base/sun.reflect.annotation=ALL-UNNAMED","--add-opens=java.base/java.math=ALL-UNNAMED","--add-opens=java.base/java.util=ALL-UNNAMED","--add-opens=java.base/sun.util.calendar=ALL-UNNAMED","--add-opens=java.base/java.io=ALL-UNNAMED","--add-opens=java.base/java.net=ALL-UNNAMED","--add-opens=java.base/java.text=ALL-UNNAMED","--add-opens=java.base/java.nio=ALL-UNNAMED","--add-opens=java.base/java.security=ALL-UNNAMED","--add-opens=java.base/java.time=ALL-UNNAMED","--add-opens=java.base/jdk.internal.access=ALL-UNNAMED","--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED", \
"-Xms256M","-Xmx4g","-XX:+HeapDumpOnOutOfMemoryError","-XX:+UseG1GC","-XX:MaxGCPauseMillis=100"]
