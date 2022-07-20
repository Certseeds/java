import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MainFrame mainGameFrame = new MainFrame();
            mainGameFrame.setVisible(true);
        });
    }
}
