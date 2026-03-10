@echo off
REM Script para carregar variáveis de ambiente do arquivo .env e executar LiterAlura

echo.
echo ========================================
echo  LiterAlura - Carregando Configuracoes
echo ========================================
echo.

REM Arquivo .env
set ENV_FILE=.env

REM Verificar se .env existe
if not exist %ENV_FILE% (
    echo ERRO: Arquivo %ENV_FILE% nao encontrado!
    echo.
    echo Crie um arquivo .env com o seguinte conteudo:
    echo DB_URL=jdbc:postgresql://localhost:5432/literalura
    echo DB_USER=postgres
    echo DB_PASSWORD=sua_senha
    echo.
    pause
    exit /b 1
)

REM Ler arquivo .env e definir variáveis
echo Carregando variáveis de %ENV_FILE%...
for /f "tokens=1,2 delims==" %%A in (%ENV_FILE%) do (
    if not "%%A"=="" (
        if not "%%A:~0,1%%"=="REM" (
            if not "%%A:~0,1%%"=="#" (
                set "%%A=%%B"
                echo  [OK] %%A
            )
        )
    )
)

echo.
echo Variáveis carregadas com sucesso!
echo.
echo Iniciando LiterAlura...
echo.

REM Executar Maven
call mvnw spring-boot:run

pause

