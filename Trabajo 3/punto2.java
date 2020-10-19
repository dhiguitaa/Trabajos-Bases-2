import java.awt.Dimension;
import java.awt.FlowLayout;
// import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
// import javax.swing.JOptionPane;

// import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class punto2 implements ActionListener{//implementando el listener de eventos
    
  JFrame frame;
  JPanel paneliz, panelde, panelab, panelar, panelex;
  JLabel t1, t2, t3, at1, at2, at3, s1,s2, s3;
  JTextField text1, text2, text3, text4, text5,text6;
  JTextArea text7,text8;
  JButton boton1;
  Border border;
  public punto2(){//constructor de la clase        
      
    frame = new JFrame();
    paneliz  = new JPanel();
    panelde  = new JPanel();
    panelab  = new JPanel();
    panelar  = new JPanel();
    panelex = new JPanel();
    t1 = new JLabel();
    t2 = new JLabel();
    t3 = new JLabel();
    at1 = new JLabel();
    at2 = new JLabel();
    at3 = new JLabel();
    s1 = new JLabel();
    s3 = new JLabel();
    text1 = new JTextField(10);
    text2 = new JTextField(10);
    text3 = new JTextField(10);
    text4 = new JTextField(10);
    text5 = new JTextField(10);
    text6 = new JTextField(10);
    text7 = new JTextArea(10,10);
    text8 = new JTextArea(10,10);
    boton1 = new JButton();
  

    border = BorderFactory.createLineBorder(Color.BLACK, 1);

    text7.setBorder(border);
    text8.setBorder(border);
      
    frame.setTitle("Formulario");
    frame.setLayout(new FlowLayout());      
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setSize(700,700);
      
    t1.setText("Nombre t1");
    t2.setText("Nombre t2");
    t3.setText("Nombre t3");
    at1.setText("Atributos de t1");
    at2.setText("Atributos de t2");
    at3.setText("Atributos de t3");
    s1.setText("S1");
    s3.setText("S3");
      
    boton1.setText("Aceptar");
              
    paneliz.setLayout(new GridLayout(9,0));
    paneliz.add(t1);
    paneliz.add(t2);
    paneliz.add(t3);
    paneliz.add(at1);
    paneliz.add(at2);
    paneliz.add(at3);
    paneliz.add(s1);
    paneliz.add(s3);
    
    panelde.setLayout(new GridLayout(9,0));
    panelde.add(text1);
    panelde.add(text2);
    panelde.add(text3);
    panelde.add(text4);
    panelde.add(text5);
    panelde.add(text6);
    panelde.add(text7);
    panelde.add(text8);
      
    panelar.setLayout(new GridLayout(1,1));
    panelar.setPreferredSize(new Dimension(400,400));
    panelar.add(paneliz);
    panelar.add(panelde);
              
    panelab.setLayout(new FlowLayout());
    panelab.setPreferredSize(new Dimension(300,50));
    panelab.add(boton1);
      
    panelex.setLayout(new GridLayout(2,0));
    panelex.add(panelar);
    panelex.add(panelab);
      
    frame.add(panelex);

    boton1.addActionListener(this);
  }

  public static void main(String[] args) {
  punto2 gj = new punto2();//uso de constructor para la ventana
  }

  @Override
  public void actionPerformed(final ActionEvent e) {// sobreescribimos el metgetLocaleso del listener

    // variables que almacenaran los textos de los campos de texto
    List<String> atributosT1 = new ArrayList<String>();
    List<String> atributosT2 = new ArrayList<String>();
    List<String> atributosT3 = new ArrayList<String>();
    List<List<String>> atributosS1 = new ArrayList<List<String>>();
    List<List<String>> atributosS2 = new ArrayList<List<String>>();
    List<List<String>> atributosS3 = new ArrayList<List<String>>();

    
    
    String nombreT1 = text1.getText().toUpperCase();
    String nombreT2 = text2.getText().toUpperCase();
    String nombreT3 = text3.getText().toUpperCase();
    String atrT1 = text4.getText();
    String atrT2 = text5.getText();
    String atrT3 = text6.getText();
    String atrS1 = text7.getText();
    String atrS3 = text8.getText();
    String[] splitT1= atrT1.split(",");
    String[] splitT2= atrT2.split(",");
    String[] splitT3= atrT3.split(",");
    String[] splitS1= atrS1.split("\n");
    String[] splitS3= atrS3.split("\n");
    for (int i = 0; i < splitT1.length; i++) {
      atributosT1.add(splitT1[i].trim().toLowerCase());
    }
    for (int i = 0; i < splitT2.length; i++) {
      atributosT2.add(splitT2[i].trim().toLowerCase());
    }
    for (int i = 0; i < splitT3.length; i++) {
        atributosT3.add(splitT3[i].trim().toLowerCase());
    }

    //Creando S1 y S3

    for (int i = 0; i < splitS1.length; i++) {
      String[] conjunto = splitS1[i].split(",");
      atributosS1.add(new ArrayList<String>());
      for (int j = 0; j < conjunto.length; j++) {
        atributosS1.get(i).add(conjunto[j].trim().toLowerCase());
      }
    }


    for (int i = 0; i < splitS3.length; i++) {
        String[] conjunto = splitS3[i].split(",");
        atributosS3.add(new ArrayList<String>());
        for (int j = 0; j < conjunto.length; j++) {
          atributosS3.get(i).add(conjunto[j].trim().toLowerCase());
        }
    }

    System.out.println("atributosS3");
    System.out.println(atributosS3);

    //regla
    //todo lo de t2 que esta en s3
    List<String> tprima2s3 = new ArrayList<String>();
    List<String> tprima2 = new ArrayList<String>();
    List<String> tdoblePrima2 = new ArrayList<String>();
    for (int i = 0; i < atributosS3.size(); i++) {
      for (int j = 0; j < atributosS3.get(i).size(); j++) {
        String atributo = atributosS3.get(i).get(j);
        if (atributosT2.indexOf(atributo)>=0) {
          tprima2s3.add(atributo);
          tprima2.add(atributo);
        } 
      }
    }

    System.out.println("tprima2s3");
    System.out.println(tprima2s3);

    //creacion de t'2 y t''2 definitivas
    for (int i = 0; i < atributosS1.size(); i++) {
      List<String> conjunto = atributosS1.get(i);
      int elementosBorrados = 0;
      //resta


      for (int j = 0; j < conjunto.size(); j++) {
        if (tprima2s3.indexOf(conjunto.get(j)) >= 0){
          conjunto.remove(j);
          elementosBorrados ++;
        } 
      }
      
      //vacio o igual a s1
      if (conjunto.size()!=0 && elementosBorrados != 0) {
        for (int j = 0; j < conjunto.size(); j++) {
          if (tprima2.indexOf(conjunto.get(j)) <  0){
            tprima2.add(conjunto.get(j));
          } 
        }
      }
      //interseccion S1i con t'2
      if(elementosBorrados == 0){
        for (int j = 0; j < conjunto.size(); j++) {
          if ((tdoblePrima2.indexOf(conjunto.get(j)) <  0) && (atributosT2.indexOf(conjunto.get(j)) >=  0) ){
            tdoblePrima2.add(conjunto.get(j));
          } 
        }
      }
    }

    System.out.println("tprima2");
    System.out.println(tprima2);
    System.out.println("tdoblePrima2");
    System.out.println(tdoblePrima2);

    //regla
    //todo lo de t3 que esta en s3
    List<String> tprima3s3 = new ArrayList<String>();
    List<String> tprima3 = new ArrayList<String>();
    List<String> tdoblePrima3 = new ArrayList<String>();
    for (int i = 0; i < atributosS3.size(); i++) {
      for (int j = 0; j < atributosS3.get(i).size(); j++) {
        String atributo = atributosS3.get(i).get(j);
        if (atributosT3.indexOf(atributo)>=0) {
          tprima3s3.add(atributo);
          tprima3.add(atributo);
        } 
      }
    }

    System.out.println("tprima3s3");
    System.out.println(tprima3s3);

    //creacion de t'3 y t''3 definitivas
    for (int i = 0; i < atributosS1.size(); i++) {
      List<String> conjunto = atributosS1.get(i);
      int elementosBorrados = 0;
      //resta


      for (int j = 0; j < conjunto.size(); j++) {
        if (tprima3s3.indexOf(conjunto.get(j)) >= 0){
          conjunto.remove(j);
          elementosBorrados ++;
        } 
      }
      
      //vacio o igual a s1
      if (conjunto.size()!=0 && elementosBorrados != 0) {
        for (int j = 0; j < conjunto.size(); j++) {
          if (tprima3.indexOf(conjunto.get(j)) <  0){
            tprima3.add(conjunto.get(j));
          } 
        }
      }
      //interseccion S1i con t'3
      if(elementosBorrados == 0){
        for (int j = 0; j < conjunto.size(); j++) {
          if ((tdoblePrima3.indexOf(conjunto.get(j)) <  0) && (atributosT3.indexOf(conjunto.get(j)) >=  0)){
            tdoblePrima3.add(conjunto.get(j));
          } 
        }
      }
    }

    System.out.println("tprima3");
    System.out.println(tprima3);

    System.out.println("tdoblePrima3");
    System.out.println(tdoblePrima3);


    //creación de 
    //Q'={todo lo de t1,t'2:{sus cosas},t''2:{sus cosas}, t'3: {sus cosas}, t''3:{sus cosas}}

    String qprima = "Q'={";

    for (int i = 0; i < atributosT1.size(); i++) {
      qprima += atributosT1.get(i)+", ";
    }
    qprima += nombreT2+"_of_"+nombreT1+": {";
    for (int i = 0; i < tprima2.size(); i++) {
      qprima += tprima2.get(i)+", ";
    }
    
    qprima = qprima.substring(0, qprima.length()-2);
    qprima += "}";
    if(tdoblePrima2.size() != 0){
      qprima += ", T''2: {";
      for (int i = 0; i < tdoblePrima2.size(); i++) {
        qprima += tdoblePrima2.get(i)+", ";
      }
      qprima = qprima.substring(0, qprima.length()-2);
      qprima += "} ,";
    }
    qprima += nombreT3+"_of_"+nombreT1+": {";
    for (int i = 0; i < tprima3.size(); i++) {
      qprima += tprima3.get(i)+", ";
    }
    
    qprima = qprima.substring(0, qprima.length()-2);
    qprima += "}";
    if(tdoblePrima3.size() != 0){
      qprima += ", T''3: {";
      for (int i = 0; i < tdoblePrima3.size(); i++) {
        qprima += tdoblePrima3.get(i)+", ";
      }
      qprima = qprima.substring(0, qprima.length()-2);
      qprima += "}";
    }
    qprima += "}";
    
    System.out.println(qprima);
  }      
}