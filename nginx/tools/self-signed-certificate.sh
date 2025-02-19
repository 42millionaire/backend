#!/bin/bash

openssl req -newkey rsa:4096 -days 365 -nodes -x509 -subj "/C=KR/ST=Seoul/L=Seoul/O=42Seoul/OU=soohlee/CN=localhost" -keyout /etc/ssl/private/localhost.dev.key -out /etc/ssl/certs/localhost.dev.crt
chmod 600 /etc/ssl/private/localhost.dev.key
chmod 600 /etc/ssl/certs/localhost.dev.crt

exec "$@"