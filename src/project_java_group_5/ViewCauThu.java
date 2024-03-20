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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewCauThu {

    public static void saveTableModelToFile(DefaultTableModel model, String filePath) {
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

    public static boolean isRealNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(str);
            if (x > 0)
                return true;
            else
                return false;
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
    
    public static boolean isStringInFormat(String input) {
        // Kiểm tra định dạng cơ bản của chuỗi sử dụng biểu thức chính quy
        if (!input.matches("(\\d)-(\\d)-(\\d)-(\\d)-(\\d)")) {
            return false;
        }

        // Tách chuỗi thành mảng các phần tử dựa trên dấu "-"
        String[] parts = input.split("-");

        // Kiểm tra từng phần tử để đảm bảo chúng là số nguyên từ 1 đến 10
        for (String part : parts) {
            try {
                int number = Integer.parseInt(part);
                if (number < 1 || number > 10) {
                    // Nếu số nằm ngoài khoảng 1 đến 10, trả về false
                    return false;
                }
            } catch (NumberFormatException e) {
                // Nếu không thể chuyển đổi thành số, trả về false
                return false;
            }
        }

        // Nếu tất cả kiểm tra đều hợp lệ, trả về true
        return true;
    }

    public static void view() {
        Object[][] data = null; // Initialize data outside the try block
        DateValidator validator = new DateValidator("dd/MM/uuuu");

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
            data = new Object[rowCount][10];

            int rowIndex = 0;

            // Read data from the file and populate the array
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                for (int i = 0; i < value.length; i++) {
                    data[rowIndex][i] = value[i];
                }
                rowIndex++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame("Cau thu");

        String[] col = { "Ten", "Quoc tich", "Gioi tinh", "Ngay Sinh", "Ngay tham gia", "Vi tri thi dau", "So tran",
                "So ban thang", "Luong thoa thuan", "Diem so 5 tran gan nhat" };

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
            JComboBox<String> genderField = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });

            String[] days = new String[31];
            for (int i = 1; i <= 9; i++) {
                days[i - 1] = "0" + String.valueOf(i); // Fill the array with day numbers as strings
            }
            for (int i = 10; i <= 31; i++) {
                days[i - 1] = String.valueOf(i); // Fill the array with day numbers as strings
            }
            JComboBox<String> dayComboBox = new JComboBox<>(days);

            JComboBox<String> monthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });

            String[] years = new String[61];
            for (int i = 0; i < 61; i++) {
                years[i] = String.valueOf(2024 - i); // Fill the array with day numbers as strings
            }
            JComboBox<String> yearComboBox = new JComboBox<>(years);

            JComboBox<String> joinDayComboBox = new JComboBox<>(days);
            JComboBox<String> joinMonthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
            JComboBox<String> joinYearComboBox = new JComboBox<>(years);

            JComboBox<String> positionComboBox = new JComboBox<>(new String[] { "Tiền đạo", "Hậu vệ", "Tiền vệ" });
            JTextField matchField = new JTextField();
            JTextField goalField = new JTextField();
            JTextField salaryField = new JTextField();
            JTextField lastFiveMatchesField = new JTextField();

            Object[] fields = {
                    "Ten:", nameField,
                    "Quoc tich:", nationalityField,
                    "Gioi tinh:", genderField,
                    "Ngay Sinh:", dayComboBox,
                    "Thang Sinh: ", monthComboBox,
                    "Nam Sinh: ", yearComboBox,
                    "Ngay tham gia:", joinDayComboBox,
                    "Thang tham gia:", joinMonthComboBox,
                    "Nam tham gia:", joinYearComboBox,
                    "Vi tri thi dau:", positionComboBox,
                    "So tran:", matchField,
                    "So ban thang:", goalField,
                    "Luong thoa thuan:", salaryField,
                    "Diem so 5 tran gan nhat (cach nhau 1 dau gach ngang):", lastFiveMatchesField, };

            int result = JOptionPane.showConfirmDialog(null, fields, "Nhập thông tin cầu thủ mới",
                    JOptionPane.OK_CANCEL_OPTION);

            // Validate and add the new row to the model
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Convert relevant fields to appropriate types
                    String name1 = nameField.getText();
                    String nationality1 = nationalityField.getText();

                    if (containsNumber(name1) || containsNumber(nationality1)) {
                        JOptionPane.showMessageDialog(null,
                                "Vui lòng nhập các dữ liệu tên, quốc tịch không chứa kí tự đặc biệt hoặc số", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String name = normalizeName(name1);
                    String nationality = normalizeName(nationality1);

                    String joinD = (String) joinDayComboBox.getSelectedItem() + "/"
                            + (String) joinMonthComboBox.getSelectedItem() + "/"
                            + (String) joinYearComboBox.getSelectedItem();

                    // Integer sotran = Integer.parseInt(matchField.getText());
                    // Integer soBanThang = Integer.parseInt(goalField.getText());
                    // Integer Luong = Integer.parseInt(salaryField.getText());
                    String sotran = matchField.getText();
                    String soBanThang = goalField.getText();
                    String Luong = salaryField.getText();

                    // Get the selected position from JComboBox
                    String gender = (String) genderField.getSelectedItem();
                    String selectedPosition = (String) positionComboBox.getSelectedItem();
                    String birth = (String) dayComboBox.getSelectedItem() + "/"
                            + (String) monthComboBox.getSelectedItem() + "/"
                            + (String) yearComboBox.getSelectedItem();

                    // Xu li ngoai le ngay thang nam
                    if (!validator.isValid(joinD) || !validator.isValid(birth)) {
                        JOptionPane.showMessageDialog(null, "Ngày tháng năm không hợp lệ", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Xu li ngoai le chuoi nhap vao khong phai so
                    if (name.isEmpty() || nationality.isEmpty() || sotran.isEmpty() || soBanThang.isEmpty()
                            || Luong.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ dữ liệu", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!isRealNumber(sotran) || !isRealNumber(soBanThang) || !isRealNumber(Luong)) {
                        JOptionPane.showMessageDialog(null,
                                "Vui lòng nhập các dữ liệu số trận, số bàn thắng và lương là các số thực hợp lệ",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String lastFiveMatchesScores = lastFiveMatchesField.getText();
                    if(!isStringInFormat(lastFiveMatchesScores)){
                        JOptionPane.showMessageDialog(null,
                                "Vui lòng nhập định dạng điểm số 5 trận gần nhất hợp lệ",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Add the new row to the model
                    model.addRow(new Object[] { name, nationality, gender, birth, joinD, selectedPosition, sotran,
                            soBanThang, Luong, lastFiveMatchesScores });
                    saveTableModelToFile(model, "Data.csv");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Số trận, số bàn thắng và lương phải là số nguyên. Vui lòng nhập lại.", "Lỗi nhập liệu",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai. Vui lòng không để trống các trường yêu cầu.",
                            "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        changeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Menu lựa chọn các trường để chỉnh sửa
                final JCheckBox nameCheck = new JCheckBox("Tên");
                final JCheckBox nationalityCheck = new JCheckBox("Quốc tịch");
                final JCheckBox genderCheck = new JCheckBox("Giới tính");
                final JCheckBox birthDateCheck = new JCheckBox("Ngày Sinh");
                final JCheckBox joinDateCheck = new JCheckBox("Ngày tham gia");
                final JCheckBox positionCheck = new JCheckBox("Vị trí thi đấu");
                final JCheckBox matchCheck = new JCheckBox("Số trận");
                final JCheckBox goalCheck = new JCheckBox("Số bàn thắng");
                final JCheckBox salaryCheck = new JCheckBox("Lương thỏa thuận");
                final JCheckBox lastFiveMatchesCheck = new JCheckBox("Điểm số 5 trận gần nhất");

                Object[] options = {
                        nameCheck, nationalityCheck, genderCheck,
                        birthDateCheck, joinDateCheck, positionCheck,
                        matchCheck, goalCheck, salaryCheck, lastFiveMatchesCheck
                };

                int option = JOptionPane.showConfirmDialog(null, options, "Chọn trường dữ liệu để chỉnh sửa",
                        JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Tạo form chỉnh sửa dựa trên lựa chọn
                    ArrayList<Object> fields = new ArrayList<>();
                    if (nameCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Tên:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 0))));
                    }
                    if (nationalityCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Quốc tịch:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 1))));
                    }
                    if (genderCheck.isSelected()) {
                        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
                        genderComboBox.setSelectedItem(table.getModel().getValueAt(selectedRow, 2));
                        fields.addAll(Arrays.asList("Giới tính:", genderComboBox));
                    }
                    if (birthDateCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Ngày Sinh:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 3))));
                    }
                    if (joinDateCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Ngày tham gia:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 4))));
                    }
                    if (positionCheck.isSelected()) {
                        JComboBox<String> positionComboBox = new JComboBox<>(
                                new String[] { "Tiền đạo", "Hậu vệ", "Tiền vệ" });
                        positionComboBox.setSelectedItem(table.getModel().getValueAt(selectedRow, 5));
                        fields.addAll(Arrays.asList("Vị trí thi đấu:", positionComboBox));
                    }
                    if (matchCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Số trận:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 6))));
                    }
                    if (goalCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Số bàn thắng:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 7))));
                    }
                    if (salaryCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Lương thỏa thuận:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 8))));
                    }
                    if (lastFiveMatchesCheck.isSelected()) {
                        fields.addAll(Arrays.asList("Điểm số 5 trận gần nhất:",
                                new JTextField((String) table.getModel().getValueAt(selectedRow, 9))));
                    }

                    // Hiển thị form chỉnh sửa
                    if (!fields.isEmpty()) {
                        Object[] fieldsArray = fields.toArray();
                        int result = JOptionPane.showConfirmDialog(null, fieldsArray, "Chỉnh sửa thông tin cầu thủ",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            int fieldIndex = 1; // Index for accessing the JTextField values in fieldsArray
                            // Cập nhật dữ liệu mới vào model dựa trên nhập liệu
                            if (nameCheck.isSelected()) {
                                JTextField name = (JTextField) fieldsArray[fieldIndex];
                                String namee = (String) name.getText();
                                if (containsNumber(namee)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập các dữ liệu tên không chứa kí tự đặc biệt hoặc số", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                String name2 = normalizeName(namee);
                                model.setValueAt(name2, selectedRow, 0);
                                fieldIndex += 2;
                            }
                            // Cập nhật các trường khác dựa vào lựa chọn
                            if (nationalityCheck.isSelected()) {
                                JTextField quoctich = (JTextField) fieldsArray[fieldIndex];
                                String quocc = (String)quoctich.getText();
                                if (containsNumber(quocc)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập các dữ liệu quốc tịch không chứa kí tự đặc biệt hoặc số", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 1);
                                fieldIndex += 2;
                            }
                            if (genderCheck.isSelected()) {
                                model.setValueAt(((JComboBox) fieldsArray[fieldIndex]).getSelectedItem(), selectedRow,
                                        2);
                                fieldIndex += 2;
                            }
                            if (birthDateCheck.isSelected()) {
                                JTextField Date = (JTextField) fieldsArray[fieldIndex];
                                String birthDate = (String) Date.getText();
                                if (!validator.isValid(birthDate)) {
                                    JOptionPane.showMessageDialog(null, "Ngày tháng năm không hợp lệ", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 3);
                                fieldIndex += 2;
                            }
                            if (joinDateCheck.isSelected()) {
                                JTextField Date = (JTextField) fieldsArray[fieldIndex];
                                String joinDate = (String) Date.getText();
                                if (!validator.isValid(joinDate)) {
                                    JOptionPane.showMessageDialog(null, "Ngày tháng năm không hợp lệ", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 4);
                                fieldIndex += 2;
                            }
                            if (positionCheck.isSelected()) {
                                
                                model.setValueAt(((JComboBox) fieldsArray[fieldIndex]).getSelectedItem(), selectedRow,
                                        5);
                                fieldIndex += 2;
                            }
                            if (matchCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String sotrann = check.getText();
                                if (!isRealNumber(sotrann)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập các dữ liệu số trận là số thực hợp lệ",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 6);
                                fieldIndex += 2;
                            }
                            if (goalCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String banthang = check.getText();
                                if (!isRealNumber(banthang)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập các dữ liệu số bàn thắng là số thực hợp lệ",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 7);
                                fieldIndex += 2;
                            }
                            if (salaryCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String luongg = check.getText();
                                if (!isRealNumber(luongg)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập các dữ liệu lương là số thực hợp lệ",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 8);
                                fieldIndex += 2;
                            }
                            if (lastFiveMatchesCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String namtran = check.getText();
                                if (!isStringInFormat(namtran)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Vui lòng nhập định dạng điểm số 5 trận gần nhất hợp lệ",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow, 9);
                                // Không cần tăng fieldIndex ở đây nếu đây là trường cuối cùng
                            }

                            JOptionPane.showMessageDialog(null, "Thông tin cầu thủ đã được cập nhật.");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một trường để chỉnh sửa.");
                    }
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

        // Inside the view() method, after initializing other components
        JButton addLatestMatchScoreButton = new JButton("Add Latest Match Score");

        addLatestMatchScoreButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String latestMatchScore = JOptionPane.showInputDialog("Enter the latest match score:");

                // Get the current row's latest match scores
                String currentLastFiveMatchesScores = (String) model.getValueAt(selectedRow, 9);
                String[] lastFiveMatchesArray = currentLastFiveMatchesScores.split("-");
                LinkedList<String> lastFiveMatchesList = new LinkedList<>(Arrays.asList(lastFiveMatchesArray));

                // Add the latest match score to the end of the list
                lastFiveMatchesList.addLast(latestMatchScore);

                // Remove the first element if the list has more than 5 elements
                if (lastFiveMatchesList.size() > 5) {
                    lastFiveMatchesList.removeFirst();
                }

                // Join the elements back into a string separated by "-"
                String updatedLastFiveMatchesScores = String.join("-", lastFiveMatchesList);

                // Update the model with the updated last five matches scores
                model.setValueAt(updatedLastFiveMatchesScores, selectedRow, 9);

                // Save the updated data to the file
                saveTableModelToFile(model, "Data.csv");

                JOptionPane.showMessageDialog(null, "Latest match score added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a player to add the latest match score.");
            }
        });
        buttonPanel.add(addLatestMatchScoreButton);

        // Frame display code not shown
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
