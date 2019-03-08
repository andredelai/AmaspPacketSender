/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package amasppacketsender;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author aldelai
 */
public class FXMLAboutController implements Initializable{

    private AMASPPacketSender main;
    
    @FXML
    private Button btonClose;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void init(AMASPPacketSender mainController) {
	main = mainController;
    }
    
    public void handleBtonCloseAction(ActionEvent event) {
        main.hideAbout();
    }
}
