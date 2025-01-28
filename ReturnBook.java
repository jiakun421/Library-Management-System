import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Statement;
import javax.swing.*;
import java.util.Objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

class ReturnBook extends JFrame{
    static JButton add;
    static String book_id;

    private static String action;

    static JTextArea bookIdField;
    Statement statement;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == add) {
                action = "add";
                if (!bookIdField.getText().equals("")) {
                    book_id = bookIdField.getText();
                    bookIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter all information",
                            "Book Info Error", JOptionPane.ERROR_MESSAGE);
                    action = "no";
                }
                if (action != "no") {
                    try {
                        String sql = "select record_id from Loan_Record where book_id = '"+ book_id +
                                "' and return_date is NULL";
                        ResultSet resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        String record_id = resultSet.getString("record_id");
                        String sql2 = "UPDATE Loan_Record SET return_date = curdate() WHERE record_id = '"+record_id+"'";
                        int x = statement.executeUpdate(sql2);
                        String sql3 = "UPDATE Book set status = 'available' where book_id ='" + book_id + "'";
                        int y = statement.executeUpdate(sql3);
                        JOptionPane.showMessageDialog(null, "Return Success!", "Return Book"
                                , JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    };

    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent evt) {
            action = "closed";
        }
    };
    public ReturnBook(Statement statement) {
        super("Return Book");
        this.statement = statement;
        setLayout(new BorderLayout());
        this.setSize(500,200);
        Toolkit computer1 = Toolkit.getDefaultToolkit();
        Dimension dim = computer1.getScreenSize();
        int x = (dim.width/2) - (this.getWidth()/2);
        int y = (dim.height/2) - (this.getHeight()/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(x, y);
        this.setLocationRelativeTo(null);

        JPanel parent = new JPanel();

        add = new ButtonColor("Return Book",new Dimension(120,50));
        JPanel return_box = new JPanel();
        return_box.add(add);
        add.addActionListener(actionListener);

        Box BK_id = Box.createHorizontalBox();
        JLabel bk = new JLabel("Enter the Book_ID:");
        bookIdField = new JTextArea();
        bookIdField.setColumns(10);
        bookIdField.setRows(1);
        BK_id.add(bk);
        BK_id.add(bookIdField);


        parent.add(BK_id);
        parent.add(return_box);
        this.add(parent);
    }
}
