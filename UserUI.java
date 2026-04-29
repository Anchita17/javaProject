import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserUI {
    private JFrame frame;
    private JTextField nameField, deptField;
    private JPasswordField passwordField;
    private JTextArea outputArea;

    private ArrayList<UserService.User> users = new ArrayList<>();

    public UserUI() {
        frame = new JFrame("User Security System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        frame.add(nameField);

        frame.add(new JLabel("Department:"));
        deptField = new JTextField(15);
        frame.add(deptField);

        frame.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        frame.add(passwordField);

        JButton addButton = new JButton("Add User");
        JButton showButton = new JButton("Show Data");

        frame.add(addButton);
        frame.add(showButton);

        outputArea = new JTextArea(15, 40);
        frame.add(new JScrollPane(outputArea));

        // 🔗 Linking backend logic
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String dept = deptField.getText();
                String password = new String(passwordField.getPassword());

                UserService.User user = UserService.processUser(name, dept, password);
                users.add(user);

                outputArea.append("User Added: " + name + "\n");

                // Clear fields
                nameField.setText("");
                deptField.setText("");
                passwordField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        showButton.addActionListener(e -> {
            outputArea.append("\n--- Stored Data ---\n");

            for (UserService.User u : users) {
                outputArea.append(u.name + " (" + u.dept + ") -> Hash: "
                        + u.hash + " | Salt: " + u.salt + "\n");
            }

            outputArea.append("\n--- Graph ---\n");

            ArrayList<ArrayList<Integer>> graph = UserService.buildGraph(users);

            for (int i = 0; i < users.size(); i++) {
                outputArea.append(users.get(i).name + " connected to: ");
                for (int j : graph.get(i)) {
                    outputArea.append(users.get(j).name + " ");
                }
                outputArea.append("\n");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new UserUI();
    }
}