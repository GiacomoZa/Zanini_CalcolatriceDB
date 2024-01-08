package app.form.Calcolatrice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel panel1;
    private JButton loginButton;
    private JButton signUpButton;
    private JTextField textField1;
    private JPasswordField passwordField1;

    Database db;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void ApriFormCalcolatrice(String username) {
        Calcolatrice calcolatrice = new Calcolatrice(username, db.getConnessione());
        calcolatrice.showLoginForm2(username, db.getConnessione());
        hideForm();
    }

    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();

                if (db.login(username, password)) {
                    JOptionPane.showMessageDialog(null, "LOGIN RIUSCITO");

                    hideForm();
                    ApriFormCalcolatrice(username);
                } else {
                    JOptionPane.showMessageDialog(null, "LOGIN FALLITO");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();

                if (db.registrazione(username, password)) {
                    JOptionPane.showMessageDialog(null, "REGISTRAZIONE RIUSCITA!");

                    hideForm();
                    ApriFormCalcolatrice(username);
                } else {
                    JOptionPane.showMessageDialog(null, "REGISTRAZIONE FALLITA");
                }
            }
        });

        db = new Database("127.0.0.1", "dbjava","root", "");
    }

    private void hideForm(){
        //nascondo il form
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
        if (frame != null) {
            frame.setVisible(false);
        }
    }
}