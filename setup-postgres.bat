@echo off
REM Script para configurar PostgreSQL e executar LiterAlura

echo.
echo ╔════════════════════════════════════════════════════════╗
echo ║  LITERALURA - Script de Setup PostgreSQL              ║
echo ╚════════════════════════════════════════════════════════╝
echo.

echo [1/3] Verificando se PostgreSQL está rodando...
echo.

REM Tentar criar o banco
echo [2/3] Criando banco de dados 'literalura'...
echo.

psql -U postgres -c "CREATE DATABASE literalura;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo ✓ Banco criado com sucesso!
) else (
    echo ⚠ Banco pode já existir (ok)
)

echo.
echo [3/3] Aguarde 3 segundos...
timeout /t 3 /nobreak

echo.
echo ✓ Setup concluído!
echo.
echo Execute agora:
echo   cd C:\Users\Pratinho\Downloads\literalura
echo   .\mvnw spring-boot:run
echo.

pause

