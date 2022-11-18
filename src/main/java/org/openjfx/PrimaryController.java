package org.openjfx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    public void addClient() throws IOException {
        try {
            Socket s = new Socket("10.10.24.163", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(String.valueOf(Math.random()));
            dout.flush();
            dout.close();
            s.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
