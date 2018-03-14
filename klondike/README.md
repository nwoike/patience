# Klondike
This project implements the server side logic for Klondike Solitare.

It uses the following service dependencies.

## RabbitMQ
```
docker run -d --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3.7-management
```
 
## PostgreSQL
```
docker run -d --name postgres -p 5432:5432 postgres:10.3
```