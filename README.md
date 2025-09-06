# 🌐 ConectAção - Plataforma de Gestão de ONGs e Voluntariado

[![Java](https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=openjdk)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.5-6DB33F?style=for-the-badge&logo=springboot)](https://spring.io)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)](https://mysql.com)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.2-7952B3?style=for-the-badge&logo=bootstrap)](https://getbootstrap.com)

Plataforma web completa para conexão entre ONGs e voluntários, facilitando a gestão de eventos de voluntariado, candidaturas e engagement comunitário.

## ✨ Funcionalidades Principais

### 👥 Para Voluntários
- **📋 Sistema de Candidaturas** - Candidatura a eventos de voluntariado
- **📊 Painel Pessoal** - Acompanhamento de candidaturas e atividades
- **🔔 Notificações** - Alertas para novos eventos e atualizações
- **⭐ Sistema de Avaliações** - Avaliação de experiências de voluntariado

### 🏢 Para ONGs
- **🎯 Gestão de Eventos** - Criação e administração de eventos
- **👥 Gestão de Voluntários** - Aprovação e acompanhamento de participantes
- **📈 Dashboard Analítico** - Métricas e relatórios de participação
- **📢 Comunicação** - Ferramentas de contacto com voluntários

### 🔧 Funcionalidades Gerais
- **🔐 Autenticação Segura** - Sistema de login com Spring Security
- **📱 Design Responsivo** - Interface adaptada para todos os dispositivos
- **🌐 Multi-idioma** - Suporte para português e inglês
- **📧 Sistema de Emails** - Notificações automáticas por email

## 🛠️ Stack Tecnológico

### Backend
- **Java 17** - Linguagem principal
- **Spring Boot 3.1.5** - Framework backend
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Maven** - Gestão de dependências

### Frontend
- **Thymeleaf** - Templating engine
- **Bootstrap 5.2** - Framework CSS
- **JavaScript ES6+** - Interatividade
- **Chart.js** - Gráficos e visualizações

### Base de Dados
- **MySQL 8.0** - Base de dados relacional
- **Hibernate** - ORM

## 📦 Estrutura do Projeto

conectacao/

├── src/

│ ├── main/

│ │ ├── java/

│ │ │ └── com/

│ │ │ └── conectacao/

│ │ │ ├── controller/ # Controladores MVC

│ │ │ ├── model/ # Entidades JPA

│ │ │ ├── repository/ # Interfaces Spring Data

│ │ │ ├── service/ # Lógica de negócio

│ │ │ └── config/ # Configurações Spring

│ │ └── resources/

│ │ ├── static/ # CSS, JS, imagens

│ │ │ ├── css/

│ │ │ ├── js/

│ │ │ └── images/

│ │ ├── templates/ # Templates Thymeleaf

│ │ └── application.properties # Configurações

├── database/

│ ├── schema.sql # Estrutura da BD

│ └── sample_data.sql # Dados de exemplo

├── docker/

│ └── docker-compose.yml # Configuração Docker

└── README.md


## 🚀 Como Executar o Projeto

### Método 1: Docker (Recomendado)
 1. Clone o repositório

 git clone https://github.com/IsisVO11/ConectaAcao.git

 cd ConectaAcao

 2. Execute com Docker Compose
 docker-compose up -d

 3. A aplicação estará disponível em:
 **http://localhost:8080**

### Método 2: Execução Local

 1. Clone o repositório
 git clone https://github.com/IsisVO11/ConectaAcao.git

 cd ConectaAcao

 2. Configure a base de dados MySQL
 3. Crie uma base de dados chamada 'conectacao'

 Altere as credenciais em src/main/resources/application.properties

 4. Execute a aplicação
 mvn spring-boot:run

## 👤 Credenciais de Teste
Conta Administrativa:

Email: admin@conectacao.org

Password: Admin123!

Conta de ONG:

Email: ong@exemplo.org

Password: Ong123!

Conta de Voluntário:

Email: voluntario@exemplo.com

Password: Volunteer123!

## 📋 Funcionalidades Detalhadas
### Módulo de Autenticação
Registo de utilizadores (Voluntários e ONGs)

Login seguro com Spring Security

Recuperação de password

Roles e permissions (ADMIN, ONG, VOLUNTARIO)

### Módulo de Eventos
Criação e edição de eventos

Sistema de categorias e tags

Pesquisa e filtros avançados

Gestão de participantes

### Módulo de Candidaturas
Sistema de aplicação a eventos

Estados de candidatura (PENDENTE, APROVADA, REJEITADA)

Notificações por email

Histórico de candidaturas

### Dashboard Administrativo
Estatísticas de utilização

Gestão de utilizadores

Relatórios de atividades

Sistema de moderação

## 🐛 Reportar Bugs ou Sugerir Melhorias
Encontrou um bug ou tem uma sugestão? Por favor, abra uma issue no GitHub.

## 🤝 Como Contribuir
Faça Fork do projeto

Crie uma branch para sua feature (git checkout -b feature/AmazingFeature)

Commit suas mudanças (git commit -m 'Add some AmazingFeature')

Push para a branch (git push origin feature/AmazingFeature)

Abra um Pull Request

## 📄 Licença
Distribuído sob licença MIT. Veja LICENSE para mais informações.

## 👩💻 Autora
Isis Venturin - LinkedIn: https://www.linkedin.com/in/isis-venturin-b72443296/ | GitHub: https://github.com/IsisVO11
