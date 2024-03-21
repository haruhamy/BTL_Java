package project_java_group_5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControllerCauThu {
    public static void filterByPosition(DefaultTableModel originalModel, String position) {
        // Tạo một DefaultTableModel mới để chứa kết quả lọc
        DefaultTableModel filteredModel = new DefaultTableModel();
        // Định nghĩa tiêu đề cột cho bảng mới
        String[] columnNames = {"Tên", "Quốc tịch", "Giới tính", "Ngày Sinh", "Ngày tham gia", "Vị trí thi đấu", "Số trận", "Số bàn thắng", "Lương thỏa thuận", "Điểm 5 trận gần nhất"};
        filteredModel.setColumnIdentifiers(columnNames);

        // Duyệt qua dữ liệu gốc và lọc theo vị trí
        for (int i = 0; i < originalModel.getRowCount(); i++) {
            String playerPosition = (String) originalModel.getValueAt(i, 5); // Giả sử vị trí được lưu ở cột thứ 6
            if (playerPosition.equals(position)) {
                // Nếu vị trí khớp, thêm dòng vào model mới
                Object[] row = new Object[originalModel.getColumnCount()];
                for (int j = 0; j < originalModel.getColumnCount(); j++) {
                    row[j] = originalModel.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        // Tạo JTable mới với model đã lọc
        JTable filteredTable = new JTable(filteredModel);

        // Tạo JFrame hoặc JDialog mới để hiển thị bảng
        JDialog dialog = new JDialog();
        dialog.setTitle("Kết quả lọc: " + position);
        dialog.setSize(800, 400); // Đặt kích thước phù hợp
        dialog.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình

        // Thêm JTable vào JScrollPane và thêm JScrollPane vào dialog
        JScrollPane scrollPane = new JScrollPane(filteredTable);
        dialog.add(scrollPane);

        // Hiển thị dialog
        dialog.setVisible(true);
    }
    
    public static void sortTableByColumn(DefaultTableModel model, int column, boolean ascending) {
        ArrayList<Object[]> rowDataList = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] row = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j);
            }
            rowDataList.add(row);
        }

        // Kiểm tra xem cột có phải là dữ liệu số không để áp dụng bộ so sánh phù hợp
        if (column == 6 || column == 7 || column == 8) { // Giả sử cột 6, 7, 8 là số trận, số bàn thắng, và lương
            rowDataList.sort((o1, o2) -> {
                Double val1 = Double.parseDouble(o1[column].toString());
                Double val2 = Double.parseDouble(o2[column].toString());
                return ascending ? val1.compareTo(val2) : val2.compareTo(val1);
            });
        } else {
            // Sắp xếp theo thứ tự từ điển cho các cột khác
            rowDataList.sort((o1, o2) -> {
                if (ascending) {
                    return o1[column].toString().compareToIgnoreCase(o2[column].toString());
                } else {
                    return o2[column].toString().compareToIgnoreCase(o1[column].toString());
                }
            });
        }

        // Cập nhật lại model với dữ liệu đã được sắp xếp
        model.setRowCount(0);
        for (Object[] row : rowDataList) {
            model.addRow(row);
        }
    }
    
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
    
    public static String chuanhoa(String s) {
        String[] arr = s.split("/");
        String ans = String.format("%02d", Integer.parseInt(arr[0])) + "/";
        ans += String.format("%02d", Integer.parseInt(arr[1])) + "/" + arr[2];
        return ans;
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
        // Sửa đổi biểu thức chính quy để khớp với một số có một hoặc hai chữ số
        if (!input.matches("(\\d{1,2})-(\\d{1,2})-(\\d{1,2})-(\\d{1,2})-(\\d{1,2})")) {
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

    public static String getYearFromString(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(dateString);
            // Tạo một đối tượng SimpleDateFormat mới để chỉ định định dạng của kết quả mong
            // muốn
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            return yearFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Định dạng ngày không hợp lệ";
        }
    }
}
