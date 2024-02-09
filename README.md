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


# Medical Clinic and Exams System

This is the repository of the Medical Clinic and Exams System project, developed in Java SE using MySQL Database. The system follows MVC architecture patterns and SOLID principles, also adhering to good programming practices and clean code.

## Features

The system allows management of registrations, scheduling, and consultations related to a medical clinic and exams. Below are the main features:

### Registrations

- **Types of Exams:** Allows registration of different types of exams, such as imaging exam, blood test, X-ray exam, among others.
- **Doctors:** Registration of doctors responsible for exams and patient care.
- **Patients:** Registration of patients who will be attended at the clinic.

### Scheduling

- **Exams:** Enables scheduling exams for patients, ensuring that the same patient cannot undergo two exams simultaneously. The times are scheduled with a 15-minute interval.

### Consultations

- **All Patients:** Lists all patients registered at the clinic.
- **Birthday Patients:** Presents patients who are celebrating their birthday today or this month.
- **All Types of Exams:** Displays all types of exams available at the clinic.
- **Scheduled Exams:** Shows exams that are scheduled for execution.
- **Completed Exams:** Lists exams that have already been completed.
- **Exams per Patient:** Allows viewing exams performed by a specific patient.

### Other Observations

- **Consultation Export:** Consultations can be exported to a txt file for further analysis or storage.

## Requirements

To run the system on your local machine, you need to have installed:

- Java SE
- MySQL Database
- Java development IDE (optional)

## How to Run

1. Clone this repository to your local machine.
2. Import the project into your Java development IDE, if preferred.
3. Configure the connection with the MySQL Database by editing the `application.properties` file.
4. Execute the provided SQL script to create the database schema and insert initial data, if necessary.
5. Compile and run the project.

## Contribution

Contributions are welcome! Feel free to submit suggestions, report bugs, or propose improvements to the system.

## Authors

This project was developed by [Developer's Name] and [Developer's Name].

## License

This project is licensed under the [License XYZ]. See the LICENSE file for more details.
