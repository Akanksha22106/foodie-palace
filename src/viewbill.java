import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class viewbill extends JFrame {

    public viewbill(LinkedHashMap<String, Double> items, String username) {
        setTitle("🧾 View Bill");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

        JLabel title = new JLabel("Order Summary", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(100, 20, 300, 40);
        title.setForeground(new Color(30, 130, 76));
        c.add(title);

        JTextArea billArea = new JTextArea();
        billArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        billArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(billArea);
        scroll.setBounds(50, 80, 380, 300);
        c.add(scroll);

        double total = 0;
        StringBuilder details = new StringBuilder();

        for (Map.Entry<String, Double> entry : items.entrySet()) {
            details.append(String.format("%-25s ₹%.2f\n", entry.getKey(), entry.getValue()));
            total += entry.getValue();
        }

        details.append("\n-----------------------------\n");
        details.append(String.format("%-25s ₹%.2f", "Total", total));

        billArea.setText(details.toString());

        // 👉 JDBC: Save bill to database
        try {
            // Replace with your actual DB details
            String url = "jdbc:mysql://localhost:3306/college";
            String user = "root";
            String password = "abc123";

            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO customer_history (customer_name, item_name, item_price) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (Map.Entry<String, Double> entry : items.entrySet()) {
                ps.setString(1, username);
                ps.setString(2, entry.getKey());
                ps.setDouble(3, entry.getValue());
                ps.executeUpdate();
            }

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving bill to database:\n" + ex.getMessage());
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(180, 400, 120, 40);
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            dispose();
            new foodiepalace(""); // Go back to main page or menu
        });
        c.add(backButton);

        setVisible(true);
    }
}
