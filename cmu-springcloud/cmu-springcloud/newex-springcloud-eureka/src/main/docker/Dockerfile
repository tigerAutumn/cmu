FROM java:8
MAINTAINER newex-team <newex-team@newex.com>, git@gitlab.com:newex-springcloud/newex-springcloud-eureka.git

ENV USER_NAME admin
ENV APP_HOME /home/$USER_NAME/newex-springcloud-eureka

RUN \
  useradd -ms /bin/bash $USER_NAME && \
  mkdir -p $APP_HOME/logs

ARG JAR_FILE
ADD ${JAR_FILE} ${APP_HOME}/newex-springcloud-eureka.jar
RUN \
  chown ${USER_NAME} ${APP_HOME}/newex-springcloud-eureka.jar && \
  chown -R ${USER_NAME} ${APP_HOME}/logs

ENV JAVA_TOOL_OPTIONS "-Xms128M -Xmx128M"

USER $USER_NAME
WORKDIR $APP_HOME
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=@spring.profiles.active.name@", "newex-springcloud-eureka.jar"]
