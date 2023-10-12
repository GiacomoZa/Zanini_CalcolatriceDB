package app.form.Calcolatrice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Calcolatrice implements ActionListener{
    private JPanel Panel;
    private JButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnVirgola, btnUguale, btnSottrai, btnSomma, btnMoltiplica, btnDividi, btnRPN, btnParChiudi, btnParApri, btnSpazio;
    private JLabel lbl;
    private JButton btnCanc;

    public Calcolatrice(){
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
        btnVirgola.addActionListener(this);
        btnCanc.addActionListener(this);
    }

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
        if (e.getSource() == btnUguale)
            TraduciInRPN(lbl.getText());
        if (e.getSource() == btnVirgola)
            lbl.setText(lbl.getText() + ",");
        if (e.getSource() == btnRPN)
           CalcolaRPN(lbl.getText());
        if(e.getSource()==btnCanc)
            lbl.setText(" ");
    }
    public void TraduciInRPN(String infix) {
        StringBuilder rpn = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isDigit(c)) {
                rpn.append(c);
                while (i + 1 < infix.length() && (Character.isDigit(infix.charAt(i + 1)) || infix.charAt(i + 1) == ',')) {
                    i++;
                    rpn.append(infix.charAt(i));
                }
                rpn.append(" ");
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    rpn.append(operatorStack.pop()).append(" ");
                }
                operatorStack.pop(); // Rimuovi la parentesi aperta
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && precedenza(operatorStack.peek()) >= precedenza(c)) {
                    rpn.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }

        while (!operatorStack.isEmpty()) {
            rpn.append(operatorStack.pop()).append(" ");
        }
        System.out.println(rpn.toString().trim());
        CalcolaRPN(rpn.toString().trim());
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedenza(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        }
        return 0; // Per gli altri caratteri
    }

    public void CalcolaRPN(String n){
        Stack<String> stack = new Stack<>();
        String[] tokens = n.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Espressione RPN non valida.");
                }
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                Double result = Operazioni(Double.parseDouble(operand1), Double.parseDouble(operand2), token);
                stack.push(String.valueOf(result));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Espressione RPN non valida.");
        }
        //System.out.println(stack.pop());

        lbl.setText(stack.pop());
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static double Operazioni(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Divisione per zero.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Operatore non valido: " + operator);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calcolatrice");
        frame.setContentPane(new Calcolatrice().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
