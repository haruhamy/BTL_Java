package project_java_group_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViewHuanLuyenVien extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtNationality, txtBirthDate, txtExperience;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnSave, btnLoad;   
    
    public static boolean isRealNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(str);
            if (x > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean containsNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true; // Tìm thấy số, trả về true
            }
        }
        // Kiểm tra ký tự đặc biệt, cho phép dấu cách
        String specialCharactersPattern = "[^a-zA-Z0-9\\s]";
        boolean containsSpecialCharacter = str.matches(".*" + specialCharactersPattern + ".*");

        return containsSpecialCharacter; // Trả về true nếu có ký tự đặc biệt, nghĩa là chuỗi không hợp lệ
    }

    public static String normalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        String[] words = name.trim().split("\\s+"); // Tách chuỗi dựa trên một hoặc nhiều dấu cách
        StringBuilder normalized = new StringBuilder();

        for (String word : words) {
            String normalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            normalized.append(normalizedWord).append(" ");
        }

        return normalized.toString().trim(); // Loại bỏ dấu cách thừa ở cuối chuỗi trước khi trả về
    }

    public static String chuanhoa(String s) {
        String[] arr = s.split("/");
        String ans = String.format("%02d", Integer.parseInt(arr[0])) + "/";
        ans += String.format("%02d", Integer.parseInt(arr[1])) + "/" + arr[2];
        return ans;
    }

    private boolean hasHeadCoach(int excludeRowIndex) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (i == excludeRowIndex) {
                continue; // Bỏ qua dòng hiện tại
            }
            String role = model.getValueAt(i, 3).toString(); // Giả sử cột 3 chứa thông tin về vai trò
            if ("HLV trưởng".equals(role)) {
                return true; // Đã tìm thấy HLV trưởng trong danh sách
            }
        }
        return false; // Không tìm thấy HLV trưởng
    }


    
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

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSave);
        btnPanel.add(btnLoad);

        add(panel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

        // Button functionalities
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCoach();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCoach();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCoach();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
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

    }

    private void addCoach() {
        String name = txtName.getText().trim();
        String nationality = txtNationality.getText().trim();
        String birthDate = txtBirthDate.getText().trim();
        
        String qualifications = cbRole.getSelectedItem().toString();
        
        String experience = txtExperience.getText().trim();
        DateValidator validator = new DateValidator("dd/MM/uuuu");
        // Validate the input data
        if (name.isEmpty() || nationality.isEmpty() || birthDate.isEmpty() || qualifications.isEmpty()
                || experience.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (containsNumber(name) || containsNumber(nationality)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hoặc quốc tịch hợp lệ", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("HLV trưởng".equals(qualifications) && hasHeadCoach(0)) {
            JOptionPane.showMessageDialog(this, "Đã có HLV trưởng, không thể thêm thêm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng thực hiện nếu đã có HLV trưởng
        }

        if (!validator.isValid(chuanhoa(birthDate))) {
            JOptionPane.showMessageDialog(null, "Ngày tháng năm không hợp lệ", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isRealNumber(experience)) {
            JOptionPane.showMessageDialog(null, "Số năm kinh nghiệm chưa hợp lệ", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add data to the table model
        model.addRow(new Object[]{normalizeName(name), normalizeName(nationality), chuanhoa(birthDate), qualifications, experience});

        // Clear the input fields after adding
        txtName.setText("");
        txtNationality.setText("");
        txtBirthDate.setText("");
        
        txtExperience.setText("");
    }

    private void updateCoach() {
        DateValidator validator = new DateValidator("dd/MM/uuuu");
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Only update fields if the corresponding text field is not empty.
            
            String name = txtName.getText().trim();
            String nationality = txtNationality.getText().trim();
            String birthDate = txtBirthDate.getText().trim();
            String qualifications = cbRole.getSelectedItem().toString();

            
            
            String experience = txtExperience.getText().trim();
            
            if(name.isEmpty() && nationality.isEmpty() && birthDate.isEmpty() && qualifications.isEmpty() && experience.isEmpty()){
                JOptionPane.showMessageDialog(null, "Vui lòng nhập ít nhất 1 dữ liệu", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            if (!name.isEmpty()) {
                if (containsNumber(name)) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hợp lệ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.setValueAt(normalizeName(name), selectedRow, 0);
            }           

            if (!nationality.isEmpty()) {
                if (containsNumber(nationality)) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập quốc tịch hợp lệ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.setValueAt(normalizeName(nationality), selectedRow, 1);
            }           

            if (!birthDate.isEmpty()) {
                
                if (!validator.isValid(chuanhoa(birthDate))) {
                    JOptionPane.showMessageDialog(null, "Ngày tháng năm không hợp lệ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.setValueAt(chuanhoa(birthDate), selectedRow, 2);
            }

            if (!qualifications.isEmpty()) {
                boolean existingHeadCoach = hasHeadCoach(selectedRow); // Truyền chỉ số dòng hiện tại
                String currentRole = selectedRow >= 0 ? model.getValueAt(selectedRow, 3).toString() : "";

                // Kiểm tra nếu vai trò mới là HLV trưởng và đã có HLV trưởng khác trong danh sách
                if ("HLV trưởng".equals(qualifications) && existingHeadCoach && !"HLV trưởng".equals(currentRole)) {
                    JOptionPane.showMessageDialog(this, "Đã có HLV trưởng, không thể cập nhật thành HLV trưởng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Dừng thực hiện nếu cố gắng cập nhật vai trò thành HLV trưởng khi đã có một HLV trưởng
                }
                model.setValueAt(qualifications, selectedRow, 3);
            }

            if (!experience.isEmpty()) {
                if (!isRealNumber(experience)) {
                    JOptionPane.showMessageDialog(null, "Số năm kinh nghiệm chưa hợp lệ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.setValueAt(experience, selectedRow, 4);
            }

            JOptionPane.showMessageDialog(this, "Coach information updated successfully.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a coach to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCoach() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Remove the selected row from the model
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Coach information deleted successfully.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // No row is selected
            JOptionPane.showMessageDialog(this, "Please select a coach to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData() {
        File file = new File("coach.csv");
        try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    bw.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) { // Avoid adding comma at the end of the line
                        bw.write(",");
                    }
                }
                bw.newLine(); // Move to the next line after writing all columns of the current row
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + file.getAbsolutePath(), "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("coach.csv"))) {
            String line;
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing data
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
