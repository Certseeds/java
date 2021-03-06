package assignment2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

//https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/pie-chart.htm#CIHFDADD
public class PieChartSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13), new PieChart.Data("Oranges", 25), new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22), new PieChart.Data("Apples", 30));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
}