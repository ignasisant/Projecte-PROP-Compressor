/**
 * Class: VistaInfo
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

public class VistaInfo {


  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica
  private JPanel panelInformacio = new JPanel();
  private JButton buttonHome = new JButton("Menú Home");
  private JPanel panelLabels = new JPanel();
  private JPanel panelButtons = new JPanel();
  private JButton buttonCompare = new JButton("Comparar Abans/Després");
  

  // Resto de atributos
 
  private VistaPrincipal vistaPrincipal;
  private String[] stats;
  private Boolean comprimir;

//////////////////////// Constructor y metodos publicos

  public VistaInfo (IOUtils pIOUtils, Boolean c, VistaPrincipal vp) {
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

  public void actionPerformed_buttonCompare(ActionEvent event){
    new VistaCompare(iIOUtils.getCompare()); //s'obre finestra per comparar
  }

//////////////////////// Asignacion de listeners

  private void asignar_listenersComponentes() {

    buttonHome.addActionListener(
        new ActionListener(){
            public void actionPerformed (ActionEvent event){
                actionPerformed_buttonHome(event);
            }
        }
    );

    buttonCompare.addActionListener(
      new ActionListener(){
        public void actionPerformed (ActionEvent event){
          actionPerformed_buttonCompare(event);
        }
      }
    );
  }
  
//////////////////////// Resto de metodos privados

  private void inicializarComponentes() {
    incializar_panelLabels();
    inicializar_panelButtons();
    inicializar_panelInformacio();
    asignar_listenersComponentes();
  }

  private void incializar_panelLabels(){ //tota la iformació de la compressió
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

  private void inicializar_panelInformacio(){ //continguts finals
    panelInformacio.setLayout(new BorderLayout());
    if(comprimir) panelInformacio.add(new JLabel("ARXIU COMPRIMIT AMB ÈXIT!"), BorderLayout.NORTH);
    else panelInformacio.add(new JLabel("ARXIU DESCOMPRIMIT AMB ÈXIT!"), BorderLayout.NORTH);
    panelInformacio.add(panelLabels, BorderLayout.CENTER);
    panelInformacio.add(panelButtons, BorderLayout.SOUTH);   
  }

  private void inicializar_panelButtons(){
    panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
    if(iIOUtils.getType() == 1) buttonCompare.setEnabled(false);
    if(comprimir) panelButtons.add(buttonCompare);
    panelButtons.add(buttonHome);
  }
  

}