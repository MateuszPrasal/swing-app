/*
 * Created by JFormDesigner on Sat Aug 06 07:00:57 CDT 2022
 */

package com.example.ui;

import com.example.config.AppConfig;
import com.example.interfaces.StudentsCreatedListenerInterface;
import com.example.interfaces.StudentsUpdatedListenerInterface;
import com.example.models.Student;
import com.example.services.StudentManager;
import com.example.services.StudentManagerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author unknown
 */
public class MainFrame extends JFrame implements StudentsCreatedListenerInterface, StudentsUpdatedListenerInterface {
    private JPanel mainPanel = new JPanel();
    private JTable table;
    private DefaultTableModel tableModel;

    private StudentManager studentManager = new StudentManagerImpl();

    public MainFrame() {
        setMinimumSize(new Dimension(650, 470));
        setTitle(AppConfig.APP_NAME);

        initTable();
        initButtons();
        initPanel();

        pack();
        setLocationRelativeTo(null); // Centers Window
        setVisible(true);
    }

    private void initPanel() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
    }

    private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Color.BLACK);    // Kolor tła nagłówka
                label.setForeground(Color.WHITE);  // Kolor tekstu nagłówka
                label.setHorizontalAlignment(SwingConstants.CENTER); // Wyśrodkowanie tekstu
                label.setFont(new Font("Arial", Font.BOLD, 14)); // Czcionka nagłówka
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane);
    }

    private void initButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        JButton addBtn = new JButton("Add Student");
        JButton removeStudentBtn = new JButton("Remove Student");
        JButton updateStudentsBtn = new JButton("Update Student");
        JButton displayAllStudentsBtn = new JButton("Display all Students");
        JButton calculateAverageBtn = new JButton("Calculate Average");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentFrame addStudentFrame = new AddStudentFrame();
                addStudentFrame.setStudentsCreatedListener(MainFrame.this);
            }
        });

        removeStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    Integer id = (Integer) table.getModel().getValueAt(selectedRow, 0);
                    studentManager.removeStudent(String.valueOf(id));
                    tableModel.removeRow(selectedRow);

                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No row was chosen");
                }
            }
        });

        displayAllStudentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableModelData();
            }
        });

        updateStudentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    Integer id = (Integer) table.getModel().getValueAt(selectedRow, 0);
                    Student student = studentManager.getStudentById(id);

                    EditStudentFrame editStudentFrame = new EditStudentFrame(student);
                    editStudentFrame.setStudentsUpdatedListener(MainFrame.this);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No row was chosen");
                }
            }
        });

        calculateAverageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double avg = studentManager.calculateAverageGrade();

                JOptionPane.showMessageDialog(mainPanel, "Average grade of students: " + avg);
            }
        });

        buttonsPanel.add(addBtn);
        buttonsPanel.add(removeStudentBtn);
        buttonsPanel.add(displayAllStudentsBtn);
        buttonsPanel.add(updateStudentsBtn);
        buttonsPanel.add(calculateAverageBtn);

        JPanel panel = new JPanel();
        panel.add(buttonsPanel);

        mainPanel.add(panel);
    }

    private void setTableModelData() {
        String[] columnNames = {"StudentID", "Name", "Age", "Grade"};
        var students = this.studentManager.displayAllStudents();
        Object[][] data = new Object[students.size()][4];

        for (int i = 0; i < students.size(); i++) {
            Student st = students.get(i);
            data[i] = st.toTableObject();
        }

        tableModel.setDataVector(data, columnNames);
    }

    @Override
    public void onStudentCreated(Student student) {
        this.studentManager.addStudent(student);
        setTableModelData();
    }

    @Override
    public void onStudentUpdated(Student student) {
        this.studentManager.updateStudent(student);
        setTableModelData();
    }
}
