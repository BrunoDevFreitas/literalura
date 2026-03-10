# 🚀 INSTRUÇÕES PARA RODAR O LITERALURA

## Método 1: Via PowerShell (Recomendado)

```powershell
# Abra PowerShell como Administrador
cd C:\Users\Pratinho\Downloads\literalura

# Execute:
.\mvnw spring-boot:run
```

**Esperado:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot :: (v3.3.0)

=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
... (menu)
```

---

## Método 2: Via Batch Script

Clique duas vezes em:
```
C:\Users\Pratinho\Downloads\literalura\RODAR_APLICACAO.bat
```

---

## Método 3: Capturar Logs

```powershell
cd C:\Users\Pratinho\Downloads\literalura

# Executar e salvar log:
.\mvnw spring-boot:run 2>&1 | Tee-Object -FilePath "literalura_$(Get-Date -Format 'yyyy-MM-dd_HH-mm-ss').log"
```

---

## Método 4: Executar JAR Diretamente

```powershell
cd C:\Users\Pratinho\Downloads\literalura

# Compilar JAR:
.\mvnw clean package -DskipTests

# Executar:
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

---

## ⚙️ Pré-requisitos

- ✅ Java 17+ instalado
- ✅ Maven 3.6+ (incluído no projeto)
- ✅ PostgreSQL rodando em localhost:5432
- ✅ Credenciais corretas em application.properties

---

## 🔧 Verificação Rápida

```powershell
# Verificar Java:
java -version

# Verificar Maven:
.\mvnw -v

# Verificar conexão PostgreSQL:
# (Tente conectar com cliente PostgreSQL)
```

---

## 📋 Se houver erro de autenticação PostgreSQL:

```
FATAL: autenticação do tipo senha falhou para o usuário "postgres"
```

**Solução:**
1. Verifique user/password em `src/main/resources/application.properties`
2. Verifique se PostgreSQL está rodando
3. Verifique se banco "literatura" existe

```sql
-- SQL para criar banco (se não existir):
CREATE DATABASE literatura;
```

---

## ✅ Aplicação Iniciada com Sucesso!

Você verá:
- ✅ Spring Boot startup
- ✅ Hibernate criando tabelas
- ✅ Menu no terminal
- ✅ "Pronto para usar!"

---

**Divirta-se! 📚✨**

