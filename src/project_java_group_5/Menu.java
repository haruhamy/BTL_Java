package project_java_group_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {
    public Menu() {
        initComponents();
       
    }

    public void initComponents() {
    try {
        // Set a modern look and feel
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }

    setTitle("Menu");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 200);
    setLocationRelativeTo(null);
    
    // Using BorderLayout for better arrangement
    setLayout(new BorderLayout(10, 10)); // Horizontal and vertical gaps

    JButton btnCauThu = new JButton("Cầu thủ");
    JButton btnHuanLuyenVien = new JButton("Huấn luyện viên");

    // Customize buttons
    btnCauThu.setBackground(new Color(100, 100, 255)); // Example color
    btnCauThu.setForeground(Color.WHITE);
    btnCauThu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi người dùng chọn "Cầu thủ"
                ViewCauThu.view();
                dispose();
            }
        });
    
    btnHuanLuyenVien.setBackground(new Color(255, 100, 100)); // Example color
    btnHuanLuyenVien.setForeground(Color.WHITE);
    btnHuanLuyenVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewHuanLuyenVien.view();   
                dispose();
            }
        });
    
    // Add padding around buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Grid with gaps
    buttonPanel.add(btnCauThu);
    buttonPanel.add(btnHuanLuyenVien);
    buttonPanel.setBorder(new EmptyBorder(10, 30, 10, 30)); // Padding

    add(buttonPanel, BorderLayout.CENTER);

    setVisible(true);
}

}
