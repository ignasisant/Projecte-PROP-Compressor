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

public class VistaCompare {


  private JFrame frameVista = new JFrame();
  private JPanel panelContenidos = new JPanel();
  private JButton buttonVolver = new JButton("Tornar");
  private JTextArea jtextOriginal = new JTextArea();
  private JTextArea jtextTractat = new JTextArea();
  JScrollPane jscrollOriginal = new JScrollPane(jtextOriginal);
  JScrollPane jscrollTractat = new JScrollPane(jtextOriginal);

    


  

//////////////////////// Constructor y metodos publicos

  public VistaCompare () {
    inicializarComponentes();
  }
  
//////////////////////// Metodos de las interfaces Listener

private void actionPerformed_buttonVolver(ActionEvent event){
    frameVista.dispatchEvent(new WindowEvent(frameVista, WindowEvent.WINDOW_CLOSING));
}
 
//////////////////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

        buttonVolver.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_buttonVolver(event);
                }
            }
        );
    }

//////////////////////// Resto de metodos privados

  private void inicializarComponentes() {

    inicializar_panelContenidos();
    inicializar_frameVista();
    asignar_listenersComponentes();
  }

  private void inicializar_panelContenidos(){
      panelContenidos.setLayout(new BorderLayout());
      panelContenidos.add(new JLabel("Original vs Tractat:"),BorderLayout.NORTH);
      JPanel panelCentre = new JPanel();
      panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.X_AXIS));
      panelCentre.add(jscrollOriginal);
      panelCentre.add(jscrollTractat);
      panelContenidos.add(panelCentre, BorderLayout.CENTER);
      panelContenidos.add(buttonVolver, BorderLayout.SOUTH);
  }

  private void inicializar_frameVista(){
    frameVista.setMinimumSize(new Dimension(700,400));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(false);
    frameVista.setLocationRelativeTo(null);
    frameVista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
    frameVista.pack();
    frameVista.setVisible(true);
  }
  

}