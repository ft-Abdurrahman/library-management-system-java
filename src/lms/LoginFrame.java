package lms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPass = new JPasswordField(20);
    private final AuthDAO authDAO = new AuthDAO();

    public LoginFrame() {
        setTitle("LMS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 230);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(3,2,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        center.add(new JLabel("Username:"));
        center.add(txtUser);
        center.add(new JLabel("Password:"));
        center.add(txtPass);

        JButton btnLogin = new JButton("Login");
        JButton btnSignup = new JButton("Sign Up");
        JButton btnForgot = new JButton("Forgot Password");

        JPanel bottom = new JPanel();
        bottom.add(btnLogin);
        bottom.add(btnSignup);
        bottom.add(btnForgot);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnLogin.addActionListener((ActionEvent e) -> doLogin());
        btnSignup.addActionListener((ActionEvent e) -> {
            new SignupFrame().setVisible(true);
        });
        btnForgot.addActionListener((ActionEvent e) -> {
            new ForgotPasswordFrame().setVisible(true);
        });
    }

    private void doLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (authDAO.login(user, pass)) {
            JOptionPane.showMessageDialog(this, "Login successful.");
            // Open admin main window
            new AdminMainFrame(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username/password.", "Login failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
