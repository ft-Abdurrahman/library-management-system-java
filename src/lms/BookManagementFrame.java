package lms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookManagementFrame extends JFrame {
    private final BookDAO dao = new BookDAO();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Title","Author","Publisher","Qty"},0);
    private final JTable table = new JTable(model);

    public BookManagementFrame() {
        setTitle("Book Management");
        setSize(700,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        refreshTable();

        JPanel form = new JPanel(new GridLayout(5,2,6,6));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtTitle = new JTextField();
        JTextField txtAuthor = new JTextField();
        JTextField txtPublisher = new JTextField();
        JTextField txtQty = new JTextField("1");

        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Title:"));
        form.add(txtTitle);
        form.add(new JLabel("Author:"));
        form.add(txtAuthor);
        form.add(new JLabel("Publisher:"));
        form.add(txtPublisher);
        form.add(new JLabel("Quantity:"));
        form.add(txtQty);

        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        buttons.add(btnAdd); buttons.add(btnUpdate); buttons.add(btnDelete); buttons.add(btnRefresh);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);

        // table select to form
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtId.setText(model.getValueAt(r,0).toString());
                txtTitle.setText(model.getValueAt(r,1).toString());
                txtAuthor.setText(model.getValueAt(r,2).toString());
                txtPublisher.setText(model.getValueAt(r,3).toString());
                txtQty.setText(model.getValueAt(r,4).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            if (dao.addBook(txtTitle.getText(), txtAuthor.getText(), txtPublisher.getText(),
                    Integer.parseInt(txtQty.getText()))) {
                JOptionPane.showMessageDialog(this, "Book added.");
                refreshTable();
            } else JOptionPane.showMessageDialog(this, "Add failed.");
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select book."); return; }
            if (dao.updateBook(Integer.parseInt(txtId.getText()), txtTitle.getText(), txtAuthor.getText(), txtPublisher.getText(),
                    Integer.parseInt(txtQty.getText()))) {
                JOptionPane.showMessageDialog(this, "Updated.");
                refreshTable();
            } else JOptionPane.showMessageDialog(this, "Update failed.");
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Select book."); return; }
            if (dao.deleteBook(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Deleted.");
                refreshTable();
            } else JOptionPane.showMessageDialog(this, "Delete failed.");
        });

        btnRefresh.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<String[]> rows = dao.listBooks();
        for (String[] r : rows) model.addRow(r);
    }
}
