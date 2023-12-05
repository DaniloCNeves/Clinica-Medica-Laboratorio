package View;

import Controllers.AgendamentoController;
import Controllers.CadastroExameController;
import Controllers.CadastroMedicoController;
import Controllers.CadastroPacienteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JFrame {

    public TelaMenu() {
        setTitle("Tela Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(10, 2));

        JButton cadastrarPacienteButton = new JButton("Cadastrar Paciente");
        cadastrarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroPacienteController();
            }
        });
        container.add(cadastrarPacienteButton);

        JButton cadastrarMedicoButton = new JButton("Cadastrar Médico");
        cadastrarMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroMedicoController();
            }
        });
        container.add(cadastrarMedicoButton);

        JButton cadastrarExameButton = new JButton("Cadastrar Exame");
        cadastrarExameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroExameController();
            }
        });
        container.add(cadastrarExameButton);

        JButton agendarExameButton = new JButton("Agendar Exame");
        agendarExameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgendamentoController();
            }
        });
        container.add(agendarExameButton);

        JButton consultaTodosPacientesButton = new JButton("Consultar Todos os Pacientes");
        consultaTodosPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaPacientes();
            }
        });
        container.add(consultaTodosPacientesButton);

        JButton consultaAniversariantesMesButton = new JButton("Consultar Pacientes Aniversariantes do Mês");
        consultaAniversariantesMesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaAniversariantesMes();
            }
        });
        container.add(consultaAniversariantesMesButton);

        JButton consultaAniversariantesDiaButton = new JButton("Consultar Pacientes Aniversariantes do Dia");
        consultaAniversariantesDiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaAniversariantesDia();
            }
        });
        container.add(consultaAniversariantesDiaButton);

        JButton consultaExamesPorTipoButton = new JButton("Consultar Exames por Tipo");
        consultaExamesPorTipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesPorTipo();
            }
        });
        container.add(consultaExamesPorTipoButton);

        JButton consultaExamesAgendadosButton = new JButton("Consultar Exames Agendados");
        consultaExamesAgendadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesAgendados();
            }
        });
        container.add(consultaExamesAgendadosButton);

        JButton consultaExamesRealizadosButton = new JButton("Consultar Exames Realizados");
        consultaExamesRealizadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesRealizados();
            }
        });
        container.add(consultaExamesRealizadosButton);

        JButton consultaExamesPorPacienteButton = new JButton("Consultar Exames por Paciente");
        consultaExamesPorPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesPorPaciente();
            }
        });
        container.add(consultaExamesPorPacienteButton);

        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaMenu();
            }
        });
    }
}