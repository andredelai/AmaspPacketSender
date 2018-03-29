/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.fazecast.jSerialComm.SerialPort;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
/**
 *
 * @author delai
 */

public class FXMLMainWindowController implements Initializable {

      
    
    private AMASPPacketSender main;
    
    @FXML
    private AnchorPane aPneSendRec;
    
    @FXML
    private GridPane gPneSendRec;

    @FXML
    private GridPane gPneSender;

    @FXML
    private AnchorPane gPneMaster;
    
    @FXML
    private AnchorPane aPneSender;
    
    @FXML
    private AnchorPane aPneReceiver;
    
    @FXML
    private Label lbelStatus;
    
    @FXML
    private MenuItem mIteFileConnect;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        aPneSendRec.setDisable(true);
        aPneSender.setDisable(false);
        aPneReceiver.setDisable(true);
        
    }

    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        if(mIteFileConnect.getText() == "Disconnect")
        {
            main.disconnectSerial();
        }
        else
        {
            main.showSerialConf();
        }
    }
    
    @FXML
    private void handleMIteFileExitAction(ActionEvent event)
    {
        
    }
    
    public void init(AMASPPacketSender mainController) 
    {	
        main = mainController;
    }
    
    public void setStatusLabel(String status)
    {
        lbelStatus.setText(status);
    }
    
    public void setConMenuItemText(String text)
    {
        mIteFileConnect.setText(text);
    }
    
    public void enableAllFields(boolean enabled)
    {
        aPneSendRec.setDisable(!enabled);
    }
    
    public void enableSenderFields(boolean enabled)
    {
        aPneSender.setDisable(!enabled);
    }
    
    public void enableReceiverFields(boolean enabled)
    {
        aPneReceiver.setDisable(!enabled);
    }
}
