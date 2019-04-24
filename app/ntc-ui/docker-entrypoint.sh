#!/bin/sh
tar --create \
--file - \
--one-file-system \
--directory /app/code \
--exclude docker-entrypoint.sh \
--exclude Dockerfile \
. | tar --extract --file -
exec "$@"