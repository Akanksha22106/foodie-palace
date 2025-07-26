import javax.swing.*;
import java.awt.*;

public class foodiepalace extends JFrame {

    public foodiepalace(String username) {
        setTitle("🍽️ Foodie Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Panel
        JLabel titleLabel = new JLabel("Welcome to FoodiePalace 🍽️", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0x2C3E50));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel - Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0xECF0F1));

        JButton dominosBtn = createButton("Dominos", "/images/dominos.png");
        JButton starbucksBtn = createButton("Starbucks", "/images/starbucks.png");
        JButton bkBtn = createButton("Burger King", "/images/burgerking.png");
        JButton dessertBtn = createButton("Dessert Heaven", "/images/dessert.png");

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(dominosBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(starbucksBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(bkBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(dessertBtn);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Customer History Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0x2C3E50));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton historyButton = new JButton("📜 Customer History");
        historyButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBackground(new Color(0x8E44AD));
        historyButton.setFocusPainted(false);
        historyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        historyButton.setPreferredSize(new Dimension(250, 40));
        historyButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Hover effect for Customer History button
        historyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historyButton.setBackground(new Color(0x9B59B6));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                historyButton.setBackground(new Color(0x8E44AD));
            }
        });

        // Action for history button
        historyButton.addActionListener(e -> {
           // JOptionPane.showMessageDialog(null, "Customer history feature coming soon!");
            // You can replace this with: new CustomerHistory();
            new displayhistory(username);
            dispose();
        });

        bottomPanel.add(historyButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        dominosBtn.addActionListener(e -> openPlace(new dominos(username)));
        starbucksBtn.addActionListener(e -> openPlace(new starbucks(username)));
        bkBtn.addActionListener(e -> openPlace(new burger(username)));
        dessertBtn.addActionListener(e -> openPlace(new Dessert(username)));

        setVisible(true);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x3498DB));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(300, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(15);
        } catch (Exception e) {
            System.out.println("Icon not found for " + text + " at " + iconPath);
        }

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x1ABC9C));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x3498DB));
            }
        });

        return button;
    }

    private void openPlace(JFrame next) {
        dispose();
        next.setVisible(true);
    }

    public static void main(String[] args) {
        new foodiepalace("Akanksha1");
    }
}
