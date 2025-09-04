# 📚 Estrutura do CRUD Biblioteca

## 🎯 Objetivo
Criar um CRUD de Biblioteca utilizando Spring Boot + Java, aplicando DTOs e relacionamento entre entidades (Autor e Livro).

---

## 🚀 Criar projeto Spring Boot
**Dependências:**
- Spring Web
- Spring Data JPA
- Validation
- H2 Database

**Nome do projeto:** `api_biblioteca`

---

## 📂 Estrutura de pacotes
```
src/main/java/io/github/usuario/api_biblioteca/
├── controller
├── dto
├── model
├── repository
└── service
```
---

## 🧩 Model (Entidades)

### Classe Autor
**Atributos:**
- id (Long)
- nome (String)

**Anotações:**
- @Entity, @Id, @GeneratedValue
- @Column(nullable = false)

**Relacionamento:**
- @OneToMany(mappedBy = "autor") → lista de livros

### Classe Livro
**Atributos:**
- id (Long)
- titulo (String)
- anoPublicacao (Integer)
- genero (String)
- status (String: DISPONIVEL, EMPRESTADO)
- autor (Autor)

**Anotações:**
- @Entity, @Id, @GeneratedValue
- @Column(nullable = false)

**Relacionamento:**
- @ManyToOne → autor

---
## 📝 DTOs
- `AutorRequestDTO` → usado para entrada de dados (POST, PUT)  
- `AutorResponseDTO` → usado para retorno (GET)  
- `LivroRequestDTO` → usado para entrada de dados (POST, PUT)  
- `LivroResponseDTO` → usado para retorno (GET)  

---

## 🗄️ Repository
- `AutorRepository` → estende `JpaRepository<Autor, Long>`  
- `LivroRepository` → estende `JpaRepository<Livro, Long>`  

---

## ⚙️ Service
### AutorService
- Criar autor  
- Listar autores  
- Buscar por ID  
- Atualizar autor  
- Excluir autor  

### LivroService
- Criar livro (já vinculando a um autor existente)  
- Listar todos livros  
- Buscar por ID  
- Atualizar livro  
- Excluir livro  

---

## 🌐 Controller
### AutorController
- POST `/autores` → criar autor  
- GET `/autores` → listar todos  
- GET `/autores/{id}` → buscar por ID  
- PUT `/autores/{id}` → atualizar  
- DELETE `/autores/{id}` → excluir  

### LivroController
- POST `/livros` → criar livro (com id do autor)  
- GET `/livros` → listar todos  
- GET `/livros/{id}` → buscar por ID  
- PUT `/livros/{id}` → atualizar  
- DELETE `/livros/{id}` → excluir  

---

## 🗄️ Banco de Dados H2
Configurar `application.yml` para usar banco em memória e habilitar `/h2-console`.

---

## 🔍 Testes manuais
Usar Postman ou Insomnia para testar os endpoints.  
Criar primeiro Autor, depois cadastrar Livros vinculados a ele.

---

## ✅ Testes Unitários (JUnit + Mockito)
**Objetivo:** garantir que os métodos das `Services` funcionem corretamente isoladamente.

### Como testar:
- Criar classes de teste no pacote `src/test/java/io/github/usuario/api_biblioteca/service/`
- Usar `@ExtendWith(MockitoExtension.class)` para habilitar mocks
- Mockar os `Repositories` com `@Mock` e injetar no `Service` com `@InjectMocks`
- Exemplos de métodos de teste:
  - Criar um autor → verificar se o `save()` do repository foi chamado  
  - Listar autores → mockar retorno do repository e validar lista  
  - Criar livro → verificar associação com autor existente  
  - Atualizar / deletar → validar chamadas do repository e retorno esperado  

**Dependências para testes:**
```
xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
```
