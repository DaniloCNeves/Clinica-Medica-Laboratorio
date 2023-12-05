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
import java.util.Vector;

public class ConsultaPacientes extends JFrame {
    private JTable pacientesTable;

    public ConsultaPacientes() {
        setTitle("Consulta de Pacientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Vector<String> colunas = new Vector<>();
        colunas.add("ID");
        colunas.add("Nome");
        colunas.add("CPF");
        colunas.add("Data de Nascimento");
        colunas.add("Gênero");
        colunas.add("Telefone");
        colunas.add("E-Mail");
        colunas.add("Endereço");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conn = DB.connecta();
        String sql = "SELECT * FROM Pacientes";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("Nome"));
                linha.add(resultSet.getString("CPF"));
                linha.add(resultSet.getString("DataNascimento"));
                linha.add(resultSet.getString("Genero"));
                linha.add(resultSet.getString("Telefone"));
                linha.add(resultSet.getString("Email"));
                linha.add(resultSet.getString("Endereco"));
                dados.add(linha);
            }

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        pacientesTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
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
                    FileWriter arquivo = new FileWriter("C:\\Users\\danil\\Desktop\\Projeto Final Java SE - Laboratório\\ListaPaciente.txt", true);
                    PrintWriter escreve = new PrintWriter(arquivo);

                    escreve.println("---------------Lista de Paciente------------------");
                    escreve.println("--------------------------------------------------");

                    for (Vector<String> linha : dados) {
                        String linhaTexto = String.join("\t", linha);
                        String tabela = String.join("\t", colunas);
                        escreve.println(tabela);
                        escreve.println(linhaTexto);
                        escreve.print("\n");
                    }

                    arquivo.close();
                    JOptionPane.showMessageDialog(ConsultaPacientes.this, "Dados exportados com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ConsultaPacientes.this, "Erro ao exportar dados.");
                }
            }
        });
        buttonPanel.add(exportarButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPacientes();
            }
        });
    }
}