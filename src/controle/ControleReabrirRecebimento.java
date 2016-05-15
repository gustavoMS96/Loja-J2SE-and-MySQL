/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloCompra;
import modelo.ModeloRecebimento;
        
        
public class ControleReabrirRecebimento {
   ModeloCompra modCompra = new ModeloCompra();
   ModeloRecebimento modReceb = new ModeloRecebimento();
   
   ConexaoBanco connReceb = new ConexaoBanco();
   ConexaoBanco connCompra = new ConexaoBanco();
   ConexaoBanco connItem = new ConexaoBanco();
   public static int c = 0;
   
   public void criaAssociacao(){
      modReceb.setCompra(modCompra);
   
   }
   
   private String dataBrasil(String dataBanco){//data para usuario
      return dataBanco.substring(8,10) + "/" + dataBanco.substring(5,7) + "/" + dataBanco.substring(0,4);
   }
   
   
   
   public ModeloRecebimento procuraRecebimento(int codigo){
      try{
         criaAssociacao();
         
         connReceb.conexao();
         connReceb.executaaSQL("SELECT R.STATUS, R.DATA_RECEBIMENTO, R.DATA_EMISSAO, R.NOTA_FISCAL, R.SERIE_NF, C.CODIGO_COMPRA, C.DATA_COMPRA, C.VALOR_COMPRA "
                 + "FROM RECEBER_COMPRA R "
                 + "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA "
                 + "WHERE R.CODIGO_RECEBIMENTO = '" + codigo + "' AND STATUS = 'FECHADO';");
         //connReceb.rs.first();
         
         if(!connReceb.rs.first()){
            JOptionPane.showMessageDialog(null, "Este recebimento já está aberto!");
            c = 1;
         }else{
         
            modReceb.setDataRecebimento(dataBrasil(connReceb.rs.getString("DATA_RECEBIMENTO")));
        // JOptionPane.showMessageDialog(null, modReceb.getDataRecebimento());
         
            modReceb.setDataEmissao(dataBrasil(connReceb.rs.getString("DATA_EMISSAO")));
            modReceb.setNotaFiscal(connReceb.rs.getString("NOTA_FISCAL"));
            modReceb.setSerie(connReceb.rs.getString("SERIE_NF"));
            modReceb.getCompra().setCodigoCompra(connReceb.rs.getInt("CODIGO_COMPRA"));
            modReceb.getCompra().setDataCompra(dataBrasil(connReceb.rs.getString("DATA_COMPRA")));
            modReceb.getCompra().setValorTotal(connReceb.rs.getDouble("VALOR_COMPRA"));
         }
         
         
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nprocurarRecebimento()\n" + e.getMessage()); 
      
        }
      connReceb.desconecta();
      return modReceb;
     
   }
   

   public void reabrirRecebimento(int codigoReceb, int codigoCompra){
      try{
         
         connReceb.conexao();
         connCompra.conexao();
         connItem.conexao();
         
         PreparedStatement reabrir = connReceb.conn.prepareStatement("UPDATE RECEBER_COMPRA SET STATUS = 'ABERTO' WHERE CODIGO_RECEBIMENTO = '" + codigoReceb + "';");
         reabrir.execute();
         
         
         PreparedStatement statusCompra = connCompra.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'APROVADA' WHERE CODIGO_COMPRA =  '" + codigoCompra + "';");
         statusCompra.execute();
         
         
         
         PreparedStatement statusItem = connCompra.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'APROVADO' WHERE CODIGO_COMPRA = '" + codigoCompra + "';");
         statusItem.execute();
         
         
         JOptionPane.showMessageDialog(null, "Recebimento aberto!");
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nreabrirRecebimento()\n" + e.getMessage());
        }
      
      connReceb.desconecta();
      connCompra.desconecta();
      connItem.desconecta();
      
   }

