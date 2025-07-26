import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import javax.swing.border.LineBorder;

public class starbucks extends JFrame {

    double coffeePrice = 0, typePrice = 0, addOnPrice = 0;
    JLabel totalLabel;

    public starbucks(String username) {
        Font titleFont = new Font("Georgia", Font.BOLD, 38);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 18);

        JLabel title = new JLabel("Starbucks Coffee Bar ☕");
        title.setFont(titleFont);
        title.setForeground(new Color(0x4E342E));
        title.setBounds(180, 10, 500, 50);

        JLabel typeLabel = new JLabel("Select Coffee Type:");
        typeLabel.setFont(labelFont);
        typeLabel.setBounds(150, 70, 300, 30);

        JRadioButton hot = new JRadioButton("Hot");
        JRadioButton cold = new JRadioButton("Cold");
        hot.setFont(inputFont);
        cold.setFont(inputFont);
        hot.setBounds(150, 100, 100, 30);
        cold.setBounds(270, 100, 100, 30);
        hot.setOpaque(false);
        cold.setOpaque(false);

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(hot);
        typeGroup.add(cold);

        JLabel coffeeLabel = new JLabel("Select Coffee:");
        coffeeLabel.setFont(labelFont);
        coffeeLabel.setBounds(150, 140, 300, 30);

        JComboBox<String> coffeeBox = new JComboBox<>(new String[]{
                "Caffè Latte", "Caramel Macchiato", "Mocha", "Flat White", "Cold Brew"
        });
        coffeeBox.setFont(inputFont);
        coffeeBox.setBounds(150, 170, 400, 30);

        JLabel addOnLabel = new JLabel("Add-ons:");
        addOnLabel.setFont(labelFont);
        addOnLabel.setBounds(150, 220, 300, 30);

        JCheckBox cb1 = new JCheckBox("Whipped Cream (₹30)");
        JCheckBox cb2 = new JCheckBox("Caramel Drizzle (₹25)");
        JCheckBox cb3 = new JCheckBox("Extra Espresso Shot (₹40)");

        JCheckBox[] addOns = {cb1, cb2, cb3};
        int y = 250;
        for (JCheckBox cb : addOns) {
            cb.setBounds(150, y, 300, 30);
            cb.setFont(inputFont);
            cb.setOpaque(false);
            y += 30;
            cb.addItemListener(e -> {
                if (cb.isSelected()) addOnPrice += getAddOnPrice(cb);
                else addOnPrice -= getAddOnPrice(cb);
                updateTotalLabel();
            });
        }

        totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(0x1E8449));
        totalLabel.setBounds(150, 350, 300, 30);

        JButton orderButton = new JButton("Place Order");
        orderButton.setBounds(400, 400, 180, 40);
        orderButton.setFont(inputFont);
        orderButton.setBackground(new Color(30, 130, 76));
        orderButton.setForeground(Color.WHITE);
        orderButton.setBorder(new LineBorder(Color.WHITE, 2));

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 400, 180, 40);
        backButton.setFont(inputFont);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));

        coffeeBox.addActionListener(e -> {
            coffeePrice = getCoffeePrice((String) coffeeBox.getSelectedItem());
            updateTotalLabel();
        });

        ActionListener typeListener = e -> {
            typePrice = hot.isSelected() ? 0 : 20;
            updateTotalLabel();
        };
        hot.addActionListener(typeListener);
        cold.addActionListener(typeListener);

        orderButton.addActionListener(e -> {
            LinkedHashMap<String, Double> items = new LinkedHashMap<>();
            items.put("Coffee", coffeePrice);
            items.put("Type (Hot/Cold)", typePrice);

            for (JCheckBox cb : addOns) {
                if (cb.isSelected()) {
                    items.put(cb.getText(), getAddOnPrice(cb));
                }
            }

            dispose();
            new viewbill(items,username);  // ✅ Updated to match your viewbill.java
        });

        backButton.addActionListener(e -> {
            dispose();
            new foodiepalace(""); // Make sure foodiepalace.java exists
        });

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(245, 240, 230));
        c.add(title);
        c.add(typeLabel);
        c.add(hot);
        c.add(cold);
        c.add(coffeeLabel);
        c.add(coffeeBox);
        c.add(addOnLabel);
        for (JCheckBox cb : addOns) c.add(cb);
        c.add(totalLabel);
        c.add(orderButton);
        c.add(backButton);

        setTitle("Starbucks Order App");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double getAddOnPrice(JCheckBox cb) {
        return switch (cb.getText()) {
            case "Whipped Cream (₹30)" -> 30;
            case "Caramel Drizzle (₹25)" -> 25;
            case "Extra Espresso Shot (₹40)" -> 40;
            default -> 0;
        };
    }

    private double getCoffeePrice(String coffee) {
        return switch (coffee) {
            case "Caffè Latte" -> 160;
            case "Caramel Macchiato" -> 180;
            case "Mocha" -> 170;
            case "Flat White" -> 175;
            case "Cold Brew" -> 200;
            default -> 0;
        };
    }

    private void updateTotalLabel() {
        double total = coffeePrice + typePrice + addOnPrice;
        totalLabel.setText("Total: ₹" + total);
    }

    public static void main(String[] args) {
        new starbucks("");
    }
}
