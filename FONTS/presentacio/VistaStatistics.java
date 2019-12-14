package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

import presentacio.IOUtils;
import presentacio.VistaPrincipal;
import java.util.*;

////////////////////////

public class VistaStatistics {
  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica

  private JPanel panelStatistics = new JPanel();
  private JButton buttonHome = new JButton("Menu Home");
  private JPanel panelLabels = new JPanel();
  
  // Resto de atributos
 
  private VistaPrincipal vistaPrincipal;

//////////////////////// Constructor y metodos publicos

  public VistaStatistics (IOUtils pIOUtils, VistaPrincipal vp) {
    System.out.print("vista info comp/desc"); 
    System.out.println
      ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
    iIOUtils = pIOUtils;
    vistaPrincipal = vp;
    inicializarComponentes();
  }

  public JPanel getPanelStatistics(){
    return panelStatistics
;
 }
//////////////////////// Metodos de las interfaces Listener

  public void actionPerformed_buttonHome(ActionEvent event){
      System.out.println("boto home");
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
    inicializar_panelStatistics();
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
      panelLabels.add(new JLabel("Temps: "));
  }

  private void inicializar_panelStatistics(){
    panelStatistics.setLayout(new BorderLayout());
    // Paneles
    panelStatistics.add(new JLabel("Estadístiques Històriques:"),BorderLayout.NORTH);
    panelStatistics.add(panelLabels, BorderLayout.CENTER);
    panelStatistics.add(buttonHome, BorderLayout.SOUTH);   

  }
}