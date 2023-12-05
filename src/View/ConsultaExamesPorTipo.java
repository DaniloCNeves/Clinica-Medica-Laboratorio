package View;

import DB.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class ConsultaExamesPorTipo extends JFrame {
    private JComboBox<String> tiposExameComboBox;
    private JTable examesPorTipoTable;

    public ConsultaExamesPorTipo() {
        setTitle("Consulta de Exames por Tipo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel comboBoxPanel = new JPanel();
        tiposExameComboBox = new JComboBox<>();
        preencherComboBoxTiposExame();
        comboBoxPanel.add(new JLabel("Selecione o Tipo de Exame:"));
        comboBoxPanel.add(tiposExameComboBox);
        container.add(comboBoxPanel, BorderLayout.NORTH);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Paciente");
        colunas.add("Médico");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        tiposExameComboBox.addActionListener(e -> {
            dados.clear();
            String tipoExameSelecionado = (String) tiposExameComboBox.getSelectedItem();

            Connection conn = DB.connecta();
            String sql = "SELECT Agendamentos.ID, Pacientes.Nome AS NomePaciente, Medicos.Nome AS NomeMedico, " +
                    "DataHoraAgendamento " +
                    "FROM Agendamentos " +
                    "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                    "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                    "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID " +
                    "WHERE Exames.TipoExame = ?";

            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, tipoExameSelecionado);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Vector<String> linha = new Vector<>();
                    linha.add(resultSet.getString("ID"));
                    linha.add(resultSet.getString("NomePaciente"));
                    linha.add(resultSet.getString("NomeMedico"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                    linha.add(dataHora);

                    dados.add(linha);
                }

                DB.desconecta(conn);
                examesPorTipoTable.setModel(new DefaultTableModel(dados, colunas));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        examesPorTipoTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesPorTipoTable);
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
                    FileWriter arquivo = new FileWriter("C:\\Users\\danil\\Desktop\\Projeto Final Java SE - Laboratório\\ListaExames.txt", true);
                    PrintWriter escreve = new PrintWriter(arquivo);

                    escreve.println("----------------Lista de Exames-------------------");
                    escreve.println("--------------------------------------------------");

                    for (Vector<String> linha : dados) {
                        String linhaTexto = String.join("\t", linha);
                        String tabela = String.join("\t", colunas);
                        escreve.println(tabela);
                        escreve.println(linhaTexto);
                        escreve.print("\n");
                    }

                    arquivo.close();
                    JOptionPane.showMessageDialog(ConsultaExamesPorTipo.this, "Dados exportados com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ConsultaExamesPorTipo.this, "Erro ao exportar dados.");
                }
            }
        });
        buttonPanel.add(exportarButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void preencherComboBoxTiposExame() {
        Connection conn = DB.connecta();
        String sql = "SELECT DISTINCT TipoExame FROM Exames";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tiposExameComboBox.addItem(resultSet.getString("TipoExame"));
            }

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaExamesPorTipo());
    }
}