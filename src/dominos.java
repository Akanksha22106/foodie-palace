import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class dominos extends JFrame {

    double pizzaPrice = 0, sizePrice = 0, crustPrice = 0, addOnPrice = 0;
    JLabel totalLabel;

    public dominos(String username) {
        Font titleFont = new Font("Verdana", Font.BOLD, 40);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 18);

        JLabel title = new JLabel("Domino's Pizza");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);
        title.setBounds(220, 10, 500, 50);

        JLabel l1 = new JLabel("Choose Category:");
        l1.setFont(labelFont);
        l1.setForeground(Color.WHITE);
        l1.setBounds(150, 70, 250, 30);

        JRadioButton r1 = new JRadioButton("Veg Pizza");
        JRadioButton r2 = new JRadioButton("Non-Veg Pizza");
        r1.setFont(inputFont);
        r2.setFont(inputFont);
        r1.setBounds(150, 100, 120, 30);
        r2.setBounds(300, 100, 140, 30);
        r1.setOpaque(false);
        r2.setOpaque(false);
        r1.setForeground(Color.WHITE);
        r2.setForeground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(r1);
        group.add(r2);

        JComboBox<String> pizzaBox = new JComboBox<>(new String[]{
                "Veg Extravaganza", "Peppy Paneer", "Chicken Dominator", "Indi Chicken Tikka"
        });
        pizzaBox.setFont(inputFont);
        pizzaBox.setBounds(150, 140, 500, 30);

        JComboBox<String> sizeBox = new JComboBox<>(new String[]{
                "Regular", "Medium", "Large"
        });
        sizeBox.setFont(inputFont);
        sizeBox.setBounds(150, 210, 200, 30);

        JLabel lSize = new JLabel("Choose Size:");
        lSize.setFont(labelFont);
        lSize.setForeground(Color.WHITE);
        lSize.setBounds(150, 180, 250, 30);

        JLabel l2 = new JLabel("Choose Crust:");
        l2.setFont(labelFont);
        l2.setForeground(Color.WHITE);
        l2.setBounds(150, 250, 250, 30);

        JComboBox<String> crustBox = new JComboBox<>(new String[]{
                "Classic Hand Tossed", "Cheese Burst", "Wheat Thin Crust"
        });
        crustBox.setFont(inputFont);
        crustBox.setBounds(150, 280, 500, 30);

        JLabel l4 = new JLabel("Add-ons:");
        l4.setFont(labelFont);
        l4.setForeground(Color.WHITE);
        l4.setBounds(150, 320, 200, 30);

        JCheckBox cb1 = new JCheckBox("Extra Cheese (₹40)");
        JCheckBox cb2 = new JCheckBox("Chicken Sausage (₹60)");
        JCheckBox cb3 = new JCheckBox("Jalapeno & Olive (₹35)");
        JCheckBox cb4 = new JCheckBox("Onion & Capsicum (₹30)");

        JCheckBox[] toppings = {cb1, cb2, cb3, cb4};
        int y = 350;
        for (JCheckBox cb : toppings) {
            cb.setBounds(150, y, 300, 30);
            cb.setFont(inputFont);
            cb.setOpaque(false);
            cb.setForeground(Color.WHITE);
            y += 30;

            cb.addItemListener(e -> {
                if (cb.isSelected()) {
                    addOnPrice += getAddOnPrice(cb);
                } else {
                    addOnPrice -= getAddOnPrice(cb);
                }
                updateTotalLabel();
            });
        }

        totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(Color.YELLOW);
        totalLabel.setBounds(150, 470, 300, 30);

        JButton orderButton = new JButton("Place Order");
        orderButton.setBounds(420, 500, 200, 40);
        orderButton.setFont(inputFont);
        orderButton.setBackground(new Color(220, 20, 60));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFocusPainted(false);
        orderButton.setBorder(new LineBorder(Color.WHITE, 2));

        JButton backButton = new JButton("Back");
        backButton.setBounds(170, 500, 200, 40);
        backButton.setFont(inputFont);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(0, 51, 102));
        c.add(title); c.add(l1); c.add(r1); c.add(r2);
        c.add(pizzaBox); c.add(lSize); c.add(sizeBox);
        c.add(l2); c.add(crustBox); c.add(l4);
        for (JCheckBox cb : toppings) c.add(cb);
        c.add(orderButton); c.add(backButton);
        c.add(totalLabel);

        pizzaBox.addActionListener(e -> {
            pizzaPrice = getPizzaPrice((String) pizzaBox.getSelectedItem());
            updateTotalLabel();
        });

        sizeBox.addActionListener(e -> {
            sizePrice = getSizePrice((String) sizeBox.getSelectedItem());
            updateTotalLabel();
        });

        crustBox.addActionListener(e -> {
            crustPrice = getCrustPrice((String) crustBox.getSelectedItem());
            updateTotalLabel();
        });

        orderButton.addActionListener(e -> {
            LinkedHashMap<String, Double> items = new LinkedHashMap<>();
            items.put("Pizza", pizzaPrice);
            items.put("Size", sizePrice);
            items.put("Crust", crustPrice);
            for (JCheckBox cb : toppings) {
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

        setTitle("Domino's Ordering App");
        setSize(800, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private double getAddOnPrice(JCheckBox cb) {
        return switch (cb.getText()) {
            case "Extra Cheese (₹40)" -> 40;
            case "Chicken Sausage (₹60)" -> 60;
            case "Jalapeno & Olive (₹35)" -> 35;
            case "Onion & Capsicum (₹30)" -> 30;
            default -> 0;
        };
    }

    private double getSizePrice(String size) {
        return switch (size) {
            case "Regular" -> 0;
            case "Medium" -> 50;
            case "Large" -> 100;
            default -> 0;
        };
    }

    private double getCrustPrice(String crust) {
        return switch (crust) {
            case "Classic Hand Tossed" -> 0;
            case "Cheese Burst" -> 80;
            case "Wheat Thin Crust" -> 50;
            default -> 0;
        };
    }

    private double getPizzaPrice(String pizza) {
        return switch (pizza) {
            case "Veg Extravaganza" -> 250;
            case "Peppy Paneer" -> 230;
            case "Chicken Dominator" -> 300;
            case "Indi Chicken Tikka" -> 320;
            default -> 0;
        };
    }

    private void updateTotalLabel() {
        double total = pizzaPrice + sizePrice + crustPrice + addOnPrice;
        totalLabel.setText("Total: ₹" + total);
    }

    public static void main(String[] args) {
        new dominos("");
    }
}
