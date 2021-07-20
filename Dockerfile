FROM  bellsoft/liberica-openjdk-alpine:15.0.1 AS build
COPY application /app/application
COPY buildSrc /app/buildSrc
COPY connection-manager /app/connection-manager
COPY database-migration /app/database-migration
COPY data-model /app/data-model
COPY report-manager /app/report-manager
COPY report-viewer /app/report-viewer
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY gradle.properties /app/gradle.properties
COPY gradlew /app/gradlew
COPY settings.gradle /app/settings.gradle
WORKDIR /app
RUN ./gradlew build -x test --no-daemon

FROM bellsoft/liberica-openjdk-alpine:15.0.1
LABEL org.opencontainers.image.source=https://github.com/eaxdev/simple-report-system
WORKDIR /app
COPY --from=build /app/application/build/libs ./
COPY ./entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh
CMD ["/app/entrypoint.sh"]
HEALTHCHECK --start-period=15s --interval=1m --timeout=10s --retries=5 \
            CMD curl --silent --fail --request GET http://localhost:$PORT/actuator/health \
                | jq --exit-status '.status == "UP"' || exit 1