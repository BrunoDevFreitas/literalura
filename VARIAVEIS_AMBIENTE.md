# 🔐 COMO USAR VARIÁVEIS DE AMBIENTE - Windows PowerShell

## OPÇÃO 1: Variáveis de Ambiente Temporárias (Sessão Atual)

Abra PowerShell e execute:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/literalura"
$env:DB_USER="postgres"
$env:DB_PASSWORD="sua_senha_aqui"
```

**Depois execute a aplicação:**
```powershell
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

⚠️ **Nota:** Essas variáveis expiram quando você fecha o PowerShell


---

## OPÇÃO 2: Variáveis de Ambiente Permanentes (Recomendado)

### Via PowerShell (Como Admin):

```powershell
# Abra PowerShell como Administrador

# Defina as variáveis:
[Environment]::SetEnvironmentVariable("DB_URL", "jdbc:postgresql://localhost:5432/literalura", "User")
[Environment]::SetEnvironmentVariable("DB_USER", "postgres", "User")
[Environment]::SetEnvironmentVariable("DB_PASSWORD", "sua_senha_aqui", "User")
```

**Depois reinicie o PowerShell (feche e abra novamente)**

```powershell
# Verificar se foi criado:
$env:DB_URL
$env:DB_USER
$env:DB_PASSWORD
```

---

## OPÇÃO 3: Variáveis de Ambiente do Windows (GUI)

1. Pressione: `Win + X` → Abrir Configurações
2. Digite: "Variáveis de ambiente" → Clique em "Editar variáveis de ambiente do sistema"
3. Clique em "Variáveis de ambiente..."
4. Clique em "Nova..." (em Variáveis de usuário)
5. Adicione:
   - Nome: `DB_URL`
   - Valor: `jdbc:postgresql://localhost:5432/literalura`
6. Repita para `DB_USER` e `DB_PASSWORD`
7. Clique OK e reinicie o IDE/Terminal

---

## VERIFICAR SE ESTÁ FUNCIONANDO

```powershell
# No PowerShell, execute:
$env:DB_URL
$env:DB_USER
$env:DB_PASSWORD

# Deve retornar os valores que você definiu
```

---

## Executar Aplicação com Variáveis de Ambiente

```powershell
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

Spring Boot automaticamente lerá as variáveis de `${DB_URL}`, `${DB_USER}` e `${DB_PASSWORD}`

