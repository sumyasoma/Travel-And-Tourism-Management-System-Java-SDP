package travel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.sql.*;

public class AddHotel extends JFrame implements ActionListener {

    JPanel headerPanel;
    JTextField t1, t2, t3, t4, t5;
    JLabel l1, l2, l3, l4, l5, l6, l7, filename;
    JButton b1, b2, b3;
    File file;
    HotelPanel hp;
    HotelDetails hd;
    String name;
    byte[] photo = null;

    AddHotel() {
        // Frame properties
        setSize(750, 612);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#f8f9fa")); // Light background
        setLayout(null);

        // Header panel
        headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#343a40")); // Dark header
        headerPanel.setBounds(0, 0, 750, 50);
        headerPanel.setLayout(null);
        add(headerPanel);

        l1 = new JLabel("Add Hotel");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setBounds(0, 5, 750, 50);
        headerPanel.add(l1);

        // Labels
        l2 = createLabel("Hotel Name:", 60, 80);
        l4 = createLabel("City/State Name:", 60, 130);
        l5 = createLabel("AC Cost Per Day:", 60, 180);
        l6 = createLabel("Food Cost Per Day:", 60, 230);
        l7 = createLabel("Hotel Cost Per Day:", 60, 280);

        // Text fields
        t1 = createTextField(270, 80);
        t2 = createTextField(270, 130);
        t3 = createTextField(270, 180);
        t4 = createTextField(270, 230);
        t5 = createTextField(270, 280);

        // Image preview
        l3 = new JLabel();
        l3.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        l3.setBounds(60, 335, 220, 220);
        Image placeholderImage = new ImageIcon(ClassLoader.getSystemResource("Travel/Management/System/icons/hotelicon.jpeg"))
                .getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        l3.setIcon(new ImageIcon(placeholderImage));
        add(l3);

        // Buttons
        b1 = createButton("Choose File", 290, 425, "#6c757d"); // Grey button
        b2 = createButton("Add", 578, 525, "#28a745"); // Green button
        b3 = createButton("Update", 578, 525, "#007bff"); // Blue button
        b3.setVisible(false);

        filename = new JLabel("No file chosen");
        filename.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filename.setBounds(290, 470, 550, 32);
        add(filename);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setBounds(x, y, 200, 40);
        add(label);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setBounds(x, y, 380, 40);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        add(textField);
        return textField;
    }

    private JButton createButton(String text, int x, int y, String colorHex) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(Color.decode(colorHex));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBounds(x, y, 140, 38);
        button.setFocusable(false);
        button.addActionListener(this); // Ensure buttons remain functional
        add(button);
        return button;
    }

    public AddHotel(HotelPanel hp) {
        this();
        this.hp = hp;
    }

    public AddHotel(HotelDetails hd, String name) {
        this();
        this.hd = hd;
        this.name = name;
        l1.setText("Edit Hotel");
        b2.setVisible(false);
        b3.setVisible(true);
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM hotel WHERE name='" + name + "'";
            PreparedStatement ps = conn.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t1.setEditable(false);
                t1.setBackground(Color.WHITE);
                t1.setText(rs.getString(1));
                t2.setText(rs.getString(2));
                t3.setText(rs.getString(3));
                t4.setText(rs.getString(4));
                t5.setText(rs.getString(5));
                byte[] photo = rs.getBytes(6);
                Image i1 = new ImageIcon(photo).getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
                l3.setIcon(new ImageIcon(i1));
            }
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            FileDialog fd = new FileDialog(this, "Choose a Hotel Image", FileDialog.LOAD);
            fd.setFile("*.jpeg;*.jpg;*.png");
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename != null) {
                file = new File(fd.getDirectory() + filename);
                try {
                    Image image = ImageIO.read(file).getScaledInstance(220, 220, Image.SCALE_SMOOTH);
                    l3.setIcon(new ImageIcon(image));
                    this.filename.setText(file.getName());

                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                        bos.write(buf, 0, readNum);
                    }
                    photo = bos.toByteArray();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == b2) {
            try {
                Conn conn = new Conn();
                String sql = "INSERT INTO hotel VALUES(?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.c.prepareStatement(sql);
                ps.setString(1, t1.getText());
                ps.setString(2, t2.getText());
                ps.setString(3, t3.getText());
                ps.setString(4, t4.getText());
                ps.setString(5, t5.getText());
                ps.setBytes(6, photo);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Hotel Added Successfully!");
                hp.createtablemodel();
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == b3) {
            try {
                Conn conn = new Conn();
                String sql = "UPDATE hotel SET state=?, ac=?, food=?, hotelcost=?, image=? WHERE name=?";
                PreparedStatement ps = conn.c.prepareStatement(sql);
                ps.setString(1, t2.getText());
                ps.setString(2, t3.getText());
                ps.setString(3, t4.getText());
                ps.setString(4, t5.getText());
                ps.setBytes(5, photo);
                ps.setString(6, name);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Hotel Updated Successfully!");
                hd.display();
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
