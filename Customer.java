import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;


public class Customer extends JFrame {
    private final JTextField firstnameTextField;
    private final JTextField lastnameTextField;
    private final JTextField idTextField;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JLabel firstnameLabel;
    private final JLabel lastnameLabel;
    private final JLabel idLabel;
    private final Statement statement;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                String firstname = firstnameTextField.getText();
                String lastname = lastnameTextField.getText();
                firstnameTextField.setText("");
                lastnameTextField.setText("");
                if (firstname.equals("") || lastname.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter valid first name and" +
                            " last name for customer", "Add Customer", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String sql = "select customer_id from Customer order by customer_id desc limit 1";
                        ResultSet resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        String prevId = resultSet.getString("customer_id");
                        prevId = prevId.substring(1);
                        int prevNumber = Integer.parseInt(prevId);
                        int IdNumber = prevNumber + 1;
                        String customerId = "C" + String.format("%08d", IdNumber);
                        String customerName = firstname + " " + lastname;
                        String sql2 = "INSERT INTO Customer VALUES('" + customerId + "','" + customerName + "')";
                        statement.execute(sql2);
                        firstnameTextField.setText("");
                        lastnameTextField.setText("");
                        String message = "The customer has been successfully added! His/Her id is: " + customerId;
                        JOptionPane.showMessageDialog(null, message, "successfully added"
                                , JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (e.getSource() == deleteButton) {
                String id = idTextField.getText();
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter valid customer's ID"
                            , "Delete Customer", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String getID = "SELECT count(*) as c FROM Customer WHERE customer_id='" + id + "'";
                        ResultSet r1 = statement.executeQuery(getID);
                        r1.next();
                        String res = r1.getString("c");
                        if (res.equals("0")) {
                            JOptionPane.showMessageDialog(null, "The customer ID entered does not" +
                                    " exist, please try again", "Delete Customer", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String delete = "DELETE FROM Customer WHERE customer_id='" + id + "'";
                            statement.execute(delete);
                            idTextField.setText("");
                            String message = "The customer has been successfully deleted!";
                            JOptionPane.showMessageDialog(null, message, "successfully deleted"
                                    , JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    };

    public Customer(Statement statement) {
        super("Manage Customer");
        this.statement = statement;
        setLayout(new BorderLayout());
        this.setSize(300, 380);
        this.setMinimumSize(new Dimension(300, 360));
        this.setMaximumSize(new Dimension(300, 360));
        Toolkit computer1 = Toolkit.getDefaultToolkit();
        Dimension dim = computer1.getScreenSize();
        int x = (dim.width / 2) - (this.getWidth() / 2);
        int y = (dim.height / 2) - (this.getHeight() / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(x, y);
        this.setLocationRelativeTo(null);

        JPanel parent = new JPanel(new GridLayout(0, 1));

        JPanel Location = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel firstname = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstnameLabel = new JLabel("First Name:");
        firstnameTextField = new JTextField(10);
        firstname.add(firstnameLabel);
        firstname.add(firstnameTextField);

        JPanel lastname = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lastnameLabel = new JLabel("Last Name:");
        lastnameTextField = new JTextField(10);
        lastname.add(lastnameLabel);
        lastname.add(lastnameTextField);

        JPanel add = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new ButtonColor("Add Customer", new Dimension(180, 50));
        addButton.addActionListener(actionListener);
        add.add(addButton);

        JPanel id = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idLabel = new JLabel("Customer ID:");
        idTextField = new JTextField(10);
        id.add(idLabel);
        id.add(idTextField);

        JPanel delete = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteButton = new ButtonColor("Delete Customer", new Dimension(180, 50));
        deleteButton.addActionListener(actionListener);
        delete.add(deleteButton);

        parent.add(firstname);
        parent.add(lastname);
        parent.add(add);
        parent.add(new JSeparator(SwingConstants.HORIZONTAL));
        parent.add(id);
        parent.add(delete);
        this.add(parent);
    }
}
