import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.awt.*;

public class Deposit extends JFrame implements ActionListener {
    String dbURL = "jdbc:mysql://localhost:3306/banking";
    String username = "root";
    String password = "Sad@qls1610-";
    String queryString = "select * from account";
    String Name;
    String Email;
    String AccountNumber;
    String Sex;
    String Balance;
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    JComboBox comboBox;
    JTextField textField = new JTextField(20);
    JButton submitButton, BackButton;
    JLabel label, label2;
    String accId;

    Deposit() {
        String query = "SELECT * FROM Customer INNER JOIN account ON customer.Customer_ID=account.Customer_ID";
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(layout);
        label = new JLabel("Deposit ");
        label2 = new JLabel("Amount:");

        // add(label, BorderLayout.CENTER);

        try (
                Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

        ) {
            ResultSet rs = statement.executeQuery(query);

            comboBox = new JComboBox<>();
            submitButton = new JButton("Submit ");
            BackButton = new JButton("Return ");
            submitButton.addActionListener(this);
            BackButton.addActionListener(this);
            while (rs.next()) {
                String name = rs.getString("First_Name");
                String id = rs.getString("Customer_ID");
                comboBox.addItem(id + ", " + name);
            }

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1.0;
            constraints.weighty = 0.0;
            constraints.insets = new Insets(5, 15, 10, 20);

            // adding the components to the frame
            // constraints.anchor=GridBagConstraints.EAST;
            addComponent(label, 1, 0, 1, 1); // Span across 2 columns
            addComponent(comboBox, 0, 1, 2, 1);
            addComponent(label2, 0, 2, 1, 1);
            addComponent(textField, 1, 2, 1, 1);

            constraints.anchor = GridBagConstraints.LINE_END;
            addComponent(BackButton, 0, 3, 1, 1);
            constraints.anchor = GridBagConstraints.LINE_START;
            addComponent(submitButton, 1, 3, 1, 1);
            setVisible(true);
        } catch (Exception e) {

        }

    }

    private void addComponent(Component component,
            int row,
            int column,
            int width,
            int height) {
        // Check if the component is null
        if (component == null) {
            // Print an error message and return
            System.err.println("Cannot add a null component");
            return;
        }
        constraints.gridx = row;
        constraints.gridy = column;
        constraints.gridheight = height;
        constraints.gridwidth = width;

        layout.setConstraints(component, constraints); // set the constraints on the component

        add(component);
    }

    public void actionPerformed(ActionEvent e) {

        // accessing the value to be deposited
        String selectedItem = (String) comboBox.getSelectedItem();
        String[] words = selectedItem.split(", ");
        String query2 = "SELECT * FROM customer left JOIN Account ON customer.Customer_ID=Account.Customer_ID";

        String amount = textField.getText();

        if (e.getSource() == submitButton) {
            
            try {
                int amountInt = Integer.parseInt(textField.getText());
                // implementing transaciton while money transfer
                if (amountInt > 0) {

                    try {
                        Connection connection2 = DriverManager.getConnection(dbURL, username, password);
                        connection2.setAutoCommit(false);

                        try {

                            Statement statement3 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);

                            statement3.executeUpdate("UPDATE `account` SET `Balance`=`Balance`+" + amount
                                    + " WHERE `Customer_ID`=" + words[0]);
                            ResultSet rSet = statement3.executeQuery("Select * from account");
                            
                            while (rSet.next()) {
                                accId = rSet.getString("Account_ID");
                            }

                            String transactionString = "INSERT INTO transactions(Datte,Amount,TType,Account_ID) Values('"
                                    + LocalDate.now() +
                                    "','" + textField.getText() +
                                    "','" + "Deposit" +
                                    "','" + accId + "') ";
                            statement3.executeUpdate(transactionString);

                            connection2.commit();
                            JOptionPane.showMessageDialog(null, "Success!!");

                        } catch (Exception exception) {
                            // System.out.println("sdfd");
                            exception.printStackTrace();
                        } finally {
                            connection2.close();
                        }
                    } catch (SQLException e1) {

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Amount must be greater than 0!!");
                }

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Must be integer value!!");

            }

        } else if (e.getSource() == BackButton) {
            dispose();
            Home home = new Home();
        }

    }
}
