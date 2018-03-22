/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import com.fazecast.jSerialComm.SerialPort;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author delai
 */
public class AMASPPacketSender extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaxHeight(650);
        stage.setMaxWidth(850);
        stage.setMinHeight(650);
        stage.setMinWidth(850);
        stage.setTitle("AMASP Packet Sender 1.0");
        stage.show();
        
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
