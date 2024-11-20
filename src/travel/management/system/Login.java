package travel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JPanel implements ActionListener {
    
    JPanel p1, p2;
    JTextField t1;
    JPasswordField passwordField;
    JCheckBox showPassword;
    JButton b1, b2, b3;
    Home home;
    JLabel l5;

    Login() {

        setSize(1540, 800);
        setBackground(new Color(240, 248, 255)); // Light blue background
        setLayout(null);

        p1 = new JPanel();
        p1.setBackground(new Color(255, 239, 213)); // Peach puff
        p1.setBounds(440, 230, 360, 320);
        p1.setLayout(null);
        p1.setBorder(BorderFactory.createLineBorder(new Color(205, 92, 92), 2)); // Dark red border
        add(p1);

        p2 = new JPanel();
        p2.setBounds(850, 220, 300, 300);
        p2.setBackground(new Color(255, 248, 220)); // Cornsilk
        p2.setLayout(null);
        p2.setBorder(BorderFactory.createLineBorder(new Color(46, 139, 87), 2)); // Green border
        add(p2);

        JLabel l1 = new JLabel("Username");
        l1.setBounds(30, 20, 100, 25);
        l1.setFont(new Font("SansSerif", Font.BOLD, 16));
        l1.setForeground(new Color(70, 130, 180)); // Steel blue
        p1.add(l1);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(30, 90, 100, 25);
        l2.setFont(new Font("SansSerif", Font.BOLD, 16));
        l2.setForeground(new Color(70, 130, 180)); // Steel blue
        p1.add(l2);

        t1 = new JTextField();
        t1.setBounds(30, 55, 280, 25);
        t1.setBorder(BorderFactory.createLineBorder(new Color(176, 196, 222), 1)); // Light steel blue
        t1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p1.add(t1);

        passwordField = new JPasswordField();
        passwordField.setBounds(30, 125, 280, 25);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(176, 196, 222), 1)); // Light steel blue
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p1.add(passwordField);

        // Show Password Checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(30, 155, 150, 25);
        showPassword.setBackground(new Color(255, 239, 213)); // Peach puff background
        showPassword.setFont(new Font("SansSerif", Font.ITALIC, 12));
        showPassword.setFocusPainted(false);
        showPassword.addActionListener(this);
        p1.add(showPassword);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Travel/Management/System/icons/login.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 300, 300);
        p2.add(l3);

        l5 = new JLabel("LOGIN");
        l5.setBounds(600, 160, 400, 50);
        l5.setFont(new Font("Times New Roman", Font.BOLD, 42));
        l5.setForeground(new Color(138, 43, 226)); // Blue Violet
        add(l5);

        b1 = new JButton("Login");
        b1.setBounds(30, 190, 120, 30);
        b1.setBackground(new Color(135, 206, 250)); // Sky blue
        b1.setForeground(new Color(25, 25, 112)); // Midnight blue
        b1.setFont(new Font("SansSerif", Font.BOLD, 14));
        b1.addActionListener(this);
        p1.add(b1);

        b2 = new JButton("Sign Up");
        b2.setBounds(190, 190, 120, 30);
        b2.setBackground(new Color(240, 128, 128)); // Light coral
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("SansSerif", Font.BOLD, 14));
        b2.addActionListener(this);
        p1.add(b2);

        b3 = new JButton("Forgot Password?");
        b3.setBounds(100, 240, 160, 30);
        b3.setBackground(new Color(255, 160, 122)); // Light salmon
        b3.setForeground(new Color(139, 69, 19)); // Saddle brown
        b3.setFont(new Font("SansSerif", Font.BOLD, 12));
        b3.addActionListener(this);
        p1.add(b3);
    }

    public Login(Home h) {
        this();
        this.home = h;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        } else if (ae.getSource() == b1) {
            try {
                Conn con = new Conn();
                String sql = "select * from account where username=? and password=?";
                PreparedStatement st = con.c.prepareStatement(sql);

                st.setString(1, t1.getText());
                st.setString(2, passwordField.getText());

                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    this.setVisible(false);
                    new CustomerHome(t1.getText()).setVisible(true);
                    home.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!!!");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (ae.getSource() == b2) {
            Signup s = new Signup();
            l5.setVisible(false);
            p1.setVisible(false);
            p2.setVisible(false);
            s.setFocusable(true);
            this.add(s);
            s.setVisible(true);

        } else if (ae.getSource() == b3) {
            ForgotPassword f = new ForgotPassword();
            l5.setVisible(false);
            p1.setVisible(false);
            p2.setVisible(false);
            f.setFocusable(true);
            this.add(f);
            f.setVisible(true);
        }
    }
}
