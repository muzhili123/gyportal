FROM nginx:1.13.8
MAINTAINER "MoMo" <95112082@.qq>

COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

COPY . /app/code

VOLUME /var/www/html

WORKDIR /var/www/html

EXPOSE 80

ENTRYPOINT [ "docker-entrypoint.sh" ]
CMD ["nginx", "-g", "daemon off;"]