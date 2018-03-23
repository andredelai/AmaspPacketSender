/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author delai
 */
public class MainController
{
    private Stage stage1, stage2;
    private SerialPort serialCom;
      
    @FXML
    FXMLMainWindowController mainWindowCtrl;
    
    @FXML
    FXMLSerialComController serialComCtrl;
    
    
    public void initialize(Stage stage1, Stage stage2)
    {
        serialCom = null;
        mainWindowCtrl.init(this, serialCom);
        serialComCtrl.init(this, serialCom);
        this.stage1 = stage1;
        this.stage2 = stage2;
    }

    public void showSerialConf()
    {
        stage2.show();
    }
    
    public void exitProgram()
    {
        stage1.close();
    }
    
    public void hideSerialConf()
    {
        stage2.hide();
    }
    
    public void setStatusLabel(String status)
    {
        mainWindowCtrl.setStatusLabel(status);
    }
    
    
}
