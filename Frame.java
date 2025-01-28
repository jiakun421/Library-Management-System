import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Statement;


public class Frame extends JFrame {
    int flag = -1;

    public Frame(String title, Statement statement) throws Exception {
        super(title);
        setLayout(new BorderLayout());
        this.setSize(400, 450);
        this.setMinimumSize(new Dimension(400, 450));
        this.setMaximumSize(new Dimension(400, 450));
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int x = (dim.width / 2) - (this.getWidth() / 2);
        int y = (dim.height / 2) - (this.getHeight() / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(x, y);
        this.setLocationRelativeTo(null);

        JPanel parent = new JPanel(new GridLayout(0, 1));

        JPanel child0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        child0.setSize(200, 100);
        JLabel addDelete = new JLabel("Add/Delete information");
        addDelete.setSize(new Dimension(200, 100));
        Font f = new Font("TimesRoman", Font.PLAIN, 20);
        addDelete.setFont(f);
        addDelete.setBounds(80, 20, 250, 80);
        child0.add(addDelete);

        JPanel child00 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton book = new ButtonColor("Manage Book", new Dimension(180, 50));
        JButton customerButton = new ButtonColor("Manage Customer", new Dimension(180, 50));
        child00.add(book);
        child00.add(customerButton);

        JPanel child1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        child1.setSize(200, 100);
        JLabel borrowReturn = new JLabel("Book Transactions");
        borrowReturn.setSize(new Dimension(200, 100));
        Font f1 = new Font("TimesRoman", Font.PLAIN, 20);
        borrowReturn.setFont(f1);
        borrowReturn.setBounds(80, 20, 250, 80);
        child1.add(borrowReturn);


        JPanel child11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton borrow = new ButtonColor("Borrow Book", new Dimension(180, 50));
        JButton returnBook = new ButtonColor("Return Book", new Dimension(180, 50));
        child11.add(borrow);
        child11.add(returnBook);

        JPanel child2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        child2.setSize(200, 100);
        JLabel search = new JLabel("Search information");
        search.setSize(new Dimension(200, 100));
        Font f2 = new Font("TimesRoman", Font.PLAIN, 20);
        search.setFont(f2);
        search.setBounds(80, 20, 250, 80);
        child2.add(search);

        JPanel child22 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchBook = new ButtonColor("Search Book", new Dimension(180, 50));
        JButton showRecord = new ButtonColor("Show Record", new Dimension(180, 50));
        child22.add(searchBook);
        child22.add(showRecord);

        parent.add(child0);
        parent.add(child00);
        parent.add(child1);
        parent.add(child11);
        parent.add(child2);
        parent.add(child22);
        this.add(parent);

        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("icon.jpg"));
        this.setIconImage(logo.getImage());

        book.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                Manage manage = new Manage(statement);
                manage.setVisible(true);
                Frame.this.flag = 1;
                manage.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        Frame.this.flag = -1;
                    }
                });
            }
        });

        customerButton.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                Customer customer = new Customer(statement);
                SwingUtilities.invokeLater(() -> {
                    customer.setVisible(true);
                });
                customer.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        Frame.this.flag = -1;
                    }
                });
            }
        });

        borrow.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                Borrow borrow1 = new Borrow(statement);
                SwingUtilities.invokeLater(() -> {
                    borrow1.setVisible(true);
                });
                borrow1.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        Frame.this.flag = -1;
                    }
                });
            }
        });

        returnBook.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                ReturnBook fs = new ReturnBook(statement);
                fs.setVisible(true);
                Frame.this.flag = 1;
                fs.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        Frame.this.flag = -1;
                    }
                });
            }
        });

        searchBook.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                SearchBook searchBook1 = new SearchBook(statement);
                searchBook1.setVisible(true);
                Frame.this.flag = 1;
                searchBook1.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        Frame.this.flag = -1;
                    }
                });
            }
        });

        showRecord.addActionListener(e -> {
            if (Frame.this.flag == -1) {
                try {
                    ShowRecord showRecord1 = new ShowRecord(statement);
                    showRecord1.setVisible(true);
                    Frame.this.flag = 1;
                    showRecord1.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            Frame.this.flag = -1;
                        }
                    });
                } catch (Exception e1 ) {
                    e1.printStackTrace();
                }
            }
        });
    }
}