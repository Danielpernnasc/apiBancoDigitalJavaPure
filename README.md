


🏦 API - Livraria Ecommerce (Java Servlet)

Este é o backend do projeto Livraria Ecommerce, desenvolvido em Java puro com Servlets, utilizando conexão direta com MySQL via JDBC.

A aplicação realiza operações como cadastro e listagem de clientes e livros, além de autenticação com geração de token.
Ideal para estudos e prática com Java Web sem frameworks.


---

🚀 Tecnologias Utilizadas

✅ Java 17

✅ Jakarta Servlet API

✅ Apache Tomcat 11

✅ MySQL 8

✅ JDBC

✅ Maven

✅ Gson (para manipulação de JSON)



---

📁 Estrutura do Projeto

BookStore/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ... (Serviços, DAOs, Models e Servlets)
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── web.xml
├── pom.xml

⚙️ Como Executar o Projeto Localmente

1. Clone o repositório

git clone https://github.com/Danielpernnasc/apiBancoDigitalJavaPure.git
cd apiBancoDigitalJavaPure

2. Compile o projeto com Maven

mvn clean package

> Isso irá gerar um arquivo .war dentro da pasta target/.



3. Importe o .war no Tomcat

Copie o arquivo BookStore.war para a pasta webapps do Tomcat.

Ou renomeie para ROOT.war para acesso direto via localhost:8080/.


4. Inicie o servidor

Windows: startup.bat

Linux/Mac: startup.sh


5. Acesse a aplicação

Swagger UI:
http://localhost:8080/BookStoreDigital/swagger

Listar clientes (GET):
http://localhost:8080/BookStoreDigital/clientes

Cadastrar cliente (POST):
http://localhost:8080/BookStore/clientes

Listar Livros (GET):
http://localhost:8080/BookStoreDigital/Livros

Cadastrar Livros (POST);
http://localhost:8080/BookStoreDigital/Livros


---

📦 Exemplo de JSON para Cadastro (POST)

{
  "nome": "João",
  "email": "joao@email.com",
  "password": "123456",
  "repeatpassword": "123456"
  "cpf": "9999999999",
  "endereco"; "Av Paulista, 1000"
}




