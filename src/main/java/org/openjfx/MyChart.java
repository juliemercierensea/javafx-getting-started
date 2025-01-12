package org.openjfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

public class MyChart extends VBox {
    XYChart.Series<Double, Double> series = new XYChart.Series<>();
    private Double x = 0.0;

    public LineChart buildSampleLineChart() {
        series.getData().add(new XYChart.Data<>(0.0, 0.0));
        series.getData().add(new XYChart.Data<>(0.7, 0.5));
        series.getData().add(new XYChart.Data<>(1.0, 0.632));
        series.getData().add(new XYChart.Data<>(2.0, 0.865));
        series.getData().add(new XYChart.Data<>(3.0, 0.95));
        series.getData().add(new XYChart.Data<>(4.0, 0.982));
        series.getData().add(new XYChart.Data<>(5.0, 0.993));
        LineChart lc = new LineChart(
                new NumberAxis("Time Constant", 0.0, 5.0, 1),
                new NumberAxis("Voltage (Vs)", 0.0, 1.0, 0.1)
        );
        lc.getData().add(series);
        return lc;
    }

    public void add(Double args) {
        series.getData().add(new XYChart.Data<>(0.0, args));
    }

    public MyChart() {
        getChildren().add(buildSampleLineChart());
        Button myButton = new Button("Add a random value");
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Runnable updater = new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("Button clicked");
                                //Random rand = new Random(); //méthode 1
                                //double randomT = 5 * rand.nextDouble(); //méthode 1
                                //double randomV = rand.nextDouble(); //méthode 1
                                //series.getData().add(new XYChart.Data<>(randomT, randomV)); //méthode 1
                                series.getData().add(new XYChart.Data<>(Math.random() * 5, Math.random())); //méthode 2
                                //series.getData().add(new XYChart.Data<>(x++, Math.random())); //méthode 3
                                if (series.getData().size() <= 5)
                                    series.getData().remove(0);
                            }
                        };
                        while (true) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                            }
                            Platform.runLater(updater);
                        }
                    }
                });

                myThread.setDaemon(true);
                myThread.start();
            }
        });
        getChildren().add(myButton);
    }
}
