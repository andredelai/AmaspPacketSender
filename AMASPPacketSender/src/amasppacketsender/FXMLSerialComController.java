/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import com.sun.javafx.collections.ElementObservableListDecorator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author aldelai
 */
public class FXMLSerialComController implements Initializable { 
    
    @FXML
    private ChoiceBox cBoxSerialPorts;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // TODO
        ObservableList <String> list = FXCollections.observableArrayList();
        list.add("Bozo");
        list.add("Papai Papudo");
        cBoxSerialPorts.setItems(list);
    }    
    
}
