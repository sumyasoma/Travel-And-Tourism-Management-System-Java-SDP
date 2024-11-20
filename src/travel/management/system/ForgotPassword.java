package travel.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class ForgotPassword extends JPanel implements ActionListener {

    JPanel p1, p2;
    JTextField t1, t2, t3, t4, t5;
    JButton b1, b2, b3;
    JLabel l6;

    public static void main(String[] args) {
        new ForgotPassword().setVisible(true);
    }

    ForgotPassword() {
        setSize(1518, 800);
        setBackground(Color.WHITE);
        setLayout(null);

        // Header Label
        l6 = new JLabel("FORGOT PASSWORD");
        l6.setBounds(550, 160, 400, 50);
        l6.setFont(new Font("Tahoma", Font.BOLD, 36));
        l6.setForeground(new Color(128, 0, 128)); // Modern purple color
        l6.setHorizontalAlignment(SwingConstants.CENTER);
        add(l6);

        // Panel for Form
        p1 = new JPanel();
        p1.setBounds(440, 230, 465, 275);
        p1.setBackground(new Color(245, 245, 245)); // Subtle gray background
        p1.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 128), 2)); // Border for better look
        p1.setLayout(null);
        add(p1);

        // Panel for Image
        p2 = new JPanel();
        p2.setBounds(905, 200, 315, 330);
        p2.setBackground(Color.WHITE);
        p2.setLayout(null);
        add(p2);

        // Labels
        JLabel l1 = new JLabel("Username:");
        l1.setFont(new Font("Tahoma", Font.BOLD, 14));
        l1.setBounds(30, 20, 165, 30);
        p1.add(l1);

        JLabel l2 = new JLabel("Name:");
        l2.setFont(new Font("Tahoma", Font.BOLD, 14));
        l2.setBounds(30, 60, 165, 30);
        p1.add(l2);

        JLabel l3 = new JLabel("Security Question:");
        l3.setFont(new Font("Tahoma", Font.BOLD, 14));
        l3.setBounds(30, 100, 165, 30);
        p1.add(l3);

        JLabel l4 = new JLabel("Answer:");
        l4.setFont(new Font("Tahoma", Font.BOLD, 14));
        l4.setBounds(30, 140, 165, 30);
        p1.add(l4);

        JLabel l5 = new JLabel("Password:");
        l5.setFont(new Font("Tahoma", Font.BOLD, 14));
        l5.setBounds(30, 180, 165, 30);
        p1.add(l5);

        // Text Fields
        t1 = new JTextField();
        t1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t1.setBounds(205, 25, 160, 25);
        t1.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169)));
        p1.add(t1);

        t2 = new JTextField();
        t2.setEditable(false);
        t2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t2.setBounds(205, 65, 160, 25);
        t2.setBackground(Color.WHITE);
        t2.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169)));
        p1.add(t2);

        t3 = new JTextField();
        t3.setEditable(false);
        t3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t3.setBounds(205, 105, 160, 25);
        t3.setBackground(Color.WHITE);
        t3.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169)));
        p1.add(t3);

        t4 = new JTextField();
        t4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t4.setBounds(205, 145, 160, 25);
        t4.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169)));
        p1.add(t4);

        t5 = new JTextField();
        t5.setEditable(false);
        t5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t5.setBounds(205, 185, 160, 25);
        t5.setBackground(Color.WHITE);
        t5.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169)));
        p1.add(t5);

        // Image
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("Travel/Management/System/icons/forgot password.jpeg"));
        Image i1 = c1.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        JLabel lImage = new JLabel(new ImageIcon(i1));
        lImage.setBounds(30, 30, 256, 256);
        p2.add(lImage);

        // Buttons
        b1 = new JButton("Search");
        b1.addActionListener(this);
        b1.setFont(new Font("Tahoma", Font.BOLD, 12));
        b1.setBounds(370, 27, 90, 25);
        b1.setBackground(new Color(128, 0, 128));
        b1.setForeground(Color.WHITE);
        p1.add(b1);

        b2 = new JButton("Retrieve");
        b2.addActionListener(this);
        b2.setFont(new Font("Tahoma", Font.BOLD, 12));
        b2.setBounds(370, 146, 90, 25);
        b2.setBackground(new Color(128, 0, 128));
        b2.setForeground(Color.WHITE);
        p1.add(b2);

        b3 = new JButton("Back");
        b3.addActionListener(this);
        b3.setFont(new Font("Tahoma", Font.BOLD, 13));
        b3.setBounds(150, 230, 100, 25);
        b3.setBackground(new Color(128, 0, 128));
        b3.setForeground(Color.WHITE);
        p1.add(b3);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn con = new Conn();
            if (ae.getSource() == b1) {
                String sql = "select * from account where username=?";
                PreparedStatement st = con.c.prepareStatement(sql);
                st.setString(1, t1.getText());
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    t2.setText(rs.getString("name"));
                    t3.setText(rs.getString("security"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username!!!");
                }
            } else if (ae.getSource() == b2) {
                String sql = "select * from account where username=? and answer=?";
                PreparedStatement st = con.c.prepareStatement(sql);
                st.setString(1, t1.getText());
                st.setString(2, t4.getText());
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    t5.setText(rs.getString("password"));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Answer!!!");
                }
            } else if (ae.getSource() == b3) {
                Login n = new Login();
                l6.setVisible(false);
                p1.setVisible(false);
                p2.setVisible(false);
                n.setFocusable(true);
                add(n);
                n.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
