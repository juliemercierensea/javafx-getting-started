package org.openjfx;

import javafx.application.Platform;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChartUpdater extends Thread {
    private MyChart myChart;

    public ChartUpdater(MyChart myChart) {
        this.myChart = myChart;
    }

    @Override
    public void run() {
        //server
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept(); // established connection
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = (String) dis.readUTF();
            Thread updater = new Thread(new Runnable() {
                @Override
                public void run() {
                    myChart.add(Double.valueOf(str));
                }
            });
            Platform.runLater(updater);
            System.out.println("message= " + str);
            ss.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
