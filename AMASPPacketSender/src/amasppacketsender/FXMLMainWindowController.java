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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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
    
    @FXML
    private Spinner <Integer> spinMRPDevId;
    
    @FXML
    private TextField txFdMRPMsg;
    
    // Spinner value factory.
    SpinnerValueFactory<Integer> valueFactory = //
       new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4095, 0);
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        aPneSendRec.setDisable(true);
        aPneSender.setDisable(false);
        aPneReceiver.setDisable(true);
        spinMRPDevId.setValueFactory(valueFactory);
        //spinMRPDevId.getEditor().setTextFormatter(value);
        
    }

    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        if("Disconnect".equals(mIteFileConnect.getText()))
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
    
    @FXML
    private void handleBtnMRPSendAction(ActionEvent event)
    {   
        main.getMaster().sendRequest(spinMRPDevId.getValue(), txFdMRPMsg.getText(), txFdMRPMsg.getLength());
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
