/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amasppacketsender;

import AMASPJava.AMASPSerial;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aldelai
 */
public class ReceiverThread implements Runnable{

    private FXMLMainWindowController mainControl;
    
    public ReceiverThread(FXMLMainWindowController mainControl)
    {
        this.mainControl = mainControl;
    }
    
    @Override
    public void run() {
        
    }
    
}
