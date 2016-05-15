/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

/**
 *
 * @author Gustavo M S
 */
public class ManipulacaoDatas {
   public String dataBR(String d){//pego data do banco e transformo padrao brasileiro
      String dataNormal = d.substring(8) + "/" + d.substring(5,7) + "/" + d.substring(0,4);
      
      return dataNormal;
   }
   
   
   
   
   private String transformaData(String data){ // pego data do textField e transformo no padrao americano
      String dataBanco = data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
      //JOptionPane.showMessageDialog(null, dataBanco);
      return dataBanco;
   }
}
