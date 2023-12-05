package Controllers;

import DB.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroExameController extends JFrame {
    private JTextField nomeExameField;
    private JTextField tipoExameField;
    public CadastroExameController() {
        setTitle("Cadastro de Exame");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(4, 2));

        container.add(new JLabel("Nome do Exame:"));
        nomeExameField = new JTextField();
        container.add(nomeExameField);

        container.add(new JLabel("Tipo de Exame:"));
        tipoExameField = new JTextField();
        container.add(tipoExameField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarExame();
            }
        });

        JButton voltarButton = new JButton("Voltar à Tela Principal");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retornarTelaPrincipal();
            }
        });

        container.add(new JLabel());
        container.add(cadastrarButton);
        container.add(new JLabel());
        container.add(voltarButton);

        setVisible(true);
    }

    private void cadastrarExame() {
        String nomeExame = nomeExameField.getText();
        String tipoExame = tipoExameField.getText();

        Connection conn = DB.connecta();
        String sql = "INSERT INTO Exames (NomeExame, TipoExame) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nomeExame);
            preparedStatement.setString(2, tipoExame);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Exame cadastrado com sucesso!");

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar exame.");
        }
    }

    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroExameController();
            }
        });
    }
}
