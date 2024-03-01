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
    private JTextField txtName, txtNationality, txtBirthDate, txtQualifications, txtExperience;
    private JButton btnAdd, btnUpdate, btnDelete, btnSave, btnLoad;

    public ViewHuanLuyenVien() {
        setTitle("Quản lý Huấn luyện viên");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = { "Name", "Nationality", "BirthDate", "Qualifications", "Experience" };
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form setup
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);
        panel.add(new JLabel("Nationality:"));
        txtNationality = new JTextField();
        panel.add(txtNationality);
        panel.add(new JLabel("Birth Date:"));
        txtBirthDate = new JTextField();
        panel.add(txtBirthDate);
        panel.add(new JLabel("Qualifications:"));
        txtQualifications = new JTextField();
        panel.add(txtQualifications);
        panel.add(new JLabel("Experience:"));
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
        String qualifications = txtQualifications.getText().trim();
        String experience = txtExperience.getText().trim();

        // Validate the input data
        if (name.isEmpty() || nationality.isEmpty() || birthDate.isEmpty() || qualifications.isEmpty()
                || experience.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add data to the table model
        model.addRow(new Object[] { name, nationality, birthDate, qualifications, experience });

        // Clear the input fields after adding
        txtName.setText("");
        txtNationality.setText("");
        txtBirthDate.setText("");
        txtQualifications.setText("");
        txtExperience.setText("");
    }

    private void updateCoach() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow >= 0) {
        // Only update fields if the corresponding text field is not empty.
        String name = txtName.getText().trim();
        if (!name.isEmpty()) {
            model.setValueAt(name, selectedRow, 0);
        }

        String nationality = txtNationality.getText().trim();
        if (!nationality.isEmpty()) {
            model.setValueAt(nationality, selectedRow, 1);
        }

        String birthDate = txtBirthDate.getText().trim();
        if (!birthDate.isEmpty()) {
            model.setValueAt(birthDate, selectedRow, 2);
        }

        String qualifications = txtQualifications.getText().trim();
        if (!qualifications.isEmpty()) {
            model.setValueAt(qualifications, selectedRow, 3);
        }

        String experience = txtExperience.getText().trim();
        if (!experience.isEmpty()) {
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