   public void cancelarRecebimento(int codigoReceb, int codigoCompra){
      try{
         connReceb.conexao();
         connCompra.conexao();
         connItem.conexao();
         
         PreparedStatement cancela = connReceb.conn.prepareStatement("DELETE FROM RECEBER_COMPRA WHERE CODIGO_RECEBIMENTO = '" + codigoReceb + "';");
         cancela.execute();
         
         PreparedStatement statusCompra = connCompra.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'APROVADA' WHERE CODIGO_COMPRA = '" + codigoCompra + "';");
         statusCompra.execute();
         
         PreparedStatement statusItem = connItem.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'APROVADO' WHERE CODIGO_COMPRA = '" + codigoCompra + "';");
         statusItem.execute();
      
      
      
      
      
         JOptionPane.showMessageDialog(null, "Recebimento cancelado!");
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\ncancelarRecebimento()\n" + e.getMessage());
      
        }
   
      connReceb.desconecta();
      connCompra.desconecta();
      connItem.desconecta();
   
   }


   public ModeloRecebimento primeiro(){
      try{
         connReceb.conexao();
         connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, R.DATA_EMISSAO, R.NOTA_FISCAL, R.SERIE_NF, C.CODIGO_COMPRA, C.DATA_COMPRA, C.VALOR_COMPRA "
                 + "FROM RECEBER_COMPRA R "
                 + "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE R.STATUS = 'FECHADO';");
         connReceb.rs.first();
         
         defineAtributos();
         
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nprimeiro()\n" + e.getMessage());
        }
      
      
   
   
      return modReceb;
   }

   
   
   public ModeloRecebimento anterior(){
      try{
         connReceb.conexao();
       //  connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, R.DATA_EMISSAO, R.NOTA_FISCAL, R.SERIE_NF, C.CODIGO_COMPRA, C.DATA_COMPRA, C.VALOR_COMPRA "
       //          + "FROM RECEBER_COMPRA R "
       //          + "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE R.STATUS = 'FECHADO';");
         connReceb.rs.previous();
         
         defineAtributos();
         
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nanterior()\n" + e.getMessage());
        }
      
      
   
   
      return modReceb;
   }
   
   
   public ModeloRecebimento proximo(){
      try{
         connReceb.conexao();
       //  connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, R.DATA_EMISSAO, R.NOTA_FISCAL, R.SERIE_NF, C.CODIGO_COMPRA, C.DATA_COMPRA, C.VALOR_COMPRA "
       //          + "FROM RECEBER_COMPRA R "
       //          + "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE R.STATUS = 'FECHADO';");
         connReceb.rs.next();
         
         defineAtributos();
         
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nproximo()\n" + e.getMessage());
        }
      
      
   
   
      return modReceb;
   }
   
   
   
   public ModeloRecebimento ultimo(){
      try{
         connReceb.conexao();
         connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, R.DATA_EMISSAO, R.NOTA_FISCAL, R.SERIE_NF, C.CODIGO_COMPRA, C.DATA_COMPRA, C.VALOR_COMPRA "
                 + "FROM RECEBER_COMPRA R "
                 + "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE R.STATUS = 'FECHADO';");
         connReceb.rs.last();
         
         defineAtributos();
         
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\nultimo()\n" + e.getMessage());
        }
      
      
   
   
      return modReceb;
   }
   
   
   
   
   public ModeloRecebimento defineAtributos(){
      criaAssociacao();
      try{
         
         modReceb.setCodigoRecebimento(connReceb.rs.getInt("CODIGO_RECEBIMENTO"));
         modReceb.setDataRecebimento(dataBrasil(connReceb.rs.getString("DATA_RECEBIMENTO")));
         modReceb.setDataEmissao(dataBrasil(connReceb.rs.getString("DATA_EMISSAO")));
         modReceb.setNotaFiscal(connReceb.rs.getString("NOTA_FISCAL"));
         modReceb.setSerie(connReceb.rs.getString("SERIE_NF"));
         modReceb.getCompra().setCodigoCompra(connReceb.rs.getInt("CODIGO_COMPRA"));
         modReceb.getCompra().setValorTotal(connReceb.rs.getDouble("VALOR_COMPRA"));
         modReceb.getCompra().setDataCompra(dataBrasil(connReceb.rs.getString("DATA_COMPRA")));
         
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "ControleReabrirRecebimento:\ndefineAtributos()\n" + e.getMessage());
        }
      
      
      return modReceb;
   }


}
