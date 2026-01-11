package lms;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private final String username;

    public AdminMainFrame(String username) {
        this.username = username;
        setTitle("LMS - Admin Panel (" + username + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel top = new JPanel();
        top.add(new JLabel("Welcome, " + username));

        JButton btnBooks = new JButton("Books");
        JButton btnMembers = new JButton("Members");
        JButton btnIssue = new JButton("Issue / Return");
        JButton btnLogout = new JButton("Logout");

        JPanel left = new JPanel(new GridLayout(4,1,8,8));
        left.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        left.add(btnBooks);
        left.add(btnMembers);
        left.add(btnIssue);
        left.add(btnLogout);

        add(top, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);

        btnBooks.addActionListener(e -> new BookManagementFrame().setVisible(true));
        btnMembers.addActionListener(e -> new MemberManagementFrame().setVisible(true));
        btnIssue.addActionListener(e -> new IssueReturnFrame().setVisible(true));
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}

