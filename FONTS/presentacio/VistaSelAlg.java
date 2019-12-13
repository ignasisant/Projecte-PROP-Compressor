package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import javafx.scene.control.RadioButton;
import presentacio.IOUtils;
import presentacio.VistaInfo;
import presentacio.*;

import java.util.*;


////////////////////////

public class VistaSelAlg {


  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica
  private JPanel panelComprimir = new JPanel();
  private JButton buttonComprimir = new JButton("Comprimir");
  private JRadioButton[] alg = new JRadioButton[3];
  private JPanel panelRadioButton = new JPanel();
  private ButtonGroup grupo2 = new ButtonGroup();
  private JTextField jtextfield = new JTextField();

  // Resto de atributos

  private VistaInfo vistaInfo;
  private VistaPrincipal vistaPrincipal;
  


//////////////////////// Constructor y metodos publicos


  public VistaSelAlg (IOUtils pIOUtils, VistaPrincipal vp) {
    System.out.print("vista seleccio alg"); 
    System.out.println
      ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
    iIOUtils = pIOUtils;
    vistaPrincipal = vp;
    inicializarComponentes();   
  }

  public JPanel getPanelComprimir(){
    return panelComprimir;
 }


//////////////////////// Metodos de las interfaces Listener

  public void actionPerformed_buttonComprimir(ActionEvent event){
      vistaInfo = new VistaInfo(iIOUtils , true , vistaPrincipal);
      panelComprimir.removeAll();
      panelComprimir.add(vistaInfo.getPanelInformacio());
      iIOUtils.setOutputFile(jtextfield.getText());
      vistaPrincipal.update();
    try{
      iIOUtils.run();
    }catch(Exception e){
        e.printStackTrace();
    }
    System.out.println("boto Comprimir");
  }

  public void actionPerformed_RadioButtonAlg0(ActionEvent event){
    iIOUtils.setAlgorithm(0);
    System.out.println("RB 0 comp");
  }

  public void actionPerformed_RadioButtonAlg1(ActionEvent event){
    iIOUtils.setAlgorithm(1);
    System.out.println("RB 1 comp");
}

public void actionPerformed_RadioButtonAlg2(ActionEvent event){
    iIOUtils.setAlgorithm(2);
    System.out.println("RB 2 comp");
}

public void actionPerformed_jtextfield(FocusEvent event){
    System.out.print( jtextfield.getText());
    System.out.print( "holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

}




//////////////////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

    // Listeners para los botones
    buttonComprimir.addActionListener(
        new ActionListener(){
            public void actionPerformed (ActionEvent event){
                actionPerformed_buttonComprimir(event);
            }
        }
    );

    alg[0].addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent event){
                actionPerformed_RadioButtonAlg0(event);
            }
        }
    );

    alg[1].addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent event){
                actionPerformed_RadioButtonAlg1(event);
            }
        }
    );

    alg[2].addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent event){
                actionPerformed_RadioButtonAlg2(event);
            }
        }
    );
 

    // Listeners para el resto de componentes

    jtextfield.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //actionPerformed_jtextfield(event);
            }
        }
    );
    jtextfield.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent e) {
            actionPerformed_jtextfield(e);
        }
    });
    jtextfield.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent e) {
            jtextfield.setText("");
            jtextfield.removeFocusListener(this);
        }
    });

    }


//////////////////////// Resto de metodos privados


  private void inicializarComponentes() {
    inicializar_radioButtonComp();
    inicialitzar_panelRadioButtonComp();
    inicializar_jtextfield();
    inicializar_panelComprimir();
    asignar_listenersComponentes();
  }

  private void inicializar_radioButtonComp(){
    alg[0]=new JRadioButton("LZ78", true);
    iIOUtils.setAlgorithm(0);
    alg[1]=new JRadioButton("LZW", false);
    alg[2]=new JRadioButton("JPEG", false);
    grupo2.add(alg[0]);
    grupo2.add(alg[1]);
    grupo2.add(alg[2]);
  }

  private void inicialitzar_panelRadioButtonComp(){
    panelRadioButton.add(alg[0]);
    panelRadioButton.add(alg[1]);
    panelRadioButton.add(alg[2]);
  }

  private void inicializar_panelComprimir(){
    panelComprimir.setLayout(new BorderLayout());
    // Paneles
    panelComprimir.add(new JLabel("Selecciona Algoritme de Compressió"), BorderLayout.NORTH);
    panelComprimir.add(panelRadioButton, BorderLayout.CENTER);
    panelComprimir.add(jtextfield, BorderLayout.CENTER);
    panelComprimir.add(buttonComprimir, BorderLayout.SOUTH);   

  }
  private void inicializar_jtextfield(){
    jtextfield.setText("Escriu el nom de sortida que desitges");
    jtextfield.setMaximumSize(new Dimension(250, 50));

  }
  

}