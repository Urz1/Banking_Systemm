import java.awt.event.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.awt.*;
import java.util.jar.Attributes.Name;

public class TransferPage extends JFrame implements ActionListener {
    String dbURL = "jdbc:mysql://localhost:3306/banking";
    String username = "root";
    String password = "Sad@qls1610-";
    String queryString = "select * from user";
    String Name;
    String Email;
    String AccountNumber;
    String Sex;
    String Balance;
    String BeforeWithdraw;

    // Creating some swing components
    JComboBox<String> SenderBox, RecieverBox;
    JTextField textField = new JTextField();

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    JLabel label, label1, label2, label3;
    JButton submitButton, BackButton;

    TransferPage() {
        String query = "SELECT * FROM Customer INNER JOIN account ON customer.Customer_ID=account.Customer_ID";
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creating a combobox and popullating the field
        SenderBox = new JComboBox<>();
        RecieverBox = new JComboBox<>();

        // creating a label
        label = new JLabel("TRANSACTION ");
        label1 = new JLabel("Amount to be transffered :");
        label2 = new JLabel("Recievers Account: ");
        label3 = new JLabel("Senders Account");

        setLayout(layout);

        try (

                Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);) {
            ResultSet rs = statement.executeQuery(query);

            // Adding account choice and amount along submit button to complete the
            submitButton = new JButton("Submit ");
            BackButton = new JButton("Back");
            submitButton.addActionListener(this);
            BackButton.addActionListener(this);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1.0;
            constraints.weighty = 0.0;
            constraints.insets = new Insets(5, 15, 10, 20);

            // adding the components to the frame
            addComponent(label, 0, 0, 1, 1); // Span across 2 columns

            // Add the table right below the label
            addComponent(textField, 1, 3, 1, 1); // Span across 2 columns
            addComponent(label1, 0, 3, 1, 1);

            // Add the text field and combo box side by side below the table
            addComponent(SenderBox, 0, 2, 1, 1); // Text field on the left
            addComponent(label3, 0, 1, 1, 1);
            addComponent(RecieverBox, 1, 2, 1, 1); // Combo box on the right
            addComponent(label2, 1, 1, 1, 1);

            // Add the submit button below, centered by spanning across both columns
            addComponent(submitButton, 1, 4, 1, 1); // Span across 2 columns
            addComponent(BackButton, 0, 4, 1, 1);

            while (rs.next()) {
                String name = rs.getString("First_Name");
                String id = rs.getString("Customer_ID");
                SenderBox.addItem(id + ", " + name);
                RecieverBox.addItem(id + ", " + name);
            }

            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
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

        // storing the values in the box as an array of individual elements
        String selectedItem = (String) SenderBox.getSelectedItem();
        String[] words = selectedItem.split(", ");

        String selectedItem2 = (String) RecieverBox.getSelectedItem();
        String[] words2 = selectedItem2.split(", ");
        System.out.println(words[1]);

        // accessing the value to be transffered to the user
        String amount = textField.getText();
        String transferString = "INSERT INTO fund_transfer(Senders_ID,Recievers_ID,Amount,Transfer_Date) Values('"
                + words[0] +
                "','" + words2[0] +
                "','" + textField.getText() +
                "','" + LocalDate.now() + "') ";
        if (e.getSource() == submitButton) {
            if (Integer.parseInt(words[0]) == Integer.parseInt(words2[0])) {
                JOptionPane.showMessageDialog(null, "you can't transfer for yourself!!");
            } else {
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

                                ResultSet resultSet = statement3
                                        .executeQuery(
                                                "Select balance from account where Customer_ID='" + words2[0] + "';");

                                while (resultSet.next()) {
                                    BeforeWithdraw = resultSet.getString("Balance");
                                }
                                if (Integer.parseInt(BeforeWithdraw) < amountInt) {
                                    JOptionPane.showMessageDialog(null, "Insufficient Balance!!");
                                } else {
                                    statement3.executeUpdate(
                                            "UPDATE `account` SET `Balance`=`Balance`+" + amount
                                                    + " WHERE `Customer_ID`="
                                                    + words[0]);
                                    statement3.executeUpdate("UPDATE `account` SET `Balance`=`Balance`-" + amount
                                            + " WHERE `Customer_ID`=" + words2[0]);

                                    statement3.executeUpdate(transferString);

                                    connection2.commit();
                                    JOptionPane.showMessageDialog(null, "Success!!");
                                }
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
            }

        } else if (e.getSource() == BackButton) {
            Home home = new Home();
            dispose();
        }

    }

}
