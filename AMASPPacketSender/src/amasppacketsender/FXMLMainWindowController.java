/*    
  Created by Andre L. Delai.

  This is a free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package amasppacketsender;

import AMASPJava.AMASPSerial;
import AMASPJava.AMASPSerial.PacketType;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;

/**
 *
 * @author delai
 */
public class FXMLMainWindowController implements Initializable {

    private AMASPPacketSender main;
    private Thread tReceiver;
    private volatile boolean tReceivRunning = true;

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
    private MenuItem mIteAboutPktSend;

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
    private TextField txFdRecPktType;

    @FXML
    private TextField txFdRecDevId;

    @FXML
    private TextField txFdRecCodeLen;

    @FXML
    private TextField txFdRecMsg;

    @FXML
    private TextField txFdRecECA;

    @FXML
    volatile private TextArea txArRecPktHist;

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

    @FXML
    private Label lbelHexMRPEc;
    
    @FXML
    private Label lbelHexSRPEc;
    
    @FXML
    private Label lbelHexSRPIc;
    
    @FXML
    private ComboBox<String> cboxErrorCheck;

    AMASPSerial.PacketData packetData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> ErroCheckAlgList = FXCollections.observableArrayList();

        ErroCheckAlgList.addAll("None", "XOR8", "Checksum16", "LRC16", "Fletcher16", "CRC16");

        cboxErrorCheck.setItems(ErroCheckAlgList);
        cboxErrorCheck.setValue("None");

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
        
        spinMCEPErCode.valueProperty().addListener((obs, oldValue, newValue) -> {
            {
                lbelHexMRPEc.setText(String.format("Hex: %02X", (spinMCEPErCode.getValue())));
            }
        });
        
        spinSCEPErCode.valueProperty().addListener((obs, oldValue, newValue) -> {
            {
                lbelHexSRPEc.setText(String.format("Hex: %02X", (spinSCEPErCode.getValue())));
            }
        });
        
        spinSIPIntCode.valueProperty().addListener((obs, oldValue, newValue) -> {
            {
                lbelHexSRPIc.setText(String.format("Hex: %02X", (spinSIPIntCode.getValue())));
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
    public void handleBtnMRPSendAction(ActionEvent event) {

        spinMRPDevId.increment();
        spinMRPDevId.decrement();
        main.getMaster().sendRequest(spinMRPDevId.getValue(), txFdMRPMsg.getText(), txFdMRPMsg.getLength());
        txFdSentPkt.setText("<MRP><" + String.format("%03X", spinMRPDevId.getValue()) + "><" + String.format("%02X", txFdMRPMsg.getLength()) + "><" + txFdMRPMsg.getText() + ">");
    }

    @FXML
    public void handleBtnMCEPSendAction(ActionEvent event) {
        spinMRPDevId.increment();
        spinMRPDevId.decrement();
        spinMCEPErCode.increment();
        spinMCEPErCode.decrement();
        main.getMaster().sendError(spinMRPDevId.getValue(), spinMCEPErCode.getValue());
        txFdSentPkt.setText("<CEP><" + String.format("%03X", spinMRPDevId.getValue()) + "><" + String.format("%02X", spinMCEPErCode.getValue()) + ">");
    }

    @FXML
    public void handleBtnSRPSendAction(ActionEvent event) {

        spinSRPDevId.increment();
        spinSRPDevId.decrement();
        main.getSlave().sendResponse(spinSRPDevId.getValue(), txFdSRPMsg.getText(), txFdSRPMsg.getLength());
        txFdSentPkt.setText("<SRP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", txFdSRPMsg.getLength()) + "><" + txFdSRPMsg.getText() + ">");
    }

    @FXML
    public void handleBtnSCEPSendAction(ActionEvent event) {
        spinSRPDevId.increment();
        spinSRPDevId.decrement();
        spinSCEPErCode.increment();
        spinSCEPErCode.decrement();
        main.getSlave().sendError(spinSRPDevId.getValue(), spinSCEPErCode.getValue());
        txFdSentPkt.setText("<CEP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", spinSCEPErCode.getValue()) + ">");
    }

    @FXML
    public void handleBtnSIPSendAction(ActionEvent event) {   
        spinSRPDevId.increment();
        spinSRPDevId.decrement();
        spinSIPIntCode.increment();
        spinSIPIntCode.decrement();
        main.getSlave().sendInterruption(spinSRPDevId.getValue(), spinSIPIntCode.getValue());
        txFdSentPkt.setText("<SIP><" + String.format("%03X", spinSRPDevId.getValue()) + "><" + String.format("%02X", spinSIPIntCode.getValue()) + ">");
    }

    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        if ("Disconnect".equals(mIteFileConnect.getText())) {
            receiverOn(false);
            main.disconnectSerial();
        } else {
            main.showSerialConf();
        }
    }
    
