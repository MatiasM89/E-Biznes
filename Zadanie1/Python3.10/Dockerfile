FROM ubuntu:22.04

RUN apt-get update

RUN apt-get install -y python3.10

RUN update-alternatives --install /usr/bin/python3 python3 /usr/bin/python3.10 1

CMD ["python3", "--version"]