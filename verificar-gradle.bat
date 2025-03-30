@echo off
echo Verificando e reparando a instalacao do Gradle...

cd %~dp0

echo Verificando a versao do Java...
java -version

echo Verificando Gradle...
call gradlew.bat --version

echo Criando diretorio para o agente de instrumentacao...
if not exist "C:\Users\isisv\AndroidStudioProjects\lib\agents" mkdir "C:\Users\isisv\AndroidStudioProjects\lib\agents"

echo Baixando o agente de instrumentacao...
powershell -Command "& { Invoke-WebRequest -Uri 'https://services.gradle.org/distributions-snapshots/gradle-8.2-20230630000041+0000-bin.zip' -OutFile 'gradle-temp.zip' }"
powershell -Command "& { Expand-Archive -Path 'gradle-temp.zip' -DestinationPath 'temp' -Force }"

echo Extraindo agente de instrumentacao...
if exist "temp\gradle-8.2-20230630000041+0000\lib\gradle-instrumentation-agent-8.2.jar" (
    copy "temp\gradle-8.2-20230630000041+0000\lib\gradle-instrumentation-agent-8.2.jar" "C:\Users\isisv\AndroidStudioProjects\lib\agents\gradle-instrumentation-agent-8.2.jar"
    echo Agente de instrumentacao copiado com sucesso!
) else (
    echo Arquivo do agente nao encontrado no pacote!
)

echo Limpando arquivos temporarios...
rmdir /s /q temp
del gradle-temp.zip

echo Verificando propriedades do Gradle...
if exist gradle.properties (
    echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 >> gradle.properties
) else (
    echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 > gradle.properties
)

echo Verificacao e reparo concluidos! 
echo Tente compilar o projeto novamente usando o comando 'gradlew.bat build' 