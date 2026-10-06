# Desafio Técnico Júnior #1 – Cadastro e Consulta de Abastecimentos

API REST desenvolvida em **Java 21 + Spring Boot** para cadastro e consulta de abastecimentos em um posto de combustível, com persistência em **PostgreSQL**.

## 🛠 Tecnologias

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Gradle
* Bean Validation
* Lombok

## 🏗️ Estrutura

A aplicação utiliza uma arquitetura em camadas:

```text id="5vlbkw"
Controller → Service → Repository → PostgreSQL
```

   Também foram utilizados **DTOs**, tratamento centralizado de exceções e **exceptions personalizadas** para diferentes cenários da aplicação.

   O acesso aos dados é realizado através do Spring Data JPA. O JpaRepository já fornece a camada de acesso aos dados (DAO), não sendo necessário criar uma classe **DAO** separada.

## 📋 Funcionalidades

### Tipos de Combustível

* Criar, listar, consultar, alterar e excluir.
* Nome e preço por litro.

### Bombas

* Criar, listar, consultar, alterar e excluir.
* Nome e tipo de combustível.

### Abastecimentos

* Criar, listar, consultar, alterar e excluir.
* Bomba, data, valor total e litragem.

## 🔗 Endpoints

```text id="w1h6dq"
Tipos de Combustível
POST   /api/tipos-combustiveis
GET    /api/tipos-combustiveis
GET    /api/tipos-combustiveis/{id}
PUT    /api/tipos-combustiveis/{id}
DELETE /api/tipos-combustiveis/{id}

Bombas
POST   /api/bombas
GET    /api/bombas
GET    /api/bombas/{id}
PUT    /api/bombas/{id}
DELETE /api/bombas/{id}

Abastecimentos
POST   /api/abastecimentos
GET    /api/abastecimentos
GET    /api/abastecimentos/{id}
PUT    /api/abastecimentos/{id}
DELETE /api/abastecimentos/{id}
```

## 💡 Regra de negócio

No cadastro de abastecimentos, pode ser informado o **valor total**, a **litragem** ou ambos.

* Somente litragem → o valor total é calculado.
* Somente valor → a litragem é calculada.
* Ambos → os valores são validados para garantir compatibilidade.

## ⚙️ Como executar

### Pré-requisitos

* Java 21
* PostgreSQL
* Git

### Banco de dados

Crie um banco PostgreSQL chamado:

```text id="z2o9is"
project_merito
```

**Não esqueça de anotar a sua senha do banco no PostgreSQL!**

### Executando

```bash id="3opd9s"
git clone https://github.com/Wevertonbarbosa/vaga-junior.git
```

```bash id="3opd9s"
cd backend/project_merito
```

Configure as credenciais no arquivo:

```text id="6z8p6v"
backend/project_merito/src/main/resources/application.properties
```

Exemplo:

```properties id="7j3b1f"
spring.datasource.url=jdbc:postgresql://localhost:5432/project_merito
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```

Rode o projeto Bash/PowerShell
```bash id="3opd9s"
./gradlew.bat bootRun
```


A API ficará disponível em:

```text id="9gkq38"
http://localhost:8080
```

### Testando

A API pode ser testada utilizando **Postman, Insomnia, Bruno ou cURL**.

Exemplo:

```http
POST /api/tipos-combustiveis
```

```json id="7t6b5j"
{
    "nome": "Gasolina",
    "precoPorLitro": 5.89
}
```

## 💾 Persistência

Os dados são armazenados no PostgreSQL e permanecem disponíveis após reiniciar a aplicação.

## 📌 Observações

A solução utiliza **API REST**, conforme uma das opções previstas no desafio, e possui CRUD completo para as três entidades, validações, tratamento de exceções e regras de negócio para os abastecimentos.


