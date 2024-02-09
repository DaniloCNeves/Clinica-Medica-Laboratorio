# Sistema de Clínica Médica e Exames
Este é o repositório do projeto do Sistema de Clínica Médica e Exames, desenvolvido em Java SE com utilização do Banco de Dados MySQL. O sistema segue padrões de arquitetura MVC e princípios SOLID de design, aderindo também às boas práticas de programação e código limpo.

## Funcionalidades

O sistema permite o gerenciamento de cadastros, agendamentos e consultas relacionados a uma clínica médica e de exames. Abaixo estão as funcionalidades principais:

### Cadastros

- **Tipos de Exames:** Permite cadastrar diferentes tipos de exames, como exame de imagem, exame de sangue, exame de raio X, entre outros.
- **Médicos:** Cadastro de médicos responsáveis pelos exames e atendimento aos pacientes.
- **Pacientes:** Cadastro de pacientes que serão atendidos na clínica.

### Agendamentos

- **Exames:** Possibilita agendar exames para os pacientes, garantindo que o mesmo paciente não possa fazer dois exames simultaneamente. Os horários são marcados com intervalo de 15 minutos.

### Consultas

- **Todos os Pacientes:** Lista todos os pacientes cadastrados na clínica.
- **Pacientes Aniversariantes:** Apresenta os pacientes que estão fazendo aniversário no dia ou no mês atual.
- **Todos os Tipos de Exames:** Exibe todos os tipos de exames disponíveis na clínica.
- **Exames Agendados:** Mostra os exames que estão agendados para realização.
- **Exames Realizados:** Lista os exames que já foram realizados.
- **Exames por Paciente:** Permite visualizar os exames realizados por um determinado paciente.

### Outras Observações

- **Exportação de Consultas:** As consultas podem ser exportadas para um arquivo txt para posterior análise ou armazenamento.

## Requisitos

Para executar o sistema em sua máquina local, é necessário ter instalado:

- Java SE
- Banco de Dados MySQL
- IDE de desenvolvimento Java (opcional)

## Como Executar

1. Clone este repositório em sua máquina local.
2. Importe o projeto em sua IDE de desenvolvimento Java, se preferir.
3. Configure a conexão com o Banco de Dados MySQL, editando o arquivo `application.properties`.
4. Execute o script SQL fornecido para criar o esquema do banco de dados e inserir dados iniciais, se necessário.
5. Compile e execute o projeto.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para enviar sugestões, reportar bugs ou propor melhorias para o sistema.
