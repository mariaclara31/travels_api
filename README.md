# travels_api
API em Java e Spring Boot para controle de gerenciamento de viagens.

- Repositório criado para estudo de complemento na linguagem JAVA.
- Projeto referência para estudo: https://github.com/mariazevedo88/travels-api

## Funcionamento da API:
A intenção da API é ser um CRUD(create, read, update, delete) para 
gerenciar viagens. Também é possível calcular as estatísticas das viagens craidas.

Os endpoints criados para realizar as requisições foram:

#### POST - `POST/api-travels/travels`

<code>
{
  "id": 1,
  "orderNumber": "220788",
  "amount": "22.88",
  "startDate": "2019-09-11T09:59:51.312Z",
  "endDate": "2019-09-21T21:05:06.500Z",
  "type": "RETURN"
}
</code>

**Onde:**

`id`: número único da viagem;

`orderNumber`: número de identificação da viagem no sistema.

`amount`: valor da transação; deve ser uma String de tamanho arbitrário que pode ser parseada como um BigDecimal;

`startDate`: data de início da viagem no formato ISO 8601 YYYY-MM-DDThh:mm:ss.sssZ no timezone local.

`endDate`: data de fim da viagem no formato ISO 8601 YYYY-MM-DDThh:mm:ss.sssZ no timezone local. Pode ser nulo se a viagem é só de ida.

`type`: se a viagem é somente de ida (ONE-WAY), ida e volta (RETURN) ou múltiplos destinos (MULTI-CITY).

Deve retornar com body vazio com um dos códigos a seguir:

* 201: caso a viagem seja criada com sucesso.
* 400: caso o JSON seja inválido.
* 422: se qualquer um dos campos não for parseável ou se a data de início for mais ao futuro que a data final.
* 500: erro no servidor (são raros)

#### PUT - `PUT/api-travels/travels/{id}`
<code>
{
  "orderNumber": "220788",
  "amount": "50.50",
  "startDate": "2019-09-11T09:59:51.312Z",
  "endDate": "2019-09-21T21:05:06.500Z",
  "type": "RETURN"
}
</code>

Deve ser enviado o objeto que será modificado. O retorno deve ser o próprio objeto modificado.

<code>
{
  "id": 1,
  "orderNumber": "220788",
  "amount": "50.50",
  "startDate": "2019-09-11T09:59:51.312Z",
  "endDate": "2019-09-21T21:05:06.500Z",
  "type": "RETURN"
}
</code>

A resposta deve conter os códigos a seguir:

* 200: em caso de sucesso.
* 400: caso o JSON seja inválido.
* 404: caso tentem atualizar um registro que não existe.
* 422: se qualquer um dos campos não for parseável (JSON mal formatado).

#### GET -`GET/api-travels/travels`

Deve retornar uma lista de viagens.

<code>
{
   "id": 1,
   "orderCode": "220788",
   "amount": "22.88",
   "initialDate": "2019-09-11T09:59:51.312Z",
   "finalDate": "2019-09-21T21:05:06.500Z",
   "type": "RETURN"
},
{   
   "id": 2,
   "orderCode": "300691",
   "amount": "120.0",
   "initialDate": "2019–10–25T16:18:30.541Z",
   "type": "ONE-WAY"
}
</code>

A resposta deve conter os códigos a seguir:

* 200: caso exista viagens cadastradas
* 404: caso não exista viagens criadas.

#### DELETE - `DELETE/api-travels/travels`

Deve aceitar uma requisição com body vazio e retornar 204.

#### GET - `GET/api-travels/statistics`

<code>
{   
   "sum": "142.88",
   "avg": "71.44",
   "max": "120.0",
   "min": "22.88",
   "count": "2"
}
</code>

Sendo que:

`sum`: um BigDecimal especificando a soma total das viagens criadas.

`avg`: um BigDecimal especificando a média dos valores das viagens criadas.

`max`: um BigDecimal especificando o maior valor dentre as viagens criadas.

`min`: um BigDecimal especificando o menor valor dentre as viagens criadas.

`count`: um long especificando o número total de viagens.

### Testes

* Para executar o teste unitário, o comando executado deve ser:

```
mvn test
```

* Para executar todos os testes (incluindo o de integração), o comando executado deve ser:

```
mvn integration-test
```

### Execução

Para executar a API via .jar:

```
java -jar travels-api-2.0.1.jar --spring.profiles.active=dev
```

ou

```
mvn spring-boot:run -Dspring.profiles.active=dev
```