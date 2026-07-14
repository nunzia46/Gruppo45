package ospedale.gui;

import ospedale.controller.Controller;
import ospedale.utenti.Amministratore;
import ospedale.utenti.Medico;
import ospedale.utenti.Utente;

import javax.swing.*;
import java.awt.*;

/**
 * Schermata di autenticazione per l'accesso al sistema ospedaliero.
 * Discrimina tra amministratore e medico e apre la dashboard corretta.
 */
public class LoginFrame extends JFrame {

    private final Controller controller;

    private final JTextField campoLogin = new JTextField(15);
    private final JPasswordField campoPassword = new JPasswordField(15);
    private final JLabel labelMessaggio = new JLabel(" ");

    /**
     * Costruisce il frame di login con il controller specificato.
     *
     * @param controller il controller del sistema
     */
    public LoginFrame(Controller controller) {
        this.controller = controller;
        costruisciUI();
    }

    /** Configura l'interfaccia grafica del login. */
    private void costruisciUI() {
        setTitle("Ospedale — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel pannello = new JPanel(new GridBagLayout());
        pannello.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titolo = new JLabel("Sistema Ospedaliero", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        pannello.add(titolo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; pannello.add(new JLabel("Login:"), gbc);
        gbc.gridx = 1; pannello.add(campoLogin, gbc);

        gbc.gridy = 2; gbc.gridx = 0; pannello.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; pannello.add(campoPassword, gbc);

        JButton btnAccedi = new JButton("Accedi");
        btnAccedi.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        pannello.add(btnAccedi, gbc);

        labelMessaggio.setForeground(Color.RED);
        labelMessaggio.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        pannello.add(labelMessaggio, gbc);

        btnAccedi.addActionListener(e -> tentaLogin());
        campoPassword.addActionListener(e -> tentaLogin());

        add(pannello);
        pack();
        setLocationRelativeTo(null);
    }

    /** Esegue il tentativo di login con le credenziali inserite. */
    private void tentaLogin() {
        String login = campoLogin.getText().trim();
        String password = new String(campoPassword.getPassword());
        Utente utente = controller.login(login, password);

        if (utente == null) {
            labelMessaggio.setText("Credenziali non valide. Riprovare.");
            campoPassword.setText("");
        } else if (utente instanceof Amministratore) {
            new AdminFrame(controller).setVisible(true);
            dispose();
        } else if (utente instanceof Medico) {
            new MedicoFrame(controller, (Medico) utente).setVisible(true);
            dispose();
        }
    }

    /**
     * Punto di ingresso autonomo per il test del login.
     *
     * @param args argomenti da riga di comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller c = new Controller();
            new LoginFrame(c).setVisible(true);
        });
    }
}