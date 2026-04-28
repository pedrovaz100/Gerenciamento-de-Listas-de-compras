# 🛒 Lista de Compras API

API desenvolvida com Spring Boot para gerenciar listas de compras e itens.

---

## 👨‍💻 Autor

Projeto acadêmico - ADS FIAP

- Nome: Joao Victor Luiz rm:565139
- Nome: Pedro Vaz rm:566551

---

## 🚀 Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok

---


## 🌐 Endpoints

### Listas

- GET /listas  
- GET /listas/{id}  
- POST /listas  
- PUT /listas/{id}  
- DELETE /listas/{id}  

### Itens

- GET /itens  
- GET /itens/{id}  
- POST /itens  
- PUT /itens/{id}  
- DELETE /itens/{id}  

### Outros

- GET /health

---


## 💻 Front-end

O front que consome essa API está disponível em:

👉 https://front-lista-de-compras.vercel.app

Também existe uma versão local simples em `frontend/index.html` para testes rápidos.

Como usar o front local:

1. Inicie a API em `http://localhost:8081`.
2. Abra `frontend/index.html` no navegador.
3. Cadastre e consulte mercados, listas e itens pela interface.

