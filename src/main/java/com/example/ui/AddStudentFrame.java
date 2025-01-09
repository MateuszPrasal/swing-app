package com.example.ui;

import com.example.config.AppConfig;
import com.example.interfaces.StudentsCreatedListenerInterface;
import com.example.models.Student;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa widoku dodawania studenta
 */
public class AddStudentFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JTextField nameTextField = new JTextField();
    private JTextField ageTextField = new JTextField();
    private JTextField gradeTextField = new JTextField();

    @Setter
    private StudentsCreatedListenerInterface studentsCreatedListener;

    public AddStudentFrame() {
        setMinimumSize(new Dimension(650, 470));
        setTitle(AppConfig.ADD_STUDENT_FRAME);

        initTextFieldsLayout();
        initButtonsLayout();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Metoda inicjalizująca pola tekstowe w widoku
     */
    private void initTextFieldsLayout() {
        JPanel textFieldsLayout = new JPanel();
        textFieldsLayout.setLayout(new BoxLayout(textFieldsLayout, BoxLayout.Y_AXIS));

        nameTextField.setMaximumSize(new Dimension(300, 50));
        ageTextField.setMaximumSize(new Dimension(300, 50));
        gradeTextField.setMaximumSize(new Dimension(300, 50));

        JPanel nameTextFieldLayout = new JPanel();
        nameTextFieldLayout.setLayout(new BoxLayout(nameTextFieldLayout, BoxLayout.X_AXIS));
        nameTextFieldLayout.add(new JLabel("Name"));
        nameTextFieldLayout.add(nameTextField);

        JPanel ageTextFieldLayout = new JPanel();
        ageTextFieldLayout.setLayout(new BoxLayout(ageTextFieldLayout, BoxLayout.X_AXIS));
        ageTextFieldLayout.add(new JLabel("Age"));
        ageTextFieldLayout.add(ageTextField);

        JPanel gradeTextFieldLayout = new JPanel();
        gradeTextFieldLayout.setLayout(new BoxLayout(gradeTextFieldLayout, BoxLayout.X_AXIS));
        gradeTextFieldLayout.add(new JLabel("Average grade"));
        gradeTextFieldLayout.add(gradeTextField);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(nameTextFieldLayout);
        mainPanel.add(ageTextFieldLayout);
        mainPanel.add(gradeTextFieldLayout);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(textFieldsLayout);
    }

    /**
     * Metoda inicjalizująca panel z przyciskami kontrolnymi
     */
    private void initButtonsLayout() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = new Student();

                student.setName(nameTextField.getText());
                student.setAge(Integer.parseInt(ageTextField.getText()));
                student.setGrade(Double.parseDouble(gradeTextField.getText()));

                if (studentsCreatedListener != null) studentsCreatedListener.onStudentCreated(student);
                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel);
    }

}
