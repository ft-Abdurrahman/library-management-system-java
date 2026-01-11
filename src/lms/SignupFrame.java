package lms;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPass = new JPasswordField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final AuthDAO authDAO = new AuthDAO();

    public SignupFrame() {
        setTitle("LMS - Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 240);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(4,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        p.add(new JLabel("Username:"));
        p.add(txtUser);
        p.add(new JLabel("Password:"));
        p.add(txtPass);
        p.add(new JLabel("Email:"));
        p.add(txtEmail);
        JButton btnCreate = new JButton("Create Account");
        p.add(new JLabel());
        p.add(btnCreate);
        add(p);

        btnCreate.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String pword = new String(txtPass.getPassword());
            String email = txtEmail.getText().trim();
            if (u.isEmpty() || pword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and password.");
                return;
            }
            boolean ok = authDAO.signUp(u, pword, email);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Account created. You can log in now.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Could not create account (maybe exists).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
