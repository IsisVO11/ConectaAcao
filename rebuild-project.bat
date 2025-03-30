@echo off
echo Reconstruindo o projeto com configuracoes atualizadas...

cd %~dp0

echo Limpando build...
call gradlew.bat clean --no-daemon

echo Invalidando caches...
call gradlew.bat --refresh-dependencies --no-daemon

echo Compilando projeto...
call gradlew.bat build --no-daemon -x test

echo Processo concluido! Tente abrir o projeto no Android Studio novamente. 