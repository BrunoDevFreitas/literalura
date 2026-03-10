#!/usr/bin/env pwsh

<#
.SYNOPSIS
    Script para rodar LiterAlura com captura de logs
.DESCRIPTION
    Executa o projeto Spring Boot e salva todos os logs em arquivo
.EXAMPLE
    .\RODAR_COM_LOGS.ps1
#>

param(
    [switch]$BuildOnly = $false,
    [switch]$ShowLogs = $false
)

# Cores
$Green = [System.ConsoleColor]::Green
$Red = [System.ConsoleColor]::Red
$Yellow = [System.ConsoleColor]::Yellow
$Cyan = [System.ConsoleColor]::Cyan

function Write-Header {
    param([string]$Text)
    Write-Host ""
    Write-Host "════════════════════════════════════════════════════════════" -ForegroundColor $Cyan
    Write-Host "  $Text" -ForegroundColor $Green
    Write-Host "════════════════════════════════════════════════════════════" -ForegroundColor $Cyan
    Write-Host ""
}

function Write-Success {
    param([string]$Text)
    Write-Host "✅ $Text" -ForegroundColor $Green
}

function Write-Error {
    param([string]$Text)
    Write-Host "❌ $Text" -ForegroundColor $Red
}

function Write-Info {
    param([string]$Text)
    Write-Host "ℹ️  $Text" -ForegroundColor $Yellow
}

# Início
Write-Header "LiterAlura - Iniciando Aplicação"

# Verificar diretório
$projectDir = "C:\Users\Pratinho\Downloads\literalura"
if (-not (Test-Path $projectDir)) {
    Write-Error "Diretório não encontrado: $projectDir"
    exit 1
}

cd $projectDir
Write-Success "Diretório: $projectDir"

# Criar diretório de logs
$logDir = "$projectDir\logs"
if (-not (Test-Path $logDir)) {
    New-Item -ItemType Directory -Path $logDir | Out-Null
    Write-Success "Diretório de logs criado: $logDir"
}

# Timestamp para logs
$timestamp = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
$logFile = "$logDir\literalura_$timestamp.log"

Write-Header "Compilando Projeto"

# Compilar
Write-Info "Executando: mvnw clean compile"
& .\mvnw clean compile -q 2>&1 | Tee-Object -FilePath $logFile

if ($LASTEXITCODE -ne 0) {
    Write-Error "Erro na compilação!"
    Write-Info "Log salvo em: $logFile"
    if ($ShowLogs) {
        notepad $logFile
    }
    exit 1
}

Write-Success "Compilação concluída com sucesso!"

if ($BuildOnly) {
    Write-Success "Build concluído. Encerrando..."
    exit 0
}

Write-Header "Iniciando Aplicação Spring Boot"

Write-Info "Log será salvo em: $logFile"
Write-Info "Aguarde enquanto a aplicação inicia (10-15 segundos)..."
Write-Host ""

# Executar
& .\mvnw spring-boot:run 2>&1 | Tee-Object -FilePath $logFile -Append

Write-Host ""
Write-Info "Log completo salvo em: $logFile"

# Opção de ver logs
$response = Read-Host "Deseja abrir o arquivo de log? (S/N)"
if ($response -eq "S" -or $response -eq "s") {
    if (Test-Path $logFile) {
        Start-Process "notepad" $logFile
    }
}

