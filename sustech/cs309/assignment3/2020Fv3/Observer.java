public interface Observer {
    void update(char keyChar);

    void update(int x, int y);

    void update(Ball ball);
}
