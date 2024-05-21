## Descricao

Este projeto é um gerenciador de carteira de ações, onde é possivel gerenciar multiplas carteiras de um usuário.

## Tecnologias

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- Kotlin - Linguagem de programação
- Spring Boot - Framework para desenvolvimento de aplicações Java
- PostgreSQL - Banco de dados
- Docker - Ferramenta para criação de containers

## Executando o projeto

Para executar o projeto, é necessário ter o Docker instalado na máquina. 
Após a instalação, basta executar o comando abaixo na raiz do projeto:

```bash
docker compose up -d
```

## Estrutura do projeto

### Módulos

O projeto foi dividido em módulos:

- **account**: Contém as regras de negócio dos usuários
- **wallet**: Contém as regras de negócio das carteiras
- **stock**: Contém as regras de negócio das ações
- **transaction**: Contém as regras de negócio das transações
- **common**: Contém as classes comuns a todos os módulos
