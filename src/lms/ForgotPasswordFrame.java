package lms;

import javax.swing.*;
import java.awt.*;

public class ForgotPasswordFrame extends JFrame {
    private final JTextField txtUser = new JTextField(20);
    private final JTextField txtNewPass = new JTextField(20);
    private final AuthDAO authDAO = new AuthDAO();

    public ForgotPasswordFrame() {
        setTitle("LMS - Reset Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 200);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        p.add(new JLabel("Username:"));
        p.add(txtUser);
        p.add(new JLabel("New Password:"));
        p.add(txtNewPass);
        JButton btnReset = new JButton("Reset");
        p.add(new JLabel());
        p.add(btnReset);
        add(p);

        btnReset.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String newp = txtNewPass.getText();
            if (user.isEmpty() || newp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and new password.");
                return;
            }
            boolean ok = authDAO.resetPassword(user, newp);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Password reset successful.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Could not reset password. Check username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
