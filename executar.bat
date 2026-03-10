@echo off
REM Script para rodar a aplicação Literalura

echo.
echo ========================================
echo   INICIANDO LITERALURA APPLICATION
echo ========================================
echo.

REM Configurar variáveis de ambiente
set DB_URL=jdbc:postgresql://localhost:5432/literatura
set DB_USER=postgres
set DB_PASSWORD=skillet1302

echo Configuracoes:
echo Database: %DB_URL%
echo User: %DB_USER%
echo Password: [CONFIGURADA]
echo.

echo Verificando se o JAR existe...
if not exist "target\literalura-0.0.1-SNAPSHOT.jar" (
    echo.
    echo ERRO: JAR nao encontrado! Fazendo build...
    call mvnw.cmd clean package -DskipTests
    if errorlevel 1 (
        echo ERRO na compilacao!
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo   RODANDO A APLICACAO
echo ========================================
echo.

REM Passar variáveis para a aplicação Java
java -Dspring.datasource.url=%DB_URL% ^
     -Dspring.datasource.username=%DB_USER% ^
     -Dspring.datasource.password=%DB_PASSWORD% ^
     -jar target\literalura-0.0.1-SNAPSHOT.jar

pause

