package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import javafx.scene.control.RadioButton;
import presentacio.IOUtils;
import presentacio.VistaPrincipal;

import java.util.*;


////////////////////////

public class VistaInfo {


  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica
  private JPanel panelInformacio = new JPanel();
  private JButton buttonHome = new JButton("Menu Home");
  private JPanel panelLabels = new JPanel();
  

  // Resto de atributos
 
  private VistaPrincipal vistaPrincipal;

  Boolean comprimir;

//////////////////////// Constructor y metodos publicos


  public VistaInfo (IOUtils pIOUtils, Boolean c, VistaPrincipal vp) {
    System.out.println
      ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
    comprimir=c;
    iIOUtils = pIOUtils;
    vistaPrincipal = vp;
    inicializarComponentes();
  }

  public JPanel getPanelInformacio(){
    return panelInformacio;
 }


//////////////////////// Metodos de las interfaces Listener

  public void actionPerformed_buttonHome(ActionEvent event){
      vistaPrincipal.volverHome();
  }


//////////////////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

    // Listeners para los botones
    buttonHome.addActionListener(
        new ActionListener(){
            public void actionPerformed (ActionEvent event){
                actionPerformed_buttonHome(event);
            }
        }
    );


    // Listeners para el resto de componentes

    }


//////////////////////// Resto de metodos privados


  private void inicializarComponentes() {

    incializar_panelLabels();
    inicializar_panelInformacio();
    asignar_listenersComponentes();
  }

  private void incializar_panelLabels(){
      panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.PAGE_AXIS));
      panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
      panelLabels.add(new JLabel("Nom Arxiu: "));
      panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
      panelLabels.add(new JLabel("Pes Inicial:  "));
      panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
      panelLabels.add(new JLabel("Pes Final: "));
      panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
      if(comprimir) panelLabels.add(new JLabel("Grau Compressió: "));
      else panelLabels.add(new JLabel("Augment Pes: "));
      panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
      panelLabels.add(new JLabel("Temps: "));
  }



  private void inicializar_panelInformacio(){
    panelInformacio.setLayout(new BorderLayout());
    // Paneles
    if(comprimir) panelInformacio.add(new JLabel("ARXIU COMPRIMIT AMB ÈXIT!"), BorderLayout.NORTH);
    else panelInformacio.add(new JLabel("ARXIU DESCOMPRIMIT AMB ÈXIT!"), BorderLayout.NORTH);
    panelInformacio.add(panelLabels, BorderLayout.CENTER);
    panelInformacio.add(buttonHome, BorderLayout.SOUTH);   

  }
  

}