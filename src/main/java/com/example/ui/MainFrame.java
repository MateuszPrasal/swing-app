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
import java.util.Date;

/**
 * Klasa głównego widoku aplikacji
 */
public class MainFrame extends JFrame implements StudentsCreatedListenerInterface, StudentsUpdatedListenerInterface {
    private JPanel mainPanel = new JPanel();
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentManager studentManager = new StudentManagerImpl();
    private DefaultListModel listModel = new DefaultListModel();

    public MainFrame() {
        setMinimumSize(new Dimension(650, 470));
        setTitle(AppConfig.APP_NAME);

        initTable();
        initButtons();
        initPanel();
        initListLayout();

        pack();
        setLocationRelativeTo(null); // Centers Window
        setVisible(true);
    }

    /**
     * Inicjalizacja layoutu panelu
     */
    private void initPanel() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
    }

    /**
     * Inicjalizacja tabeli wyświetlającej wyniki
     */
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

    /**
     * Inicjalizacja panelu zawierającego przyciski kontrolne
     */
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

                addLog("New student window triggered");
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

                    addLog("Student removed [" + id + "]");

                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No row was chosen");
                }
            }
        });

        displayAllStudentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableModelData();
                addLog("Displaying all students triggered");
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

                    addLog("Edit student window triggered");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "No row was chosen");
                }
            }
        });

        calculateAverageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double avg = studentManager.calculateAverageGrade();

                addLog("Calculating average grade triggered");
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

        Box.createVerticalGlue();
        mainPanel.add(panel);
        Box.createVerticalGlue();
    }

    private void initListLayout() {
        JList list = new JList<>(listModel);
        JScrollPane listscroll = new JScrollPane(list);

        mainPanel.add(listscroll);
    }

    /**
     * Metoda aktualizująca tabelę w oparciu o dane z bazy SQLite
     */
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

    private void addLog(String log) {
        listModel.addElement("[" + new Date() + "] " + log);
    }

    /**
     * Metoda nasłuchująca na utworzenie studenta
     *
     * @param student
     */
    @Override
    public void onStudentCreated(Student student) {
        this.studentManager.addStudent(student);
        setTableModelData();
        addLog("New student created [" + student + "]");
    }

    /**
     * Metoda nasłuchująca na aktualizację studenta
     *
     * @param student
     */
    @Override
    public void onStudentUpdated(Student student) {
        this.studentManager.updateStudent(student);
        setTableModelData();

        addLog("Student updated [" + student + "]");
    }
}
