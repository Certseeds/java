public interface Observer {
    void update(char keyChar);

    void update(int x, int y);

    boolean isCollision();
}
