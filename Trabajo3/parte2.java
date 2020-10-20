import com.mongodb.client.MongoClients;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import java.util.Arrays;
import java.util.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class parte2 implements ActionListener {

  JButton extraer, visualizar;
  JLabel informacion;
  JFrame jf = new JFrame("Formulario");

  public parte2() {

    jf.setLayout(new FlowLayout());
    extraer = new JButton("Generar y cargar estadisticas");
    visualizar = new JButton("Visualizar estadisticas");
    informacion = new JLabel();
    extraer.addActionListener(this);
    visualizar.addActionListener(this);

    jf.add(extraer);
    jf.add(visualizar);
    jf.add(informacion);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza el programa cuando se da click en la X
    jf.setResizable(false);// para configurar si se redimensiona la ventana
    jf.setSize(600, 600);// configurando tamaño de la ventana (ancho, alto)
    jf.setVisible(true);
  }

  public void actionPerformed(final ActionEvent e) {

    if (e.getSource() == extraer) {

      Connection conn;
      Statement sentencia;
      ResultSet resultado;
      MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
      MongoDatabase db = mongoClient.getDatabase("parte2");
      MongoCollection<Document> marca = db.getCollection("marca");
      MongoCollection<Document> producto = db.getCollection("producto");
      MongoCollection<Document> gremio = db.getCollection("gremio");
      MongoCollection<Document> vendedor = db.getCollection("vendedor");
      MongoCollection<Document> pais = db.getCollection("pais");
      MongoCollection<Document> dpto = db.getCollection("dpto");
      MongoCollection<Document> ciudad = db.getCollection("ciudad");
      MongoCollection<Document> sucursal = db.getCollection("sucursal");
      marca.drop();
      producto.drop();
      gremio.drop();
      vendedor.drop();
      pais.drop();
      dpto.drop();
      ciudad.drop();
      sucursal.drop();
      try { // Se carga el driver JDBC-ODBC
        Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (final Exception err) {
        System.out.println("No se pudo cargar el driver JDBC");
        return;
      }

      try { // Se establece la conexi�n con la base de datos Oracle Express
        conn = DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LV4ONBF:1521:xe", "dani", "dani");
        sentencia = conn.createStatement();
      } catch (final SQLException err) {
        System.out.println("No hay conexi�n con la base de datos.");
        return;
      }

      try {
        // ventas totales por sucursal
        resultado = sentencia.executeQuery(
            "SELECT sucursal.codigo as sucursal, sucursal.nombre as nombre, sum(venta.valor) as ventas FROM ((((pais full outer JOIN dpto ON pais.nombre=dpto.nombrePais)full outer join ciudad on dpto.codigo=ciudad.codigoDpto)full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)full outer join venta on sucursal.codigo=venta.codigoSucursal) group by sucursal.codigo, sucursal.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("sucursal"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          sucursal.insertOne(document);
        }
        // ventas totales por ciudad
        resultado = sentencia.executeQuery(
            "SELECT ciudad.codigo as ciudad, ciudad.nombre as nombre,sum(venta.valor) as ventas FROM ((((pais full outer JOIN dpto ON pais.nombre=dpto.nombrePais)full outer join ciudad on dpto.codigo=ciudad.codigoDpto)full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)full outer join venta on sucursal.codigo=venta.codigoSucursal) group by ciudad.codigo,ciudad.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("ciudad"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          ciudad.insertOne(document);
        }
        // ventas totales por dpto
        resultado = sentencia.executeQuery(
            "SELECT dpto.codigo as dpto,dpto.nombre as nombre,sum(venta.valor) as ventas FROM ((((pais full outer JOIN dpto ON pais.nombre=dpto.nombrePais) full outer join ciudad on dpto.codigo=ciudad.codigoDpto)full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)full outer join venta on sucursal.codigo=venta.codigoSucursal) group by dpto.codigo,dpto.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("dpto"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          dpto.insertOne(document);
        }
        // ventas totales por pais
        resultado = sentencia.executeQuery(
            "SELECT pais.nombre as pais,sum(venta.valor) as ventas FROM ((((pais full outer JOIN dpto ON pais.nombre=dpto.nombrePais)full outer join ciudad on dpto.codigo=ciudad.codigoDpto)full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)full outer join venta on sucursal.codigo=venta.codigoSucursal)group by pais.nombre");

        while (resultado.next()) {
          Document document = new Document("nombre", resultado.getString("pais"));
          
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          pais.insertOne(document);
        }
        System.out.println("--");
        // ventas totales por vendedor
        resultado = sentencia.executeQuery(
            "SELECT vendedor.codigo as vendedor, vendedor.nombre as nombre, sum(venta.valor) as ventas FROM ((gremio full outer JOIN vendedor ON gremio.codigo=vendedor.codigoGremio)full outer join venta on vendedor.codigo=venta.codigoVendedor) group by vendedor.codigo,vendedor.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("vendedor"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          vendedor.insertOne(document);
        }

        // ventas totales por gremio
        resultado = sentencia.executeQuery(
            "SELECT gremio.codigo as gremio, gremio.nombre as nombre, sum(venta.valor) as ventas FROM ((gremio full outer JOIN vendedor ON gremio.codigo=vendedor.codigoGremio)full outer join venta on vendedor.codigo=venta.codigoVendedor)group by gremio.codigo, gremio.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("gremio"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          gremio.insertOne(document);
        }
        System.out.println("--");
        // ventas totales por producto
        resultado = sentencia.executeQuery(
            "SELECT producto.codbarras as producto, producto.nombre as nombre,sum(venta.valor) as ventas FROM ((marca full outer JOIN producto ON marca.nombre=producto.nombreMarca)full outer join venta on producto.codbarras=venta.codbarrasProducto) group by producto.codbarras, producto.nombre");

        while (resultado.next()) {
          Document document = new Document("codigo", resultado.getString("producto"));
          document.append("nombre", resultado.getString("nombre"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          producto.insertOne(document);
        }
        System.out.println("--");
        // ventas totales por marca
        resultado = sentencia.executeQuery(
            "SELECT marca.nombre as marca, marca.descripcion as descripcion, sum(venta.valor) as ventas FROM ((marca full outer JOIN producto ON marca.nombre=producto.nombreMarca)full outer join venta on producto.codbarras=venta.codbarrasProducto) group by marca.nombre,marca.descripcion");

        while (resultado.next()) {
          Document document = new Document("nombre", resultado.getString("marca"));
          document.append("descripcion", resultado.getString("descripcion"));
          if(Objects.isNull(resultado.getString("ventas"))){
            document.append("ventaTotal", 0);
          }else{
            document.append("ventaTotal", Integer.parseInt(resultado.getString("ventas")));
          }
          marca.insertOne(document);
        }

      } catch (Exception err) {
        System.out.println("error");
        System.out.println(err);
      }
    }
    if(e.getSource() == visualizar){
      MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
      MongoDatabase db = mongoClient.getDatabase("parte2");
      MongoCollection<Document> marca = db.getCollection("marca");
      MongoCollection<Document> producto = db.getCollection("producto");
      MongoCollection<Document> gremio = db.getCollection("gremio");
      MongoCollection<Document> vendedor = db.getCollection("vendedor");
      MongoCollection<Document> pais = db.getCollection("pais");
      MongoCollection<Document> dpto = db.getCollection("dpto");
      MongoCollection<Document> ciudad = db.getCollection("ciudad");
      MongoCollection<Document> sucursal = db.getCollection("sucursal");
      // marca
      System.out.println("marca");
      FindIterable<Document> elementosMarca = marca.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosMarca) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", descripcion: "+document.get("descripcion");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //producto
      System.out.println("producto");
      FindIterable<Document> elementosProducto = producto.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosProducto) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codbarras: "+document.get("codbarras");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //gremio
      System.out.println("gremio");
      FindIterable<Document> elementosGremio = gremio.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosGremio) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codigo: "+document.get("codigo");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //Vendedor
      System.out.println("vendedor");
      FindIterable<Document> elementosVendedor = vendedor.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosVendedor) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codigo: "+document.get("codigo");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //Pais
      System.out.println("pais");
      FindIterable<Document> elementosPais = pais.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosPais) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //Dpto
      System.out.println("dpto");
      FindIterable<Document> elementosDpto = dpto.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosDpto) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codigo: "+document.get("codigo");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //Ciudad
      System.out.println("ciudad");
      FindIterable<Document> elementosCiudad = ciudad.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosCiudad) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codigo: "+document.get("codigo");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      //Sucursal
      System.out.println("sucursal");
      FindIterable<Document> elementosSucursal = sucursal.find().sort(Sorts.descending("ventaTotal")).limit(3);
      for (Document document : elementosSucursal) {
        String resultado = "";
        resultado+="nombre: "+document.get("nombre");
        resultado+=", codigo: "+document.get("codigo");
        resultado+=", ventas totales: "+document.get("ventaTotal");
        System.out.println(resultado);
      }
      
    }

  }

  public static void main(String[] args) {
    // MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
    // MongoDatabase db = mongoClient.getDatabase("tienda");
    // for (String name : db.listCollectionNames()) {
    // System.out.println(name);
    parte2 gj = new parte2();
  }

}
