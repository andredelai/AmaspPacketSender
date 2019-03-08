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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import com.fazecast.jSerialComm.SerialPort;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author aldelai
 */
public class FXMLSerialComController implements Initializable { 
    
    private AMASPPacketSender main;
    SerialPort[] ports;
    
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
        cBoxBaudRate.setValue("9600");
        cBoxParity.setItems(ParityList);
        cBoxParity.setValue("None");
        cBoxDataBits.setItems(DataBitsList);
        cBoxDataBits.setValue("8");
        cBoxStopBits.setItems(StopBitsList);
        cBoxStopBits.setValue("1");
        
        
        ports = SerialPort.getCommPorts();
        for(int i = 0; i< ports.length; i++)
        {
            PortList.add(ports[i].getSystemPortName());
        }
        
        cBoxSerialPorts.setItems(PortList);
        cBoxSerialPorts.setValue(ports[0].getSystemPortName());
        
    }
    
    public void init(AMASPPacketSender mainController) {
	main = mainController;
    }
    
    public void handleBtonCancelAction(ActionEvent event) {
        main.hideSerialConf();
    }
    
    public void handleBtonConnectAction(ActionEvent event) {
        
        int aux;
        cBoxSerialPorts.getSelectionModel().getSelectedIndex();
        if(!cBoxSerialPorts.getItems().isEmpty())
        {
            main.setSerialConfig(ports[cBoxSerialPorts.getSelectionModel().getSelectedIndex()], Integer.parseInt((String)cBoxBaudRate.getValue()), cBoxParity.getSelectionModel().getSelectedIndex(), Integer.parseInt((String)cBoxDataBits.getValue()), Integer.parseInt((String)cBoxStopBits.getValue()));
        } 
        main.hideSerialConf();
    }
}
