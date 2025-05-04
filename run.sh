#!/bin/bash

set -e

function usage() {
  echo "Usage: $0 [up|down|reset|app|all]"
  echo "  up     - Start DB (preserve data)"
  echo "  down   - Stop DB (preserve data)"
  echo "  reset  - Stop and remove DB and data (danger: full reset)"
  echo "  app    - Build and run the Spring Boot app"
  echo "  all    - Start DB and run app (preserve data)"
}

case "$1" in
  up)
    echo "Starting DB (preserving data)..."
    docker compose up -d
    ;;
  down)
    echo "Stopping DB (preserving data)..."
    docker compose down
    ;;
  reset)
    echo "Resetting DB and removing all data..."
    docker compose down -v
    docker compose up -d
    ;;
  app)
    echo "Building and running the Spring Boot app..."
    ./gradlew clean build
    ./gradlew bootRun
    ;;
  all)
    echo "Starting DB and running app (preserving data)..."
    docker compose up -d
    ./gradlew clean build
    ./gradlew bootRun
    ;;
  *)
    usage
    ;;
esac
