package lms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberManagementFrame extends JFrame {
    private final MemberDAO dao = new MemberDAO();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name","Email","Contact","Dept"},0);

    public MemberManagementFrame() {
        setTitle("Member Management");
        setSize(780,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        refreshTable();

        JPanel form = new JPanel(new GridLayout(5,2,6,6));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtContact = new JTextField();
        JTextField txtDept = new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Name:")); form.add(txtName);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Contact:")); form.add(txtContact);
        form.add(new JLabel("Dept:")); form.add(txtDept);

        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        buttons.add(btnAdd); buttons.add(btnUpdate); buttons.add(btnDelete); buttons.add(btnRefresh);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtId.setText(model.getValueAt(r,0).toString());
                txtName.setText(model.getValueAt(r,1).toString());
                txtEmail.setText(model.getValueAt(r,2).toString());
                txtContact.setText(model.getValueAt(r,3).toString());
                txtDept.setText(model.getValueAt(r,4).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            if (dao.addMember(txtName.getText(), txtEmail.getText(), txtContact.getText(), txtDept.getText())) {
                JOptionPane.showMessageDialog(this,"Member added."); refreshTable();
            } else JOptionPane.showMessageDialog(this,"Add failed.");
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select member."); return; }
            if (dao.updateMember(Integer.parseInt(txtId.getText()), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtDept.getText())) {
                JOptionPane.showMessageDialog(this,"Updated."); refreshTable();
            } else JOptionPane.showMessageDialog(this,"Update failed.");
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select member."); return; }
            if (dao.deleteMember(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this,"Deleted."); refreshTable();
            } else JOptionPane.showMessageDialog(this,"Delete failed.");
        });

        btnRefresh.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<String[]> rows = dao.listMembers();
        for (String[] r : rows) model.addRow(r);
    }
}
