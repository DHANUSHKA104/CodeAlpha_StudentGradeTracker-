import java.awt.*;
import java.util.*;
import javax.swing.*;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        grades = new ArrayList<>();
    }

    void addGrade(int grade) {
        grades.add(grade);
    }

    double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int g : grades) sum += g;
        return (double) sum / grades.size();
    }

    int getHighest() {
        if (grades.isEmpty()) return 0;
        return Collections.max(grades);
    }

    int getLowest() {
        if (grades.isEmpty()) return 0;
        return Collections.min(grades);
    }
}

public class StudentGradeTrackerGUI {

    static ArrayList<Student> students = new ArrayList<>();
    static JTextArea displayArea;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Grade Tracker Dashboard");
        frame.setSize(600,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,3,10,10));

        JButton addStudent = new JButton("Add Student");
        JButton addGrade = new JButton("Add Grade");
        JButton deleteStudent = new JButton("Delete Student");
        JButton viewStudent = new JButton("View Student Report");
        JButton viewAll = new JButton("View All Students");
        JButton exit = new JButton("Exit");

        buttonPanel.add(addStudent);
        buttonPanel.add(addGrade);
        buttonPanel.add(deleteStudent);
        buttonPanel.add(viewStudent);
        buttonPanel.add(viewAll);
        buttonPanel.add(exit);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(displayArea);

        frame.add(buttonPanel,BorderLayout.NORTH);
        frame.add(scroll,BorderLayout.CENTER);

        addStudent.addActionListener(e -> addStudent());
        addGrade.addActionListener(e -> addGrade());
        deleteStudent.addActionListener(e -> deleteStudent());
        viewStudent.addActionListener(e -> viewStudent());
        viewAll.addActionListener(e -> viewAllStudents());
        exit.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    static void addStudent() {

        String name = JOptionPane.showInputDialog("Enter student name:");

        if(name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Invalid name");
            return;
        }

        students.add(new Student(name));
        displayArea.setText("Student added: " + name);
    }

    static void addGrade() {

        String name = JOptionPane.showInputDialog("Enter student name:");

        Student found = null;

        for(Student s : students) {
            if(s.name.equalsIgnoreCase(name)) {
                found = s;
                break;
            }
        }

        if(found == null) {
            JOptionPane.showMessageDialog(null,"Student not found");
            return;
        }

        try {

            int grade = Integer.parseInt(
                JOptionPane.showInputDialog("Enter grade (0-100):")
            );

            if(grade < 0 || grade > 100) {
                JOptionPane.showMessageDialog(null,"Grade must be 0-100");
                return;
            }

            found.addGrade(grade);
            displayArea.setText("Grade added for " + name);

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Invalid grade input");
        }
    }

    static void deleteStudent() {

        String name = JOptionPane.showInputDialog("Enter student name to delete:");

        Iterator<Student> it = students.iterator();

        while(it.hasNext()) {

            Student s = it.next();

            if(s.name.equalsIgnoreCase(name)) {
                it.remove();
                displayArea.setText("Student deleted: " + name);
                return;
            }
        }

        JOptionPane.showMessageDialog(null,"Student not found");
    }

    static void viewStudent() {

        String name = JOptionPane.showInputDialog("Enter student name:");

        for(Student s : students) {

            if(s.name.equalsIgnoreCase(name)) {

                displayArea.setText(
                        "Student: " + s.name +
                        "\nGrades: " + s.grades +
                        "\nAverage: " + s.getAverage() +
                        "\nHighest: " + s.getHighest() +
                        "\nLowest: " + s.getLowest()
                );

                return;
            }
        }

        JOptionPane.showMessageDialog(null,"Student not found");
    }

    static void viewAllStudents() {

        if(students.isEmpty()) {
            displayArea.setText("No students available");
            return;
        }

        StringBuilder report = new StringBuilder();

        for(Student s : students) {

            report.append("Name: ").append(s.name).append("\n");
            report.append("Grades: ").append(s.grades).append("\n");
            report.append("Average: ").append(s.getAverage()).append("\n");
            report.append("-------------------------\n");
        }

        displayArea.setText(report.toString());
    }
}