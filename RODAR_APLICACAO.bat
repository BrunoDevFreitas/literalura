@echo off
cd C:\Users\Pratinho\Downloads\literalura
echo.
echo ========================================
echo  LiterAlura - Iniciando Aplicacao
echo ========================================
echo.
echo Compilando...
call mvnw clean compile -q
if %ERRORLEVEL% NEQ 0 (
    echo ERRO NA COMPILACAO!
    mvnw clean compile
    pause
    exit /b 1
)
echo Compilacao OK!
echo.
echo Iniciando aplicacao...
echo.
call mvnw spring-boot:run
pause

