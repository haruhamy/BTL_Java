package project_java_group_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewHuanLuyenVien extends JFrame {
    
    //Setup
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtNationality, txtBirthDate, txtExperience;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnSave, btnLoad, btnIn4, btnBack;         
    private JPanel btnPanel, panel;
    
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
        panel = new JPanel(new GridLayout(0, 2));
        
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
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Thay đổi");
        btnDelete = new JButton("Xóa");
        btnSave = new JButton("Lưu");
        btnLoad = new JButton("Hiện dữ liệu");
        btnIn4 = new JButton("Chi tiết lương thưởng");               
        btnBack = new JButton("Quay lại");       
        
        btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSave);
        btnPanel.add(btnLoad);
        btnPanel.add(btnIn4);
        btnPanel.add(btnBack); 

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
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                if (Login.phanQuyen() == 2) {
                    Login.view();
                    Login.setFlag(9);
                } else {
                    new Menu().setVisible(true); // Call the main menu display method
                }
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
