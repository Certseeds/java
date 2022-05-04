package week05;
/**
 * @Github: https://github.com/Certseeds
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-03-17 22:14:23
 * @LastEditors : nanoseeds
 * @LastEditTime : 2020-03-17 22:14:23
 */

import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainUIfxml implements Initializable {
    // Screen size
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    // The random generator MUST be static
    private static Random rand_generator;
    // Keep track of the current location of the window

    private double x;
    private double y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// TODO Auto-generated method stub
        rand_generator = new Random();
    }

    public void moveWindow(MouseEvent me) {
        final Node node = (Node) me.getSource();
// Returns a reference to the button
        final Stage stage = (Stage) node.getScene().getWindow();
        final double height = screenBounds.getHeight();
        final double width = screenBounds.getWidth();
// Add a fixed value to make sure that the Window
// moves far enough
        final double x_move = width / 10 + rand_generator.nextDouble() * width / 2;
        final double y_move = height / 10 + rand_generator.nextDouble() * height / 2;
// As x and y represent the upper left corner, we
// take care of not having part of most of the
// window outside the screen
        System.out.println(this.x);
        System.out.println(this.y);
        this.x = (double) ((long) (this.x + x_move) % (long) (width -
                stage.getWidth()));
        this.y = (double) ((long) (this.y + y_move) % (long) (height -
                stage.getHeight()));
        System.out.println(this.x);
        System.out.println(this.y);
        stage.setX(this.x);
        stage.setY(this.y);
    }
}