/* CS209A_JAVA2/Assignment_02/CS209_DataVisualisation
   Copyright (C) 2020  nanoseeds

   CS209A_JAVA2/Assignment_02/CS209_DataVisualisation is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   CS209A_JAVA2/Assignment_02/CS209_DataVisualisation2 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.
   */
/**
 * @Github: https://github.com/Certseeds/CS209A_JAVA2/Assignment_02/CS209_DataVisualisation
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-04-16 19:10:22
 * @LastEditors : nanoseeds
 */
package assignment2;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Controller {

    @FXML
    private BarChart<String, Number> gene_chart;
    @FXML
    private TabPane in_tabpane;
    @FXML
    private MenuButton chart_choice;
    private XYChart.Series<String, Number> series_sorted;
    private Stage stage;
    private List<Integer> intervals = new ArrayList<>();
    private List<String> interval_names = new ArrayList<>();
    private String root;
    private Counter counter;

    @FXML
    public void init(String path) {
        if (parse_root_path(path) && parse_intervals(path)) {
            get_graph(new File(root));
        }
    }


    public void get_graph(File file) {
        Counter middle_count = counter;
        try {
            middle_count = new Counter(file, intervals, interval_names);
        } catch (FileNotFoundException ffe) {
            System.out.println("FIle not found");
            return;
        } catch (NullPointerException npe) {
            show_alert("Unknown Error", "Choose another directiory", "Choose once again.");
        }
        counter = middle_count;
        set_UI();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void set_UI() {
        init_series();
        set_gene_chart();
        setIn_tabpane(CHART.PIE_CHART);
    }

    public void init_series() {
        this.series_sorted = new XYChart.Series<>();
        for (String key : counter.getSizeMap().keySet()) {
            int count = 0;
            for (Counter.IntervalEntity ie : counter.getSizeMap().get(key)) {
                count += ie.getCount();
            }
            series_sorted.getData().add(new XYChart.Data<String, Number>(key, count));
        }
        this.series_sorted.getData()
                .sort(Comparator.comparingDouble(d -> -1 * d.getYValue().doubleValue()));
    }

    public void set_gene_chart() {
        gene_chart.getData().clear();
        gene_chart.setTitle("File quantity statistics");
        gene_chart.getXAxis().setLabel("File Category");
        gene_chart.getYAxis().setLabel("File Count");
        for (XYChart.Data<String, Number> i : this.series_sorted.getData()) {
            XYChart.Series<String, Number> temp = new XYChart.Series<>();
            temp.setName(i.getXValue());
            temp.getData().add(new XYChart.Data<>("", i.getYValue()));
            gene_chart.getData().add(temp);
        }
        gene_chart.setBarGap(10);
    }

    public void setIn_tabpane(CHART chart) {
        in_tabpane.getTabs().clear();
        switch (chart) {
            case PIE_CHART: {
                for (XYChart.Data<String, Number> i : this.series_sorted.getData()) {
                    Tab tab = new Tab();
                    tab.setText(i.getXValue());
                    Chart temp = get_pieChart(counter.getSizeMap().get(i.getXValue()));
                    tab.setContent(temp);
                    in_tabpane.getTabs().add(tab);
                }
                break;
            }
            case BAR_CHART1: {
                for (XYChart.Data<String, Number> i : this.series_sorted.getData()) {
                    Tab tab = new Tab();
                    tab.setText(i.getXValue());
                    Chart temp = get_BarChart1(counter.getSizeMap().get(i.getXValue()));
                    tab.setContent(temp);
                    in_tabpane.getTabs().add(tab);
                }
                break;
            }
            case BAR_CHART2: {
                for (XYChart.Data<String, Number> i : this.series_sorted.getData()) {
                    Tab tab = new Tab();
                    tab.setText(i.getXValue());
                    Chart temp = get_BarChart2(counter.getSizeMap().get(i.getXValue()));
                    tab.setContent(temp);
                    in_tabpane.getTabs().add(tab);
                }
                break;
            }
        }
        System.out.println(1);
    }

    public PieChart get_pieChart(List<Counter.IntervalEntity> lcie) {
        PieChart will_return = new PieChart();
        for (Counter.IntervalEntity cie : lcie) {
            if (0.0f == cie.getRatio()) {
                continue;
            }
            will_return.getData().add(new PieChart.Data(cie.getName(), cie.getRatio()));
        }
        return will_return;
    }

    public StackedBarChart<String, Number> get_BarChart1(List<Counter.IntervalEntity> lcie) {
        final StackedBarChart<String, Number> will_return =
                new StackedBarChart<String, Number>(new CategoryAxis(), new NumberAxis());
        for (Counter.IntervalEntity cie : lcie) {
            XYChart.Series<String, Number> temp = new XYChart.Series<>();
            if (0.0f == cie.getRatio()) {
                continue;
            }
            temp.setName(cie.getName());
            temp.getData().add(new XYChart.Data<>("", cie.getRatio()));
            will_return.getData().add(temp);
        }
        //will_return.setBarGap(10);
        return will_return;
    }

    public BarChart<String, Number> get_BarChart2(List<Counter.IntervalEntity> lcie) {
        final BarChart<String, Number> will_return =
                new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());
        for (Counter.IntervalEntity cie : lcie) {
            XYChart.Series<String, Number> temp = new XYChart.Series<>();
            if (0.0f == cie.getRatio()) {
                continue;
            }
            temp.setName(cie.getName());
            temp.getData().add(new XYChart.Data<>("", cie.getRatio()));
            will_return.getData().add(temp);
        }
        //will_return.setBarGap(10);
        return will_return;
    }

    public boolean parse_root_path(String path) {
        Yaml yaml = new Yaml();
        Map<String, Object> root_map = null;
        //System.out.println(System.getProperty("user.dir"));
        try (BufferedReader yamlReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8.name()))) {
            root_map = yaml.load(yamlReader);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Config file doesn't exist.");
        } catch (IOException e) {
            System.err.println("IO Exception happened.");
            e.printStackTrace();
        }
        if (null == root_map) {
            show_alert("Warning",
                    "Yml file can not load",
                    "Please select a yml which can load");
            return false;
        }
        String middle_root = "";
        if (root_map.containsKey("Root")) {
            middle_root = (String) root_map.get("Root");
            File test = new File(middle_root);
            if (test.exists() && test.isDirectory()) {
                this.root = middle_root;
            } else {
                show_alert("Wrong Path",
                        "Root_Path is wrong",
                        "Please ensure the");
                return false;
            }
        } else {
            show_alert("No root path",
                    "Yml file dont have root path",
                    "Please select a yml which have root path");
            return false;
        }
        return true;

    }

    public boolean parse_intervals(String path) {
        Yaml yaml = new Yaml();
        Map<String, Object> root_map = null;
        //System.out.println(System.getProperty("user.dir"));
        try (BufferedReader yamlReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8.name()))) {
            root_map = yaml.load(yamlReader);
        } catch (FileNotFoundException e) {
            show_alert("Warning !",
                    "Yml file do not exist",
                    "Please choose a exist file");
        } catch (IOException e) {
            System.err.println("IO Exception happened.");
            e.printStackTrace();
        }
        if (null == root_map) {
            show_alert("Warning !",
                    "Yml file can not load",
                    "Please select a yml which have information");
            return false;
        }
        List<Integer> middle_intervals = new ArrayList<>();
        List<String> middle_interval_names = new ArrayList<>();
        if (root_map.containsKey("Intervals")) {
            middle_intervals = (List<Integer>) root_map.get("Intervals");
        } else {
            show_alert("No Intervals",
                    "Yml file dont have Intervals",
                    "Please select a yml which have Intervals");
            return false;
        }
        if (root_map.containsKey("IntervalNames")) {
            middle_interval_names = (List<String>) root_map.get("IntervalNames");
        } else {
            show_alert("No IntervalNames",
                    "Yml file dont have IntervalNames",
                    "Please select a yml which have IntervalNames");
            return false;
        }
        if (middle_intervals.size() + 1 == middle_interval_names.size()) {
            this.intervals = middle_intervals;
            this.interval_names = middle_interval_names;
        } else {
            show_alert("Warning!",
                    "intervals and interval_names do not match",
                    "Please choose yml which have correct file format");
            return false;
        }
        return true;
    }

    public void handle_set_root() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("");
        File defaultDirectory = new File("." + File.separator);
        directoryChooser.setInitialDirectory(defaultDirectory);
        File file = directoryChooser.showDialog(getStage());
        if (file != null) {
            root = file.getAbsolutePath();
            get_graph(new File(root));
        }
    }

    public void handle_set_intervals() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "YML files (*.yml)", "*.yml");
        fileChooser.getExtensionFilters().add(extFilter);
        File defaultDirectory = new File("." + File.separator);
        fileChooser.setInitialDirectory(defaultDirectory);
        // Show save file dialog
        File file = fileChooser.showOpenDialog(getStage());
        if (null != file && file.exists()) {
            //System.out.println(file.getPath());
            if (parse_intervals(file.getAbsolutePath())) {
                get_graph(new File(root));
            }
        }
    }

    public void handle_set_config() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "YML files (*.yml)", "*.yml");
        fileChooser.getExtensionFilters().add(extFilter);
        File defaultDirectory = new File("." + File.separator);
        fileChooser.setInitialDirectory(defaultDirectory);
        // Show save file dialog
        File file = fileChooser.showOpenDialog(getStage());
        if (null != file && file.exists()) {
            init(file.getAbsolutePath());
        }
    }

    public void show_alert(String str1, String str2, String str3) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner((getStage()));
        alert.setTitle(str1);
        alert.setHeaderText(str2);
        alert.setContentText(str3);
        alert.showAndWait();
    }

    public void handle_pie_chart() {
        chart_choice.setText("Pie Chart");
        setIn_tabpane(CHART.PIE_CHART);
    }

    public void handle_bar_chart() {
        chart_choice.setText("Bar Chart1");
        setIn_tabpane(CHART.BAR_CHART1);
    }

    public void handle_bar_chart2() {
        chart_choice.setText("Bar Chart2");
        setIn_tabpane(CHART.BAR_CHART2);
    }

    private enum CHART {PIE_CHART, BAR_CHART1, BAR_CHART2}
}
