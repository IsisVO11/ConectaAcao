@echo off
echo Regenerando Gradle Wrapper...
cd %~dp0
if exist gradlew.bat del gradlew.bat
if exist gradlew del gradlew
if exist gradle\wrapper\gradle-wrapper.jar del gradle\wrapper\gradle-wrapper.jar

echo Baixando wrapper novo...
powershell -Command "& { Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.2-bin.zip' -OutFile 'gradle-8.2-bin.zip' }"
powershell -Command "& { Expand-Archive -Path 'gradle-8.2-bin.zip' -DestinationPath 'temp' -Force }"
copy temp\gradle-8.2\bin\gradle.bat gradlew.bat
copy temp\gradle-8.2\lib\gradle-wrapper.jar gradle\wrapper\

echo Limpando arquivos temporários...
rmdir /s /q temp
del gradle-8.2-bin.zip

echo Limpando cache do Gradle...
echo Para finalizar a limpeza do cache, execute manualmente os comandos abaixo:
echo 1. Feche o Android Studio completamente
echo 2. Exclua a pasta: %USERPROFILE%\.gradle\caches
echo 3. Abra o Android Studio e recompile o projeto

echo Regeneração do Gradle Wrapper concluída! 