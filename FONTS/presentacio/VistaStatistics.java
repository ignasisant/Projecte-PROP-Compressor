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
  private String[] stats;

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
    
    stats = iIOUtils.getStats();
    panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.PAGE_AXIS));
    panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
    panelLabels.add(new JLabel("Nom Arxiu: " + stats[0]));
    panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
    if(stats[1].equals("0"))panelLabels.add(new JLabel("Algoritme: LZ78" ));
    else if(stats[1].equals("1"))panelLabels.add(new JLabel("Algoritme: LZW" ));
    else panelLabels.add(new JLabel("Algoritme: JPEG" ));
    panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
    panelLabels.add(new JLabel("Pes Inicial:  "+stats[2]+" bytes"));
    panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
    panelLabels.add(new JLabel("Pes Final: "+stats[3]+" bytes"));
    panelLabels.add(new JLabel("Grau : "+stats[4]+"%"));
    panelLabels.add(Box.createRigidArea(new Dimension(0,5)));
    panelLabels.add(new JLabel("Temps: "+stats[5]+" ms"));
  }

  private void inicializar_panelStatistics(){
    panelStatistics.setLayout(new BorderLayout());
    // Paneles
    panelStatistics.add(new JLabel("Estadístiques Històriques:"),BorderLayout.NORTH);
    panelStatistics.add(panelLabels, BorderLayout.CENTER);
    panelStatistics.add(buttonHome, BorderLayout.SOUTH);   

  }
}