import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class ButtonColor extends JButton {

    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    final Font font_name = new Font("Times New Roman", Font.PLAIN, 22);
    final int state = 0;

    public ButtonColor(String text, Dimension dimension) {
        super("<html> <center>" + text.replaceAll("\n", "<br/>")
                + "</center> </html>");
        this.setPreferredSize(dimension);
        setFont(font_name);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setContentAreaFilled(false);
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int buttonWidth = getWidth() - 1;
        int buttonHeight = getHeight() - 1;
        Shape shape = new RoundRectangle2D.Float(0, 0, buttonWidth,
                buttonHeight, WIDTH, HEIGHT);
        g2.setColor(new Color(110, 172, 183));
        g2.fill(shape);
        super.paintComponent(g);
    }
}

