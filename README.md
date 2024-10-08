# Инструкция по запуску

## 1. Создание базы данных

Скрипт для создания базы данных:

```sql
CREATE DATABASE IF NOT EXISTS library;

USE library;

CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isbn` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `library` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NOT NULL,
  `borrow_at` date NOT NULL,
  `return_before` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 2. Сборка проекта

```shell
mvn clean install
```

## 3a. Запуск вручную

Запустить модули в порядке

1) eureka-server
2) authentication-service
3) book-service
4) library-service
5) api-gateway

## 3b. Запуск через docker

Собрать образы

```shell
docker-compose build
```

Запустить проект

```shell
docker-compose up
```

# Swagger

* [authentication-service](http://localhost:8081/swagger-ui/index.html#/Library)
* [book-service](http://localhost:8082/swagger-ui/index.html#/Library)
* [library-service](http://localhost:8083/swagger-ui/index.html#/Library)
