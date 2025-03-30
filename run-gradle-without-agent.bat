@echo off
echo Executando Gradle sem agente de instrumentacao...

set JAVA_OPTS=-Xms256m -Xmx2048m
set GRADLE_OPTS=-Dorg.gradle.jvmargs="-Xms256m -Xmx2048m"

cd %~dp0
call gradlew.bat --no-daemon clean build -x test

echo Compilacao concluida! 