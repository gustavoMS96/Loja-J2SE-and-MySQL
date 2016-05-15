
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.ModeloCompra;


public class ControleAvaliacaoDeCompra {
   ConexaoBanco connAvalia = new ConexaoBanco();
   ModeloCompra modCompra = new ModeloCompra();
   
   public void aprovaCompra(/*ModeloCompra modCompra,*/ int codigo){
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      
      try{
         connAvalia.conexao();
         
         PreparedStatement pst = connAvalia.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'APROVADA' WHERE CODIGO_COMPRA = '" + codigo + "'");
         PreparedStatement pst1 = connAvalia.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'APROVADO' WHERE CODIGO_COMPRA = '" + codigo + "' AND STATUS != 'CANCELADO' ");
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja aprovar a compra")){
             case 0:
                pst.execute();
                pst1.execute();
                break;
             case 1:
                JOptionPane.showMessageDialog(null,"Operação cancelada");
                break;
             case 2:
                break; 
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleAvaliacaoDeCompra\n\nErro ao aprovar compra\n" + ex);
        } finally{
             connAvalia.desconecta();
          }
   
   }
   
   
   public void reprovaCompra( int codigo, String obs){
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      
      try{
         connAvalia.conexao();
         
         PreparedStatement pst = connAvalia.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'REPROVADO', OBSERVACAO = '" + obs + "' WHERE CODIGO_COMPRA = '" + codigo + "'");
         PreparedStatement pst1 = connAvalia.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'REPROVADO' WHERE CODIGO_COMPRA = '" + codigo + "'");
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja reprovar a compra")){
             case 0:
                pst.execute();
                pst1.execute();
                break;
             case 1:
                JOptionPane.showMessageDialog(null,"Operação cancelada");
                break;
             case 2:
                break; 
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleAvaliacaoDeCompra\n\nErro ao reprovar compra\n" + ex);
        } finally{
             connAvalia.desconecta();
          }
   
   }
   
   
   public void reavaliarCompra(ModeloCompra modCompra, int codigo, String obs){
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      
      try{
         connAvalia.conexao();
         
         PreparedStatement pst = connAvalia.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'REAVALIAR', OBSERVACAO = '" + obs + "' WHERE CODIGO_COMPRA = '" + codigo + "'");
         PreparedStatement pst1 = connAvalia.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'REAVALIAR' WHERE CODIGO_COMPRA = '" + codigo + "'");
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja reprovar a compra")){
             case 0:
                pst.execute();
                pst1.execute();
                break;
             case 1:
                JOptionPane.showMessageDialog(null,"Operação cancelada");
                break;
             case 2:
                break; 
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleAvaliacaoDeCompra\n\nErro ao reprovar compra\n" + ex);
        } finally{
             connAvalia.desconecta();
          }
   
   }
   
   
   
   
   
    public void cancelaCompra(ModeloCompra modCompra, int codigo){
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      
      try{
         connAvalia.conexao();
         
         PreparedStatement pst = connAvalia.conn.prepareStatement("UPDATE COMPRA SET SITUACAO = 'CANCELADO' WHERE CODIGO_COMPRA = '" + codigo + "'");
         PreparedStatement pst1 = connAvalia.conn.prepareStatement("UPDATE ITEM_COMPRA SET STATUS = 'CANCELADO' WHERE CODIGO_COMPRA = '" + codigo + "'");
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja cancelar a compra")){
             case 0:
                pst.execute();
                pst1.execute();
                break;
             case 1:
                JOptionPane.showMessageDialog(null,"Operação cancelada");
                break;
             case 2:
                break; 
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleAvaliacaoDeCompra\n\nErro ao cancelar compra\n" + ex);
        } finally{
             connAvalia.desconecta();
          }
   
   }
   
   
}
