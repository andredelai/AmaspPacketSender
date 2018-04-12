/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import java.awt.event.InputMethodEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.awt.event.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ContextMenuEvent;

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
    private Label lbelStatus;

    @FXML
    private MenuItem mIteFileConnect;

    @FXML
    private Spinner<Integer> spinMRPDevId;
    
    @FXML
    private Spinner<Integer> spinSRPDevId;
    
    @FXML
    private Spinner<Integer> spinMCEPErCode;

    @FXML
    private Spinner<Integer> spinSCEPErCode;

    @FXML
    private Spinner<Integer> spinSIPIntCode;

    @FXML
    private TextField txFdMRPMsg;
    
    @FXML
    private TextField txFdSRPMsg;
    
    @FXML
    private TextField txFdSentPkt;

    @FXML
    private RadioButton rBtnMaster;

    @FXML
    private RadioButton rBtnSlave;

    @FXML
    private AnchorPane aPneMaster;

    @FXML
    private AnchorPane aPneSlave;

    @FXML
    private ToggleGroup masSlvRadGroup;

    @FXML
    private Label lbelHexMRPId;
    
    @FXML
    private Label lbelHexSRPId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        aPneSendRec.setDisable(true);
        aPneSender.setDisable(false);
        spinMRPDevId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4095, 0));
        spinSRPDevId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4095, 0));
        spinMCEPErCode.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinSCEPErCode.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinSIPIntCode.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinMRPDevId.valueProperty().addListener((obs, oldValue, newValue) -> {
            {
                lbelHexMRPId.setText(String.format("Hex: %03X", (spinMRPDevId.getValue())));
            }
        });      
        
        spinSRPDevId.valueProperty().addListener((obs, oldValue, newValue) -> {
            {
                lbelHexSRPId.setText(String.format("Hex: %03X", (spinSRPDevId.getValue())));
            }
        });      
        
        spinMRPDevId.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:                                    
                    spinMRPDevId.increment(1);
                    break;
                case DOWN:
                    spinMRPDevId.decrement(1);
                    break;
            }
        });
    }

    
    //Event Handlers*
    
    @FXML
    public void handleBtnMRPSendAction (ActionEvent event)
    {
        main.getMaster().sendRequest(spinMRPDevId.getValue(), txFdMRPMsg.getText(), txFdMRPMsg.getLength());
        txFdSentPkt.setText("<MRP><" + String.format("%03X", spinMRPDevId.getValue()) + "><" + String.format("%02X", txFdMRPMsg.getLength()) + "><" +txFdMRPMsg.getText() + ">" );
    }
    
    @FXML
    public void handleBtnMCEPSendAction (ActionEvent event)
    {
        main.getMaster().sendError(spinMRPDevId.getValue(), spinMCEPErCode.getValue());
        txFdSentPkt.setText("<CEP><" + String.format("%03X", spinMRPDevId.getValue()) + "><" + String.format("%02X", spinMCEPErCode.getValue()) + ">");
    }
    
    @FXML
    public void handleBtnSRPSendAction (ActionEvent event)
    {
        main.getSlave().sendResponse(spinSRPDevId.getValue(), txFdSRPMsg.getText() , txFdSRPMsg.getLength());
        txFdSentPkt.setText("<SRP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", txFdSRPMsg.getLength()) + "><" +txFdSRPMsg.getText() + ">" );
    }
    
    @FXML
    public void handleBtnSCEPSendAction (ActionEvent event)
    {
        main.getSlave().sendError(spinSRPDevId.getValue(), spinSCEPErCode.getValue());
        txFdSentPkt.setText("<CEP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", spinSCEPErCode.getValue()) + ">");
    }
    
    @FXML
    public void handleBtnSIPSendAction (ActionEvent event)
    {
        main.getSlave().sendInterruption(spinSRPDevId.getValue(), spinSIPIntCode.getValue());
        txFdSentPkt.setText("<SIP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", spinSIPIntCode.getValue()) + ">");
    }
    
    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        if ("Disconnect".equals(mIteFileConnect.getText())) {
            main.disconnectSerial();
        } else {
            main.showSerialConf();
        }
    }

    @FXML
    private void handleRBtnMasterAction(ActionEvent event) {
        aPneMaster.setDisable(false);
        aPneSlave.setDisable(true);
    }

    @FXML
    private void handleRBtnSlaveAction(ActionEvent event) {
        aPneMaster.setDisable(true);
        aPneSlave.setDisable(false);
    }

    @FXML
    private void handleMIteFileExitAction(ActionEvent event) {
        main.exitProgram();
        
    }

    public void init(AMASPPacketSender mainController) {
        main = mainController;
    }

    public void setStatusLabel(String status) {
        lbelStatus.setText(status);
    }

    public void setConMenuItemText(String text) {
        mIteFileConnect.setText(text);
    }

    public void enableAllFields(boolean enabled) {
        aPneSendRec.setDisable(!enabled);
        handleRBtnMasterAction(null);

    }

    public void enableSenderFields(boolean enabled) {
        aPneSender.setDisable(!enabled);
    }

}
