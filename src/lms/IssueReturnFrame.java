package lms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class IssueReturnFrame extends JFrame {
    private final IssueDAO dao = new IssueDAO();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"IssueID","BookID","Title","MemberID","MemberName","IssueDate","DueDate","ReturnDate","Fine"},0);

    public IssueReturnFrame() {
        setTitle("Issue & Return");
        setSize(900,480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        refreshTable();

        JPanel form = new JPanel(new GridLayout(6,2,6,6));
        JTextField txtIssueId = new JTextField(); txtIssueId.setEditable(false);
        JTextField txtBookId = new JTextField();
        JTextField txtMemberId = new JTextField();
        JTextField txtIssueDate = new JTextField(LocalDate.now().toString());
        JTextField txtDueDate = new JTextField(LocalDate.now().plusDays(14).toString()); // default 2 weeks
        JTextField txtReturnDate = new JTextField(LocalDate.now().toString());

        form.add(new JLabel("Issue ID:")); form.add(txtIssueId);
        form.add(new JLabel("Book ID:")); form.add(txtBookId);
        form.add(new JLabel("Member ID:")); form.add(txtMemberId);
        form.add(new JLabel("Issue Date (YYYY-MM-DD):")); form.add(txtIssueDate);
        form.add(new JLabel("Due Date (YYYY-MM-DD):")); form.add(txtDueDate);
        form.add(new JLabel("Return Date (for return):")); form.add(txtReturnDate);

        JPanel buttons = new JPanel();
        JButton btnIssue = new JButton("Issue Book");
        JButton btnReturn = new JButton("Return Book");
        JButton btnRefresh = new JButton("Refresh");
        buttons.add(btnIssue); buttons.add(btnReturn); buttons.add(btnRefresh);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);

        btnIssue.addActionListener(e -> {
            try {
                int bId = Integer.parseInt(txtBookId.getText());
                int mId = Integer.parseInt(txtMemberId.getText());
                LocalDate iss = LocalDate.parse(txtIssueDate.getText());
                LocalDate due = LocalDate.parse(txtDueDate.getText());
                boolean ok = dao.issueBook(bId, mId, iss, due);
                JOptionPane.showMessageDialog(this, ok ? "Issued." : "Issue failed (maybe no quantity).");
                refreshTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid data."); }
        });

        btnReturn.addActionListener(e -> {
            try {
                int issueId = Integer.parseInt(txtIssueId.getText());
                LocalDate ret = LocalDate.parse(txtReturnDate.getText());
                boolean ok = dao.returnBook(issueId, ret);
                JOptionPane.showMessageDialog(this, ok ? "Return recorded." : "Return failed.");
                refreshTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid data."); }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtIssueId.setText(model.getValueAt(r,0).toString());
                txtBookId.setText(model.getValueAt(r,1).toString());
                txtMemberId.setText(model.getValueAt(r,3).toString());
                txtIssueDate.setText(model.getValueAt(r,5).toString());
                txtDueDate.setText(model.getValueAt(r,6).toString());
                txtReturnDate.setText(model.getValueAt(r,7).toString().isEmpty() ? LocalDate.now().toString() : model.getValueAt(r,7).toString());
            }
        });

        btnRefresh.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<String[]> rows = dao.listIssues();
        for (String[] r : rows) model.addRow(r);
    }
}
