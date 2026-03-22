<div align="center">

```
              ██████╗ ███████╗███████╗████████╗     █████╗ ██████╗ ██╗
              ██╔══██╗██╔════╝██╔════╝╚══██╔══╝   ██╔══██╗██╔══██╗██║
              ██████╔╝█████╗  ███████╗   ██║      ███████║██████╔╝██║
              ██╔══██╗██╔══╝  ╚════██║   ██║      ██╔══██║██╔═══╝ ██║
              ██║  ██║███████╗███████║   ██║      ██║  ██║██║     ██║
              ╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝      ╚═╝  ╚═╝╚═╝     ╚═╝
```

### **Gerenciamento de Usuários e Missões**

*Uma API REST profissional construída com Spring Boot 4 · Java 21 · MySQL*

<br/>

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Lombok](https://img.shields.io/badge/Lombok-red?style=for-the-badge)

</div>

---

## 📌 Sobre o Projeto

> API REST desenvolvida como projeto de prática com **Spring Boot**, **Git** e **GitHub**, cobrindo desde a modelagem de dados até boas práticas de uma API de nível profissional.

O sistema permite gerenciar **Usuários** e **Missões**, associando cada usuário a uma missão com grau de dificuldade e status. O projeto foi construído iterativamente, evoluindo de um esqueleto básico até uma API com validações, tratamento de erros padronizado, logging estruturado e documentação automática via Swagger.

---

## 🗂️ Estrutura do Projeto

```
src/main/java/org/SpringBoot_GitHub/
│
├── 📁 Config/
│   ├── ConfigSwagger/        → Configuração do Swagger / OpenAPI
│   └── ConfigWebApp/         → Registro do interceptor de logging
│
├── 📁 Controllers/
│   ├── MissoesController     → Endpoints REST de Missões
│   └── UsuarioController     → Endpoints REST de Usuários
│
├── 📁 Docs/
│   └── ProblemResponse       → Modelo padronizado de resposta de erro
│
├── 📁 GerenciamentoErros/
│   ├── ManipuladorExcecoesGlobais       → @RestControllerAdvice global
│   └── RecursosNaoEncontradosException  → Exceção customizada 404
│
├── 📁 Loggings/
│   └── InterceptorLoggingApiUsuario     → Interceptor de acesso com tempo de resposta
│
├── 📁 Models/
│   ├── DTOs/
│   │   ├── MissoesDTO        → DTO com validações para Missões
│   │   └── UsuarioDTO        → DTO com validações para Usuários
│   ├── Entities/
│   │   ├── Missoes           → Entidade JPA da tabela missoes
│   │   └── Usuario           → Entidade JPA da tabela usuarios
│   └── Enums/
│       ├── GrauDificuldade   → FACIL | MEDIO | DIFICIL | EXTREMO
│       └── StatusMissao      → PENDENTE | EM_ANDAMENTO | CONCLUIDA | CANCELADA
│
├── 📁 Repositories/
│   ├── MissoesRepository     → JpaRepository<Missoes, Long>
│   └── UsuarioRepository     → JpaRepository<Usuario, Long>
│
└── 📁 Services/
    ├── MissoesService        → Regras de negócio de Missões
    └── UsuarioService        → Regras de negócio de Usuários
```

---

## 🚀 Funcionalidades

### 🎯 Missões — `/api/v1/missoes`

| Método | Endpoint        | Descrição                        | Status de Resposta |
|--------|-----------------|----------------------------------|--------------------|
| GET    | `/`             | Lista todas as missões           | `200 OK`           |
| GET    | `/{id}`         | Busca missão por ID              | `200` / `404`      |
| POST   | `/`             | Cadastra nova missão             | `201` / `400`      |
| PUT    | `/{id}`         | Atualiza missão existente        | `200` / `400` / `404` |
| DELETE | `/{id}`         | Remove missão pelo ID            | `204` / `404`      |

### 👤 Usuários — `/api/v1/usuarios`

| Método | Endpoint        | Descrição                        | Status de Resposta |
|--------|-----------------|----------------------------------|--------------------|
| GET    | `/`             | Lista todos os usuários          | `200 OK`           |
| GET    | `/{id}`         | Busca usuário por ID             | `200` / `404`      |
| POST   | `/`             | Cadastra novo usuário            | `201` / `400` / `404` |
| PUT    | `/{id}`         | Atualiza usuário existente       | `200` / `400` / `404` |
| DELETE | `/{id}`         | Remove usuário pelo ID           | `204` / `404`      |

---

## ✅ Validações de Campos

### MissoesDTO
| Campo            | Regra                                      |
|------------------|--------------------------------------------|
| `nomeMissao`     | Obrigatório, não vazio                     |
| `descricao`      | Obrigatório, não vazio                     |
| `grauDificuldade`| Obrigatório — `FACIL / MEDIO / DIFICIL / EXTREMO` |
| `statusMissao`   | Obrigatório — `PENDENTE / EM_ANDAMENTO / CONCLUIDA / CANCELADA` |

### UsuarioDTO
| Campo            | Regra                                      |
|------------------|--------------------------------------------|
| `nome`           | Obrigatório, não vazio                     |
| `sobrenome`      | Obrigatório, não vazio                     |
| `cpf`            | Obrigatório + validação de dígito verificador (`@CPF`) |
| `dataNascimento` | Obrigatório                                |
| `telefone`       | Obrigatório, não vazio                     |
| `email`          | Obrigatório + formato válido (`@Email`)    |
| `idMissao`       | Obrigatório — missão deve existir no banco |

---

## 🛡️ Tratamento de Erros Global

Todas as exceções são capturadas pelo `ManipuladorExcecoesGlobais` (`@RestControllerAdvice`) e retornam um JSON padronizado:

```json
{
  "status": 404,
  "titulo": "Recurso não encontrado",
  "detalhe": "Usuário não encontrado com o ID: 99",
  "instancia": "/api/v1/usuarios/99",
  "timestamp": "21/03/2026 17:41:50",
  "metodo": "GET"
}
```

| Exceção                              | HTTP Status | Cenário                                      |
|--------------------------------------|-------------|----------------------------------------------|
| `RecursosNaoEncontradosException`    | `404`       | ID não existe no banco                       |
| `NoResourceFoundException`           | `404`       | Rota ou recurso estático inexistente         |
| `MethodArgumentTypeMismatchException`| `400`       | Tipo inválido na URL (ex: texto no lugar de número) |
| `MethodArgumentNotValidException`    | `400`       | Falha nas validações do DTO (`@Valid`)        |
| `DataIntegrityViolationException`    | `409`       | Violação de unicidade no banco (CPF, email, telefone duplicados) |
| `IllegalStateException`              | `422`       | Violação de regra de negócio                 |
| `IllegalArgumentException`           | `400`       | Argumento inválido na requisição             |
| `Exception`                          | `500`       | Erro interno inesperado                      |

---

## 📋 Logging Estruturado

O projeto utiliza **Logback** com 3 arquivos de log separados, com **rolling diário** (mantidos por 30 dias):

| Arquivo              | Conteúdo                                           |
|----------------------|----------------------------------------------------|
| `logs/app.log`       | Log geral da aplicação (INFO+)                     |
| `logs/error.log`     | Apenas erros críticos (ERROR)                      |
| `logs/api-access.log`| Registro de cada requisição: IP, método, URI, status HTTP e tempo de resposta |

**Exemplo de entrada no `api-access.log`:**
```
2026-03-21 17:41:50 IP=127.0.0.1 METHOD=POST URI=/api/v1/usuarios STATUS=201 TIME=43ms
```

O interceptor `InterceptorLoggingApiUsuario` mede o tempo de resposta de cada requisição usando `System.currentTimeMillis()` no `preHandle` e calculando a diferença no `afterCompletion`.

---

## 📖 Documentação com Swagger

A API possui documentação interativa gerada automaticamente via **SpringDoc OpenAPI**.

Acesse após subir a aplicação:

```
http://localhost:8080/swagger-ui.html
```

Todos os endpoints estão documentados com `@Operation`, `@ApiResponse` e o modelo de erro `ProblemResponse` exposto via `@Schema`.

---

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 21+
- Maven 3.9+
- MySQL 8.0+

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/Pratica_SpringBoot_GitHub.git
cd Pratica_SpringBoot_GitHub
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE pratica_springboot_github;
```

### 3. Configure o `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pratica_springboot_github?useSSL=false&serverTimezone=UTC
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 🧪 Exemplo de Requisições

### Criar uma Missão

```http
POST /api/v1/missoes
Content-Type: application/json

{
  "nomeMissao": "Operação Tempestade",
  "descricao": "Infiltrar o quartel inimigo e recuperar os dados",
  "grauDificuldade": "EXTREMO",
  "statusMissao": "PENDENTE"
}
```

### Criar um Usuário

```http
POST /api/v1/usuarios
Content-Type: application/json

{
  "nome": "João",
  "sobrenome": "Silva",
  "cpf": "12345678909",
  "dataNascimento": "1995-08-15",
  "telefone": "47999998888",
  "email": "joao.silva@email.com",
  "idMissao": 1
}
```

### Atualizar Status de uma Missão

```http
PUT /api/v1/missoes/1
Content-Type: application/json

{
  "nomeMissao": "Operação Tempestade",
  "descricao": "Infiltrar o quartel inimigo e recuperar os dados",
  "grauDificuldade": "EXTREMO",
  "statusMissao": "EM_ANDAMENTO"
}
```

---

## 🏗️ Tecnologias Utilizadas

| Tecnologia                  | Versão   | Uso                                      |
|-----------------------------|----------|------------------------------------------|
| Java                        | 21       | Linguagem principal                      |
| Spring Boot                 | 4.0.4    | Framework base                           |
| Spring Data JPA             | —        | Persistência e repositórios              |
| Spring Validation           | —        | Validação de DTOs com Bean Validation    |
| Hibernate Validator         | —        | `@CPF` e demais constraints              |
| MySQL Connector             | —        | Driver JDBC para MySQL                   |
| Lombok                      | —        | Redução de boilerplate                   |
| SpringDoc OpenAPI           | 2.8.5    | Documentação Swagger automática          |
| Logback                     | —        | Logging estruturado com rolling diário   |
| Spring Boot DevTools        | —        | Reload automático em desenvolvimento     |

---

## 📐 Diferenciais Técnicos

- **Tratamento de erros RFC-inspirado** — `ProblemResponse` com `status`, `titulo`, `detalhe`, `instancia`, `timestamp` e `metodo HTTP`, seguindo o espírito do RFC 7807
- **Validação de CPF real** — uso do `@CPF` do Hibernate Validator que valida o dígito verificador, não apenas o formato
- **Interceptor de performance** — mede e registra o tempo de cada requisição em milissegundos
- **Logging tripartido** — separação clara entre log geral, erros e acesso à API
- **`ddl-auto=none`** — controle total do schema do banco, sem alterações automáticas em produção
- **Cobertura de erros de borda** — trata `MethodArgumentTypeMismatchException` (ex: `/usuarios/abc`) e `NoResourceFoundException` (ex: `/favicon.ico`) com resposta JSON padronizada

---

<div align="center">

*Desenvolvido como projeto de prática — Spring Boot · Git · GitHub*

</div>
