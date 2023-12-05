package Controllers;

import DB.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgendamentoController extends JFrame {
    private JComboBox<String> pacienteComboBox;
    private JComboBox<String> medicoComboBox;
    private JComboBox<String> exameComboBox;
    private JTextField dataHoraField;

    public AgendamentoController() {
        setTitle("Agendamento de Exame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(6, 2));

        container.add(new JLabel("Paciente:"));
        pacienteComboBox = new JComboBox<>();
        preencherComboBoxPacientes();
        container.add(pacienteComboBox);

        container.add(new JLabel("Médico:"));
        medicoComboBox = new JComboBox<>();
        preencherComboBoxMedicos();
        container.add(medicoComboBox);

        container.add(new JLabel("Exame:"));
        exameComboBox = new JComboBox<>();
        preencherComboBoxExames();
        container.add(exameComboBox);

        container.add(new JLabel("Data e Hora (YYYY-MM-DDTHH:MM):"));
        dataHoraField = new JTextField();
        container.add(dataHoraField);

        JButton agendarButton = new JButton("Agendar");
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarExame();
            }
        });

        JButton voltarButton = new JButton("Voltar à Tela Principal");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retornarTelaPrincipal();
            }
        });

        container.add(new JLabel());
        container.add(agendarButton);
        container.add(new JLabel());
        container.add(voltarButton);

        setVisible(true);
    }

    private void preencherComboBoxPacientes() {
        Connection conn = DB.connecta();

        if (conn != null) {
            try {
                String sql = "SELECT Nome FROM Pacientes";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomePaciente = resultSet.getString("Nome");
                    pacienteComboBox.addItem(nomePaciente);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conn);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void preencherComboBoxMedicos() {
        Connection conn = DB.connecta();

        if (conn != null) {
            try {
                String sql = "SELECT Nome FROM Medicos";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomeMedico = resultSet.getString("Nome");
                    medicoComboBox.addItem(nomeMedico);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conn);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void preencherComboBoxExames() {
        Connection conn = DB.connecta();

        if (conn != null) {
            try {
                String sql = "SELECT NomeExame FROM Exames";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomeExame = resultSet.getString("NomeExame");
                    exameComboBox.addItem(nomeExame);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conn);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void agendarExame() {
        String paciente = (String) pacienteComboBox.getSelectedItem();
        String medico = (String) medicoComboBox.getSelectedItem();
        String exame = (String) exameComboBox.getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraField.getText(), formatter);

        if (verificarConflitoHorarios(medico, dataHora)) {
            JOptionPane.showMessageDialog(this, "Conflito de horários! O médico e/ou paciente já possui um agendamento para esse horário.");
            return;
        }

        if (!verificarIntervaloMinimo(paciente, dataHora)) {
            JOptionPane.showMessageDialog(this, "Conflito de horários! É necessário respeitar o intervalo mínimo de 15 minutos entre agendamentos consecutivos para o mesmo paciente.");
            return;
        }

        Connection conn = DB.connecta();
        String sql = "INSERT INTO Agendamentos (PacienteID, MedicoID, ExameID, DataHoraAgendamento) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdPaciente(paciente));
            preparedStatement.setInt(2, obterIdMedico(medico));
            preparedStatement.setInt(3, obterIdExame(exame));
            preparedStatement.setObject(4, dataHora);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Exame agendado com sucesso!");

            // Atualiza a coluna Realizado no exame
            atualizarExameRealizado(obterIdExame(exame));

            DB.desconecta(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao agendar exame.");
        }
    }

    private boolean verificarConflitoHorarios(String medico, LocalDateTime dataHora) {
        Connection conn = DB.connecta();
        String sql = "SELECT COUNT(*) FROM Agendamentos WHERE MedicoID = ? AND DataHoraAgendamento = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdMedico(medico));
            preparedStatement.setObject(2, dataHora);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            DB.desconecta(conn);

            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean verificarIntervaloMinimo(String paciente, LocalDateTime dataHora) {
        Connection conn = DB.connecta();
        String sql = "SELECT MAX(DataHoraAgendamento) FROM Agendamentos WHERE PacienteID = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdPaciente(paciente));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                if (timestamp != null) {
                    LocalDateTime ultimaDataHora = timestamp.toLocalDateTime();
                    LocalDateTime minimaDataHora = ultimaDataHora.plusMinutes(15);
                    return dataHora.isAfter(minimaDataHora);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DB.desconecta(conn);
        }
    }

    private int obterIdPaciente(String nomePaciente) {
        Connection conn = DB.connecta();
        String sql = "SELECT ID FROM Pacientes WHERE Nome = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nomePaciente);

            ResultSet resultSet = preparedStatement.executeQuery();

            int pacienteId = -1;

            if (resultSet.next()) {
                pacienteId = resultSet.getInt("ID");
            }

            DB.desconecta(conn);

            return pacienteId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int obterIdMedico(String nomeMedico) {
        Connection conn = DB.connecta();
        String sql = "SELECT ID FROM Medicos WHERE Nome = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nomeMedico);

            ResultSet resultSet = preparedStatement.executeQuery();

            int medicoId = -1;

            if (resultSet.next()) {
                medicoId = resultSet.getInt("ID");
            }

            DB.desconecta(conn);

            return medicoId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int obterIdExame(String nomeExame) {
        Connection conn = DB.connecta();
        String sql = "SELECT ID FROM Exames WHERE NomeExame = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nomeExame);

            ResultSet resultSet = preparedStatement.executeQuery();

            int exameId = -1;

            if (resultSet.next()) {
                exameId = resultSet.getInt("ID");
            }

            DB.desconecta(conn);

            return exameId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private void atualizarExameRealizado(int exameId) {
        Connection conn = DB.connecta();
        String sql = "UPDATE Exames SET Realizado = Realizado + 1 WHERE ID = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, exameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.desconecta(conn);
        }
    }

    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AgendamentoController();
            }
        });
    }
}