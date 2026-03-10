# ❌ ERRO: BANCO DE DADOS NÃO EXISTE

## Problema
```
FATAL: banco de dados "literalura" não existe
```

---

## ✅ Solução: Criar o Banco de Dados

### OPÇÃO 1: Usar pgAdmin (Interface Gráfica)

1. Abra **pgAdmin**
2. Clique em **Servers** → **PostgreSQL**
3. Clique com botão direito em **Databases**
4. Selecione **Create** → **Database**
5. Nome: `literalura`
6. Clique **Save**

---

### OPÇÃO 2: Usar SQL Direto

#### No pgAdmin Query Tool:
```sql
CREATE DATABASE literalura;
```

#### Ou via PowerShell (se PostgreSQL estiver configurado):
```powershell
"CREATE DATABASE literalura;" | psql -U postgres
```

---

### OPÇÃO 3: Script SQL Fornecido

Arquivo criado: `create_database.sql`

Se tiver psql instalado:
```powershell
psql -U postgres -h localhost -f create_database.sql
```

---

## 🔧 Após Criar o Banco

Execute novamente:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/literalura"
$env:DB_USER="postgres"
$env:DB_PASSWORD="skillet1302"

cd C:\Users\Pratinho\Downloads\literalura
.\mvnw spring-boot:run
```

---

## ✨ Aplicação Iniciará com Sucesso!

Você verá o menu:
```
=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair
Escolha uma opção:
```


