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
import java.util.*;
import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class punto2 extends JFrame implements ActionListener{//implementando el listener de eventos
    
JLabel t1, t2, t3, at1, at2, at3, s12,s13, s3;
JTextField text1, text2, text3, text4, text5,text6;
JButton boton1;
JTextArea text7,text8, text9;
JPanel panel;
  Border border;
  public punto2(){//constructor de la clase  

    t1 = new JLabel("T1");
    t1.setBounds(10,0,80,40);

    t2 = new JLabel("T2");
    t2.setBounds(10,50,80,40);

    t3 = new JLabel("T3");
    t3.setBounds(10,100,80,40);

    at1 = new JLabel("Atributos T1");
    at1.setBounds(10,150,80,40);

    at2 = new JLabel("Atributos T2");
    at2.setBounds(10,200,80,40);

    at3 = new JLabel("Atributos T3");
    at3.setBounds(10,250,80,40);

    s12 = new JLabel("Consultas S1 (T2)");
    s12.setBounds(10,300,100,40);

    s13 = new JLabel("Consultas S1 (T3)");
    s13.setBounds(690,300,100,40);

    s3 = new JLabel("Consultas S3");
    s3.setBounds(10,460,80,40);
    
    text1 = new JTextField();
    text1.setBounds(100,5,550,40);

    text2 = new JTextField();
    text2.setBounds(100,50,550,40);

    text3 = new JTextField();
    text3.setBounds(100,100,550,40);

    text4 = new JTextField();
    text4.setBounds(100,150,550,40);

    text5 = new JTextField();
    text5.setBounds(100,200,550,40);

    text6 = new JTextField();
    text6.setBounds(100,250,550,40);

    text7 = new JTextArea();
    text7.setBounds(120,300,550,170);

    text8 = new JTextArea();
    text8.setBounds(800,300,550,170);

    text9 = new JTextArea();
    text9.setBounds(100,480,550,170);

    boton1 = new JButton();
    boton1.setText("Aceptar");
    boton1.setBounds(300,670,80,20);
    boton1.addActionListener(this);

    panel=new JPanel();
    panel.setLayout(null);
    panel.add(t1);
    panel.add(t2);
    panel.add(t3);
    panel.add(at1);
    panel.add(at2);
    panel.add(at3);
    panel.add(s12);
    panel.add(s13);
    panel.add(s3);
    panel.add(text1);
    panel.add(text2);
    panel.add(text3);
    panel.add(text4);
    panel.add(text5);
    panel.add(text6);
    panel.add(text7);
    panel.add(text8);
    panel.add(text9);
    panel.add(boton1);
   

    add(panel);
    setTitle("Formulario");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,900);
    setVisible(true);
              
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
    List<List<String>> atributosS12 = new ArrayList<List<String>>();
    List<List<String>> atributosS13 = new ArrayList<List<String>>();
    List<List<String>> atributosS3 = new ArrayList<List<String>>();

    
    
    String nombreT1 = text1.getText().toUpperCase();
    String nombreT2 = text2.getText().toUpperCase();
    String nombreT3 = text3.getText().toUpperCase();
    String atrT1 = text4.getText();
    String atrT2 = text5.getText();
    String atrT3 = text6.getText();
    String atrS12 = text7.getText();
    String atrS13 = text8.getText();
    String atrS3 = text9.getText();
    String[] splitT1= atrT1.split(",");
    String[] splitT2= atrT2.split(",");
    String[] splitT3= atrT3.split(",");
    String[] splitS12= atrS12.split("\n");
    String[] splitS13= atrS13.split("\n");
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

    String clave =  " ";
    for (int i = 0; i < splitT1.length; i++){
        for (int j = 0; j <splitT2.length; j++){
          if(splitT2[j].equals(splitT1[i])){
            clave = splitT1[i];
            break;
          }
        }
    }

    //Creando S12 y S13 
    for (int i = 0; i < splitS12.length; i++) {
      String palabra = " ";
      for (int l = 0; l < splitT2.length; l++) {
        if (splitS12[i].contains(splitT2[l]) && (splitT2[l].equals(clave))== false) {
            palabra += splitT2[l] +  "," ;
        }
      }
      if (!(palabra.equals(" "))) {
        String[] conjunto = palabra.split(",");
        atributosS12.add(new ArrayList<String>());
        for (int j = 0; j < conjunto.length; j++) {
          atributosS12.get(i).add(conjunto[j].trim().toLowerCase());
        }
      }
    }

    for (int i = 0; i < splitS13.length; i++) {
      String palabra = " ";
      for (int l = 0; l < splitT3.length; l++) {
        if (splitS12[i].contains(splitT3[l]) && (splitT3[l].equals(clave))== false) {
            palabra += splitT3[l] +  "," ;
        }
      }
      if (!(palabra.equals(" "))) {
        String[] conjunto = palabra.split(",");
        atributosS13.add(new ArrayList<String>());
        for (int j = 0; j < conjunto.length; j++) {
          atributosS13.get(i).add(conjunto[j].trim().toLowerCase());
        }
      }
    }

    //Creando S3

    for (int i = 0; i < splitS3.length; i++) {
      String palabra = " ";
      for (int l = 0; l < splitT2.length; l++) {
        if (splitS3[i].contains(splitT2[l]) && (splitT2[l].equals(clave))== false) {
            palabra += splitT2[l] +  "," ;
        }
      }
      for (int l = 0; l < splitT3.length; l++) {
        if (splitS3[i].contains(splitT3[l]) && (splitT3[l].equals(clave))== false) {
            palabra += splitT3[l] +  "," ;
        }
      }
      String[] conjunto = palabra.split(",");
      atributosS3.add(new ArrayList<String>());
      for (int j = 0; j < conjunto.length; j++) {
          atributosS3.get(i).add(conjunto[j].trim().toLowerCase());
      }
    }

    System.out.println("atributosS3");
    System.out.println(atributosS3);

    //Unir S3
    Set <String> set = new HashSet <String>();
    for (int i = 0; i < atributosS3.size(); i++){
        set.addAll(atributosS3.get(i));
    }

    List<String> ConjS3 = new ArrayList<String>(set);

    //regla
    //todo lo de t2 que esta en s3
    List<String> tprima2s3 = new ArrayList<String>();
    List<String> tprima2 = new ArrayList<String>();
    List<String> tdoblePrima2 = new ArrayList<String>();
    for (int i = 0; i < ConjS3.size(); i++) {
      String atributo = ConjS3.get(i);
      if (atributosT2.indexOf(atributo)>=0) {
        tprima2s3.add(atributo);
        tprima2.add(atributo);
      } 
    }

    System.out.println("tprima2s3");
    System.out.println(tprima2s3);

    //creacion de t'2 y t''2 definitivas
    for (int i = 0; i < atributosS12.size(); i++) {
      List<String> conjunto = atributosS12.get(i);
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
    for (int i = 0; i < ConjS3.size(); i++) {
      String atributo = ConjS3.get(i);
      if (atributosT3.indexOf(atributo)>=0) {
        tprima3s3.add(atributo);
        tprima3.add(atributo);
      } 
    }

    System.out.println("tprima3s3");
    System.out.println(tprima3s3);

    //creacion de t'3 y t''3 definitivas
    for (int i = 0; i < atributosS13.size(); i++) {
      List<String> conjunto = atributosS13.get(i);
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