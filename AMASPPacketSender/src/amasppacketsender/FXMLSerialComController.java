/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import com.fazecast.jSerialComm.SerialPort;

/**
 * FXML Controller class
 *
 * @author aldelai
 */
public class FXMLSerialComController implements Initializable { 
    
    private MainController main;
    SerialPort serialCom;
    
    @FXML
    private ChoiceBox cBoxSerialPorts;
    @FXML
    private ChoiceBox cBoxBaudRate;
    @FXML
    private ChoiceBox cBoxParity;
    @FXML
    private ChoiceBox cBoxDataBits;
    @FXML
    private ChoiceBox cBoxStopBits;
    @FXML
    private Button btonConnect, btonCancel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // TODO
        
        ObservableList <String> PortList = FXCollections.observableArrayList();
        ObservableList <String> BaudRateList = FXCollections.observableArrayList();
        ObservableList <String> ParityList = FXCollections.observableArrayList();
        ObservableList <String> DataBitsList = FXCollections.observableArrayList();
        ObservableList <String> StopBitsList = FXCollections.observableArrayList();
        
        BaudRateList.addAll("300", "1200", "9600", "19200", "38400", "57600", "115200");
        ParityList.addAll("None", "Odd", "Even");
        DataBitsList.addAll("8","7","6","5");
        StopBitsList.addAll("1", "2");
        
        cBoxBaudRate.setItems(BaudRateList);
        cBoxParity.setItems(ParityList);
        cBoxDataBits.setItems(DataBitsList);
        cBoxStopBits.setItems(StopBitsList);
        
        
        SerialPort[] ports = SerialPort.getCommPorts();
        for(int i = 0; i< ports.length; i++)
        {
            PortList.add(ports[i].getSystemPortName());
        }
        
        cBoxSerialPorts.setItems(PortList);
        
        
    }
    
    public void init(MainController mainController, SerialPort serialCom) {
	main = mainController;
        this.serialCom = serialCom;
    }
}
