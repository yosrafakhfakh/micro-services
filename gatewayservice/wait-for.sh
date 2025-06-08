#!/bin/sh
# wait-for.sh

set -e

host="$1"
shift
cmd="$@"

until nc -z "$host" 5555; do
  echo "Waiting for $host to be available on port 5555..."
  sleep 2
done

echo "$host is up - executing command"
exec $cmd