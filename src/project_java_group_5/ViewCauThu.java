package project_java_group_5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewCauThu {

    private static void saveTableModelToFile(DefaultTableModel model, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing data to file: " + e.getMessage());
        }
    }

    private static void displayPlayerStats(DefaultTableModel model, int rowIndex) {
        String playerName = (String) model.getValueAt(rowIndex, 0); // Lấy tên cầu thủ

        // Yêu cầu người dùng nhập điểm số cho 5 trận gần nhất
        String input = JOptionPane.showInputDialog(null, "Nhập điểm số của 5 trận gần nhất cho " + playerName + ", phân tách bằng dấu phẩy (,):");
        // Parse input và chuyển thành một mảng số nguyên
        int[] lastFiveMatchScores = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Tính toán số phút tham gia trung bình (giả sử là một giá trị cố định trong ví dụ này)
        int averageMinutesPlayed = 76;

        // Xây dựng và hiển thị thông điệp
        StringBuilder message = new StringBuilder("Cầu thủ: " + playerName + "\nĐiểm số 5 trận cuối: ");
        for (int score : lastFiveMatchScores) {
            message.append(score).append(", ");
        }
        message.append("\nSố phút tham gia trung bình trong 5 trận cuối: ").append(averageMinutesPlayed);

        JOptionPane.showMessageDialog(null, message.toString());
    }

    public static void view() {
        Object[][] data = null; // Initialize data outside the try block

        try {
            BufferedReader br = new BufferedReader(new FileReader("Data.csv"));
            String line = br.readLine();
            int rowCount = 0;

            // Count the number of lines in the file to determine the array size
            while (line != null) {
                rowCount++;
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader("Data.csv"));

            // Initialize the data array with the determined size
            data = new Object[rowCount][9];

            int rowIndex = 0;

            // Read data from the file and populate the array
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                data[rowIndex][0] = value[0]; // Ho va ten
                data[rowIndex][1] = value[1]; // Quoc tich
                data[rowIndex][2] = value[2]; // Gioi tinh
                data[rowIndex][3] = value[3]; // Ngay sinh
                data[rowIndex][4] = value[4]; // Ngay tham gia
                data[rowIndex][5] = value[5]; // Vi tri thi dau
                data[rowIndex][6] = value[6]; // So tran
                data[rowIndex][7] = value[7]; // So ban thang
                data[rowIndex][8] = value[8]; // Luong thoa thuan
                rowIndex++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame("Cau thu");

        String[] col = {"Name", "Quoc tich", "Gioi tinh", "Ngay Sinh", "Ngay tham gia", "Vi tri thi dau", "So tran",
            "So ban thang", "Luong thoa thuan"};

        DefaultTableModel model = new DefaultTableModel(data, col);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        // Use FlowLayout for the button panel with horizontal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));

        JButton addButton = new JButton("Add");
        JButton changeButton = new JButton("Change");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> {
            // Show a dialog to input new data
            JTextField nameField = new JTextField();
            JTextField nationalityField = new JTextField();
            JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});

            String[] days = new String[31];
            for (int i = 1; i <= 31; i++) {
                days[i - 1] = String.valueOf(i); // Fill the array with day numbers as strings
            }
            JComboBox<String> dayComboBox = new JComboBox<>(days);

            JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});

            String[] years = new String[61];
            for (int i = 0; i < 61; i++) {
                years[i] = String.valueOf(2024 - i); // Fill the array with day numbers as strings
            }
            JComboBox<String> yearComboBox = new JComboBox<>(years);

            JTextField joinDateField = new JTextField();
            JComboBox<String> positionComboBox = new JComboBox<>(new String[]{"Tien dao", "Hau ve", "Tien Ve"});
            JTextField matchField = new JTextField();
            JTextField goalField = new JTextField();
            JTextField salaryField = new JTextField();

            Object[] fields = {
                "Name:", nameField,
                "Quoc tich:", nationalityField,
                "Gioi tinh:", genderField,
                "Ngay Sinh:", dayComboBox,
                "Thang Sinh: ", monthComboBox,
                "Nam Sinh: ", yearComboBox,
                "Ngay tham gia:", joinDateField,
                "Vi tri thi dau:", positionComboBox,
                "So tran:", matchField,
                "So ban thang:", goalField,
                "Luong thoa thuan:", salaryField
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Enter player information",
                    JOptionPane.OK_CANCEL_OPTION);

            // Validate and add the new row to the model
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Convert relevant fields to appropriate types
                    String name = nameField.getText();
                    String nationality = nationalityField.getText();

                    String joinD = joinDateField.getText();
                    Integer sotran = Integer.parseInt(matchField.getText());
                    Integer soBanThang = Integer.parseInt(goalField.getText());
                    Integer Luong = Integer.parseInt(salaryField.getText());

                    // Get the selected position from JComboBox
                    String gender = (String) genderField.getSelectedItem();
                    String selectedPosition = (String) positionComboBox.getSelectedItem();
                    String birth = (String) dayComboBox.getSelectedItem() + "/" + (String) monthComboBox.getSelectedItem() + "/" + (String) yearComboBox.getSelectedItem();

                    // Add the new row to the model
                    model.addRow(new Object[]{name, nationality, gender, birth, joinD, selectedPosition, sotran,
                        soBanThang, Luong});
                    saveTableModelToFile(model, "Data.csv");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid data.");
                }
            }
        });

        changeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu hiện tại từ hàng được chọn
                Object[] currentData = new Object[table.getColumnCount()];
                for (int i = 0; i < table.getColumnCount(); i++) {
                    currentData[i] = table.getModel().getValueAt(selectedRow, i);
                }

                // Tạo các trường nhập dữ liệu với dữ liệu hiện tại làm giá trị mặc định
                JTextField nameField = new JTextField((String) currentData[0]);
                JTextField nationalityField = new JTextField((String) currentData[1]);

                JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
                genderField.setSelectedItem(currentData[2]); // Giả sử cột 2 là giới tính                
                
                JTextField birthDateField = new JTextField((String) currentData[3]);
                JTextField joinDateField = new JTextField((String) currentData[4]);
                
                JComboBox<String> positionField1 = new JComboBox<>(new String[]{"Tien dao", "Hau ve", "Tien Ve"});
                positionField1.setSelectedItem(currentData[5]); // Giả sử cột 5 là vị trí thi đấu               
                
                JTextField matchField = new JTextField((String) currentData[6]);
                JTextField goalField = new JTextField((String) currentData[7]);
                JTextField salaryField = new JTextField((String) currentData[8]);

                Object[] fields = {
                    "Name:", nameField,
                    "Quốc tịch:", nationalityField,
                    "Giới tính:", genderField,
                    "Ngày Sinh:", birthDateField,
                    "Ngày tham gia:", joinDateField,
                    "Vị trí thi đấu:", positionField1,
                    "Số trận:", matchField,
                    "Số bàn thắng:", goalField,
                    "Lương thỏa thuận:", salaryField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Chỉnh sửa thông tin cầu thủ", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Cập nhật dữ liệu mới vào model
                    model.setValueAt(nameField.getText(), selectedRow, 0);
                    model.setValueAt(nationalityField.getText(), selectedRow, 1);
                    
                    model.setValueAt(genderField.getSelectedItem(), selectedRow, 2);
                    model.setValueAt(positionField1.getSelectedItem(), selectedRow, 5);

                    model.setValueAt(birthDateField.getText(), selectedRow, 3);
                    model.setValueAt(joinDateField.getText(), selectedRow, 4);
                    
                    model.setValueAt(matchField.getText(), selectedRow, 6);
                    model.setValueAt(goalField.getText(), selectedRow, 7);
                    model.setValueAt(salaryField.getText(), selectedRow, 8);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một cầu thủ để chỉnh sửa.");
            }
        });

        // Inside the view() method, after initializing other buttons
        JButton saveButton = new JButton("Save");

        buttonPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            saveTableModelToFile(model, "Data.csv");
            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        });

        deleteButton.addActionListener(e -> {
            // Add your logic for deleting the selected row here
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                saveTableModelToFile(model, "Data.csv");
            }
        });
        // Inside the view() method, after initializing other components
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current window
            // Assuming your main menu method is static and in a class called MainMenu
            new Menu().setVisible(true); // Call the main menu display method
        });

        // Tạo nút
        JButton btnViewDetails = new JButton("Xem Chi Tiet");
        btnViewDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    displayPlayerStats(model, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một cầu thủ.");
                }
            }
        });
        buttonPanel.add(btnViewDetails);
// Thêm nút vào panel hoặc một container phù hợp trong giao diện của bạn

        // Frame display code not shown
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
