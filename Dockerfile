FROM eclipse-temurin:11-jdk-alpine
#FROM arm64v8/eclipse-temurin:11-jdk

EXPOSE 8082

VOLUME /tmp

ENV DB_USERNAME="postgres"
ENV DB_PASSWORD="mysecretpassword"
ENV DB_URL="jdbc:postgresql://localhost:5432/db_ucb_judge"

ENV ZIPKIN_SERVER_URI="http://localhost:9411"

ENV PORT=8083

ENV EUREKA_SERVER_URI="http://localhost:8761/eureka"

ENV KEYCLOAK_SERVER_URI="http://localhost:8080/"
ENV KEYCLOAK_CLIENT_SECRET="BNHREThKV64GqdjrJKJqkmLLowtwjfSi"
ENV KEYCLOAK_REALM="ucb-judge"
ENV KEYCLOAK_CLIENT_ID="uj-problems"

ENV MINIO_URL="http://localhost:9000"
ENV MINIO_ACCESS_KEY="iKOtBf9MN2CpPKsB"
ENV MINIO_SECRET_KEY="Zrxoy7B2pv6FSXBOXIlCsNJcGFPZynPb"

ENV CONFIG_SERVER_URI="http://localhost:8888"
ENV CONFIG_SERVER_PROFILE="test"

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","ucb.judge.ujusers.UjUsersApplicationKt"]