import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

class Home extends JFrame implements ActionListener {

    JButton Create_User,Clients,Deposit,Withdrawal,Transfer,Loan,Transaction_History,ImageButton;
    JToolBar toolBar;
    Home() {
        super("Banking System");// Calling the constructor of the parent class to name the frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setFont(new Font(getName(), ABORT, E_RESIZE_CURSOR));
         // Set the default foreground color
        UIManager.put("Label.foreground", Color.BLACK);
        
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\HP\\Desktop\\NotePad Project\\skeleton\\Banking-Courses.png");
        ImageIcon LogoImage = new ImageIcon("C:\\\\Users\\\\HP\\\\Desktop\\\\NotePad Project\\\\skeleton\\\\banking.png");
        JLabel label=new JLabel(backgroundImage);
        

        Image orginal=LogoImage.getImage();
        Image resizedImage=orginal.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon=new ImageIcon(resizedImage);

        // Creating a logo and adding it to the label component 
        JLabel label2=new JLabel(resizedIcon);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(label,BorderLayout.AFTER_LINE_ENDS);// inserting the image as an icon using jlabel
        getContentPane().setBackground(new Color(20, 31, 31));// changing the bgcolor of the frame

        // setting the components on the toolbar creating buttons and adding action listener to each elements 
        toolBar=new JToolBar();
        Create_User=new JButton("Create User");
        Create_User.addActionListener(this);

        Deposit=new JButton("Deposit");
        Deposit.addActionListener(this);

        Withdrawal=new JButton("Withdrawal");
        Withdrawal.addActionListener(this);

        Transfer=new JButton("Transfer");
        Transfer.addActionListener(this);

        Loan=new JButton("Loan");
        Loan.addActionListener(this);



        Create_User.setBackground(new Color(102, 153, 153));
        Border bborder=BorderFactory.createLineBorder(new Color(102, 153, 153), 10, true);
        Create_User.setBorder(bborder);

        
        

        // adding the componets to the toobar 
        toolBar.add(label2, BorderLayout.WEST);
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(Create_User,BorderLayout.WEST);       
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));
        toolBar.add(Deposit,BorderLayout.WEST);
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));//This line is used to insert a scattering property for the components on the toolbar 
        toolBar.add(Withdrawal,BorderLayout.WEST);
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));//This line is used to insert a scattering property for the components on the toolbar 
        toolBar.add(Transfer,BorderLayout.WEST);
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));//This line is used to insert a scattering property for the components on the toolbar 
        toolBar.add(Loan,BorderLayout.WEST);
        toolBar.add(Box.createRigidArea(new Dimension(20, 0)));//This line is used to insert a scattering property for the components on the toolbar 
        

        // Editing the toolbar 
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(Box.createRigidArea(new Dimension(50, 0)));//This line is used to insert a scattering property for the components on the toolbar 
        toolBar.setPreferredSize(new Dimension(800,80));
        toolBar.setBackground(new Color(148, 184, 184));
        toolBar.setFloatable(false);// avoid being resizable and floatable by the user 
        
        add(toolBar, BorderLayout.NORTH);
        

        // Image image = backgroundImage.getImage();
        // Image resizedImage = image.getScaledInstance(1300, 700, Image.SCALE_SMOOTH);

        // // Create a new ImageIcon with the resized image
        // ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        // JLabel backgroundLabel = new JLabel(resizedImageIcon);

        // // Set the size and position of the label
        // backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        // getContentPane().setLayout(null);

        // // Add the background label to the content pane
        // getContentPane().add(backgroundLabel);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Create_User){
            CreateUser createUser=new CreateUser();
            dispose();
        }
        else if(e.getSource()==Clients){
            UItemplate uItemplate=new UItemplate();
            dispose();
        }
        else if (e.getSource()==Deposit){
            Deposit deposit=new Deposit();
            dispose();
        }
        else if (e.getSource()==Withdrawal){
            Withdrawal withdrawal=new Withdrawal();
            dispose();

        }
        else if(e.getSource()==Transfer){
            TransferPage transferPage=new TransferPage();
            dispose();
        }
        else if (e.getSource()==Loan){
            Loan loan=new Loan();
            dispose();
        }
    }

}

public class Bank {
    public static void main(String[] args) {

        // UItemplate uItemplate=new UItemplate();
        // Withdrawal withdrawal=new Withdrawal("10001237");
        // TransferPage transferPage=new TransferPage("10001237");
        // UItemplate home = new UItemplate();
        Home home=new Home();
    }
}
