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

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import AMASPJava.AMASPSerialMaster;
import AMASPJava.AMASPSerialSlave;
import javafx.application.Platform;


/**
 *
 * @author delai
 */
public class AMASPPacketSender extends Application {

    /**
     * @return the slave
     */
    public AMASPSerialSlave getSlave()
    {
        return slave;
    }

    /**
     * @return the master
     */
    public AMASPSerialMaster getMaster() {
        return master;
    }
    
    private Stage stage;
    private Stage stage2;
    private Stage stage3;
    private SerialPort serialCom;
    private AMASPSerialMaster master;
    private AMASPSerialSlave slave;
    private FXMLMainWindowController mainWindowCtrl;
    private FXMLSerialComController serialComCtrl;
    private FXMLAboutController aboutCtrl;
            
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("FXMLMainWindow.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FXMLSerialCom.fxml"));
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("FXMLAbout.fxml"));
        
        mainWindowCtrl = new FXMLMainWindowController();
        serialComCtrl = new FXMLSerialComController();
        aboutCtrl = new FXMLAboutController();
        
        mainWindowCtrl.init(this);
        serialComCtrl.init(this);
        aboutCtrl.init(this);
        
        
        loader1.setController(mainWindowCtrl);
        loader2.setController(serialComCtrl);
        loader3.setController(aboutCtrl);
        
        Parent main = loader1.load();
        Parent serialConf = loader2.load();
        Parent about = loader3.load();
        
        Scene scene = new Scene(main);
        Scene scene2 = new Scene(serialConf);
        Scene scene3 = new Scene(about);
        
        stage.setScene(scene);
        stage.setMaxHeight(650);
        stage.setMaxWidth(850);
        stage.setMinHeight(650);
        stage.setMinWidth(850);
        stage.setTitle("AMASP Packet Sender 1.2.0");
        
        stage.setOnCloseRequest((event) -> 
        {
            exitProgram();
        });
        
        
        stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setMaxHeight(170);
        stage2.setMaxWidth(573);
        stage2.setMinHeight(170);
        stage2.setMinWidth(573);
        stage2.initStyle(StageStyle.UNDECORATED);
        //stage2.setTitle("Serial Connection Configuration");
        stage2.initOwner(stage);
        stage2.initModality(Modality.WINDOW_MODAL);   
        
        stage3 = new Stage();
        stage3.setScene(scene3);
        stage3.setMaxHeight(250);
        stage3.setMaxWidth(450);
        stage3.setMinHeight(250);
        stage3.setMinWidth(450);
        stage3.initStyle(StageStyle.UNDECORATED);
        //stage2.setTitle("Serial Connection Configuration");
        stage3.initOwner(stage);
        stage3.initModality(Modality.WINDOW_MODAL); 
        
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
        mainWindowCtrl.receiverOn(false);
        disconnectSerial();
        Platform.exit();
        System.exit(0);
        
    }
    
    public void hideSerialConf()
    {
        
        stage2.hide();
    }
    
    public void showSerialConf()
    {
        stage2.show();
    }
    
    public void showAbout()
    {
        stage3.show();
    }
    
    public void hideAbout()
    {
        stage3.hide();
    }
    
    public void setSerialConfig(SerialPort port, int baudRate, int parity, int dataBits, int stopBits )
    {
        String aux = " ";
        
        serialCom = port;
        serialCom.setBaudRate(baudRate);
        serialCom.setParity(parity);
        serialCom.setNumDataBits(dataBits);
        serialCom.setNumStopBits(stopBits);
        serialCom.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
        
        if(!serialCom.openPort())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error to open the serial port. Check if Another device is using the port.");
            alert.showAndWait();
            return;
        }
        
        master = new AMASPSerialMaster();
        master.begin(serialCom);
        
        slave = new AMASPSerialSlave();
        slave.begin(serialCom);
        
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
            serialCom.closePort();         
        }
         mainWindowCtrl.setStatusLabel("No connection");
         mainWindowCtrl.setConMenuItemText("Connect");
         mainWindowCtrl.enableAllFields(false);
    }
    
}
