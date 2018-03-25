/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author delai
 */
public class AMASPPacketSender extends Application {
    
    private Stage stage2;
    
    //@FXML
    private MainController mainCtrl;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainWindow.fxml"));
        Parent node1 = FXMLLoader.load(getClass().getResource("FXMLSerialCom.fxml"));
        
        Scene scene = new Scene(root);
        Scene scene2 = new Scene(node1);
        
        stage.setScene(scene);
        stage.setMaxHeight(650);
        stage.setMaxWidth(850);
        stage.setMinHeight(650);
        stage.setMinWidth(850);
        stage.setTitle("AMASP Packet Sender 1.0");
        
        stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setMaxHeight(650);
        stage2.setMaxWidth(850);
        stage2.setMinHeight(650);
        stage2.setMinWidth(850);
        stage2.setTitle("Serial Connection Configuration");
        stage2.initOwner(stage);
        stage2.initModality(Modality.WINDOW_MODAL);
        
        mainCtrl = new MainController();
        
        mainCtrl.nothing();
        
        mainCtrl.initialize(stage, stage2);
        mainCtrl.setStatusLabel("TESTE");
        
        stage.show();     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    
    
}
