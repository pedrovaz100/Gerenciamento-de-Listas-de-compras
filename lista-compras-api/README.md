# API Lista de Compras

API REST desenvolvida com Spring Boot para gerenciamento de listas de compras, mercados e itens.

## Stack

- Spring Boot
- Spring Data JPA
- Bean Validation
- H2 Database
- Lombok

## Arquitetura de pacotes

- `controller`: endpoints REST
- `model`: regras de negócio
- `repository`: acesso a dados com JPA
- `entity`: entidades e relacionamentos
- `dto`: contratos de entrada e saída
- `validation`: validações customizadas

## Entidades

- `Mercado`: id, nome, endereco
- `ListaCompra`: id, nome, dataCriacao, mercado, itens
- `ItemCompra`: id, nome, quantidade, preco, listaCompra

## Endpoints principais

### Mercado
- `GET /mercados`
- `GET /mercados/{id}`
- `POST /mercados`
- `PUT /mercados/{id}`
- `DELETE /mercados/{id}`

### ListaCompra
- `GET /listas`
- `GET /listas/{id}`
- `POST /listas`
- `PUT /listas/{id}`
- `DELETE /listas/{id}`
- `GET /listas/busca/nome?nome=mensal`
- `GET /listas/busca/mercado/{mercadoId}`
- `GET /listas/resumo`

### ItemCompra
- `GET /itens`
- `GET /itens/{id}`
- `POST /itens`
- `PUT /itens/{id}`
- `DELETE /itens/{id}`
- `GET /itens/busca/nome?nome=arroz`
- `GET /itens/busca/preco?precoMin=5&precoMax=30`
- `GET /itens/busca/quantidade-minima?quantidade=2`

### Health check
- `GET /health`

## Paginação e ordenação

Todos os endpoints de listagem e busca aceitam `Pageable`.

Exemplo:

`GET /listas?page=0&size=10&sort=nome,asc`

## Exemplos de requisição JSON

### POST /mercados
```json
{
  "nome": "Supermercado Central",
  "endereco": "Rua das Flores, 100"
}
```

### POST /listas
```json
{
  "nome": "Compras do mês",
  "dataCriacao": "2026-04-28",
  "mercadoId": 1
}
```

### POST /itens
```json
{
  "nome": "Arroz",
  "quantidade": 2,
  "preco": 28.90,
  "listaCompraId": 1
}
```

## H2 Console

- URL: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:listacomprasdb`
- User: `sa`
- Password: (vazia)
