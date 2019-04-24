FROM daocloud.io/buxiaomo/maven:6.2.4 as builder
COPY . /code

WORKDIR /code

RUN mvn package -Dmaven.test.skip=true && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

FROM java:8u111-jdk
MAINTAINER "MoMo" <95112082@.qq>

ENV Xms=2048m \
    Xmx=4096m

COPY --from=builder /code/target/gyportal.jar /app/

EXPOSE 8080

WORKDIR /app

CMD [ "java","-jar","gyportal.jar" ]