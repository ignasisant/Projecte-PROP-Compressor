package presentacio;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;


import presentacio.IOUtils;
import presentacio.*;

import java.util.*;


////////////////////////

public class VistaPrincipal {


  // Controlador de presentacJScrollPane sp = new JScrollPane(ta);
  private IOUtils iIOUtils;

  // Componentes de la interficie grafica
  private static JFrame frameVista = new JFrame("Compressor PROP");
  private JPanel panelContenidos = new JPanel();
  private JRadioButton[] opcio = new JRadioButton[3];
  private ButtonGroup grupo1 = new ButtonGroup();
  private JPanel panelRadioButton = new JPanel();
  private JButton buttonContinuar = new JButton("Continuar");
  private JFileChooser fileChooser;
  private JFileChooser fileChooser2;
  private JPanel panelMenuPrincipal = new JPanel();
  private JPanel panelCenter = new JPanel();
  private JButton buttonInput = new JButton("Seleccionar arxiu");
  private JButton buttonOutput = new JButton("Selecciona ubicació sortida");
  private JLabel labelInput = new JLabel();
  private JLabel labelOutput = new JLabel();
  private JPanel panel1 = new JPanel();
  private JPanel panel2 = new JPanel();

  //Cmprimir
  private VistaSelAlg vistaSelAlg;
  
  // Resto de atributos
  int selection = -1;

//////////////////////// Constructor y metodos publicos


  public VistaPrincipal (IOUtils pIOUtils) {
    iIOUtils = pIOUtils;
    inicializarComponentes();
    
    
  }

  public VistaPrincipal () {
  }

  public void hacerVisible() {
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
      if(opcio[0].isSelected()) {
          vistaSelAlg = new VistaSelAlg(iIOUtils, this);
          panelContenidos.add(vistaSelAlg.getPanelComprimir());
      }
      else if (opcio[1].isSelected()) {
        try{
            iIOUtils.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        VistaInfo vistaInfo = new VistaInfo(iIOUtils, false, this);
        panelContenidos.add(vistaInfo.getPanelInformacio() );
      }
      else  {
        System.out.println("hola");
        VistaStatistics vistaStatistics = new VistaStatistics(iIOUtils, this);
        panelContenidos.add(vistaStatistics.getPanelStatistics() );
      }
      update();
  }

    public void actionPerformed_RadioButtonOpcio0(ActionEvent event){
        iIOUtils.setAction(0);
        if(selection != JFileChooser.APPROVE_OPTION){
            buttonContinuar.setEnabled(false);
        }
    }
    public void actionPerformed_RadioButtonOpcio1(ActionEvent event){
        iIOUtils.setAction(1);
        if(selection != JFileChooser.APPROVE_OPTION){
            buttonContinuar.setEnabled(false);
        }
    }
    public void actionPerformed_RadioButtonOpcio2(ActionEvent event){
        buttonContinuar.setEnabled(true);
        
    }

    public void actionPerformed_fileChooser(ActionEvent event){
        if (selection == JFileChooser.APPROVE_OPTION){
            try{
                labelInput.setText(fileChooser.getSelectedFile().getAbsolutePath());
                iIOUtils.setInputFile(fileChooser.getSelectedFile().getAbsolutePath());
                if(fileChooser.getSelectedFile().isDirectory()) iIOUtils.setType(1);
                else iIOUtils.setType(0);
                if(opcio[0].isSelected()){
                    String extension = "";
                    int i = fileChooser.getSelectedFile().getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = fileChooser.getSelectedFile().getName().substring(i+1);
                    }
                    if (extension.equals("lz78") ||  extension.equals("lzw") || extension.equals("jpeg") ) {
                        String message = "Aquest arxiu ja está comprimit!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                        volverHome();
                        return ;
                    } 
                }
                else if(opcio[1].isSelected()){
                    String extension = "";
                    int i = fileChooser.getSelectedFile().getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = fileChooser.getSelectedFile().getName().substring(i+1);
                    }
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
                }
        
            }catch(Exception e){
                e.printStackTrace();
            }
            buttonContinuar.setEnabled(true);
            System.out.print(fileChooser.getSelectedFile().getName());
        }
    }
    public void actionPerformed_fileChooser2(ActionEvent event){
        if (selection == JFileChooser.APPROVE_OPTION){
            try{
            iIOUtils.setOutputFile(fileChooser2.getSelectedFile().getAbsolutePath());
            labelOutput.setText(fileChooser2.getSelectedFile().getAbsolutePath());
            
            }catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.print(fileChooser2.getSelectedFile().getName());
        }
    }

    public void actionPerformed_buttonInput(ActionEvent event){
        if(!opcio[2].isSelected()) selection = fileChooser.showOpenDialog(null);
    }

    public void actionPerformed_buttonOutput(ActionEvent event){
        if(!opcio[2].isSelected()) selection = fileChooser2.showOpenDialog(null);
    }

   

//////////////////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

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

        fileChooser.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_fileChooser(event);
                }
            }
        );

        fileChooser2.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_fileChooser2(event);
                }
            }
        );
         
        buttonInput.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_buttonInput(event);
                }
            }
        );

        buttonOutput.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    actionPerformed_buttonOutput(event);
                }
            }
        );

    }

//////////////////////// Resto de metodos privados

  private void inicializarComponentes() {
    
    inicializar_radioButton();
    inicializar_panelRadioButton();
    inicializar_panelMenuPrincipal();
    inicializar_panelContenidos();
    inicializar_fileChooser();
    inicializar_frameVista();
    asignar_listenersComponentes();
  }
  private void inicializarComponentes2() {
    inicializar_radioButton(); 
    inicializar_panelRadioButton();
    inicializar_panelMenuPrincipal();
    inicializar_panelContenidos();
    inicializar_fileChooser();
    selection = -1;
    update_frameVista();
    asignar_listenersComponentes();
  }


  private void inicializar_frameVista() {
    frameVista.setMinimumSize(new Dimension(700,400));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(false);
    frameVista.setLocationRelativeTo(null);
    frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

  private void update_frameVista() {
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

  private void inicializar_panelMenuPrincipal() {
    buttonContinuar.setEnabled(true);
    panelMenuPrincipal.setLayout(new BorderLayout());
    panelMenuPrincipal.add(new JLabel("Selecciona una opció"), BorderLayout.NORTH);
    inicializar_panelCenter();
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
    iIOUtils.setOutputFile("");
    labelInput.setText("Input no seleccionat");
    labelOutput.setText("Ubicació output no seleccionada");
    fileChooser  = new JFileChooser();
    fileChooser.setSelectedFile(null);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    fileChooser2 = new JFileChooser();
    fileChooser2.setSelectedFile(null);
    fileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  }

  private void inicializar_panelCenter(){
    panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
    panelCenter.add(panelRadioButton);
    panelCenter.add(Box.createRigidArea(new Dimension(0,5)));

    panel1.setLayout(new BoxLayout(panel1,BoxLayout.X_AXIS )  );
    panel1.add(buttonInput);
    panel1.add(Box.createRigidArea(new Dimension(5,0)));
    panel1.add(labelInput);

    panelCenter.add(panel1);
    panelCenter.add(Box.createRigidArea(new Dimension(0,5)));

    panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS )  );
    panel2.add(buttonOutput);
    panel2.add(Box.createRigidArea(new Dimension(5,0)));
    panel2.add(labelOutput);
    panelCenter.add(panel2);
    panelCenter.add(Box.createRigidArea(new Dimension(0,500)));

  

  }


}

////////////////////////
