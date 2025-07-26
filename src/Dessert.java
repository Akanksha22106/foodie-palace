import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class Dessert extends JFrame {

    double dessertPrice = 0, sizePrice = 0, toppingPrice = 0;
    JLabel totalLabel;

    public Dessert(String username) {
        Font titleFont = new Font("Verdana", Font.BOLD, 36);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 18);

        JLabel title = new JLabel("Dessert Delights 🍨");
        title.setFont(titleFont);
        title.setForeground(new Color(139, 69, 19));
        title.setBounds(220, 10, 500, 50);

        JLabel l1 = new JLabel("Select Dessert:");
        l1.setFont(labelFont);
        l1.setBounds(150, 70, 250, 30);

        JComboBox<String> dessertBox = new JComboBox<>(new String[]{
                "Chocolate Brownie", "Strawberry Cheesecake", "Vanilla Ice Cream", "Gulab Jamun"
        });
        dessertBox.setFont(inputFont);
        dessertBox.setBounds(150, 100, 500, 30);

        JLabel sizeLabel = new JLabel("Select Size:");
        sizeLabel.setFont(labelFont);
        sizeLabel.setBounds(150, 150, 250, 30);

        JComboBox<String> sizeBox = new JComboBox<>(new String[]{
                "Small", "Medium", "Large"
        });
        sizeBox.setFont(inputFont);
        sizeBox.setBounds(150, 180, 200, 30);

        JLabel toppingLabel = new JLabel("Add Toppings:");
        toppingLabel.setFont(labelFont);
        toppingLabel.setBounds(150, 230, 250, 30);

        JCheckBox cb1 = new JCheckBox("Choco Chips (₹20)");
        JCheckBox cb2 = new JCheckBox("Honey Drizzle (₹25)");
        JCheckBox cb3 = new JCheckBox("Sprinkles (₹15)");
        JCheckBox[] toppings = {cb1, cb2, cb3};

        int y = 260;
        for (JCheckBox cb : toppings) {
            cb.setBounds(150, y, 300, 30);
            cb.setFont(inputFont);
            cb.setOpaque(false);
            cb.setForeground(Color.DARK_GRAY);
            y += 30;
            cb.addItemListener(e -> {
                if (cb.isSelected()) toppingPrice += getToppingPrice(cb);
                else toppingPrice -= getToppingPrice(cb);
                updateTotalLabel();
            });
        }

        totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(0xC2185B));
        totalLabel.setBounds(150, 370, 300, 30);

        JButton orderButton = new JButton("Place Order");
        orderButton.setBounds(420, 420, 200, 40);
        orderButton.setFont(inputFont);
        orderButton.setBackground(new Color(128, 0, 64));
        orderButton.setForeground(Color.WHITE);
        orderButton.setBorder(new LineBorder(Color.WHITE, 2));

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 420, 200, 40);
        backButton.setFont(inputFont);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));

        dessertBox.addActionListener(e -> {
            dessertPrice = getDessertPrice((String) dessertBox.getSelectedItem());
            updateTotalLabel();
        });

        sizeBox.addActionListener(e -> {
            sizePrice = getSizePrice((String) sizeBox.getSelectedItem());
            updateTotalLabel();
        });

        orderButton.addActionListener(e -> {
            LinkedHashMap<String, Double> items = new LinkedHashMap<>();
            items.put("Dessert", dessertPrice);
            items.put("Size", sizePrice);

            for (JCheckBox cb : toppings) {
                if (cb.isSelected()) {
                    items.put(cb.getText(), getToppingPrice(cb));
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
        c.setBackground(new Color(255, 245, 238));
        c.add(title); c.add(l1); c.add(dessertBox);
        c.add(sizeLabel); c.add(sizeBox);
        c.add(toppingLabel);
        for (JCheckBox cb : toppings) c.add(cb);
        c.add(totalLabel); c.add(orderButton); c.add(backButton);

        setTitle("Dessert Ordering App");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double getToppingPrice(JCheckBox cb) {
        return switch (cb.getText()) {
            case "Choco Chips (₹20)" -> 20;
            case "Honey Drizzle (₹25)" -> 25;
            case "Sprinkles (₹15)" -> 15;
            default -> 0;
        };
    }

    private double getDessertPrice(String dessert) {
        return switch (dessert) {
            case "Chocolate Brownie" -> 120;
            case "Strawberry Cheesecake" -> 150;
            case "Vanilla Ice Cream" -> 100;
            case "Gulab Jamun" -> 90;
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
        double total = dessertPrice + sizePrice + toppingPrice;
        totalLabel.setText("Total: ₹" + total);
    }

    public static void main(String[] args) {
        new Dessert("");
    }
}
