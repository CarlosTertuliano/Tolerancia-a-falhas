# Projeto de Tolerância a Falhas

Este projeto demonstra a implementação de um sistema distribuído com microserviços que incorporam diferentes técnicas de tolerância a falhas, como circuit breakers, retries automáticos e falhas simuladas (omissões, erros e crashes). O objetivo é estudar e validar o comportamento de aplicações em cenários de falhas.

## Microserviços

O projeto é composto pelos seguintes microserviços:

1. **E-commerce**: Gerencia compras de produtos e interage com os outros microserviços.
2. **Exchange**: Retorna taxas de câmbio aleatórias, com falhas simuladas.
3. **Fidelity**: Processa o sistema de bonificação do cliente.
4. **Store**: Gerencia os produtos e realiza vendas, com suporte a falhas simuladas.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Docker** e **Docker Compose**
- **Maven**
- Banco de dados integrado ao microserviço `Store` via JPA/Hibernate

## Requisitos
Antes de iniciar, certifique-se de ter:

- **Docker** e **Docker Compose** instalados
- Porta **8080** e sequentes livres no host
- Recurso mínimo de 4 GB de RAM no ambiente para rodar os containers

## Estrutura de Diretórios

```plaintext
/
|-- ecommerce/
|   |-- Dockerfile
|   |-- pom.xml
|   |-- src/
|
|-- exchange/
|   |-- Dockerfile
|   |-- pom.xml
|   |-- src/
|
|-- fidelity/
|   |-- Dockerfile
|   |-- pom.xml
|   |-- src/
|
|-- store/
|   |-- Dockerfile
|   |-- pom.xml
|   |-- src/
|
|-- docker-compose.yml
```

## Configuração e Execução

1. **Clone o repositório:**

   ```bash
   git clone <url-do-repositorio>
   cd <diretorio-do-projeto>
   ```

2. **Build e Start do Sistema:**

   Execute o comando abaixo para construir as imagens e iniciar os containers:

   ```bash
   docker-compose up --build
   ```

   O Docker Compose irá:
   - Construir cada microserviço a partir do respectivo Dockerfile
   - Publicar os containers nas seguintes portas:
     - E-commerce: [http://localhost:8080](http://localhost:8080)
     - Store: [http://localhost:8081](http://localhost:8081)
     - Exchange0: [http://localhost:8082](http://localhost:8082)
     - Exchange1: [http://localhost:8083](http://localhost:8083)
     - Fidelity: [http://localhost:8084](http://localhost:8084)

3. **Verificação dos Logs:**

   Para monitorar os logs em tempo real, execute:

   ```bash
   docker-compose logs -f
   ```

4. **Interrupção do Sistema:**

   Para parar e remover os containers, execute:

   ```bash
   docker-compose down
   ```

## Testando o Sistema

- **E-commerce:** Faça uma requisição POST para o endpoint `/buy` com a estrutura esperada no corpo da requisição.
- **Store:** Faça uma requisição GET para `/product?id=<id_do_produto>` para buscar um produto.
- **Exchange:** Acesse `/exchange` para consultar taxas de câmbio aleatórias.
- **Fidelity:** Teste o endpoint `/bonus` para gerar ou consultar pontos de fidelidade.

### Exemplos de Requisições

#### Usando `curl`

- **E-commerce:**

  ```bash
  curl -X POST http://localhost:8080/buy -H "Content-Type: application/json" -d '{"productId":1,"quantity":2}'
  ```

- **Store:**

  ```bash
  curl -X GET http://localhost:8081/product?id=1
  ```

## Observações

- **Simulação de Falhas:**
  - O microserviço `Store` possui falhas de omissão e crash simuladas.
  - O `Exchange` pode falhar aleatoriamente ao processar uma requisição.

- **Reprocessamento de Requisições:**
  O `E-commerce` possui um mecanismo de retry para reprocessar falhas em serviços externos.

## Contribuição
Sinta-se à vontade para contribuir com melhorias ou novos cenários de falhas!

1. Fork o repositório
2. Crie um branch para suas alterações: `git checkout -b minha-melhoria`
3. Envie um PR detalhando sua contribuição.

---

