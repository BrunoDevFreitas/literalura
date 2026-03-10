# 🔧 CRIAR BANCO DE DADOS - Instruções

## ⚡ Forma Rápida: Use pgAdmin

1. Abra **pgAdmin** (está instalado no seu PC)
2. Clique em **Servers** → **PostgreSQL 14** (ou sua versão)
3. Clique com botão DIREITO em **Databases**
4. Selecione **Create** → **Database...**
5. Na janela que abrir:
   - **Database name**: `literalura`
   - Deixe outras opções padrão
6. Clique **Save**

## ✅ Pronto!

Agora execute:
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/literalura"
$env:DB_USER="postgres"
$env:DB_PASSWORD="skillet1302"
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

A aplicação vai iniciar e exibir o menu!

