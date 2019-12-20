/**
 * Class: VistaStatisics
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

////////////////////////

public class VistaStatistics {
  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica

  private JPanel panelStatistics = new JPanel();
  private JButton buttonHome = new JButton("Menu Home");
  private JTextArea jtextStats = new JTextArea(600,300);
  private JPanel panelCenter = new JPanel();
  private JScrollPane jscrollStats;
  
  // Resto de atributos
 
  private VistaPrincipal vistaPrincipal;
  private String stats;

//////////////////////// Constructor y metodos publicos

  public VistaStatistics (IOUtils pIOUtils, VistaPrincipal vp) {
    iIOUtils = pIOUtils;
    vistaPrincipal = vp;
    inicializarComponentes();
  }

  public JPanel getPanelStatistics(){
    return panelStatistics;
 }
//////////////////////// Metodos de las interfaces Listener

  private void actionPerformed_buttonHome(ActionEvent event){
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

    incializar_panelCenter();
    inicializar_panelStatistics();
    asignar_listenersComponentes();
  }

  private void incializar_panelCenter(){
    panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
    stats = iIOUtils.getAllStats();
    panelCenter.add(new JLabel("         Nom Arxiu                                     Acció        Algorisme usat        Grau"));
    jtextStats.setText(stats);
    jtextStats.setLineWrap(true);
    jtextStats.setWrapStyleWord(true);
    jscrollStats = new JScrollPane(jtextStats);
    panelCenter.add(jscrollStats);    
  }

  private void inicializar_panelStatistics(){
    panelStatistics.setLayout(new BorderLayout());
    panelStatistics.add(new JLabel("Estadístiques Històriques:"),BorderLayout.NORTH);
    panelStatistics.add(panelCenter, BorderLayout.CENTER);
    panelStatistics.add(buttonHome, BorderLayout.SOUTH);   

  }
}