package travel.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class Signup extends JPanel implements ActionListener {

    JPanel p1, p2;
    JTextField t1, t2, t3, t4;
    JButton b1, b2;
    JComboBox<String> c1;
    JLabel l5;

    public Signup() {
        setSize(1518, 800);
        setBackground(new Color(240, 248, 255));
        setLayout(null);

        p1 = new JPanel();
        p1.setBounds(440, 230, 420, 330);
        p1.setBackground(new Color(180, 200, 255));
        p1.setBorder(BorderFactory.createLineBorder(new Color(100, 120, 255), 3));
        p1.setLayout(null);
        add(p1);

        p2 = new JPanel();
        p2.setBounds(900, 200, 300, 350);
        p2.setBackground(Color.WHITE);
        p2.setLayout(null);
        add(p2);

        JLabel l = new JLabel("Username:");
        l.setForeground(new Color(70, 70, 70));
        l.setFont(new Font("Tahoma", Font.BOLD, 14));
        l.setBounds(30, 30, 140, 30);
        p1.add(l);

        JLabel ll = new JLabel("Name:");
        ll.setForeground(new Color(70, 70, 70));
        ll.setFont(new Font("Tahoma", Font.BOLD, 14));
        ll.setBounds(30, 80, 140, 30);
        p1.add(ll);

        JLabel l2 = new JLabel("Password:");
        l2.setForeground(new Color(70, 70, 70));
        l2.setFont(new Font("Tahoma", Font.BOLD, 14));
        l2.setBounds(30, 130, 140, 30);
        p1.add(l2);

        JLabel l3 = new JLabel("Security Question:");
        l3.setForeground(new Color(70, 70, 70));
        l3.setFont(new Font("Tahoma", Font.BOLD, 14));
        l3.setBounds(30, 180, 140, 30);
        p1.add(l3);

        JLabel l4 = new JLabel("Answer:");
        l4.setForeground(new Color(70, 70, 70));
        l4.setFont(new Font("Tahoma", Font.BOLD, 14));
        l4.setBounds(30, 230, 140, 30);
        p1.add(l4);

        l5 = new JLabel("CREATE ACCOUNT");
        l5.setBounds(540, 150, 500, 50);
        l5.setFont(new Font("Segoe UI", Font.BOLD, 40));
        l5.setForeground(new Color(100, 149, 237));
        add(l5);

        c1 = new JComboBox<>(new String[]{
            "Your Nickname?", "Your Lucky Number?",
            "Your Favorite Anime?", "Your Childhood Name?"
        });
        c1.setBounds(190, 185, 180, 25);
        c1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        c1.setBackground(Color.WHITE);
        p1.add(c1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Travel/Management/System/icons/signup.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l6 = new JLabel(i3);
        l6.setBounds(0, 25, 300, 300);
        p2.add(l6);

        t1 = new JTextField();
        t1.setBounds(190, 35, 180, 25);
        t1.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        t1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        p1.add(t1);

        t2 = new JTextField();
        t2.setBounds(190, 85, 180, 25);
        t2.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        t2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        p1.add(t2);

        t3 = new JTextField();
        t3.setBounds(190, 135, 180, 25);
        t3.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        t3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        p1.add(t3);

        t4 = new JTextField();
        t4.setBounds(190, 235, 180, 25);
        t4.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211)));
        t4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        p1.add(t4);

        b1 = new JButton("Create");
        b1.setBounds(80, 275, 120, 30);
        b1.setFont(new Font("Tahoma", Font.BOLD, 14));
        b1.setBackground(new Color(100, 149, 237));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.addActionListener(this);
        p1.add(b1);

        b2 = new JButton("Login");
        b2.setBounds(210, 275, 120, 30);
        b2.setFont(new Font("Tahoma", Font.BOLD, 14));
        b2.setBackground(new Color(60, 179, 113));
        b2.setForeground(Color.WHITE);
        b2.setFocusPainted(false);
        b2.addActionListener(this);
        p1.add(b2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            Conn con = new Conn();

            if (ae.getSource() == b1) {
                String password = t3.getText();

                // Validate the password
                if (!PasswordChecker.isIdealPassword(password)) {
                    String feedback = PasswordChecker.getValidationFeedback(password);
                    JOptionPane.showMessageDialog(null, feedback, "Password Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String sql = "INSERT INTO account(username, name, password, security, answer) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement st = con.c.prepareStatement(sql);

                st.setString(1, t1.getText());
                st.setString(2, t2.getText());
                st.setString(3, password);
                st.setString(4, (String) c1.getSelectedItem());
                st.setString(5, t4.getText());

                String pr = "SELECT * FROM account WHERE username=?";
                PreparedStatement st1 = con.c.prepareStatement(pr);
                st1.setString(1, t1.getText());

                ResultSet rs = st1.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username Already Exists!!!");
                } else {
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Account Created Successfully");

                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                }
            } else if (ae.getSource() == b2) {
                Login n = new Login();
                l5.setVisible(false);
                p1.setVisible(false);
                p2.setVisible(false);
                n.setFocusable(true);
                this.add(n);
                n.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PasswordChecker {
        public static boolean isIdealPassword(String password) {
            boolean hasMinLength = password.length() >= 8;
            boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
            boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
            boolean hasDigit = password.chars().anyMatch(Character::isDigit);
            boolean hasSpecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
            return hasMinLength && hasUpper && hasLower && hasDigit && hasSpecial;
        }

        public static String getValidationFeedback(String password) {
            StringBuilder feedback = new StringBuilder("Password must:\n");
            feedback.append(String.format(" - %s\n", password.length() >= 8 ? "✔ Be at least 8 characters long" : "✘ Be at least 8 characters long"));
            feedback.append(String.format(" - %s\n", password.chars().anyMatch(Character::isUpperCase) ? "✔ Include at least one uppercase letter" : "✘ Include at least one uppercase letter"));
            feedback.append(String.format(" - %s\n", password.chars().anyMatch(Character::isLowerCase) ? "✔ Include at least one lowercase letter" : "✘ Include at least one lowercase letter"));
            feedback.append(String.format(" - %s\n", password.chars().anyMatch(Character::isDigit) ? "✔ Include at least one digit" : "✘ Include at least one digit"));
            feedback.append(String.format(" - %s\n", password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch)) ? "✔ Include at least one special character" : "✘ Include at least one special character"));
            return feedback.toString();
        }
    }
}
