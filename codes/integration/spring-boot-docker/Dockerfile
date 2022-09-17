FROM openjdk:8-jdk-alpine

ARG NAME
ARG VERSION
ARG JAR_FILE

LABEL name=$NAME \
      version=$VERSION

VOLUME /tmp

ENV TIME_ZONE=Asia/Shanghai
ENV APP_DIR=/home/myapp

RUN set -eux; \
    ln -snf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime; \
    echo ${TIME_ZONE} > /etc/timezone; \
    mkdir -p ${APP_DIR}/lib ${APP_DIR}/etc ${APP_DIR}/log

ADD *.sh /usr/local/bin/
ADD target/${JAR_FILE} ${APP_DIR}/app.jar

RUN chmod +x /usr/local/bin/*.sh

CMD [ "startup.sh" ]
ENTRYPOINT [ "docker-entrypoint.sh" ]

EXPOSE 8080
