import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class Ball extends JComponent {
    public static final int TOTAL_NUM = 10;
    private static int count = 0;
    private Color color;
    private int x, y;
    private int xSpeed, ySpeed;
    private final int ballSize;
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

    public static int getCount() {return count;}

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


}

