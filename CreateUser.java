import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Arrays;

public class CreateUser implements ActionListener {
    String dbURL = "jdbc:mysql://localhost:3306/banking";
    String username = "root";
    String password = "Sad@qls1610-";
    String query = "SELECT * FROM Customer INNER JOIN loan ON customer.Customer_ID=loan.Customer_ID";
    String query2 = "SELECT * FROM customer left JOIN Account ON customer.Customer_ID=Account.Customer_ID";
    JFrame frame;
    JButton createButton, homeButton, deleteButton, clearButton, updateButton;
    JTextField textField;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextField textField5;
    JComboBox intersetBox, typeBox;
    String mouseClick;

    CreateUser() {
        frame = new JFrame("Create New User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 600);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.blue);

        JPanel labelTextFieldPanel = new JPanel();
        labelTextFieldPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label1 = new JLabel("First Name");
        JLabel label2 = new JLabel("Last Name");
        JLabel label3 = new JLabel("Phone Number");
        JLabel label4 = new JLabel("Email");
        JLabel label5 = new JLabel("Initial Balance");
        JLabel label6 = new JLabel("Account Type");
        JLabel label7 = new JLabel("Interest Rate");

        gbc.gridx = 0;
        gbc.gridy = 0;
        labelTextFieldPanel.add(label1, gbc);

        gbc.gridy = 1;
        labelTextFieldPanel.add(label2, gbc);

        gbc.gridy = 2;
        labelTextFieldPanel.add(label3, gbc);

        gbc.gridy = 3;
        labelTextFieldPanel.add(label4, gbc);

        gbc.gridy = 4;
        labelTextFieldPanel.add(label5, gbc);

        gbc.gridy = 5;
        labelTextFieldPanel.add(label7, gbc);

        gbc.gridy = 6;
        labelTextFieldPanel.add(label6, gbc);

        textField = new JTextField(20);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(20);

        // Creating and filing the combobox with elements

        intersetBox = new JComboBox<>();

        String[] rate = { "3", "5", "7", "10" };
        String[] typeStrings = { "Saving", "Checking" };
        typeBox = new JComboBox<>(typeStrings);

        for (int i = 0; i < 4; i++) {
            intersetBox.addItem(rate[i] + ", percent");
        }

        gbc.gridx = 1;
        gbc.gridy = 0;
        labelTextFieldPanel.add(textField, gbc);

        gbc.gridy = 1;
        labelTextFieldPanel.add(textField2, gbc);

        gbc.gridy = 2;
        labelTextFieldPanel.add(textField3, gbc);

        gbc.gridy = 3;
        labelTextFieldPanel.add(textField4, gbc);

        gbc.gridy = 4;
        labelTextFieldPanel.add(textField5, gbc);

        gbc.gridy = 5;
        labelTextFieldPanel.add(intersetBox, gbc);

        gbc.gridy = 6;
        labelTextFieldPanel.add(typeBox, gbc);

        labelTextFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 200, 0));
        contentPanel.add(labelTextFieldPanel, BorderLayout.WEST);

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);) {
            ResultSet rs = statement.executeQuery(query2);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Customer_ID");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Phone");
            model.addColumn("Email");
            model.addColumn("Balance");
            model.addColumn("Account Type");
            model.addColumn("Interest Rate");

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getString("Customer_ID");
                row[1] = rs.getString("First_Name");
                row[2] = rs.getString("Last_Name");
                row[3] = rs.getString("Phone");
                row[4] = rs.getString("Email");
                row[5] = rs.getInt("Balance");
                row[6] = rs.getString("Account_Type");
                row[7] = rs.getString("Interest_Rate");
                model.addRow(row);
            }

            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Create a JTable with the model
            JTable table = new JTable(model);
            table.setBackground(new Color(176, 196, 200));

            // Creating a mouse listener for the table in order to respond click actions
            // undertaken by the user

            table.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    int i = table.getSelectedRow();
                    textField.setText(model.getValueAt(i, 1).toString());
                    textField2.setText(model.getValueAt(i, 2).toString());
                    textField3.setText(model.getValueAt(i, 3).toString());
                    textField4.setText(model.getValueAt(i, 4).toString());
                    textField5.setText(model.getValueAt(i, 5).toString());
                    mouseClick = model.getValueAt(i, 0).toString();

                }

            });

            // Create a JScrollPane to make the table scrollable
            JScrollPane scrollPane = new JScrollPane(table);
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            contentPanel.add(tablePanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

            // Creating the buttons and assigning the operations that they are going to
            // carry out in case of click operation

            createButton = new JButton("Add");
            createButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String selectedItem = (String) intersetBox.getSelectedItem();
                    String[] words = selectedItem.split(", ");

                    String insertQuery = "INSERT INTO customer(First_Name,Last_Name,Phone,Email) Values('"
                            + textField.getText() +
                            "','" + textField2.getText() +
                            "','" + textField3.getText() +
                            "','" + textField4.getText() + "') ";
                    String query3 = "SELECT * FROM customer LEFT JOIN Account ON customer.Customer_ID=Account.Customer_ID";

                    // final Object[] row =
                    // {textField.getText(),textField2.getText(),textField3.getText(),textField4.getText()};
                    try {
                        long phoneNumber = Long.parseLong(textField3.getText());
                        int amount = Integer.parseInt(textField5.getText());

                        // Check for appropriate values
                        if (textField3.getText().length() != 10) {
                            JOptionPane.showMessageDialog(null, "Phone number must have exactly 10 digits.");

                        } else if (amount < 0) {
                            JOptionPane.showMessageDialog(null, "Initial balance must be non-negative.");
                        } else if (textField.getText().isEmpty() || textField2.getText().isEmpty()
                                || textField3.getText().isEmpty() || textField4.getText().isEmpty()
                                || textField5.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "values can't be null!!");
                        } else if (amount > 999999999) {
                            JOptionPane.showMessageDialog(null, "To large !!");
                        } else if (!isValidEmailFormat(textField4.getText())) {
                            JOptionPane.showMessageDialog(null, "Invalid email format!");

                        } else {
                            try {

                                Connection connection = DriverManager.getConnection(dbURL, username, password);
                                Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                                int resultSet = statement2.executeUpdate(insertQuery);

                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            try {
                                Object[] row = new Object[8];
                                Connection connection2 = DriverManager.getConnection(dbURL, username, password);
                                Statement statement = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                                ResultSet rSet = statement.executeQuery(query3);
                                while (rSet.next()) {
                                    row[0] = rSet.getString("Customer_ID");
                                    row[1] = rSet.getString("First_Name");
                                    row[2] = rSet.getString("Last_Name");
                                    row[3] = rSet.getString("Phone");
                                    row[4] = rSet.getString("Email");
                                    row[5] = rSet.getInt("Balance");
                                    row[6] = rSet.getString("Account_Type");
                                    row[7] = rSet.getString("Interest_Rate");
                                }
                                try {
                                    Connection connection3 = DriverManager.getConnection(dbURL, username, password);
                                    Statement statement3 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);

                                    String insertBalance = "INSERT INTO account(Balance,Account_Type,Interest_Rate,Customer_ID) Values('"
                                            + textField5.getText() +
                                            "','" + typeBox.getSelectedItem() +
                                            "','" + words[0] +
                                            "','" + row[0] + "') ";
                                    int rSet2 = statement3.executeUpdate(insertBalance);

                                } catch (Exception exception2) {

                                }
                                row[5] = textField5.getText();
                                row[6] = typeBox.getSelectedItem();
                                row[7] = words[0];

                                model.addRow(row);

                            } catch (Exception exception2) {
                                exception2.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, "Successfully Added");
                        }
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Phone and amount should be in number!!");
                    }

                }
            });

            // creating the delete buttons and adding the action performed method to it
            deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int i = table.getSelectedRow();
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "Select a row to delete!!");
                    } else {
                        try {
                            long phoneNumber = Long.parseLong(textField3.getText());
                            int amount = Integer.parseInt(textField5.getText());
                            if (textField3.getText().length() != 10) {
                                JOptionPane.showMessageDialog(null, "Phone number must have exactly 10 digits.");

                            } else if (amount < 0) {
                                JOptionPane.showMessageDialog(null, "Initial balance must be non-negative.");
                            } else if (textField.getText().isEmpty() || textField2.getText().isEmpty()
                                    || textField3.getText().isEmpty() || textField4.getText().isEmpty()
                                    || textField5.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "There is no record as such !!");
                            } else if (amount > 999999999) {
                                JOptionPane.showMessageDialog(null, "There is no record as such !!");
                            } else if (!isValidEmailFormat(textField4.getText())) {
                                JOptionPane.showMessageDialog(null, "Invalid email format!");

                            } else {
                                String deleteString = "DELETE FROM customer WHERE Customer_ID = '"
                                        + mouseClick
                                        + "';";
                                String deleteAccount = "DELETE FROM account WHERE Customer_ID = '"
                                        + mouseClick
                                        + "';";
                                try {
                                    Connection connection = DriverManager.getConnection(dbURL, username, password);
                                    Statement statement = connection.createStatement(ResultSet.CONCUR_UPDATABLE,
                                            ResultSet.TYPE_SCROLL_INSENSITIVE);
                                    int rset3 = statement
                                            .executeUpdate("Delete from loan where Customer_ID='" + mouseClick + "';");
                                    int rSet2 = statement.executeUpdate(deleteAccount);
                                    int rset = statement.executeUpdate(deleteString);

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                model.removeRow(i);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "There is no record as such !!");
                        }
                    }

                }
            });

            // Creating the update button and adding an action listener to the button
            updateButton = new JButton("Update");
            updateButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "Select a row to be Updated!!");
                    } else {
                        try {
                            long phoneNumber = Long.parseLong(textField3.getText());
                            int amount = Integer.parseInt(textField5.getText());
                            if (textField3.getText().length() != 10) {
                                JOptionPane.showMessageDialog(null, "Phone number must have exactly 10 digits.");

                            } else if (amount < 0) {
                                JOptionPane.showMessageDialog(null, "Initial balance must be non-negative.");
                            } else if (textField.getText().isEmpty() || textField2.getText().isEmpty()
                                    || textField3.getText().isEmpty() || textField4.getText().isEmpty()
                                    || textField5.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Fill the values!!");
                            } else if (amount > 999999999) {
                                JOptionPane.showMessageDialog(null, "Too large Balance !!");
                            }

                            else if (!isValidEmailFormat(textField4.getText())) {
                                JOptionPane.showMessageDialog(null, "Invalid email format!");

                            } else {
                                String updateString = "UPDATE customer SET First_Name = '" + textField.getText()
                                        + "', Last_Name = '" + textField2.getText() + "', Phone = '"
                                        + textField3.getText()
                                        + "', Email = '" + textField4.getText() + "' WHERE Customer_ID = '" + mouseClick
                                        + "'";
                                String accountString = "UPDATE account SET Balance = '" + textField5.getText()
                                        + "', Account_Type = '" + typeBox.getSelectedItem() + "' WHERE Customer_ID = '"
                                        + mouseClick
                                        + "'";
                                try {
                                    Connection connection = DriverManager.getConnection(dbURL, username, password);
                                    Statement statement = connection.createStatement(ResultSet.CONCUR_UPDATABLE,
                                            ResultSet.TYPE_SCROLL_INSENSITIVE);
                                    int rset = statement.executeUpdate(updateString);
                                    Statement statement2 = connection.createStatement(ResultSet.CONCUR_UPDATABLE,
                                            ResultSet.TYPE_SCROLL_INSENSITIVE);

                                    int rSet2 = statement2.executeUpdate(accountString);

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                int i = table.getSelectedRow();
                                String selectedItem = (String) intersetBox.getSelectedItem();
                                String[] words = selectedItem.split(", ");

                                model.setValueAt(textField.getText(), i, 1);
                                model.setValueAt(textField2.getText(), i, 2);
                                model.setValueAt(textField3.getText(), i, 3);
                                model.setValueAt(textField4.getText(), i, 4);
                                model.setValueAt(textField5.getText(), i, 5);
                                model.setValueAt(typeBox.getSelectedItem(), i, 6);
                                model.setValueAt(words[0], i, 7);
                                JOptionPane.showMessageDialog(null, "Successfully Updated");
                            }
                        } catch (Exception Number) {
                            JOptionPane.showMessageDialog(null, "Should contain appropriate Values.");
                            
                        }
                    }

                }
            });

            clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    textField.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textField5.setText("");
                }
            });

            homeButton = new JButton("Home");
            homeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Home home = new Home();
                    frame.dispose();
                }
            });

            buttonPanel.add(createButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(homeButton);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            frame.setContentPane(contentPanel);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private boolean isValidEmailFormat(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if (atIndex >= dotIndex) {
            return false;
        }

        if (dotIndex >= email.length() - 1) {
            return false;
        }

        return true;
    }

}
