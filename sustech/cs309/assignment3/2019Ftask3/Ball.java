import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public abstract class Ball extends JComponent implements Observer {
    public static final int TOTAL_NUM = 10;
    private static int count = 0;
    private final int ballSize;
    private Color color;
    private int x, y;
    private int xSpeed, ySpeed;
    private boolean visible;

    public Ball(Color color, int xSpeed, int ySpeed, int ballSize) {
        this.color = color;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.ballSize = ballSize;

        this.visible = true;
        this.x = (int) (Math.random() * 580);
        this.y = (int) (Math.random() * 580);

        count++;

        this.setSize(ballSize, ballSize);
    }

    public static int getCount() {
        return count;
    }

    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(this.getColor());
            setLocation(x, y);
            g.fillOval(0, 0, this.getBallSize(), this.getBallSize());
        }
    }

    public void move() {
        final int newX = this.getX() + this.getXSpeed();
        final int newY = this.getY() + this.getYSpeed();

        this.setX(newX);
        this.setY(newY);

        if (newX <= 0) {
            this.setXSpeed(Math.abs(getXSpeed()));
        } else if (newX >= 600 - this.getBallSize()) {
            this.setXSpeed(-1 * Math.abs(getXSpeed()));
        }

        if (newY <= 0) {
            this.setYSpeed(Math.abs(getYSpeed()));
        } else if (newY > 600 - this.getBallSize()) {
            this.setYSpeed(-1 * Math.abs(getYSpeed()));
        }
    }

    public boolean isIntersect(Ball b) {
        final int diffX = this.getX() - b.getX();
        final int diffY = this.getY() - b.getY();
        final double dis = (this.getBallSize() + b.getBallSize()) / 2.0;

        return (diffX * diffX) + (diffY * diffY) < dis * dis;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public double get_distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean update_public(int x, int y, int distanceMax, int distance_jump, Color color) {
        if (get_distance(x, y, this.getX(), this.getY()) >= distanceMax) {
            return false;
        }
        final int high = Math.min(630, this.getY() + distance_jump);
        final int low = Math.min(0, this.getY() - distance_jump);
        final int left = Math.min(0, this.getX() - distance_jump);
        final int right = Math.max(720, this.getX() + distance_jump);
        final double leftup = get_distance(x, y, left, high);
        final double leftdown = get_distance(x, y, left, low);
        final double rightup = get_distance(x, y, right, high);
        final double rightdown = get_distance(x, y, right, low);
        int level = 1;
        int vert = 1;
        if (leftup > Math.max(leftdown, Math.max(rightup, rightdown))) {
            level = -1;
            vert = -1;
        } else if (rightup > Math.max(rightup, Math.max(leftup, leftdown))) {
            vert = -1;
        } else if (leftdown > Math.max(leftup, Math.max(rightup, rightdown))) {
            level = -1;
        }
        this.setX(this.getX() + distance_jump * level);
        this.setY(this.getY() + distance_jump * vert);
        final int xspeed = Math.abs(this.getXSpeed());
        final int yspeed = Math.abs(this.getYSpeed());
        this.setXSpeed(level * xspeed);
        this.setYSpeed(vert * yspeed);
        System.out.println("jump!");
        this.setColor(color);
        return true;
    }
}

