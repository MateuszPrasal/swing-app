package com.example.ui;

import com.example.config.AppConfig;
import com.example.interfaces.StudentsUpdatedListenerInterface;
import com.example.models.Student;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditStudentFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JTextField nameTextField = new JTextField();
    private JTextField ageTextField = new JTextField();
    private JTextField gradeTextField = new JTextField();

    @Setter
    private StudentsUpdatedListenerInterface studentsUpdatedListener;

    private Student editedStudent;

    public EditStudentFrame(Student student) {
        this.editedStudent = student;

        setMinimumSize(new Dimension(650, 470));
        setTitle(AppConfig.EDIT_STUDENT_FRAME);

        initTextFieldsLayout();
        initButtonsLayout();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initTextFieldsLayout() {
        JPanel textFieldsLayout = new JPanel();
        textFieldsLayout.setLayout(new BoxLayout(textFieldsLayout, BoxLayout.Y_AXIS));

        nameTextField.setMaximumSize(new Dimension(300, 50));
        ageTextField.setMaximumSize(new Dimension(300, 50));
        gradeTextField.setMaximumSize(new Dimension(300, 50));

        nameTextField.setText(editedStudent.getName());
        ageTextField.setText(String.valueOf(editedStudent.getAge()));
        gradeTextField.setText(String.valueOf(editedStudent.getGrade()));

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

    private void initButtonsLayout() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton saveButton = new JButton("Update");
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
                editedStudent.setName(nameTextField.getText());
                editedStudent.setAge(Integer.parseInt(ageTextField.getText()));
                editedStudent.setGrade(Double.parseDouble(gradeTextField.getText()));

                if (studentsUpdatedListener != null) studentsUpdatedListener.onStudentUpdated(editedStudent);

                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel);
    }

}
