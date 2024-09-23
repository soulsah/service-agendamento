# Hackathon 5SOAT

## Repositório para o Hackathon 5SOAT - Grupo 70

Este repositório contém o código-fonte e a documentação para o Hackathon 5SOAT/2024, desenvolvido pelo Grupo 70.

O projeto consiste em uma sistema de agendamento de consultas médicas utilizando uma arquitetura com microserviços. Além do Spring Framework, Spring Boot e Sping Data, também utilizamos neste projeto as tecnologias da AWS, sendo elas ECS e API Gateway.

1- Relatório Técnico
Tecnologias e ferramentas utilizadas

Linguagem de programação:

- Java 17

Framework:

- Spring Boot 3.3.4

Recursos AWS Criados
- AWS API Gateway
- AWS ECS
- AWS ECR
- AWS SQS
- AWS DynamoDB
- AWS VPC
- AWS LoadBalance
- AWS Lambda Function
- AWS Service task

Bibliotecas:

- Spring Web
- OpenAPI
- Lombok

Banco de dados:

- AWS DynamoDB

Outras ferramentas:

- Docker

# Configurações da solução

## Arquitetura

Para esta solução, escolhemos a arquitetura hexagonal (ports and adapters), pois, com ela, temos o total isolamento da lógica de negócio de outros componentes da aplicação, como banco de dados, apis externas e interfaces com o usuário, permitindo assim que a lógica de negócio se mantenha consistente e reutilizável, independentemente das mudanças nas tecnologias externas.
Essa abordagem nos proporciona uma estrutura modular, escalável, testável e de fácil manutenção. Haja visto que em um ambiente de microservices, onde mudanças, integrações e atualizações são constantes, a arquitetura hexagonal é uma escolha robusta que promove isolamento de responsabilidades e flexibilidade ao longo da vida útil dos serviços.

## Container Docker

Para rodar a aplicação no AWS ECS, foi criado o arquivo Dockerflie que gerencia o processo de build da aplicação através do Maven e JDK 17:

![image](https://github.com/user-attachments/assets/38ee4d73-5cc6-47cd-88ce-3c923611ba22)

## Cobertura de Testes Unitários da aplicação service-usuario

Comprovando acima de 80% de cobertura de testes unitário:

![image](https://github.com/user-attachments/assets/8e5b3d82-f90e-4eb6-9205-f0fc6ab69bec)

## Documentação das API's

Adicionamos a geração automática da documentação através da biblioteca SpringDoc Openapi, pode ser acessada em tempo de execução da aplicação nas respectivas url's: 

Microservice Usuario:  http://localhost:8080/swagger-ui/index.html

![image](https://github.com/user-attachments/assets/8df7d9ec-762b-4ea9-9189-37e0f5dd86f4)

## Arquivo Postman

Disponibilizamos um de arquivo JSON com todas as requisições Postman para testar a API, os arquivos estão disponíveis nos seguintes links:
https://github.com/soulsah/service-agendamento/Hackaton.postman_collection.json

# Conclusão

Para a solução do agendamento de consultas médicas, focamos a arquitetura em escalabilidade e flexibilidade, uma vez que ao utilizar uma solução descentralizada em microsserviços utilizando a nuvem, podemos adequar e escalar de acordo com a demanda de agendamentos, garantindo que a aplicação seja capaz de lidar com altos volumes de tráfego sem perda de desempenho, ao mesmo tempo em que se adapta facilmente a novas funcionalidades ou alterações na demanda, mantendo a qualidade e eficiência do serviço prestado.

Além disso, ao utilizar a arquitetura baseada em microsserviços, conseguimos desacoplar os módulos de forma que cada um seja desenvolvido, implantado e escalado independentemente, o que proporciona uma maior flexibilidade para ajustes futuros e facilita a manutenção. A escolha por tecnologias nativas da AWS, como DynamoDB, API Gateway e o uso de contêineres orquestrados, nos dá não só a vantagem de escalabilidade automática e confiabilidade, mas também a possibilidade de integrar novas tecnologias conforme as necessidades do projeto evoluem. Dessa forma, garantimos uma solução robusta, flexível e preparada para lidar com o crescimento futuro da aplicação, além de oferecer uma experiência de usuário estável e eficiente. Garantindo assim, que a solução proposta atende fielmente à demanda de agendamento de consultas médicas.
