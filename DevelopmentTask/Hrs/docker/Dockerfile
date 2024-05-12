FROM redhat/ubi8-minimal:latest

ARG PRODUCT_NAME=hrs
ARG USER_UID=1001
ARG USER_GID=1001
ARG USER_NAME=$PRODUCT_NAME
ARG USER_GROUP=$PRODUCT_NAME
ARG USER_HOME=/home/$USER_NAME

RUN microdnf install --nodocs java-17-openjdk-headless shadow-utils && \
    microdnf clean all && \
    rm -rf /var/cache/yum/* && \
    rm -rf ~/.cache/ && \
    rm -rf /tmp/* && \
    groupadd --gid $USER_GID $USER_GROUP && \
    useradd --create-home \
            --shell /bin/bash \
            --uid $USER_UID \
            --gid $USER_GROUP \
            --home $USER_HOME $USER_NAME && \
    chown -R $USER_NAME:$USER_GROUP $USER_HOME

USER $USER_NAME
WORKDIR $USER_HOME

ARG JAR_FILE=Hrs-1.0-SNAPSHOT.jar
ENV JAR_FILE ${JAR_FILE}

COPY --chown=$USER_NAME:$USER_GROUP ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]