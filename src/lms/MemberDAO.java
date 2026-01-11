package lms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public boolean addMember(String name, String email, String contact, String dept) {
        String sql = "INSERT INTO members (name, email, contact, department) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, dept);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateMember(int id, String name, String email, String contact, String dept) {
        String sql = "UPDATE members SET name=?, email=?, contact=?, department=? WHERE member_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, dept);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteMember(int id) {
        String sql = "DELETE FROM members WHERE member_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<String[]> listMembers() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[] {
                        String.valueOf(rs.getInt("member_id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("department")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
