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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author delai
 */
public class AMASPPacketSender extends Application {
    
    private Stage stage;
    private Stage stage2;
            
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("FXMLMainWindow.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FXMLSerialCom.fxml"));
        
        FXMLMainWindowController mainWindowCtrl = new FXMLMainWindowController();
        FXMLSerialComController serialComCtrl = new FXMLSerialComController();
        
        mainWindowCtrl.init(this);
        serialComCtrl.init(this);
        
        loader1.setController(mainWindowCtrl);
        loader2.setController(serialComCtrl);      
        
        Parent main = loader1.load();
        Parent serialConf = loader2.load();
        
        Scene scene = new Scene(main);
        Scene scene2 = new Scene(serialConf);
        
        stage.setScene(scene);
        stage.setMaxHeight(650);
        stage.setMaxWidth(850);
        stage.setMinHeight(650);
        stage.setMinWidth(850);
        stage.setTitle("AMASP Packet Sender 1.0");
        
        stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setMaxHeight(170);
        stage2.setMaxWidth(573);
        stage2.setMinHeight(170);
        stage2.setMinWidth(573);
        stage2.initOwner(stage);
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initStyle(StageStyle.UNDECORATED);
        //stage2.setTitle("Serial Connection Configuration");
        stage2.initOwner(stage);
        stage2.initModality(Modality.WINDOW_MODAL);   
              
        stage.show(); //shows stage     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    public void exitProgram()
    {
        stage.close();
    }
    
    public void hideSerialConf()
    {
        stage2.hide();
    }
    
    public void showSerialConf()
    {
        stage2.show();
    }
    
}
