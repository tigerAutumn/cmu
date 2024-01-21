FROM java:8
MAINTAINER newex-team <newex-team@newex.com>, git@gitlab.com:newex-dax/newex-web.git

ENV USER_NAME admin
ENV APP_HOME /home/$USER_NAME/app
ENV APP_LOG_HOME /home/$USER_NAME/logs

RUN \
  useradd -ms /bin/bash $USER_NAME && \
  mkdir -p $APP_HOME && \
  mkdir -p $APP_LOG_HOME

ARG JAR_FILE
ADD ${JAR_FILE} ${APP_HOME}/newex-web.jar
RUN \
  chown $USER_NAME $APP_HOME/newex-web.jar && \
  chown $USER_NAME $APP_LOG_HOME

# ENV JAVA_TOOL_OPTIONS "-Xms128M -Xmx128M"

USER $USER_NAME
WORKDIR $APP_HOME
EXPOSE @server.port@
ENTRYPOINT ["java", "-jar", "newex-web.jar"]
