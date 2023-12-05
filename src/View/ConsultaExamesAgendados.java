package View;

import DB.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ConsultaExamesAgendados extends JFrame {
    private JTable examesAgendadosTable;

    public ConsultaExamesAgendados() {
        setTitle("Consulta de Exames Agendados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Paciente");
        colunas.add("Médico");
        colunas.add("Exame");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conn = DB.connecta();
        String sql = "SELECT Agendamentos.ID, Pacientes.Nome AS NomePaciente, Medicos.Nome AS NomeMedico, " +
                "Exames.NomeExame AS NomeExame, DataHoraAgendamento " +
                "FROM Agendamentos " +
                "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("NomePaciente"));
                linha.add(resultSet.getString("NomeMedico"));
                linha.add(resultSet.getString("NomeExame"));

                // Formata a data e hora no formato dd/MM/yyyy HH:mm:ss
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                linha.add(dataHora);

                dados.add(linha);
            }

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        examesAgendadosTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesAgendadosTable);
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
                    FileWriter arquivo = new FileWriter("C:\\Users\\danil\\Desktop\\Projeto Final Java SE - Laboratório\\ExamesAgendados.txt", true);
                    PrintWriter escreve = new PrintWriter(arquivo);

                    escreve.println("----------------Exames Agendados------------------");
                    escreve.println("--------------------------------------------------");

                    for (Vector<String> linha : dados) {
                        String linhaTexto = String.join("\t", linha);
                        String tabela = String.join("\t", colunas);
                        escreve.println(tabela);
                        escreve.println(linhaTexto);
                        escreve.print("\n");
                    }

                    arquivo.close();
                    JOptionPane.showMessageDialog(ConsultaExamesAgendados.this, "Dados exportados com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ConsultaExamesAgendados.this, "Erro ao exportar dados.");
                }
            }
        });
        buttonPanel.add(exportarButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaExamesAgendados());
    }
}