import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class SearchBook extends JFrame {
    static Box content;
    static JTextField searchInfo;
    static JButton searchButton;
    static JComboBox<String> dropdown;
    static JPanel panel4;
    private static String info;
    private static String action;
    private static String filter;
    Statement statement;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == searchButton) {
                action = "search";
                filter = dropdown.getSelectedItem().toString();
                if (searchInfo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the book information!",
                            "Missing Information", JOptionPane.ERROR_MESSAGE);
                } else {
                    ResultSet r1 = null;
                    info = searchInfo.getText();
                    searchInfo.setText("");
                    if (filter.equals("Book ID")) {
                        if ((!info.substring(0, 1).equals("B")) && (!info.substring(0, 1).equals("b"))) {
                            info = "B" + info;
                        }
                        if (info.substring(0, 1).equals("b")) {  // sometimes users will use lowercase
                            info = "B" + info.substring(1);
                        }
                        String sql = "SELECT Book.book_id AS Book_ID, Book_Info.ISBN AS ISBN, Book_Info.title AS Title, Book_Info.author AS Author, " +
                                "Book_Info.genre AS Genre, Book_Info.price AS Price, Book.location_id AS Location, Book.status AS Status " +
                                "FROM Book_Info JOIN Book ON Book.ISBN = Book_Info.ISBN " +
                                "WHERE Book.book_id = '" + info + "'";
                        try {
                            r1 = statement.executeQuery(sql);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (filter.equals("ISBN")) {
                        String sql = "SELECT Book.book_id AS Book_ID, Book_Info.ISBN AS ISBN, Book_Info.title AS Title, Book_Info.author AS Author, " +
                                "Book_Info.genre AS Genre, Book_Info.price AS Price, Book.location_id AS Location, Book.status AS Status " +
                                "FROM Book_Info JOIN Book ON Book.ISBN = Book_Info.ISBN " +
                                "WHERE Book.ISBN = '" + info + "'";
                        try {
                            r1 = statement.executeQuery(sql);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (filter.equals("Title")) {
                        String[] titles = info.split(" ");
                        StringBuilder newInfo = new StringBuilder();
                        for (String t : titles) {
                            t = t.toLowerCase();
                            String first = t.substring(0, 1).toUpperCase();
                            newInfo.append(" ").append(first).append(t.substring(1));
                        }
                        String sql = "SELECT Book.book_id AS Book_ID, Book_Info.ISBN AS ISBN, Book_Info.title AS Title, Book_Info.author AS Author, " +
                                "Book_Info.genre AS Genre, Book_Info.price AS Price, Book.location_id AS Location, Book.status AS Status " +
                                "FROM Book_Info JOIN Book ON Book.ISBN = Book_Info.ISBN " +
                                "WHERE Book_Info.title LIKE '%" + newInfo.substring(1) + "%'";
                        try {
                            r1 = statement.executeQuery(sql);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (filter.equals("Author")) {
                        String[] names = info.split(" ");
                        StringBuilder name = new StringBuilder();
                        for (String n : names) {
                            n = n.toLowerCase();
                            String first = n.substring(0, 1).toUpperCase();
                            name.append(" ").append(first).append(n.substring(1));
                        }
                        String sql = "SELECT Book.book_id AS Book_ID, Book_Info.ISBN AS ISBN, Book_Info.title AS Title, Book_Info.author AS Author, " +
                                "Book_Info.genre AS Genre, Book_Info.price AS Price, Book.location_id AS Location, Book.status AS Status " +
                                "FROM Book_Info JOIN Book ON Book.ISBN = Book_Info.ISBN " +
                                "WHERE Book_Info.author LIKE '%" + name.substring(1) + "%'";
                        try {
                            r1 = statement.executeQuery(sql);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else {
                        String[] genres = info.split(" ");
                        StringBuilder genre = new StringBuilder();
                        for (String g : genres) {
                            g = g.toLowerCase();
                            String first = g.substring(0, 1).toUpperCase();
                            genre.append(" ").append(first).append(g.substring(1));
                        }
                        String sql = "SELECT Book.book_id AS Book_ID, Book_Info.ISBN AS ISBN, Book_Info.title AS Title, Book_Info.author AS Author, " +
                                "Book_Info.genre AS Genre, Book_Info.price AS Price, Book.location_id AS Location, Book.status AS Status " +
                                "FROM Book_Info JOIN Book ON Book.ISBN = Book_Info.ISBN " +
                                "WHERE Book_Info.genre LIKE '%" + genre.substring(1) + "%'";
                        try {
                            r1 = statement.executeQuery(sql);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    //Get books
                    ArrayList<String[]> books = new ArrayList<>();
                    String[] attributes = {"Book_ID", "ISBN", "Title", "Author", "Genre", "Price", "Location", "Status"};
                    try {
                        while (r1.next()) {
                            String[] b = new String[attributes.length];
                            for (int i = 0; i < attributes.length; i++) {
                                b[i] = r1.getString(attributes[i]);
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
                    table.getColumnModel().getColumn(5).setPreferredWidth(50);
                    table.getColumnModel().getColumn(6).setPreferredWidth(90);
                    table.getColumnModel().getColumn(7).setPreferredWidth(70);
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

    public SearchBook(Statement statement) {
        super("Search Book");
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
        String[] choices = {"Book ID", "ISBN", "Title", "Author", "Genre"};
        dropdown = new JComboBox<String>(choices);
        dropdown.setVisible(true);
        JPanel panel1 = new JPanel();
        panel1.add(select);
        panel1.add(dropdown);

        //Search Information
        JLabel labelInfo = new JLabel("Enter the information of the book:");
        searchInfo = new JTextField(15);
        searchInfo.addActionListener(actionListener);
        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        JPanel panel2 = new JPanel();
        panel2.add(labelInfo);
        panel2.add(searchInfo);
        panel2.add(searchButton);
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        panel3.add(panel1);
        panel3.add(panel2);
        content.add(panel3);
        this.add(content);
    }

    public String getInfo() {
        return info;
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 500);
    }
}
