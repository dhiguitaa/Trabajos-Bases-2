import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.List;
import java.util.ArrayList;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;


public class regla1 extends JFrame implements ActionListener{//implementando el listener de eventos
    
  JLabel t1, t2, at1, at2, s1, s2;
  JTextField text1, text2, text3, text4;
  JButton boton1;
  JTextArea text5,text6;
  JPanel panel;
  Border border;
  public regla1(){//constructor de la clase  

    t1 = new JLabel("T1");
    t1.setBounds(10,0,80,40);

    t2 = new JLabel("T2");
    t2.setBounds(10,50,80,40);


    at1 = new JLabel("Atributos T1");
    at1.setBounds(10,100,80,40);

    at2 = new JLabel("Atributos T2");
    at2.setBounds(10,150,80,40);


    s1 = new JLabel("Consultas S1");
    s1.setBounds(10,200,80,40);

    s2 = new JLabel("Consultas S2");
    s2.setBounds(10,360,80,40);
    
    text1 = new JTextField();
    text1.setBounds(100,5,550,40);

    text2 = new JTextField();
    text2.setBounds(100,50,550,40);

    text3 = new JTextField();
    text3.setBounds(100,100,550,40);

    text4 = new JTextField();
    text4.setBounds(100,150,550,40);

    text5 = new JTextArea();
    text5.setBounds(100,200,550,170);

    text6 = new JTextArea();
    text6.setBounds(100,380,550,170);

    boton1 = new JButton();
    boton1.setText("Aceptar");
    boton1.setBounds(300,570,80,20);
    boton1.addActionListener(this);

    panel=new JPanel();
    panel.setLayout(null);
    panel.add(t1);
    panel.add(t2);
    panel.add(at1);
    panel.add(at2);
    panel.add(s1);
    panel.add(s2);
    panel.add(text1);
    panel.add(text2);
    panel.add(text3);
    panel.add(text4);
    panel.add(text5);
    panel.add(text6);
    panel.add(boton1);
   

    add(panel);
    setTitle("Formulario");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,900);
    setVisible(true);
              
  }

  public static void main(String[] args) {
  regla1 gj = new regla1();//uso de constructor para la ventana
  }

  @Override
  public void actionPerformed(final ActionEvent e) {// sobreescribimos el metgetLocaleso del listener

    // variables que almacenaran los textos de los campos de texto
    List<String> atributosT1 = new ArrayList<String>();
    List<String> atributosT2 = new ArrayList<String>();
    List<List<String> > atributosS1 = new ArrayList<List<String>>();
    List<List<String> > atributosS2 = new ArrayList<List<String>>();
    
    
    String nombreT1 = text1.getText().toUpperCase();
    String nombreT2 = text2.getText().toUpperCase();
    String atrT1 = text3.getText();
    String atrT2 = text4.getText();
    String atrS1 = text5.getText();
    String atrS2 = text6.getText();
    String[] splitT1= atrT1.split(",");
    String[] splitT2= atrT2.split(",");
    String[] splitS1= atrS1.split("\n");
    String[] splitS2= atrS2.split("\n");
    for (int i = 0; i < splitT1.length; i++) {
      atributosT1.add(splitT1[i].trim().toLowerCase());
    }
    for (int i = 0; i < splitT2.length; i++) {
      atributosT2.add(splitT2[i].trim().toLowerCase());
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
    

    for (int i = 0; i < splitS1.length; i++) {
      String palabra = " ";
      for (int l = 0; l < splitT2.length; l++) {
        if (splitS1[i].contains(splitT2[l]) && (splitT2[l].equals(clave))== false) {
            palabra += splitT2[l] +  "," ;
        }
      }
      if (!(palabra.equals(" "))) {
        String[] conjunto = palabra.split(",");
        atributosS1.add(new ArrayList<String>());
        for (int j = 0; j < conjunto.length; j++) {
          atributosS1.get(i).add(conjunto[j].trim().toLowerCase());
        }
      }
    }
    for (int i = 0; i < splitS2.length; i++) {
        String palabra = " ";
        for (int l = 0; l < splitT2.length; l++) {
            if (splitS2[i].contains(splitT2[l]) && (splitT2[l].equals(clave))== false) {
                palabra += splitT2[l] +  "," ;
            }
        }
        String[] conjunto = palabra.split(",");
        atributosS2.add(new ArrayList<String>());
        for (int j = 0; j < conjunto.length; j++) {
            atributosS2.get(i).add(conjunto[j].trim().toLowerCase());
        }
    }

    //Unir S2
    Set <String> set = new HashSet <String>();
    for (int i = 0; i < atributosS2.size(); i++){
        set.addAll(atributosS2.get(i));
    }

    List<String> ConjS2 = new ArrayList<String>(set);

    //regla
    //todo lo de t2 que esta en s2
    List<String> tprima2s2 = new ArrayList<String>();
    List<String> tprima2 = new ArrayList<String>();
    List<String> tdoblePrima2 = new ArrayList<String>();
    for (int i = 0; i < ConjS2.size(); i++) {
        String atributo = ConjS2.get(i);
        if (atributosT2.indexOf(atributo)>=0) {
          tprima2s2.add(atributo);
          tprima2.add(atributo);
        } 
    }
    //creacion de t'2 y t''2 definitivas
    for (int i = 0; i < atributosS1.size(); i++) {
      List<String> conjunto = atributosS1.get(i);
      int elementosBorrados = 0;
      //resta
      for (int j = 0; j < conjunto.size(); j++) {
        if (tprima2s2.indexOf(conjunto.get(j)) >= 0){
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
          if (tdoblePrima2.indexOf(conjunto.get(j)) <  0){
            tdoblePrima2.add(conjunto.get(j));
          } 
        }
      }
    }

    //creación de 
    //Q'={todo lo de t1,t'2:{sus cosas},t''2:{sus cosas}}

    String qprima = "Q'={";

    for (int i = 0; i < atributosT1.size(); i++) {
      qprima += atributosT1.get(i)+", ";
    }
    qprima += nombreT2+"_of_"+nombreT1+": {";
    for(int i = 0; i < tprima2.size(); i++) {
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
      qprima += "}";
    }
    qprima += "}";
    
    System.out.println(qprima);

  }      
}