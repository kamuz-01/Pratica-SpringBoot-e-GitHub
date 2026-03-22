<div align="center">

```
 ██████╗ ██████╗ ███╗   ██╗████████╗██████╗ ██╗██████╗ ██╗   ██╗████████╗██╗███╗   ██╗ ██████╗
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝██╔══██╗██║██╔══██╗██║   ██║╚══██╔══╝██║████╗  ██║██╔════╝
██║     ██║   ██║██╔██╗ ██║   ██║   ██████╔╝██║██████╔╝██║   ██║   ██║   ██║██╔██╗ ██║██║  ███╗
██║     ██║   ██║██║╚██╗██║   ██║   ██╔══██╗██║██╔══██╗██║   ██║   ██║   ██║██║╚██╗██║██║   ██║
╚██████╗╚██████╔╝██║ ╚████║   ██║   ██║  ██║██║██████╔╝╚██████╔╝   ██║   ██║██║ ╚████║╚██████╔╝
 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝╚═════╝  ╚═════╝    ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝
```

### Obrigado por querer contribuir com este projeto! 🎉
*Leia este guia antes de abrir uma issue ou enviar um pull request.*

</div>

---

## 📋 Índice

- [Código de Conduta](#-código-de-conduta)
- [Como Posso Contribuir?](#-como-posso-contribuir)
- [Configurando o Ambiente](#-configurando-o-ambiente)
- [Fluxo de Trabalho com Git](#-fluxo-de-trabalho-com-git)
- [Convenção de Commits](#-convenção-de-commits)
- [Nomenclatura de Branches](#-nomenclatura-de-branches)
- [Padrões de Código](#-padrões-de-código)
- [Abrindo Pull Requests](#-abrindo-pull-requests)
- [Reportando Bugs](#-reportando-bugs)
- [Sugerindo Melhorias](#-sugerindo-melhorias)

---

## 🤝 Código de Conduta

Este projeto adota um ambiente colaborativo e respeitoso. Ao contribuir, você concorda em:

- Usar linguagem acolhedora e inclusiva
- Respeitar pontos de vista e experiências diferentes
- Aceitar críticas construtivas com profissionalismo
- Focar no que é melhor para o projeto e para a comunidade
- Nunca realizar ataques pessoais, assédio ou comportamento tóxico de qualquer natureza

Contribuições que violem esses princípios serão rejeitadas e o contribuidor poderá ser bloqueado do projeto.

---

## 💡 Como Posso Contribuir?

Existem várias formas de colaborar, desde as mais simples até as mais técnicas:

| Tipo de Contribuição      | Descrição                                                          |
|---------------------------|--------------------------------------------------------------------|
| 🐛 **Reportar bugs**      | Encontrou algo quebrado? Abra uma issue detalhada                 |
| 💬 **Sugerir melhorias**  | Tem uma ideia? Proponha via issue antes de implementar            |
| 📝 **Melhorar docs**      | README, comentários, Javadoc — documentação nunca é demais       |
| ✅ **Escrever testes**    | Testes unitários e de integração são sempre bem-vindos            |
| 🔧 **Corrigir bugs**      | Veja as issues abertas com a tag `bug`                            |
| ✨ **Implementar features**| Veja as issues com a tag `enhancement`                           |
| 🔍 **Revisar PRs**        | Code review é uma das contribuições mais valiosas                 |

---

## ⚙️ Configurando o Ambiente

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21+** — [Download](https://adoptium.net/)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** — [Download](https://dev.mysql.com/downloads/)
- **Git** — [Download](https://git-scm.com/)
- Uma IDE de sua preferência (**IntelliJ IDEA** recomendado)

### Passo a passo

**1. Faça um fork do repositório**

Clique no botão **Fork** no topo da página do GitHub.

**2. Clone o seu fork**

```bash
git clone https://github.com/seu-usuario/Pratica_SpringBoot_GitHub.git
cd Pratica_SpringBoot_GitHub
```

**3. Adicione o repositório original como `upstream`**

```bash
git remote add upstream https://github.com/dono-original/Pratica_SpringBoot_GitHub.git
```

**4. Crie o banco de dados**

```sql
CREATE DATABASE pratica_springboot_github;
```

**5. Configure o `application.properties`**

```properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

**6. Verifique se o projeto compila e sobe corretamente**

```bash
./mvnw spring-boot:run
```

Se a aplicação subir em `http://localhost:8080` sem erros, o ambiente está pronto. ✅

---

## 🌿 Fluxo de Trabalho com Git

Este projeto segue o modelo **Git Flow simplificado**:

```
main
 └── develop
      ├── feature/nome-da-feature
      ├── fix/nome-do-bug
      └── docs/nome-da-documentacao
```

| Branch       | Finalidade                                                  |
|--------------|-------------------------------------------------------------|
| `main`       | Código estável e pronto para produção. Nunca commite direto |
| `develop`    | Branch de integração. Base para novas branches              |
| `feature/`   | Desenvolvimento de novas funcionalidades                    |
| `fix/`       | Correção de bugs                                            |
| `docs/`      | Alterações apenas em documentação                           |
| `refactor/`  | Refatorações sem alterar comportamento                      |
| `test/`      | Adição ou correção de testes                                |

### Fluxo padrão de contribuição

```bash
# 1. Sincronize seu fork com o upstream
git fetch upstream
git checkout develop
git merge upstream/develop

# 2. Crie sua branch a partir de develop
git checkout -b feature/minha-nova-feature

# 3. Faça suas alterações e commits
git add .
git commit -m "feat: adiciona endpoint de listagem por status"

# 4. Suba sua branch para o seu fork
git push origin feature/minha-nova-feature

# 5. Abra um Pull Request no GitHub apontando para develop
```

> ⚠️ **Nunca abra PR diretamente para `main`.** PRs devem sempre apontar para `develop`.

---

## ✍️ Convenção de Commits

Este projeto segue o padrão **Conventional Commits**. Commits fora deste padrão podem ser solicitados a serem reescritos.

### Formato

```
<tipo>(escopo opcional): <descrição curta no imperativo>

[corpo opcional — explique o "por quê", não o "o quê"]

[rodapé opcional — referência a issue: Closes #42]
```

### Tipos aceitos

| Tipo       | Quando usar                                                        |
|------------|--------------------------------------------------------------------|
| `feat`     | Nova funcionalidade                                                |
| `fix`      | Correção de bug                                                    |
| `docs`     | Alterações apenas em documentação                                  |
| `style`    | Formatação, ponto e vírgula, espaços — sem mudança de lógica      |
| `refactor` | Refatoração de código sem alterar comportamento externo            |
| `test`     | Adição ou correção de testes                                       |
| `chore`    | Tarefas de manutenção (dependências, configs, CI/CD)               |
| `perf`     | Melhoria de performance                                            |

### Exemplos

```bash
# ✅ Corretos
git commit -m "feat(missoes): adiciona filtro por grau de dificuldade"
git commit -m "fix(usuarios): corrige validação de CPF com pontuação"
git commit -m "docs: atualiza exemplos de requisição no README"
git commit -m "test(missoes): adiciona teste unitário para MissoesService"
git commit -m "refactor(usuario-service): extrai método de validação de unicidade"

# ❌ Incorretos — serão solicitados para reescrita
git commit -m "ajustes"
git commit -m "corrigindo bug"
git commit -m "WIP"
git commit -m "alterações várias"
```

> 💡 **Dica:** Escreva a descrição no **imperativo presente**: "adiciona", "corrige", "remove" — não "adicionado", "foi corrigido" ou "adicionando".

---

## 🏷️ Nomenclatura de Branches

Use nomes descritivos em **kebab-case**, sempre prefixados com o tipo:

```bash
# ✅ Corretos
feature/filtro-missoes-por-status
feature/endpoint-busca-usuario-por-cpf
fix/delete-retornando-204-para-id-inexistente
fix/validacao-cpf-com-mascara
docs/adiciona-exemplos-swagger
refactor/extrair-conversao-dto-para-mapper
test/testes-unitarios-usuario-service

# ❌ Incorretos
minha-branch
bugfix
feature1
joao-trabalho
```

---

## 📐 Padrões de Código

Para manter a consistência, siga as convenções já adotadas no projeto:

### Organização de pacotes

```
Controllers   → apenas recebe e delega, sem lógica de negócio
Services      → toda a lógica de negócio fica aqui
Repositories  → apenas interfaces JpaRepository, sem queries complexas desnecessárias
DTOs          → POJOs puros com anotações de validação — NUNCA anotações de JPA
Entities      → anotações JPA, sem lógica de negócio
```

### Regras gerais

- **Injeção de dependência via construtor** — não use `@Autowired` em campo
- **DTOs não têm `@Column` ou qualquer anotação `jakarta.persistence`**
- **Toda exceção de negócio deve ter um handler** em `ManipuladorExcecoesGlobais`
- **Novos endpoints precisam ter** `@Operation` e `@ApiResponses` do Swagger
- **Não commite** credenciais, `.env`, arquivos de IDE (`.idea`, `.iml`) ou a pasta `target/`
- **Não altere** `spring.jpa.hibernate.ddl-auto` para `create` ou `update` em PRs

### Exemplo de service correto

```java
@Service
@RequiredArgsConstructor                          // injeção via construtor com Lombok
public class ExemploService {

    private final ExemploRepository repository;

    @Transactional(readOnly = true)               // use readOnly = true em consultas
    public ExemploDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::converterParaDTO)
                .orElseThrow(() -> new RecursosNaoEncontradosException(
                        "Exemplo não encontrado com o ID: " + id));
    }
}
```

---

## 📬 Abrindo Pull Requests

Antes de abrir um PR, verifique o checklist abaixo:

```
[ ] Minha branch parte de `develop`, não de `main`
[ ] Os commits seguem o padrão Conventional Commits
[ ] O código compila sem erros (`./mvnw clean package`)
[ ] A aplicação sobe corretamente localmente
[ ] Não há imports não utilizados nem código comentado desnecessário
[ ] DTOs novos não têm anotações de JPA
[ ] Novos endpoints têm documentação Swagger (@Operation e @ApiResponses)
[ ] Novos casos de erro têm handler em ManipuladorExcecoesGlobais
[ ] O PR aponta para a branch `develop`
[ ] A descrição do PR explica o que foi feito e por quê
```

### Template de Pull Request

Ao abrir o PR, preencha a descrição com:

```markdown
## O que este PR faz?
Descrição clara e objetiva da mudança.

## Por que essa mudança é necessária?
Contexto e motivação.

## Como testar?
Passo a passo para validar as alterações.

## Issues relacionadas
Closes #numero-da-issue
```

---

## 🐛 Reportando Bugs

Ao abrir uma issue de bug, inclua:

```markdown
## Descrição do Bug
O que está acontecendo de errado?

## Como Reproduzir
1. Faça uma requisição POST para `/api/v1/...`
2. Com o body: `{ ... }`
3. Observe o erro

## Comportamento Esperado
O que deveria acontecer?

## Comportamento Atual
O que está acontecendo?

## Ambiente
- OS: Windows / Linux / macOS
- Java: 21
- Versão do projeto: 1.0.0
```

> 🔎 Antes de abrir uma issue, pesquise se já existe uma aberta com o mesmo problema.

---

## ✨ Sugerindo Melhorias

Tem uma ideia? Ótimo! Antes de implementar, abra uma issue de **enhancement** descrevendo:

```markdown
## Qual problema esta melhoria resolve?
Descrição clara do problema ou necessidade.

## Solução proposta
Como você imagina que isso poderia funcionar?

## Alternativas consideradas
Você pensou em outras abordagens? Quais?

## Contexto adicional
Referências, exemplos de outros projetos, prints, etc.
```

> 💬 Aguarde um feedback antes de começar a implementar — isso evita trabalho duplicado ou em direções que não serão aceitas.

---

## ❓ Dúvidas?

Se tiver alguma dúvida sobre o projeto ou sobre como contribuir, abra uma **issue** com a tag `question`. Respondemos o mais rápido possível.

---

<div align="center">

**Toda contribuição, por menor que seja, é valorizada. Obrigado! 🚀**

</div>
