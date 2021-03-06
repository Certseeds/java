import java.awt.*;

public class GreenBall extends Ball implements Subject {
    public GreenBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    public void update(int x, int y) {}

    public void update(char keyChar) {
        switch (keyChar) {
            case 'a' -> this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
            case 'd' -> this.setXSpeed(Math.abs(this.getXSpeed()));
            case 'w' -> this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
            case 's' -> this.setYSpeed(Math.abs(this.getYSpeed()));
        }
    }

    @Override
    public void update(Ball ball) {

    }

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer temp : observers) {
            temp.update(this.getX(), this.getY());
        }
    }

    @Override
    public void notifyObservers(char keyChar) {}

    @Override

    public void move(Boolean start) {
        if (start) {
            notifyObservers();
        }
        super.move(start);
    }
}
