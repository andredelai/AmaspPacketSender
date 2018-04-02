/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import AMASPJava.*;

/**
 *
 * @author delai
 */
public class AMASPPacketSender extends Application {

    /**
     * @return the master
     */
    public AMASPSerialMaster getMaster() {
        return master;
    }
    
    private Stage stage;
    private Stage stage2;
    private SerialPort serialCom;
    private AMASPSerialMaster master;
    private FXMLMainWindowController mainWindowCtrl;
    private FXMLSerialComController serialComCtrl;
            
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("FXMLMainWindow.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FXMLSerialCom.fxml"));
        
        mainWindowCtrl = new FXMLMainWindowController();
        serialComCtrl = new FXMLSerialComController();
        
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
    
    public void setSerialConfig(SerialPort port, int baudRate, int parity, int dataBits, int stopBits )
    {
        String aux = " ";
        
        serialCom = port;
        serialCom.setBaudRate(baudRate);
        serialCom.setParity(parity);
        serialCom.setNumDataBits(dataBits);
        serialCom.setNumStopBits(stopBits);
        
        if(!serialCom.openPort())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error to open the serial port. Check if Another device is using the port.");
            alert.showAndWait();
            return;
        }
        
        master = new AMASPSerialMaster();
        getMaster().begin(serialCom);
        
        switch(serialCom.getParity())
        {
            case 0:
                aux = "None";
                break;
            case 1:
                aux = "Odd";
                break;
            case 2:
                aux = "Even";
                break;
        }
        
        mainWindowCtrl.setStatusLabel(serialCom.getSystemPortName() + " " + serialCom.getBaudRate() + " " + serialCom.getNumDataBits() + " " + aux + " " + serialCom.getNumStopBits());
        mainWindowCtrl.setConMenuItemText("Disconnect");
        mainWindowCtrl.enableAllFields(true);
    }
    
    public void disconnectSerial()
    {
        if(serialCom != null)
        {
            if(serialCom.isOpen())
            {
                serialCom.closePort();
            }
            
        }
         mainWindowCtrl.setStatusLabel("No connection");
         mainWindowCtrl.setConMenuItemText("Connect");
         mainWindowCtrl.enableAllFields(false);
    }
    
}
