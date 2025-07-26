import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class burger extends JFrame {

    double burgerPrice = 0, sizePrice = 0, addOnPrice = 0;
    JLabel totalLabel;

    public burger(String username) {
        Font titleFont = new Font("Verdana", Font.BOLD, 36);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 18);

        JLabel title = new JLabel("Burger King 🍔");
        title.setFont(titleFont);
        title.setForeground(new Color(218, 41, 28));
        title.setBounds(260, 10, 500, 50);

        JLabel l1 = new JLabel("Choose Your Burger:");
        l1.setFont(labelFont);
        l1.setBounds(150, 70, 300, 30);

        JComboBox<String> burgerBox = new JComboBox<>(new String[]{
                "Whopper", "Chicken Royale", "Veggie Burger", "Paneer King Melt"
        });
        burgerBox.setFont(inputFont);
        burgerBox.setBounds(150, 100, 500, 30);

        JLabel l2 = new JLabel("Choose Size:");
        l2.setFont(labelFont);
        l2.setBounds(150, 150, 250, 30);

        JComboBox<String> sizeBox = new JComboBox<>(new String[]{
                "Small", "Medium", "Large"
        });
        sizeBox.setFont(inputFont);
        sizeBox.setBounds(150, 180, 200, 30);

        JLabel l3 = new JLabel("Add-ons:");
        l3.setFont(labelFont);
        l3.setBounds(150, 230, 250, 30);

        JCheckBox cb1 = new JCheckBox("Cheese Slice (₹20)");
        JCheckBox cb2 = new JCheckBox("Extra Patty (₹50)");
        JCheckBox cb3 = new JCheckBox("Bacon Strips (₹40)");
        JCheckBox cb4 = new JCheckBox("Onion Rings (₹30)");
        JCheckBox[] addOns = {cb1, cb2, cb3, cb4};

        int y = 260;
        for (JCheckBox cb : addOns) {
            cb.setBounds(150, y, 300, 30);
            cb.setFont(inputFont);
            cb.setOpaque(false);
            cb.setForeground(Color.DARK_GRAY);
            y += 30;

            cb.addItemListener(e -> {
                if (cb.isSelected()) addOnPrice += getAddOnPrice(cb);
                else addOnPrice -= getAddOnPrice(cb);
                updateTotalLabel();
            });
        }

        totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(255, 87, 34));
        totalLabel.setBounds(150, 400, 300, 30);

        JButton orderButton = new JButton("Place Order");
        orderButton.setBounds(420, 450, 200, 40);
        orderButton.setFont(inputFont);
        orderButton.setBackground(new Color(255, 69, 0));
        orderButton.setForeground(Color.WHITE);
        orderButton.setBorder(new LineBorder(Color.WHITE, 2));

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 450, 200, 40);
        backButton.setFont(inputFont);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));

        burgerBox.addActionListener(e -> {
            burgerPrice = getBurgerPrice((String) burgerBox.getSelectedItem());
            updateTotalLabel();
        });

        sizeBox.addActionListener(e -> {
            sizePrice = getSizePrice((String) sizeBox.getSelectedItem());
            updateTotalLabel();
        });

        orderButton.addActionListener(e -> {
            LinkedHashMap<String, Double> items = new LinkedHashMap<>();
            items.put("Burger", burgerPrice);
            items.put("Size", sizePrice);
            for (JCheckBox cb : addOns) {
                if (cb.isSelected()) {
                    items.put(cb.getText(), getAddOnPrice(cb));
                }
            }

            new viewbill(items,username);
            dispose();
        });

        backButton.addActionListener(e -> {
            dispose();
            new foodiepalace("");
        });

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 239, 213));
        c.add(title); c.add(l1); c.add(burgerBox);
        c.add(l2); c.add(sizeBox);
        c.add(l3);
        for (JCheckBox cb : addOns) c.add(cb);
        c.add(totalLabel); c.add(orderButton); c.add(backButton);

        setTitle("Burger King Ordering App");
        setSize(800, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double getAddOnPrice(JCheckBox cb) {
        return switch (cb.getText()) {
            case "Cheese Slice (₹20)" -> 20;
            case "Extra Patty (₹50)" -> 50;
            case "Bacon Strips (₹40)" -> 40;
            case "Onion Rings (₹30)" -> 30;
            default -> 0;
        };
    }

    private double getBurgerPrice(String burger) {
        return switch (burger) {
            case "Whopper" -> 180;
            case "Chicken Royale" -> 200;
            case "Veggie Burger" -> 150;
            case "Paneer King Melt" -> 170;
            default -> 0;
        };
    }

    private double getSizePrice(String size) {
        return switch (size) {
            case "Small" -> 0;
            case "Medium" -> 30;
            case "Large" -> 60;
            default -> 0;
        };
    }

    private void updateTotalLabel() {
        double total = burgerPrice + sizePrice + addOnPrice;
        totalLabel.setText("Total: ₹" + total);
    }

    public static void main(String[] args) {
        new burger("");
    }
}
