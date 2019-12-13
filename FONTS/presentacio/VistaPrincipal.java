package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import javafx.scene.control.RadioButton;
import presentacio.IOUtils;
import presentacio.*;

import java.util.*;


////////////////////////

public class VistaPrincipal {


  // Controlador de presentacion
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica
  private static JFrame frameVista = new JFrame("Compressor PROP");
  private JPanel panelContenidos = new JPanel();
  private JRadioButton[] opcio = new JRadioButton[3];
  private ButtonGroup grupo1 = new ButtonGroup();
  private JPanel panelRadioButton = new JPanel();
  private JButton buttonContinuar = new JButton("Continuar");
  private JFileChooser fileChooser = new JFileChooser();
  private JPanel panelMenuPrincipal = new JPanel();
  private JPanel panelCenter = new JPanel();

  //Cmprimir
  private VistaSelAlg vistaSelAlg;
  

  // Resto de atributos
  int selection;



//////////////////////// Constructor y metodos publicos


  public VistaPrincipal (IOUtils pIOUtils) {
    System.out.println
      ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
    iIOUtils = pIOUtils;
    inicializarComponentes();
    vistaSelAlg = new VistaSelAlg(iIOUtils, this);
    
  }

  public VistaPrincipal () {
  }

  public void hacerVisible() {
    System.out.println
      ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
    frameVista.pack();
    frameVista.setVisible(true);
  }

  public void activar() {
    frameVista.setEnabled(true);
  }

  public void desactivar() {
    frameVista.setEnabled(false);
  }

  public static void update(){
    frameVista.pack();
    frameVista.repaint();
  }

  public void volverHome(){
      panelContenidos.removeAll();
      panelMenuPrincipal.removeAll();
      panelRadioButton.removeAll();
      panelCenter.removeAll();
      grupo1.clearSelection();
      inicializarComponentes2();
      update();
  }

//////////////////////// Metodos de las interfaces Listener

  public void actionPerformed_buttonContinuar(ActionEvent event){

      panelContenidos.removeAll();
      if(opcio[0].isSelected()) panelContenidos.add(vistaSelAlg.getPanelComprimir());
      else if (opcio[1].isSelected()) {
        VistaInfo vistaInfo = new VistaInfo(iIOUtils, false, this);
        panelContenidos.add(vistaInfo.getPanelInformacio() );
      }
      else  {
        VistaStatistics vistaStatistics = new VistaStatistics(iIOUtils, this);
        panelContenidos.add(vistaStatistics.getPanelStatistics() );
      }
      System.out.println("boto continuar");
      update();
  }

    public void actionPerformed_RadioButtonOpcio0(ActionEvent event){
        iIOUtils.setAction(0);
        selection = fileChooser.showOpenDialog(null);
    }
    public void actionPerformed_RadioButtonOpcio1(ActionEvent event){
        iIOUtils.setAction(1);
        selection = fileChooser.showOpenDialog(null);
        String extension = "";
        int i = fileChooser.getSelectedFile().getName().lastIndexOf('.');
        if (i > 0) {
            extension = fileChooser.getSelectedFile().getName().substring(i+1);
        }
        System.out.println(extension);
        System.out.println(extension.length());
        if (extension.equals("lz78")) iIOUtils.setAlgorithm(0);
        else if(extension.equals("lzw")) iIOUtils.setAlgorithm(1);
        else if(extension.equals("jpeg")) iIOUtils.setAlgorithm(2);
        else {
            String message;
            if(!extension.equals("") ) message = "no es pot descomprimir l'arxiu amb extensió " + extension;
            else message = "no es pot descomprimir el que has seleccionat";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
            JOptionPane.ERROR_MESSAGE);
            volverHome();
            return ;
        }
        try{
            iIOUtils.run();
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    public void actionPerformed_RadioButtonOpcio2(ActionEvent event){
        
    }

    public void actionPerformed_fileChooser(ActionEvent event){
        if (selection == JFileChooser.APPROVE_OPTION){
            try{
            iIOUtils.setInputFile(fileChooser.getSelectedFile().getName());
            if(fileChooser.getSelectedFile().isDirectory()) iIOUtils.setType(1);
            else iIOUtils.setType(0);
            }catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.print(fileChooser.getSelectedFile().getName());
        }
        System.out.println("file chooser");
    }

   

//////////////////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

        // Listeners para los botones
        buttonContinuar.addActionListener(
            new ActionListener(){
                public void actionPerformed (ActionEvent event){
                    actionPerformed_buttonContinuar(event);
                }
            }
        );

        opcio[0].addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_RadioButtonOpcio0(event);
                }
            }
        );

        opcio[1].addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_RadioButtonOpcio1(event);
                }
            }
        );

        opcio[2].addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_RadioButtonOpcio2(event);
                }
            }
        );
    
        // Listeners para el selector de archivo

        fileChooser.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_fileChooser(event);
                }
            }
        );

       

        // Listeners para el resto de componentes

    }

//////////////////////// Resto de metodos privados

  private void inicializarComponentes() {
    
    inicializar_radioButton();
    inicializar_panelRadioButton();
    inicializar_panelCenter();
    inicializar_panelMenuPrincipal();
    inicializar_panelContenidos();
    inicializar_fileChooser();
    inicializar_frameVista();
    asignar_listenersComponentes();
  }
  private void inicializarComponentes2() {
    inicializar_radioButton(); //per defecte veure estadistiques
    inicializar_panelRadioButton();
    inicializar_panelCenter();
    inicializar_panelMenuPrincipal();
    inicializar_panelContenidos();
    inicializar_fileChooser();
    update_frameVista();
    asignar_listenersComponentes();
  }

  private void inicializar_frameVista() {
    // Tamanyo
    frameVista.setMinimumSize(new Dimension(700,400));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(false);
    // Posicion y operaciones por defecto
    frameVista.setLocationRelativeTo(null);
    frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Se agrega panelContenidos al contentPane (el panelContenidos se
    // podria ahorrar y trabajar directamente sobre el contentPane)
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

  private void update_frameVista() {
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

 

  private void inicializar_panelMenuPrincipal() {
    // Layout
    panelMenuPrincipal.setLayout(new BorderLayout());
    // Paneles
    panelMenuPrincipal.add(new JLabel("Selecciona una opció"), BorderLayout.NORTH);
    panelMenuPrincipal.add(panelCenter, BorderLayout.CENTER);
    panelMenuPrincipal.add(buttonContinuar, BorderLayout.SOUTH);
  }

  private void inicializar_panelContenidos(){
      panelContenidos = panelMenuPrincipal;
  }

  private void inicializar_radioButton(){
    opcio[0]=new JRadioButton("Comprimir", false);
    opcio[1]=new JRadioButton("Descomprimir", false);
    opcio[2]=new JRadioButton("Veure Estadístiques", true);
    grupo1.add(opcio[0]);
    grupo1.add(opcio[1]);
    grupo1.add(opcio[2]);
  }

  private void inicializar_panelRadioButton(){
      panelRadioButton.add(opcio[0]);
      panelRadioButton.add(opcio[1]);
      panelRadioButton.add(opcio[2]);    
  }

  private void inicializar_fileChooser(){
    fileChooser.setSelectedFile(null);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  }

  private void inicializar_panelCenter(){
    panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
    panelCenter.add(panelRadioButton);
    panelCenter.add(Box.createRigidArea(new Dimension(0,500)));
    

  }


}

////////////////////////
