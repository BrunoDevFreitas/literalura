# 🔐 ERRO DE AUTENTICAÇÃO POSTGRESQL - SOLUÇÃO

## Problema

```
FATAL: autentica??o do tipo senha falhou para o usu?rio "postgres"
Failed password authentication for user "postgres"
```

**A senha do PostgreSQL não é "postgres".**

---

## ✅ SOLUÇÃO

### PASSO 1: Encontrar/Resetar Senha PostgreSQL

#### Opção A: Se você sabe a senha correta
Edite o arquivo: `src/main/resources/application.properties`

```properties
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

#### Opção B: Resetar Senha do PostgreSQL

**Windows - Reiniciar PostgreSQL com senha padrão:**

1. Abra Services (Serviços):
   - Pressione: `Windows + R`
   - Digite: `services.msc`

2. Procure: `postgresql-x64-15` (ou similar)

3. Clique com botão direito → Properties (Propriedades)

4. Na aba "Log On", marque: "Local System account"

5. Clique "Apply" e "OK"

6. Clique com botão direito no serviço → Start

7. Agora tente novamente com senha: `postgres`

#### Opção C: Mudar Senha do PostgreSQL

Se você tiver acesso ao PostgreSQL:

```bash
# Abra psql (sem conectar em banco específico)
psql -U postgres -h localhost

# Dentro do psql, mude a senha:
ALTER USER postgres WITH PASSWORD 'novasenha';

# Saia
\q
```

Depois edite `application.properties`:
```properties
spring.datasource.password=novasenha
```

---

### PASSO 2: Testar Conexão

```bash
# Teste a conexão manualmente
psql -U postgres -h localhost -c "SELECT version();"
```

Se pedir senha e aceitar, significa que está correto.

---

### PASSO 3: Rodar Aplicação

```bash
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

---

## 🆘 Se Ainda Não Funcionar

### Verifique PostgreSQL

1. **PostgreSQL está rodando?**
   ```bash
   Get-Service postgresql* | Select Status
   ```

2. **PostgreSQL está na porta correta (5432)?**
   ```bash
   netstat -an | findstr 5432
   ```

3. **Se não existir, inicie:**
   ```bash
   Get-Service postgresql* | Start-Service
   ```

---

## 📝 Arquivo de Configuração

Localização: `src/main/resources/application.properties`

```properties
# Altere apenas a senha aqui se necessário
spring.datasource.password=COLOQUE_SUA_SENHA
```

---

## ✨ Resultado Esperado

Quando funcionar, verá:

```
2026-03-09T18:22:21.901-03:00  INFO ... Bootstrapping Spring Data JPA repositories
2026-03-09T18:22:23.191-03:00  INFO ... HikariPool-1 - Starting...

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

## 📞 Dicas

- Senha padrão PostgreSQL durante instalação geralmente é `postgres`
- Se deixou em branco, tente com string vazia: `spring.datasource.password=`
- Não há espaços, use: `password=senha` (sem spaces)

---

Resolvido! Agora execute novamente. 🚀

