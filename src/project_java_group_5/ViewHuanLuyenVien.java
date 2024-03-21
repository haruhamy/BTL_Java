package project_java_group_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewHuanLuyenVien extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtNationality, txtBirthDate, txtExperience;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnSave, btnLoad, btnIn4;         
    
    public ViewHuanLuyenVien() {
        setTitle("Quản lý Huấn luyện viên");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Tên", "Quốc tịch", "Ngày sinh", "Vai trò", "Số năm kinh nghiệm"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form setup
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Tên:"));
        txtName = new JTextField();
        panel.add(txtName);
        panel.add(new JLabel("Quốc tịch:"));
        txtNationality = new JTextField();
        panel.add(txtNationality);
        panel.add(new JLabel("Ngày sinh:"));
        txtBirthDate = new JTextField();
        panel.add(txtBirthDate);

        String[] roles = {"HLV trưởng", "trợ lý HLV", "HLV cho thủ môn", "HLV chuyên về thể lực", "giám đốc kỹ thuật", "bộ phận y tế"};
        cbRole = new JComboBox<>(roles);
        panel.add(new JLabel("Vai trò:"));
        panel.add(cbRole);
        
        panel.add(new JLabel("Số năm kinh nghiệm:"));
        txtExperience = new JTextField();
        panel.add(txtExperience);

        // Buttons setup
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        btnIn4 = new JButton("Information");               

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSave);
        btnPanel.add(btnLoad);
        btnPanel.add(btnIn4);

        add(panel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

        // Button functionalities
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.addCoach(txtName, txtNationality, txtBirthDate, txtExperience, cbRole, model);
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.updateCoach(table, txtName, txtNationality, txtBirthDate, txtExperience, cbRole, model);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.deleteCoach(table, model);
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.saveData(model);
            }
        });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.loadData(table);
            }
        });
        JButton btnBack = new JButton("Back");
        btnPanel.add(btnBack); // Add the button to the panel containing other buttons

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Assuming you have a method called displayMainMenu() in your main menu class
                new Menu().setVisible(true); // Adjust this call as per your main menu display method
            }
        });
        
        btnIn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.in4(table, model);
            }
        });
    }                          
    
    public static void view() {
        EventQueue.invokeLater(() -> {
            try {
                ViewHuanLuyenVien frame = new ViewHuanLuyenVien();
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
}
