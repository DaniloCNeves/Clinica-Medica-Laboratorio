package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TelaLogin extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;

    private Map<String, String> usuarios = new HashMap<>(); // Simulação de dados de usuários e senhas

    public TelaLogin() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Simulação de dados de usuários e senhas
        usuarios.put("usuario1", "senha1");
        usuarios.put("usuario2", "senha2");

        Container container = getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5); // Espaçamento interno

        container.add(new JLabel("Usuário:"), constraints);
        constraints.gridy++;
        usuarioField = new JTextField(20); // Largura ajustável
        container.add(usuarioField, constraints);

        constraints.gridy++;
        container.add(new JLabel("Senha:"), constraints);
        constraints.gridy++;
        senhaField = new JPasswordField(20); // Largura ajustável
        container.add(senhaField, constraints);

        constraints.gridy++;

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                char[] senhaChars = senhaField.getPassword();
                String senha = new String(senhaChars);

                if (autenticarUsuario(usuario, senha)) {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Login bem-sucedido!");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new TelaMenu();
                            }
                        });
                } else {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Usuário ou senha incorretos.");
                    setVisible(true);
                }
            }
        });

        constraints.gridy++;
        container.add(loginButton, constraints);

        setVisible(true);
    }

    private boolean autenticarUsuario(String usuario, String senha) {
        setVisible(false);
        return usuario.equals("admin") && senha.equals("123");
    }
}

