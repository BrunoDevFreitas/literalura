# ❌ ERRO RESOLVIDO - PostgreSQL Não Conectou

## 🔍 Diagnóstico do Erro

```
ERROR: Unable to determine Dialect without JDBC metadata
```

**Causa**: PostgreSQL não está respondendo ou o banco não foi criado.

---

## ✅ SOLUÇÃO RÁPIDA (3 PASSOS)

### PASSO 1: Iniciar PostgreSQL

#### Opção A: Serviços Windows
```
1. Pressione: Windows + R
2. Digite: services.msc
3. Procure: postgresql-x64-15 (ou similar)
4. Clique com botão direito → Iniciar
```

#### Opção B: Command Line
```bash
# PowerShell como administrador
Get-Service postgresql* | Start-Service
```

---

### PASSO 2: Criar o Banco de Dados

```bash
# Abra PowerShell e execute:
psql -U postgres -c "CREATE DATABASE literalura;"
```

Se pedir senha, digite: `postgres`

**Resultado esperado:**
```
CREATE DATABASE
```

Ou se já existir:
```
ERROR:  database "literalura" already exists
```
(Neste caso, é ok - banco já existe)

---

### PASSO 3: Rodar a Aplicação

```bash
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

---

## 🆘 Troubleshooting Avançado

### Se PostgreSQL não inicia:

1. **Verifique se está instalado:**
```bash
psql --version
```

2. **Se não estiver:**
   - Baixe em: https://www.postgresql.org/download/windows/
   - Instale com padrões
   - Reinicie

3. **Se der erro "psql command not found":**
   - Adicione ao PATH: `C:\Program Files\PostgreSQL\15\bin`
   - Reinicie PowerShell

---

### Se ainda não conecta:

1. **Teste a conexão:**
```bash
psql -U postgres -h localhost -p 5432
```

2. **Se der erro de conexão:**
   - PostgreSQL pode estar em outro host/porta
   - Edite: `src/main/resources/application.properties`
   - Procure: `spring.datasource.url`
   - Altere para sua URL PostgreSQL

3. **Se der erro de senha:**
   - Verifique credenciais em `application.properties`
   - Padrão: username=`postgres`, password=`postgres`

---

## 🔧 Arquivo de Configuração

Localização: `src/main/resources/application.properties`

```properties
# Configuração do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres

# Se usar host/porta diferentes, altere acima
```

---

## 📋 Checklist de Resolução

- ☑️ PostgreSQL está instalado
- ☑️ PostgreSQL está rodando (Services)
- ☑️ Banco "literalura" foi criado
- ☑️ Credenciais estão corretas em application.properties
- ☑️ Executou: `.\mvnw spring-boot:run`

---

## ✨ Resultado Esperado

Quando resolver, verá:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___  '_  '_  '_ \/ _`  \ \ \ \
 \\/  ___) _)      (_   ) ) ) )
  '  ____ .___ __ _\__,  / / / /
 =========_==============___/=/_/_/_/

 :: Spring Boot ::                (v3.3.0)

2026-03-09T18:19:51.423-03:00  INFO ... Starting LiteraluraApplication

=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair
Escolha uma opção: _
```

---

## 🎯 Próximo Passo

1. Resolva o PostgreSQL usando este guia
2. Execute: `.\mvnw spring-boot:run`
3. Digite um número do menu (1-5 ou 0)
4. Aproveite! 🎉

---

Desenvolvido com ❤️ usando Spring Boot 3.3.0

