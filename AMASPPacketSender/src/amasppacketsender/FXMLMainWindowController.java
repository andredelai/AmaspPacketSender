/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author delai
 */
public class FXMLMainWindowController implements Initializable {
    
    private Stage stage2;
    private Parent setup = null;
    
    @FXML
    private AnchorPane aPneSendRec;
    
    @FXML
    private GridPane gPneSendRec;

    @FXML
    private GridPane gPneSender;

    @FXML
    private AnchorPane gPneMaster;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aPneSendRec.disableProperty();
        aPneSendRec.setDisable(true);
        
        try {
            setup = FXMLLoader.load(getClass().getResource("FXMLSerialCom.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void handleMIteFileConnectAction(ActionEvent event) {
        Scene scene2 = new Scene(setup);
        
        
        stage2 = new Stage();
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(primaryStage);
        stage2.setScene(scene2);     
        stage2.setMaxHeight(177);
        stage2.setMaxWidth(547);
        stage2.setMinHeight(177);
        stage2.setMinWidth(547);
        stage2.resizableProperty().setValue(Boolean.FALSE);
        //stage2.initStyle(StageStyle.UNDECORATED);
        stage2.show();
    }
    
    @FXML
    private void handleMIteFileExitAction(ActionEvent event)
    {
        
    }
}
