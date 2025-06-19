🏦 API - Meu Banco Digital (Java Servlet)
Este é o backend do projeto Meu Banco Digital, agora desenvolvido com Java puro (Servlets), utilizando conexão direta com MySQL via JDBC.

A aplicação realiza operações básicas de cadastro e listagem de clientes, utilizando um servlet mapeado via web.xml e/ou anotações @WebServlet. Ideal para estudos e prática com Java web sem frameworks.

🚀 Tecnologias Utilizadas
✅ Java 17

✅ Jakarta Servlet API

✅ Apache Tomcat 11

✅ MySQL 8

✅ JDBC

✅ Maven

✅ Gson (para JSON)

📁 Estrutura do Projeto
bash
Copiar
Editar
apiBancoDigital/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/api/apiBanco/
│       │       ├── controller/   # Servlets
│       │       ├── dao/         # Acesso ao banco
│       │       └── model/       # Classe Client
│       └── resources/
│           └── webapp/
│               └── WEB-INF/
│                   └── web.xml
├── pom.xml
├── README.md
⚙️ Como Executar o Projeto Localmente
1. Clone o repositório:
bash
Copiar
Editar
git clone https://github.com/Danielpernnasc/apiBancoDigital.git
cd apiBancoDigital
2. Compile o projeto:
bash
Copiar
Editar
mvn clean package
Isso gerará um arquivo .war dentro de target/.

3. Importe o .war no Tomcat
Copie o arquivo BancoDigital.war (ou renomeie para ROOT.war para acesso direto) para a pasta webapps do Tomcat.

Inicie o Tomcat (startup.bat ou startup.sh).

4. Acesse via navegador:
Listar clientes (GET):
http://localhost:8080/BancoDigital/clientes

Cadastrar cliente (POST via JSON):
http://localhost:8080/BancoDigital/clientes

📦 Exemplo de JSON para Cadastro (POST)
json
Copiar
Editar
{
  "nome": "João",
  "email": "joao@email.com",
  "password": "123456",
  "repeatpassword": "123456"
}
Use ferramentas como Postman, Insomnia ou curl para testar.

🔐 Banco de Dados
Banco: mydigitalbank

Tabela: client

Conexão feita com DriverManager via JDBC.

Credenciais estão configuradas diretamente na classe ClientDAO.java (substituir em produção!).

🧪 Testes
Este projeto ainda não possui testes automatizados.
Está em fase de aprendizado e estruturação inicial.

🌐 Frontend - Angular com Microfrontend
Este backend pode ser conectado com o frontend desenvolvido em Angular 15 + Module Federation:

👉 Repositório do Frontend:
MFEMyBank (Angular)

