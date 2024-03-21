package project_java_group_5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewCauThu {  
    private static Object[][] data, data0; 
    private static DateValidator validator;     
    private static JFrame frame;
    private static JTable table;
    private static DefaultTableModel model;
    private static JScrollPane scrollPane;
    private static JPanel buttonPanel;
    private static JButton addButton, changeButton, deleteButton, in4, sortMenuButton, addLatestMatchScoreButton, backButton;    
    private static JTextField nameField ,nationalityField, matchField, goalField, salaryField, lastFiveMatchesField;
    private static JComboBox<String> genderField, dayComboBox, monthComboBox, yearComboBox, joinDayComboBox, joinMonthComboBox, joinYearComboBox, positionComboBox;
    
    public static void view() {     
        data0 = null;
        validator = new DateValidator("dd/MM/uuuu"); 
        
        data = ControllerCauThu.loadData(data0);        
       
        frame = new JFrame("Cau thu");

        String[] col = { "Ten", "Quoc tich", "Gioi tinh", "Ngay Sinh", "Ngay tham gia", "Vi tri thi dau", "So tran",
                "So ban thang", "Luong thoa thuan", "Diem so 5 tran gan nhat" };

        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        // Use FlowLayout for the button panel with horizontal spacing
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));

        addButton = new JButton("Add");
        changeButton = new JButton("Change");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> {
            // Show a dialog to input new data
            nameField = new JTextField();
            nationalityField = new JTextField();
            genderField = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });

            String[] days = new String[31];
            for (int i = 1; i <= 9; i++) {
                days[i - 1] = "0" + String.valueOf(i); // Fill the array with day numbers as strings
            }
            for (int i = 10; i <= 31; i++) {
                days[i - 1] = String.valueOf(i); // Fill the array with day numbers as strings
            }
            dayComboBox = new JComboBox<>(days);

            monthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });

            String[] years = new String[61];
            for (int i = 0; i < 61; i++) {
                years[i] = String.valueOf(2024 - i); // Fill the array with day numbers as strings
            }
            yearComboBox = new JComboBox<>(years);

            joinDayComboBox = new JComboBox<>(days);
            joinMonthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
            joinYearComboBox = new JComboBox<>(years);

            positionComboBox = new JComboBox<>(new String[] { "Tiền đạo", "Hậu vệ", "Tiền vệ" });
            matchField = new JTextField();
            goalField = new JTextField();
            salaryField = new JTextField();
            lastFiveMatchesField = new JTextField();

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
            ControllerCauThu.them(model, fields, nameField, nationalityField, positionComboBox, genderField, joinDayComboBox, joinMonthComboBox, joinYearComboBox, matchField, goalField, salaryField, dayComboBox, monthComboBox, yearComboBox, validator, lastFiveMatchesField);
        });

        changeButton.addActionListener(e -> {
            ControllerCauThu.thaydoi(table, model, validator);
        });

        // Inside the view() method, after initializing other buttons
        JButton saveButton = new JButton("Save");

        buttonPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            ControllerCauThu.saveTableModelToFile(model, "Data.csv");
            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        });

        deleteButton.addActionListener(e -> {
            // Add your logic for deleting the selected row here
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                ControllerCauThu.saveTableModelToFile(model, "Data.csv");
            }
        });
        // Inside the view() method, after initializing other components
        backButton = new JButton("Back");
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current window
            // Assuming your main menu method is static and in a class called MainMenu
            new Menu().setVisible(true); // Call the main menu display method
        });

        // Inside the view() method, after initializing other components
        addLatestMatchScoreButton = new JButton("Add Latest Match Score");

        addLatestMatchScoreButton.addActionListener(e -> {
            ControllerCauThu.them5tran(table, model);
        });
        buttonPanel.add(addLatestMatchScoreButton);

        in4 = new JButton("Information");
        in4.addActionListener(e -> {
            ControllerCauThu.in4(table, model);
        });
        buttonPanel.add(in4);

        // Trong phần khởi tạo của bạn, thay đổi nút "Sort by Goals" thành một menu lựa chọn
        sortMenuButton = new JButton("Sort Options");
        buttonPanel.add(sortMenuButton);

        // Tạo JPopupMenu cho các lựa chọn sắp xếp
        JPopupMenu sortMenu = new JPopupMenu();

        // Thêm các mục lựa chọn sắp xếp
        JMenuItem sortByGoals = new JMenuItem("Sort by Goals");
        JMenuItem sortBySalary = new JMenuItem("Sort by Salary");
        JMenuItem sortByMatches = new JMenuItem("Sort by Matches");

        // Thêm các mục vào menu
        sortMenu.add(sortByGoals);
        sortMenu.add(sortBySalary);
        sortMenu.add(sortByMatches);

        // Xử lý sự kiện cho từng mục menu
        sortByGoals.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 7, false)); // Giả sử số bàn thắng ở cột thứ 8 (index 7)
        sortBySalary.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 8, false)); // Giả sử lương ở cột thứ 9 (index 8)
        sortByMatches.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 6, false)); // Giả sử số trận ở cột thứ 7 (index 6)

        // Hiển thị menu khi nhấp vào nút
        sortMenuButton.addActionListener(e -> sortMenu.show(sortMenuButton, 0, sortMenuButton.getHeight()));
        // Phương thức để sắp xếp bảng dựa trên cột và hướng sắp xếp       

        // Trong phương thức view() hoặc phần khởi tạo GUI của bạn
        JButton filterMenuButton = new JButton("Filter Options");
        buttonPanel.add(filterMenuButton);

        JPopupMenu filterMenu = new JPopupMenu();

        JMenuItem filterForwards = new JMenuItem("Filter Forwards");
        JMenuItem filterMidfielders = new JMenuItem("Filter Midfielders");
        JMenuItem filterDefenders = new JMenuItem("Filter Defenders");

        filterMenu.add(filterForwards);
        filterMenu.add(filterMidfielders);
        filterMenu.add(filterDefenders);

        filterMenuButton.addActionListener(e -> filterMenu.show(filterMenuButton, 0, filterMenuButton.getHeight()));

        filterForwards.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Tiền đạo"));
        filterMidfielders.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Tiền vệ"));
        filterDefenders.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Hậu vệ"));      
        
        // Frame display code not shown
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);              
    }
}
