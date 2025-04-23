#FROM maven:3.9.6-eclipse-temurin-21-alpine AS build-stage
#WORKDIR /app
#COPY ./ ./
#RUN --mount=type=cache,target=/root/.m2,id=local_repository,sharing=locked \
#

FROM eclipse-temurin:21-jdk-alpine AS prod-stage
WORKDIR /app
#COPY --from=build-stage /app/target/server.jar /app/app.jar
COPY mongo.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", \
"-Dserver.port=8080", \
"-jar", "app.jar", \
"-Xms2g", \
"-Xmx2g", \
"-XX:+HeapDumpOnOutOfMemoryError", \
"-XX:+UseG1GC", \
"-XX:MaxGCPauseMillis=100", \
"--add-opens=java.base/java.lang=ALL-UNNAMED", \
"--add-opens=java.base/java.lang.reflect=ALL-UNNAMED", \
"--add-opens=java.base/java.lang.invoke=ALL-UNNAMED", \
"--add-opens=java.base/sun.reflect.annotation=ALL-UNNAMED", \
"--add-opens=java.base/java.math=ALL-UNNAMED", \
"--add-opens=java.base/java.util=ALL-UNNAMED", \
"--add-opens=java.base/sun.util.calendar=ALL-UNNAMED", \
"--add-opens=java.base/java.io=ALL-UNNAMED", \
"--add-opens=java.base/java.net=ALL-UNNAMED", \
"--add-opens=java.base/java.text=ALL-UNNAMED", \
"--add-opens=java.base/java.nio=ALL-UNNAMED", \
"--add-opens=java.base/java.security=ALL-UNNAMED", \
"--add-opens=java.base/java.time=ALL-UNNAMED", \
"--add-opens=java.base/jdk.internal.access=ALL-UNNAMED", \
"--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED"]
