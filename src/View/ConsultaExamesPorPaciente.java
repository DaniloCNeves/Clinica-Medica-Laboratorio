package View;

import DB.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ConsultaExamesPorPaciente extends JFrame {
    private JComboBox<String> pacientesComboBox;
    private JTable examesPorPacienteTable;

    public ConsultaExamesPorPaciente() {
        setTitle("Consulta de Exames por Paciente");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel comboBoxPanel = new JPanel();
        pacientesComboBox = new JComboBox<>();
        preencherComboBoxPacientes();
        comboBoxPanel.add(new JLabel("Selecione o Paciente:"));
        comboBoxPanel.add(pacientesComboBox);
        container.add(comboBoxPanel, BorderLayout.NORTH);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Médico");
        colunas.add("Exame");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        // ...

        pacientesComboBox.addActionListener(e -> {
            dados.clear();
            String pacienteSelecionado = (String) pacientesComboBox.getSelectedItem();

            Connection conn = DB.connecta();
            String sql = "SELECT Agendamentos.ID, Medicos.Nome AS NomeMedico, Exames.NomeExame AS NomeExame, " +
                    "Agendamentos.DataHoraAgendamento " +
                    "FROM Agendamentos " +
                    "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                    "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID " +
                    "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                    "WHERE Pacientes.Nome = ?";

            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, pacienteSelecionado);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Vector<String> linha = new Vector<>();
                    linha.add(resultSet.getString("ID"));
                    linha.add(resultSet.getString("NomeMedico"));
                    linha.add(resultSet.getString("NomeExame"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                    linha.add(dataHora);

                    dados.add(linha);
                }

                DB.desconecta(conn);
                examesPorPacienteTable.setModel(new DefaultTableModel(dados, colunas));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        examesPorPacienteTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesPorPacienteTable);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(e -> dispose()); // Fecha a janela de consulta
        buttonPanel.add(fecharButton);
        JButton exportarButton = new JButton("Exportar para TXT");
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter arquivo = new FileWriter("C:\\Users\\danil\\Desktop\\Projeto Final Java SE - Laboratório\\ConsultaExamesPaciente.txt", true);
                    PrintWriter escreve = new PrintWriter(arquivo);

                    escreve.println("--------------Exames por Paciente-----------------");
                    escreve.println("--------------------------------------------------");

                    for (Vector<String> linha : dados) {
                        String linhaTexto = String.join("\t", linha);
                        String tabela = String.join("\t", colunas);
                        escreve.println(tabela);
                        escreve.println(linhaTexto);
                        escreve.print("\n");
                    }

                    arquivo.close();
                    JOptionPane.showMessageDialog(ConsultaExamesPorPaciente.this, "Dados exportados com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ConsultaExamesPorPaciente.this, "Erro ao exportar dados.");
                }
            }
        });
        buttonPanel.add(exportarButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void preencherComboBoxPacientes() {
        Connection conn = DB.connecta();
        String sql = "SELECT Nome FROM Pacientes";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pacientesComboBox.addItem(resultSet.getString("Nome"));
            }

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaExamesPorPaciente());
    }
}