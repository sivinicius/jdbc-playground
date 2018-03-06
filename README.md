# JDBC Playground

Simples projeto para estudo do Java Database Connectivity ou JDBC.

## Tecnologias

- JAVA 8
- JUnit
- Maven
- MySQL 5.7

## Como usar

- Clone este repositório
- Abra o projeto em sua IDE.
- Utilize o script `scripts_criacao.sql` localizado na pasta `resources` para criar no MySQL o banco e as tabelas necessárias para o projeto.
- Altere a classe **FabricaDeConexao** colocando suas informações de conexão com o banco. 
- Execute os testes das classes **CursoDAOTest**, **ProfessorDAOTest** e **TurmaDAOTest** para confirmar se está tudo funcionando.
- Agora você pode ficar a vontade para brincar no projeto.

## MODELO UTILIZADO

![MER](https://github.com/sivinicius/jdbc-playground/tree/master/resources/mer_jdbc_playground.jpg?raw=true)