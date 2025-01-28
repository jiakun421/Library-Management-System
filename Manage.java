import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.Statement;

class Manage extends JFrame {
    static BigInteger isbn;
    static String book_id;
    static String titles;
    static String author;
    static String genre;
    static float price;
    static JTextArea isbnField;
    static JTextArea bookIdField;
    static JTextArea authorField;
    static JTextArea titleField;
    static JTextArea genreField;
    static JTextArea priceField;
    static JTextArea locationField;
    static JButton addButton;
    static JButton deleteButton;
    private static String action;
    Statement statement;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                action = "add";
                if (!isbnField.getText().equals("") && !titleField.getText().equals("") && !authorField.getText().equals("") && !genreField.getText().equals("")) {
                    try {
                        isbn = new BigInteger(isbnField.getText());
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "ISBN has to be 13 digits number",
                                "Book Info Error", JOptionPane.ERROR_MESSAGE);
                        action = "no";
                    }
                    BigInteger isbnMin = new BigInteger("1000000000000");
                    BigInteger isbnMax = new BigInteger("9999999999999");
                    if (isbn.compareTo(isbnMin) < 0 || isbn.compareTo(isbnMax) > 0) {
                        JOptionPane.showMessageDialog(null, "ISBN has to be 13 digits number",
                                "Book Info Error", JOptionPane.ERROR_MESSAGE);
                        action = "no";
                    }
                    titles = titleField.getText();
                    author = authorField.getText();
                    genre = genreField.getText();
                    isbnField.setText("");
                    titleField.setText("");
                    authorField.setText("");
                    genreField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter all information",
                            "Book Info Error", JOptionPane.ERROR_MESSAGE);
                    action = "no";
                }
                if (!action.equals("no")) {
                    try {
                        String sql = "select book_id from Book order by book_id desc limit 1";
                        ResultSet resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        String prevId = resultSet.getString("book_id");
                        prevId = prevId.substring(1);
                        int prevNumber = Integer.parseInt(prevId);
                        int IdNumber = prevNumber + 1;
                        String bookId = "B" + String.format("%08d", IdNumber);
                        String sql2 = "INSERT INTO Book VALUES('" + bookId + "'," + isbn + ",'available', 'L-1-30-009')";
                        int x = statement.executeUpdate(sql2);

                        price = (float) 19.99;
                        String addBookInfo = "INSERT INTO Book_Info VALUES(" + isbn + ",'" + titles + "','" + author + "','"
                                + genre + "'," + price + ")";
                        statement.execute(addBookInfo);
                        String message = "The book has been successfully added!";
                        JOptionPane.showMessageDialog(null, message, "successfully added"
                                , JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (e.getSource() == deleteButton) {
                action = "delete";
                book_id = bookIdField.getText();
                try {
                    action = "delete";
                    String ISBN = "";
                    String getISBN = "SELECT ISBN FROM Book WHERE book_id='" + book_id + "'";
                    ResultSet r1 = statement.executeQuery(getISBN);
                    int temp = 0;
                    while (r1.next()) {
                        ISBN = r1.getString("ISBN");
                        System.out.println(ISBN);
                    }
                    String getCount = "SELECT COUNT(ISBN) FROM Book WHERE ISBN = '" + ISBN + "'";
                    ResultSet r2 = statement.executeQuery(getCount);
                    while (r2.next()) {
                        if (r2.getString("COUNT(ISBN)").equals("1")) {
                            temp = 1;
                        }
                    }
                    if (temp == 1) {
                        String deleteInfo = "DELETE FROM Book_Info WHERE ISBN='" + ISBN + "'";
                        statement.execute(deleteInfo);
                    }
                    String deleteBook = "DELETE FROM Book WHERE book_id='" + book_id + "'";
                    statement.execute(deleteBook);
                    bookIdField.setText("");
                    String message = "The book has been successfully deleted!";
                    JOptionPane.showMessageDialog(null, message, "successfully deleted"
                            , JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    };

    public Manage(Statement statement) {
        super("Manage Book");
        this.statement = statement;
        setLayout(new BorderLayout());
        this.setSize(350, 450);
        this.setMinimumSize(new Dimension(350, 480));
        this.setMaximumSize(new Dimension(350, 480));
        Toolkit computer1 = Toolkit.getDefaultToolkit();
        Dimension dim = computer1.getScreenSize();
        int x = (dim.width / 2) - (this.getWidth() / 2);
        int y = (dim.height / 2) - (this.getHeight() / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(x, y);
        this.setLocationRelativeTo(null);
        addButton = new ButtonColor("Add Book", new Dimension(180, 40));
        addButton.addActionListener(actionListener);
        deleteButton = new ButtonColor("Delete Book", new Dimension(180, 40));
        deleteButton.addActionListener(actionListener);

        JPanel parent = new JPanel(new GridLayout(0, 1));
        JPanel ip = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel port = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel file = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel Genre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel Status = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel receive = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel adjust = new JPanel();
        JLabel isbnLabel = new JLabel("Enter the ISBN:");
        isbnField = new JTextArea();
        isbnField.setColumns(10);
        isbnField.setRows(1);
        ip.add(isbnLabel);
        ip.add(isbnField);

        JLabel titleLabel = new JLabel("Enter the title:");
        titleField = new JTextArea();
        titleField.setColumns(10);
        titleField.setRows(1);
        file.add(titleLabel);
        file.add(titleField);

        JLabel authorLabel = new JLabel("Enter the author name:");
        authorField = new JTextArea();
        authorField.setColumns(10);
        authorField.setRows(1);
        port.add(authorLabel);
        port.add(authorField);

        JLabel genreLabel = new JLabel("Enter the genre:");
        genreField = new JTextArea();
        genreField.setColumns(10);
        genreField.setRows(1);
        Genre.add(genreLabel);
        Genre.add(genreField);

        JTextArea received_view = new JTextArea();
        received_view.setSize(200, 300);
        received_view.setEditable(false);
        JScrollPane scroll_button = new JScrollPane(received_view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        receive.add(addButton);
        adjust.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 20));
        adjust.add(received_view);
        adjust.add(scroll_button);


        JLabel bookIdLabel = new JLabel("Enter the book_id:");
        bookIdField = new JTextArea();
        bookIdField.setColumns(10);
        bookIdField.setRows(1);
        deletePanel.add(bookIdLabel);
        deletePanel.add(bookIdField);
        JPanel delete = new JPanel(new FlowLayout(FlowLayout.LEFT));
        delete.add(deleteButton);

        parent.add(ip);
        parent.add(port);
        parent.add(file);

        parent.add(Genre);
        parent.add(receive);
        parent.add(Status);
        parent.add(new JSeparator(SwingConstants.HORIZONTAL));
        parent.add(deletePanel);
        parent.add(delete);
        parent.add(adjust);
        this.add(parent);
    }

    public static String getAction() {
        return action;
    }

    public static BigInteger getIsbn() {
        return isbn;
    }

    public static String getTitles() {
        return titles;
    }

    public static String getAuthor() {
        return author;
    }

    public static String getGenre() {
        return genre;
    }
}