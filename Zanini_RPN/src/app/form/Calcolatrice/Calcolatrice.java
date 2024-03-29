package app.form.Calcolatrice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Calcolatrice implements ActionListener{
    private JPanel Panel;
    private JLabel lbl, lblConvert;
    private JButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnUguale, btnSottrai, btnSomma, btnMoltiplica, btnDividi, btnRPN, btnParChiudi, btnParApri, btnSpazio, btnCanc;
    private String username;
    private Connection conn;

    public Calcolatrice(String username, Connection connessione){
        this.username = username;
        this.conn = connessione;
        System.out.println(conn);
        btn0.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);
        btnSottrai.addActionListener(this);
        btnSomma.addActionListener(this);
        btnMoltiplica.addActionListener(this);
        btnDividi.addActionListener(this);
        btnRPN.addActionListener(this);
        btnParChiudi.addActionListener(this);
        btnParApri.addActionListener(this);
        btnSpazio.addActionListener(this);
        btnUguale.addActionListener(this);
        btnCanc.addActionListener(this);
    }

    /*public Calcolatrice(){
        btn0.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);
        btnSottrai.addActionListener(this);
        btnSomma.addActionListener(this);
        btnMoltiplica.addActionListener(this);
        btnDividi.addActionListener(this);
        btnRPN.addActionListener(this);
        btnParChiudi.addActionListener(this);
        btnParApri.addActionListener(this);
        btnSpazio.addActionListener(this);
        btnUguale.addActionListener(this);
        btnCanc.addActionListener(this);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn0)
            lbl.setText(lbl.getText() + "0");
        if (e.getSource() == btn1)
            lbl.setText(lbl.getText() + "1");
        if (e.getSource() == btn2)
            lbl.setText(lbl.getText() + "2");
        if (e.getSource() == btn3)
            lbl.setText(lbl.getText() + "3");
        if (e.getSource() == btn4)
            lbl.setText(lbl.getText() + "4");
        if (e.getSource() == btn5)
            lbl.setText(lbl.getText() + "5");
        if (e.getSource() == btn6)
            lbl.setText(lbl.getText() + "6");
        if (e.getSource() == btn7)
            lbl.setText(lbl.getText() + "7");
        if (e.getSource() == btn8)
            lbl.setText(lbl.getText() + "8");
        if (e.getSource() == btn9)
            lbl.setText(lbl.getText() + "9");
        if (e.getSource() == btnSomma)
            lbl.setText(lbl.getText() + "+");
        if (e.getSource() == btnSottrai)
            lbl.setText(lbl.getText() + "-");
        if (e.getSource() == btnMoltiplica)
            lbl.setText(lbl.getText() + "*");
        if (e.getSource() == btnDividi)
            lbl.setText(lbl.getText() + "/");
        if (e.getSource() == btnParApri)
            lbl.setText(lbl.getText() + "(");
        if (e.getSource() == btnParChiudi)
            lbl.setText(lbl.getText() + ")");
        if (e.getSource() == btnSpazio)
            lbl.setText(lbl.getText() + " ");
        if (e.getSource() == btnRPN)
            CalcolaRPN(lbl.getText());
        if(e.getSource()==btnCanc)
            lbl.setText(" ");
        if (e.getSource() == btnUguale) {
            String espress = lbl.getText();
            TraduciInRPN(espress);
            System.out.println(conn);
            SalvaRisultatoNelDatabase(espress);
        }
    }

    private void SalvaRisultatoNelDatabase(String espress) {
        try {
            if (conn == null || conn.isClosed()) {
                return;
            }

            int idUtente = getIdUtente();

            String query = "INSERT INTO cronologia (espressione, risultato, IDutente) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, espress);
                statement.setString(2, lbl.getText());
                statement.setInt(3, idUtente);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Risultato salvato nel database.");
                } else {
                    System.out.println("Errore durante il salvataggio del risultato nel database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getIdUtente() {
        int idUtente = -1;

        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Errore: Connessione al database non valida.");
                return idUtente;
            }

            String query = "SELECT ID FROM utente WHERE nome = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, this.username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idUtente = resultSet.getInt("ID");
                    } else {
                        System.out.println("Errore: Utente non trovato nel database.");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idUtente;
    }

    public void TraduciInRPN(String s) {
        StringBuilder rpn = new StringBuilder();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                rpn.append(c);
                while (i + 1 < s.length() && (Character.isDigit(s.charAt(i + 1)) || s.charAt(i + 1) == ',')) {
                    i++;
                    rpn.append(s.charAt(i));
                }
                rpn.append(" ");
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    rpn.append(ops.pop()).append(" ");
                }
                ops.pop(); // Rimozione della parentesi aperta
            } else if (ControlloOPC(c)) {
                while (!ops.isEmpty() && Precedenza(ops.peek()) >= Precedenza(c)) {
                    rpn.append(ops.pop()).append(" ");
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            rpn.append(ops.pop()).append(" ");
        }

        String sf = rpn.toString().trim();

        lblConvert.setText(sf);
        CalcolaRPN(sf);
    }

    private static boolean ControlloOPC(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int Precedenza(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        }
        return 0;
    }

    public void CalcolaRPN(String n){
        Stack<String> stack = new Stack<>();
        String[] ops = n.split("\\s+");

        for (String op : ops) {
            if (ControlloNumero(op)) {
                stack.push(op);
            } else if (ControlloOPS(op)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("ERRORE, espressione RPN non valida");
                }
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                Double result = Operazioni(Double.parseDouble(operand1), Double.parseDouble(operand2), op);
                stack.push(String.valueOf(result));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("ERRORE, espressione RPN non valida");
        }

        lbl.setText(stack.pop());
    }

    private static boolean ControlloNumero(String op) {
        try {
            Double.parseDouble(op);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean ControlloOPS(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    private static double Operazioni(double op1, double op2, String operator) {
        switch (operator) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
                if (op2 == 0) {
                    throw new ArithmeticException("ERRORE, divisione per zero");
                }
                return op1 / op2;
            default:
                throw new IllegalArgumentException("ERRORE, operatore non valido: " + operator);
        }
    }

    public void main(String[] args) {
        showLoginForm(username, conn);
    }

    private void showLoginForm(String user, Connection c) {
        JFrame frame = new JFrame("Calcolatrice");
        frame.setContentPane(new Calcolatrice(user, c).Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void showLoginForm2(String user, Connection c) {
        showLoginForm(user, c);
    }
}