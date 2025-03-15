FROM ubuntu:24.04

ENV HOME=/root

RUN apt-get update

RUN apt-get install -y curl unzip zip

RUN apt-get install -y openjdk-8-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

ENV PATH=$JAVA_HOME/bin:$PATH

RUN curl -s "https://get.sdkman.io" | bash

RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

ENV PATH=$HOME/.sdkman/candidates/kotlin/current/bin:$PATH

CMD ["bash", "-c", "java -version && kotlin -version"]