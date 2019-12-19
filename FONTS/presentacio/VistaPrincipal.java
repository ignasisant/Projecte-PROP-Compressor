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


private VistaSelAlg vistaSelAlg;
private VistaExepcions vistaExepcions = new VistaExepcions();

private int selection = -1;

//////////////////////// Constructor y metodos publicos


public VistaPrincipal (IOUtils pIOUtils) {
iIOUtils = pIOUtils;
inicializarComponentes();
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
    VistaStatistics vistaStatistics = new VistaStatistics(iIOUtils, this);
    panelContenidos.add(vistaStatistics.getPanelStatistics() );
    }
    update();
}

public void actionPerformed_RadioButtonOpcio0(ActionEvent event){
    iIOUtils.setAction(0);
    if(fileChooser.getSelectedFile() == null){
        buttonContinuar.setEnabled(false);
    }
    buttonInput.setEnabled(true);
    buttonOutput.setEnabled(true);
}
public void actionPerformed_RadioButtonOpcio1(ActionEvent event){
    iIOUtils.setAction(1);
    if(fileChooser.getSelectedFile() == null){
        buttonContinuar.setEnabled(false);
    }
    buttonInput.setEnabled(true);
    buttonOutput.setEnabled(true);
}
public void actionPerformed_RadioButtonOpcio2(ActionEvent event){
    buttonContinuar.setEnabled(true);
    buttonInput.setEnabled(false);
    buttonOutput.setEnabled(false);
}

public void actionPerformed_fileChooser(ActionEvent event){
    if (fileChooser.getSelectedFile() != null){
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
                    vistaExepcions.jaComprimit(); //exepcio per no comprimir quan ja està comprimit
                    volverHome(); //reinicializem
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
                else if(extension.equals("lzw")) iIOUtils.setAlgorithm(1);  //assinem algoritmes
                else if(extension.equals("jpeg")) iIOUtils.setAlgorithm(2);
                else {
                    vistaExepcions.noDescomprimir(extension); //exepcio no es pot descomprimir aques arxiu
                    volverHome();
                    return ;
                }
            }
    
        }catch(Exception e){
            e.printStackTrace();
        }
        buttonContinuar.setEnabled(true);
    }
}

public void actionPerformed_fileChooser2(ActionEvent event){
    if (fileChooser2.getSelectedFile() != null){
        try{
        iIOUtils.setOutputFile(fileChooser2.getSelectedFile().getAbsolutePath());
        labelOutput.setText(fileChooser2.getSelectedFile().getAbsolutePath());
        
        }catch(Exception e){
            e.printStackTrace();
        }
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

  private void inicializarComponentes2() { //per quan tornem al menu home
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
    opcio[2]=new JRadioButton("Veure Estadístiques", true); // selecció per defecte
    grupo1.add(opcio[0]);// assegurem que només
    grupo1.add(opcio[1]);// n'hi ha un de seleccionat
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
    fileChooser  = new JFileChooser(); // aixi al tornar a home es reinicialitza
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    fileChooser.setSelectedFile(null);
    fileChooser2 = new JFileChooser();
    fileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser2.setSelectedFile(null);
  }

  private void inicializar_panelCenter(){ //panel central-> opcions d'accio + seleccio arxius i ruta
    panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
    panelCenter.add(panelRadioButton);
    panelCenter.add(Box.createRigidArea(new Dimension(0,5)));

    panel1.setLayout(new BoxLayout(panel1,BoxLayout.X_AXIS )  );
    buttonInput.setEnabled(false);
    panel1.add(buttonInput);
    panel1.add(Box.createRigidArea(new Dimension(5,0)));
    panel1.add(labelInput);

    panelCenter.add(panel1);
    panelCenter.add(Box.createRigidArea(new Dimension(0,5)));

    panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS )  );
    buttonOutput.setEnabled(false);
    panel2.add(buttonOutput);
    panel2.add(Box.createRigidArea(new Dimension(5,0)));
    panel2.add(labelOutput);
    panelCenter.add(panel2);
    panelCenter.add(Box.createRigidArea(new Dimension(0,500)));
  }
}

////////////////////////
