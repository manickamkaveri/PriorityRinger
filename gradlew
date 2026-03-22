#!/usr/bin/env sh

# Gradle wrapper script for launching Gradle.

# Executable script to run the Gradle Wrapper.

# General options:
if [ "$1" = "--help" ]; then
    echo "Usage: ./gradlew [options]"
    exit 0
fi

# Execute Gradle Wrapper
java -jar gradle-wrapper.jar "$@"