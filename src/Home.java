import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Home extends JFrame {
    Home(String username) {
        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Home");
    }

    public static void main(String[] args) {
        new Home("riya");
    }
}
