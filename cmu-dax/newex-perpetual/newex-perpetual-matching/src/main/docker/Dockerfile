FROM azul/zulu-openjdk:8
MAINTAINER dengzhiwei <zhiwei.deng@newex.com>, git@gitlab.newex-inc.com:newex/newexnewex-futures.git

VOLUME /tmp

# Update CentOS
RUN \
  bash -c 'apt-get -qq update && apt-get -y upgrade && apt-get -y autoclean && apt-get -y autoremove' && \
  bash -c 'DEBIAN_FRONTEND=noninteractive apt-get install -y curl wget tar'

ENV USER_NAME newex-futures
ENV APP_HOME /opt/poc-api/$USER_NAME

RUN \
  useradd -ms /bin/bash $USER_NAME && \
  mkdir -p $APP_HOME

ADD newex-futures-web.jar ${APP_HOME}/newex-futures.jar
RUN \
  chown $USER_NAME $APP_HOME/newex-futures.jar && \
  bash -c 'touch ${APP_HOME}/newex-futures.jar'

ENV JAVA_TOOL_OPTIONS "-Xms128M -Xmx128M -Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"

USER $USER_NAME
WORKDIR $APP_HOME
ENTRYPOINT ["java", "-jar", "newex-futures-web.jar"]

# Run as:
# docker run -idt -p 8701:8701 -e appPort=8701 newex/newex-futures:latest
