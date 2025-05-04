#!/bin/bash
# Script to run the UI module on port 8081

./gradlew bootRun --args='--spring.profiles.active=ui'
