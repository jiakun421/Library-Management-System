import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginGUI
 * <p>
 * Represents the login GUI
 *
 * @author Luka Narisawa
 * @version December 12, 2022
 */

public class SQLLogin extends JComponent implements Runnable {
    public static String url;
    public static String username;
    static JFrame loginFrame;
    static JTextField urlField;
    static JTextField usernameField;
    static JTextField passwordField;
    static JButton loginButton;
    private static String password;
    private static String action;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                url = urlField.getText();
                username = usernameField.getText();
                password = passwordField.getText();
                action = "login";
                loginFrame.dispose();
            }
        }
    };
    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent evt) {
            action = "closed";
        }
    };

    public SQLLogin() {
        action = null;
        url = null;
        username = null;
        password = null;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void run() {
        loginFrame = new JFrame("SQL Login");
        Container content = loginFrame.getContentPane();
        content.setLayout(new BorderLayout());
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.addWindowListener(windowListener);
        JLabel urlLabel = new JLabel("JDBC URL");
        JLabel labelU = new JLabel("SQL Username");
        JLabel labelP = new JLabel("SQL Password");
        urlField = new JTextField("jdbc:mysql://localhost:3306/cs348project", 25); // text field for url
        urlField.addActionListener(actionListener);
        usernameField = new JTextField("root", 10); // text field for username
        usernameField.addActionListener(actionListener);
        passwordField = new JTextField("vnfjriCMDKEO1234", 20); // text field for password
        passwordField.addActionListener(actionListener);
        loginButton = new JButton("Login"); // login button
        loginButton.addActionListener(actionListener);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(urlLabel);
        panel1.add(urlField);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(labelU);
        panel2.add(usernameField);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(labelP);
        panel3.add(passwordField);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));
        panel4.add(panel1);
        panel4.add(panel2);
        panel4.add(panel3);
        panel4.add(loginButton);
        content.add(panel4, BorderLayout.CENTER);
        loginFrame.getRootPane().setDefaultButton(loginButton);
        loginFrame.setVisible(true);
    }
}