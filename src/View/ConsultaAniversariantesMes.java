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
import java.time.LocalDate;
import java.util.Vector;

public class ConsultaAniversariantesMes extends JFrame {
    private JTable aniversariantesTable;

    public ConsultaAniversariantesMes() {
        setTitle("Pacientes Aniversariantes do Mês");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Vector<String> colunas = new Vector<>();
        colunas.add("ID");
        colunas.add("Paciente");
        colunas.add("Data de Nascimento");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conn = DB.connecta();
        String sql = "SELECT * FROM Pacientes WHERE MONTH(DataNascimento) = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, LocalDate.now().getMonthValue()); // Obtém o mês atual
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("Nome"));

                // Formata a data de nascimento no formato dd/MM/yyyy
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataNascimento = dateFormat.format(resultSet.getDate("DataNascimento"));
                linha.add(dataNascimento);

                dados.add(linha);
            }

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        aniversariantesTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(aniversariantesTable);
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
                    FileWriter arquivo = new FileWriter("C:\\Users\\danil\\Desktop\\Projeto Final Java SE - Laboratório\\AniversariantesMes.txt", true);
                    PrintWriter escreve = new PrintWriter(arquivo);

                    escreve.println("-------------Aniversariantes do Mês---------------");
                    escreve.println("--------------------------------------------------");

                    for (Vector<String> linha : dados) {
                        String linhaTexto = String.join("\t", linha);
                        String tabela = String.join("\t", colunas);
                        escreve.println(tabela);
                        escreve.println(linhaTexto);
                        escreve.print("\n");
                    }

                    arquivo.close();
                    JOptionPane.showMessageDialog(ConsultaAniversariantesMes.this, "Dados exportados com sucesso!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ConsultaAniversariantesMes.this, "Erro ao exportar dados.");
                }
            }
        });
        buttonPanel.add(exportarButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaAniversariantesMes());
    }
}
