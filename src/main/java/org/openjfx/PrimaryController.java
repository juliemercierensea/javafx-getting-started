package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.chart.Chart;

public class PrimaryController {
    @FXML
    MyChart myChart = new MyChart();

    @FXML
    private void initialize() {
        ChartUpdater server = new ChartUpdater(myChart);
        server.setDaemon(true);
        server.start();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