    @FXML
    private void handleMIteAboutAction(ActionEvent event) {
        main.showAbout();
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
        receiverOn(false);
        main.exitProgram();

    }

    @FXML
    private void handleBtonRecHistClr(ActionEvent event) {
        txArRecPktHist.clear();
    }

    @FXML
    private void handleCboxErChkAction(ActionEvent event) {
        switch (cboxErrorCheck.getValue()) {
            case "None":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.None);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.None);
                break;

            case "XOR8":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.XOR8);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.XOR8);
                break;

            case "Checksum16":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.checksum16);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.checksum16);
                break;

            case "LRC16":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.LRC16);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.LRC16);
                break;

            case "Fletcher16":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.fletcher16);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.fletcher16);
                break;

            case "CRC16":
                main.getMaster().setErrorCheckType(AMASPSerial.ErrorCheckType.CRC16);
                main.getSlave().setErrorCheckType(AMASPSerial.ErrorCheckType.CRC16);
                break;
        }

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
        if (enabled == true) {
            receiverOn(true);
        }
        txArRecPktHist.clear();
        txFdRecPktType.clear();
        txFdRecDevId.clear();
        txFdRecCodeLen.clear();
        txFdRecMsg.clear();
    }

    public void enableSenderFields(boolean enabled) {
        aPneSender.setDisable(!enabled);
    }

    @SuppressWarnings("empty-statement")
    public void receiverOn(boolean status) {
        if (status == true) {
            if (tReceiver != null) {
                tReceivRunning = false; //signalize to stop the receiver thread
                while (tReceiver.isAlive()); //wait until the thread dies
                tReceivRunning = true;
            }

            tReceiver = new Thread() {

                @Override
                public void run() {

                    String strAux1, strAux2;
                    while (tReceivRunning) {
                        //txArRecPktHist.setText(txArRecPktHist.getText() + "check!\r\n");
                        packetData = main.getMaster().readPacket();
                        if (packetData.getType() != PacketType.Timeout) {
                            txFdRecPktType.setText(packetData.getType().toString());
                            txFdRecDevId.setText(String.format("%03d", packetData.getDeviceId()));
                            txFdRecCodeLen.setText(String.format("%03d", packetData.getCodeLength()));
                            txFdRecECA.setText(packetData.getErrorCheckType().toString());
                            strAux1 = "";

                            strAux2 = "<" + packetData.getType().toString() + ">";
                            strAux2 += "<" + packetData.getErrorCheckType().getValue() + ">";
                            strAux2 += "<" + String.format("%03X", packetData.getDeviceId()) + ">";
                            
                            if ((packetData.getType() == PacketType.MRP) || (packetData.getType() == PacketType.SRP)) {
                                
                                strAux2 += "<" + String.format("%03X", packetData.getCodeLength()) + ">";
                                
                                for (int i = 0; i < packetData.getCodeLength(); i++) {
                                    strAux1 += String.format("%02X", packetData.getMessage()[i]) + " ";
                                }
                                txFdRecMsg.setText(strAux1);
                                strAux2 += "<" + strAux1 + ">";
                            }
                            else
                            {
                                strAux2 += "<" + String.format("%02X", packetData.getCodeLength()) + ">";
                            }

                            if (txArRecPktHist.getLength() < 10000) {
                                txArRecPktHist.setText(txArRecPktHist.getText() + strAux2 + "\r\n");
                            } else {
                                txArRecPktHist.clear();
                                txArRecPktHist.setText(txArRecPktHist.getText() + strAux2 + "\r\n");
                            }

                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            tReceiver.start();
        } else {
            if (tReceiver != null) {
                tReceivRunning = false; //signalize to stop the receiver thread
                while (tReceiver.isAlive()); //wait until the thread dies
                tReceivRunning = true;
            }
        }
    }
}
