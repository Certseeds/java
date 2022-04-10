public class Main {
    public static void main(String[] args) {
        final MainPanel mainPanel = new MainPanel();
        SwingFacade.launch(SwingFacade.createTitledPanel("Observer Pattern Example", mainPanel), "");
    }
}
