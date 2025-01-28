import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class ShowRecord extends JFrame {
    Statement statement;
    static Container content;
    static JTextField searchInfo;
    static JButton searchButton;
    static JComboBox<String> dropdown;
    static JPanel panel4;
    static JFormattedTextField from_date;
    static JFormattedTextField to_date;
    private static String info;
    private static String action;
    private static String filter;

    private static String start_date;
    private static String end_date;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == searchButton) {
                filter = dropdown.getSelectedItem().toString();
                if (searchInfo.getText() == null || from_date.getText() == null || to_date.getText() == null ||
                        searchInfo.getText().equals("") || from_date.getText().equals("") || to_date.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter all the information!",
                            "Missing Information", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    start_date = from_date.getText();
                    end_date = to_date.getText();
                    info = searchInfo.getText();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    LocalDateTime now = LocalDateTime.now();
                    try {
                        from_date.setValue(format.parse("2000-01-01"));
                        to_date.setValue(format.parse(now.toString()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    searchInfo.setText("");
                    ResultSet r = null; // Result is here!!!!!!!!!!!
                    if (filter.equals("Book ID")) {
                        if ((!info.substring(0,1).equals("B")) && (!info.substring(0, 1).equals("b"))) {
                            info = "B" + info;
                        }
                        if (info.substring(0,1).equals("b")) {  // sometimes users will use lowercase
                            info = "B" + info.substring(1);
                        }
                        String sql1 = "CALL searchLoan('" + start_date + "', '" + end_date + "')";
                        String sql2 = "SELECT * FROM result WHERE book_id = '" + info + "' ORDER BY borrow_date";
                        try {
                            statement.execute(sql1);
                            r = statement.executeQuery(sql2);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else if (filter.equals("Customer ID")) {
                        if ((!info.substring(0,1).equals("C")) && (!info.substring(0, 1).equals("c"))) {
                            info = "C" + info;
                        }
                        if (info.substring(0,1).equals("c")) {  // sometimes users will use lowercase
                            info = "C" + info.substring(1);
                        }
                        String sql1 = "CALL searchLoan('" + start_date + "', '" + end_date + "')";
                        String sql2 = "SELECT * FROM result WHERE customer_id = '" + info + "' ORDER BY borrow_date";
                        try {
                            statement.execute(sql1);
                            r = statement.executeQuery(sql2);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    //Get books
                    ArrayList<String[]> books = new ArrayList<>();
                    String[] attributes = {"record_id", "book_id", "customer_id", "borrow_date", "return_date"};
                    try {
                        while (r.next()) {
                            String[] b = new String[attributes.length];
                            for (int i = 0; i < attributes.length; i++) {
                                b[i] = r.getString(attributes[i]);
                            }
                            books.add(b);
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    String[][] data = new String[books.size()][attributes.length];
                    for (int i = 0; i < books.size(); i++) {
                        data[i] = books.get(i);
                    }
                    JTable table = new JTable(data, attributes) {
                        @Override
                        public Dimension getMaximumSize() {
                            return new Dimension(300, 300);
                        }
                    };
                    table.setVisible(true);
                    table.getColumnModel().getColumn(1).setPreferredWidth(110);
                    table.getColumnModel().getColumn(2).setPreferredWidth(140);
                    table.getColumnModel().getColumn(3).setPreferredWidth(140);
                    table.getColumnModel().getColumn(4).setPreferredWidth(170);
                    String s = "Search by: " + filter + " : " + info + " (" + data.length + " book(s) are found)";
                    JPanel panel1 = new JPanel() {
                        @Override
                        public Dimension getMaximumSize() {
                            return getPreferredSize();
                        }
                    };
                    JLabel summary = new JLabel(s);
                    panel1.add(summary);
                    JPanel panel2 = new JPanel() {
                        @Override
                        public Dimension getMaximumSize() {
                            return getPreferredSize();
                        }
                    };
                    panel2.add(table.getTableHeader(), BorderLayout.NORTH);
                    JPanel panel3 = new JPanel() {
                        @Override
                        public Dimension getMaximumSize() {
                            return getPreferredSize();
                        }
                    };
                    panel3.add(table);
                    if (panel4 != null) {
                        content.remove(panel4);
                        setVisible(false);
                    }
                    panel4 = new JPanel() {
                        @Override
                        public Dimension getMaximumSize() {
                            return getPreferredSize();
                        }
                    };
                    BoxLayout layout = new BoxLayout(panel4, BoxLayout.PAGE_AXIS);
                    panel4.setLayout(layout);
                    panel4.add(panel1);
                    panel4.add(panel2);
                    panel4.add(panel3);
                    content.add(panel4);
                    setVisible(true);
                }
            }
        }
    };

    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            action = "close";
        }
    };

    public ShowRecord(Statement statement) throws Exception {
        super("Search Loan Record");
        this.statement = statement;
        content = Box.createVerticalBox();
        this.setSize(1000, 500);
        this.setMinimumSize(new Dimension(1000, 500));
        this.setMaximumSize(new Dimension(1000, 2000));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(windowListener);

        //Dropdown Menu
        JLabel select = new JLabel("Search by:");
        select.setVisible(true);
        String[] choices = {"Book ID", "Customer ID"};
        dropdown = new JComboBox<String>(choices);
        dropdown.setVisible(true);
        JPanel panel1 = new JPanel();
        panel1.add(select);
        panel1.add(dropdown);

        JLabel from = new JLabel("From:");
        from.setVisible(true);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        from_date = new JFormattedTextField(format);
        LocalDateTime now = LocalDateTime.now();
        from_date.setValue(format.parse("2000-01-01"));
        from_date.setColumns(10);
        JPanel panel5 = new JPanel();
        panel5.add(from);
        panel5.add(from_date);

        JLabel to = new JLabel("To:");
        to.setVisible(true);
        JPanel panel6 = new JPanel();
        to_date = new JFormattedTextField(format);
        to_date.setValue(format.parse(now.toString()));
        to_date.setColumns(10);;
        panel6.add(to);
        panel6.add(to_date);

        //Search Information
        JLabel labelInfo = new JLabel("Enter Book Information: ");
        searchInfo = new JTextField(15);

        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);

        panel1.add(labelInfo);
        panel1.add(searchInfo);
        panel1.add(searchButton);
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));

        panel3.add(panel1);
        JPanel panel8 = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        panel8.add(panel5,BorderLayout.EAST);
        panel8.add(panel6,BorderLayout.WEST);
        content.add(panel3);
        content.add(panel8);
        this.add(content);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 500);
    }
}

