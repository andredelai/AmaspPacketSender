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
/**
 *
 * @author delai
 */

public class FXMLMainWindowController implements Initializable {

      
    
    private AMASPPacketSender main;
    private SerialPort serialCom;
    
    @FXML
    private AnchorPane aPneSendRec;
    
    @FXML
    private GridPane gPneSendRec;

    @FXML
    private GridPane gPneSender;

    @FXML
    private AnchorPane gPneMaster;
    
    @FXML
    private Label lbelStatus;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aPneSendRec.disableProperty();
        aPneSendRec.setDisable(true);
        
    }

    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        main.showSerialConf();
    }
    
    @FXML
    private void handleMIteFileExitAction(ActionEvent event)
    {
        
    }
    
    public void init(AMASPPacketSender mainController) {
	main = mainController;
    }
    
    public void setStatusLabel(String status)
    {
        lbelStatus.setText(status);
    }
}
