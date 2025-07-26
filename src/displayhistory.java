import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class displayhistory extends JFrame {

    public displayhistory(String username) {
        setTitle("📜 Order History");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Order History for: " + username, JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(52, 73, 94));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        c.add(heading, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Order ID", "Item Name", "Item Price", "Order Date/Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        c.add(scrollPane, BorderLayout.CENTER);

        // JDBC: Fetch and populate table
        try {
            String url = "jdbc:mysql://localhost:3306/college";
            String user = "root";
            String password = "abc123";

            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT order_id, item_name, item_price, order_datetime FROM customer_history WHERE customer_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("order_id");
                String item = rs.getString("item_name");
                double price = rs.getDouble("item_price");
                Timestamp dt = rs.getTimestamp("order_datetime");

                model.addRow(new Object[]{id, item, String.format("₹%.2f", price), dt.toString()});
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching history:\n" + ex.getMessage());
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(41, 128, 185));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            dispose();
            new foodiepalace(username);
        });

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        southPanel.add(backButton);
        c.add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
