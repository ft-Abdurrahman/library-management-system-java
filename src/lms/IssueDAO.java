package lms;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {
    // issue a book: insert row and decrement book quantity if available
    public boolean issueBook(int bookId, int memberId, LocalDate issueDate, LocalDate dueDate) {
        String checkQty = "SELECT quantity FROM books WHERE book_id=?";
        String insert = "INSERT INTO issue_books (book_id, member_id, issue_date, due_date) VALUES (?,?,?,?)";
        String updBook = "UPDATE books SET quantity = quantity - 1 WHERE book_id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(checkQty)) {
                ps1.setInt(1, bookId);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next() || rs.getInt("quantity") <= 0) {
                        c.rollback();
                        return false; // not available
                    }
                }
            }
            try (PreparedStatement ps2 = c.prepareStatement(insert)) {
                ps2.setInt(1, bookId);
                ps2.setInt(2, memberId);
                ps2.setDate(3, Date.valueOf(issueDate));
                ps2.setDate(4, Date.valueOf(dueDate));
                ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = c.prepareStatement(updBook)) {
                ps3.setInt(1, bookId);
                ps3.executeUpdate();
            }
            c.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // return book: update return_date, calculate fine, increment book qty
    public boolean returnBook(int issueId, LocalDate returnDate) {
        String get = "SELECT book_id, due_date FROM issue_books WHERE issue_id=?";
        String updIssue = "UPDATE issue_books SET return_date=?, fine=? WHERE issue_id=?";
        String updBook = "UPDATE books SET quantity = quantity + 1 WHERE book_id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            int bId;
            LocalDate due;
            try (PreparedStatement ps = c.prepareStatement(get)) {
                ps.setInt(1, issueId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { c.rollback(); return false; }
                    bId = rs.getInt("book_id");
                    due = rs.getDate("due_date").toLocalDate();
                }
            }
            long daysLate = ChronoUnit.DAYS.between(due, returnDate);
            double fine = daysLate > 0 ? daysLate * 5.0 : 0.0; // e.g., 5 currency units per day

            try (PreparedStatement ps2 = c.prepareStatement(updIssue)) {
                ps2.setDate(1, Date.valueOf(returnDate));
                ps2.setDouble(2, fine);
                ps2.setInt(3, issueId);
                ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = c.prepareStatement(updBook)) {
                ps3.setInt(1, bId);
                ps3.executeUpdate();
            }
            c.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // list issued books (open/closed)
    public List<String[]> listIssues() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, b.title, i.member_id, m.name, i.issue_date, i.due_date, i.return_date, i.fine " +
                "FROM issue_books i JOIN books b ON i.book_id=b.book_id JOIN members m ON i.member_id=m.member_id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[] {
                        String.valueOf(rs.getInt("issue_id")),
                        String.valueOf(rs.getInt("book_id")),
                        rs.getString("title"),
                        String.valueOf(rs.getInt("member_id")),
                        rs.getString("name"),
                        rs.getDate("issue_date").toString(),
                        rs.getDate("due_date").toString(),
                        rs.getDate("return_date") == null ? "" : rs.getDate("return_date").toString(),
                        String.valueOf(rs.getDouble("fine"))
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
