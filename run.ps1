# Script para rodar a aplicação Literalura com variáveis de ambiente corretas

# Variáveis de ambiente
$env:DB_URL = "jdbc:postgresql://localhost:5432/literatura"
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "skillet1302"

Write-Host "========================================" -ForegroundColor Green
Write-Host "Iniciando Literalura Application" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Configurações:" -ForegroundColor Yellow
Write-Host "Database: $($env:DB_URL)" -ForegroundColor Yellow
Write-Host "User: $($env:DB_USER)" -ForegroundColor Yellow
Write-Host "Password: [CONFIGURADA]" -ForegroundColor Yellow
Write-Host ""

# Build e run
Write-Host "Compilando projeto..." -ForegroundColor Cyan
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw.cmd clean package -DskipTests 2>&1 | Tee-Object -FilePath compilation.log

if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "Build realizado com sucesso!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Rodando aplicação..." -ForegroundColor Cyan
    java -jar target/literalura-0.0.1-SNAPSHOT.jar 2>&1 | Tee-Object -FilePath execution.log
} else {
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "ERRO na compilação!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    exit 1
}

