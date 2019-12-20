/**
 * Class: VisaExepcions
 * Description:
 * Author: Ignasi Sant Albors
 */
package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import presentacio.IOUtils;
import presentacio.VistaPrincipal;

import java.util.*;



public class VistaExepcions {

//////////////////////// Constructor y metodos publicos


  public VistaExepcions () {}

  public void jaComprimit(){ //exepcio per cuan volem comprimir un arxiu ja comprimit
    String message = "Aquest arxiu ja está comprimit!";
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
    return ;
  }

  public void noDescomprimir(String extension){ //exepcio per cuan no es pot descomprimir
    String message;
    if(!extension.equals("") ) message = "no es pot descomprimir l'arxiu amb extensió " + extension;
    else message = "no es pot descomprimir el que has seleccionat";
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
    JOptionPane.ERROR_MESSAGE);
    return;
  }

  public void noComprimir(){ //exepcio per cuan no es pot comprimir l'arxiu
    String message = "Aquest arxiu no es pot comprimir!";
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
    return ;
  }

  public void noJpeg(){ //exepcio per cuan l'arxiu no es pot comprimir amb jpeg
    String message = "Aquest arxiu no es pot comprimir amb l'algoritme JPEG!";
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
    return;
  }

  public void error(String message){ //exepcio per cuan l'arxiu no es pot comprimir amb jpeg
    String e = "Error inesperat: "+message;
    JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
    return;
  }


}