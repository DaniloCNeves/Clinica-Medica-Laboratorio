package Controllers;
import DB.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroPacienteController extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField dataNascimentoField;
    private JTextField generoField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JTextField enderecoField;

    public CadastroPacienteController() {
        setTitle("Cadastro de Paciente");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(9, 2));

        container.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        container.add(nomeField);

        container.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        container.add(cpfField);

        container.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
        dataNascimentoField = new JTextField();
        container.add(dataNascimentoField);

        container.add(new JLabel("Gênero:"));
        generoField = new JTextField();
        container.add(generoField);

        container.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        container.add(telefoneField);

        container.add(new JLabel("E-Mail:"));
        emailField = new JTextField();
        container.add(emailField);

        container.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        container.add(enderecoField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPaciente();
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

    private void cadastrarPaciente() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
        String genero = generoField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String endereco = enderecoField.getText();

        Connection conn = DB.connecta();
        try {
            String sql = "INSERT INTO Pacientes (Nome, CPF, DataNascimento, Genero, Telefone, Email, Endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.setObject(3, dataNascimento);
            preparedStatement.setString(4, genero);
            preparedStatement.setString(5, telefone);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, endereco);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar paciente.");
        } finally {
            if(conn != null) {
                DB.desconecta(conn);
            }
        }
        }

    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroPacienteController();
            }
        });
    }
}