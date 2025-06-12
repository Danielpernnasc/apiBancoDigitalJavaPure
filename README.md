# 🏦 API - Meu Banco Digital

Este é o backend do projeto **Meu Banco Digital**, desenvolvido com **Java 17** e **Spring Boot 3.2.5**.  
A API oferece autenticação via JWT, endpoints para operações bancárias (GET, POST, PUT, DELETE) e documentação interativa com Swagger.

Este projeto foi desenvolvido como parte de um desafio técnico e representa minha primeira aplicação Java com Spring Boot. 🚀

---

## 🚀 Tecnologias Utilizadas

- ✅ Java 17
- ✅ Spring Boot 3.2.5
- ✅ Spring Security
- ✅ JWT (JSON Web Token)
- ✅ Maven
- ✅ Swagger (OpenAPI)

---

## 📁 Estrutura do Projeto


apiBancoDigital/ ├── src/ │   └── main/ │       ├── java/ │       │   └── com/digitalbank/... │       └── resources/ │           └── application.properties ├── pom.xml ├── README.md

---

## ⚙️ Como Executar o Projeto Localmente

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Danielpernnasc/apiBancoDigital.git
   cd apiBancoDigital

2. Instale as dependências

mvn clean install


3. Execute o projeto

mvn spring-boot:run


4. Acesse a documentação Swagger

http://localhost:8080/swagger-ui/index.html





---

🔐 Autenticação com JWT

A API utiliza autenticação baseada em JWT. Após realizar o login, um token é gerado e deve ser utilizado no header das requisições:

Authorization: Bearer <seu_token_aqui>


---

🧪 Testes

(em breve será adicionado suporte a testes automatizados com JUnit ou Spring Test)
Para rodar testes (caso configurado):

mvn test


---

🌐 Frontend - Angular com Microfrontend

Este projeto backend se conecta com o frontend desenvolvido com Angular 15 e Module Federation:

👉 Repositório do Frontend: MFEMyBank
 (Angular)
  https://protect2.fireeye.com/v1/url?k=10042270-7517e131-10000245-000babddbcda-475817249de4be33&q=1&e=ad19b226-0119-4236-b27c-3da737192831&u=https%3A%2F%2Fgithub.com%2FDanielpernnasc%2FMFEMyBank%2Ftree%2Fmaster


---

🗂️ Endpoints Principais

Você pode consultar e testar todos os endpoints através do Swagger:

📌 http://localhost:8080/swagger-ui/index.html


---

📌 Observações

Projeto com fins educativos e demonstrativos.

Desenvolvido como parte de um teste técnico para vaga de desenvolvedor backend.

Feedbacks são bem-vindos!

---

🏁 Próximos Passos (em progresso)

✅ Melhorar cobertura de testes

✅ Adicionar Dockerfile

✅ Automatizar deploy com GitHub Actions

✅ Implementar base de dados com JPA + H2/PostgreSQL


---

### ✅ O que fazer agora?

1. Crie um arquivo `README.md` no repositório (ou substitua o atual).
2. Copie e cole o conteúdo acima.
3. Commit e push para o GitHub.

Se quiser, posso agora gerar um **`Dockerfile`** ou configurar um

