package lms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public boolean addBook(String title, String author, String publisher, int qty) {
        String sql = "INSERT INTO books (title, author, publisher, quantity) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, publisher);
            ps.setInt(4, qty);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateBook(int id, String title, String author, String publisher, int qty) {
        String sql = "UPDATE books SET title=?, author=?, publisher=?, quantity=? WHERE book_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, publisher);
            ps.setInt(4, qty);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<String[]> listBooks() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[] {
                        String.valueOf(rs.getInt("book_id")),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        String.valueOf(rs.getInt("quantity"))
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
