FROM debian:latest

RUN apt-get update && apt-get install -y \
    apt-utils \
    nginx \
    dumb-init \
    vim \
    openssl \
    systemctl \
	psmisc \
	tree

COPY ./conf/default_dev.conf /etc/nginx/conf.d/default.conf
COPY ./tools /etc/nginx/scripts/

RUN chmod +x /etc/nginx/scripts/self-signed-certificate.sh

ENTRYPOINT ["/usr/bin/dumb-init","--","/etc/nginx/scripts/self-signed-certificate.sh"]

CMD ["nginx", "-g", "daemon off;"]

EXPOSE 443