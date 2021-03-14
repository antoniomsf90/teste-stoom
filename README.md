# Teste Stoom
## _Desenvolvido por Antonio_

## Features
- Manter País
- Manter Estado
- Manter Endereço

O sistema conecta ao Google GeoCoding API para extrair as coordenadas Latitude e Longitude quando o usuário não informa no cadastro ou na atualização.

## Principais tecnologias aplicadas:

- Spring Boot
- MongoDB
- Google API

## Instalação

Utilize o maven para iniciar o projeto:

```sh
cd adress
mvn clean install
cd target
java -jar adress-1.0.0.jar
```

Pode ser utilizado também o Docker Compose

```sh
docker-compose up
```

## Alguns exemplos de requests

Cadastrar País 
```curl --location --request POST 'localhost:8080/country' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Brasil"
}'
```

Cadastrar Estado
```curl --location --request POST 'localhost:8080/state' \
--header 'Content-Type: application/json' \
--data-raw '    {
        "name": "Rio de Janeiro",
        "country": {
            "id": "604da9b70be34d55489fb5d2",
            "name": "Brazil"
        }
    }'
```
    
Cadastrar Endereço 
```curl --location --request POST 'localhost:8080/adress' \
--header 'Content-Type: application/json' \
--data-raw '{
    "streetName" : "Rua Abilio Soares",
    "number" : 100,
    "complement" : "",
    "neighbourhood" : "Paraíso",
    "city" : "São Paulo",
    "zipcode": "09634050",
    "state": {
        "id": "604daa050be34d55489fb5d4",
        "name": "Sao Paulo",
        "country": {
            "id": "604da9b70be34d55489fb5d2",
            "name": "Brazil"
        }
    },
}'
```