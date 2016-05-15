
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloCompra;
import modelo.ModeloFornecedor;
import modelo.ModeloFuncionario;
import modelo.ModeloItemCompra;
import modelo.ModeloRecebimento;



public class ControleRecebimento {
    ConexaoBanco connReceb = new ConexaoBanco();
    ModeloRecebimento modReceb = new ModeloRecebimento();
    ModeloFornecedor modForn = new ModeloFornecedor();
    ModeloItemCompra modItemCompra = new ModeloItemCompra();
    ModeloCompra modCompra = new ModeloCompra();
    ModeloFuncionario modFunc = new ModeloFuncionario();
    
    
    private String dataBanco(String data){
       return data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
    }
    
    
    private String dataPadraoBR(String data){
       return  data.substring(8) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
    }
    
    
    
    public void salvaRecebimento(ModeloRecebimento modReceb){
       
       try{
          connReceb.conexao();
          PreparedStatement pst = connReceb.conn.prepareStatement("INSERT INTO RECEBER_COMPRA(DATA_RECEBIMENTO, CODIGO_COMPRA, NOTA_FISCAL, SERIE_NF, DATA_EMISSAO, STATUS) VALUES (?,?,?,UCASE(?),?, 'FECHADO')");
          PreparedStatement pst1 = connReceb.conn.prepareStatement("UPDATE COMPRA, ITEM_COMPRA  SET COMPRA.SITUACAO = 'ENTREGUE', ITEM_COMPRA.STATUS = 'ENTREGUE' WHERE COMPRA.CODIGO_COMPRA = ? AND ITEM_COMPRA.CODIGO_COMPRA = ?;");
          
          
          
        //  JOptionPane.showMessageDialog(null, modReceb.getCompra().getCodigoCompra());
          pst.setString(1, modReceb.getDataRecebimento());
          pst.setInt(2, modReceb.getCompra().getCodigoCompra());
          pst.setString(3, modReceb.getNotaFiscal());
          pst.setString(4, modReceb.getSerie());
          pst.setString(5, dataBanco(modReceb.getDataEmissao()));
          pst.execute();
      //    JOptionPane.showMessageDialog(null, modReceb.getCompra().getCodigoCompra());
          pst1.setInt(1, modReceb.getCompra().getCodigoCompra());
       //   JOptionPane.showMessageDialog(null, modReceb.getCompra().getCodigoCompra());
          pst1.setInt(2, modReceb.getCompra().getCodigoCompra());
          pst1.execute();
          
       } 
       catch(SQLException e){
          JOptionPane.showMessageDialog(null, "Controle Receb.\nErro ao salvar o recebimento\n" +e);
       }
       finally{
          connReceb.desconecta();
       }
       
    }
    
 
    
   
    public ModeloRecebimento procurarRecebimento(int codigo){
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT DATA_RECEBIMENTO, CODIGO_COMPRA, NOTA_FISCAL, SERIE_NF "
                  + "FROM RECEBER_COMPRA "
                  + "WHERE CODIGO_RECEBIMENTO = '" + codigo + "';");
          connReceb.rs.first();
          
          modReceb.setCodigoCompra(connReceb.rs.getInt("CODIGO_COMPRA"));
       
          modReceb.setDataRecebimento(dataPadraoBR(connReceb.rs.getString("DATA_RECEBIMENTO")));
         
          modReceb.setNotaFiscal(connReceb.rs.getString("NOTA_FISCAL"));
          modReceb.setSerie(connReceb.rs.getString("SERIE_NF"));
        
          
          
       } catch(SQLException e){
         JOptionPane.showMessageDialog(null, "ControleRecebimento\nErro ao procurar recebimento\n" + e); 
         }
       
       return modReceb;
    }   
       
    
   /*
    update do estoque do produto no momento que faço o recebimento, pegando os itens da compra que foi recebida
    */
    
    public void lancarEstoque(int quantidade, String produto){
       
       int codigoProduto;
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT CODIGO_PRODUTO FROM PRODUTO WHERE DESCRICAO = '" + produto + "';");
          connReceb.rs.first();
          codigoProduto = connReceb.rs.getInt("CODIGO_PRODUTO");
          
          PreparedStatement pst = connReceb.conn.prepareStatement("UPDATE PRODUTO SET ESTOQUE = ESTOQUE + '" + quantidade + "' WHERE CODIGO_PRODUTO = '" + codigoProduto + "' AND DESCRICAO = '" + produto + "';");
          pst.execute();
          
          
          
       } 
       catch(SQLException ex){
         JOptionPane.showMessageDialog(null, "ControleRecebimento\nErro ao procurar codigo para lançar estoque\n" + ex);
       }
       finally{
          connReceb.desconecta();
       }
    
    
    }
    
    
    
    
    //metodos para consulta de recebimento
    
    
   
      
      
     
    public String carregaDataEmissao(int codigo){
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT DATA_EMISSAO FROM VENCIMENTO WHERE CODIGO_COMPRA = '" + codigo + "' GROUP BY DATA_EMISSAO;");
          connReceb.rs.first();
          modCompra.setDataEmissao(dataBR(connReceb.rs.getString("DATA_EMISSAO")));
          
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "carregaDataEmissao\nErro\n" + e);
         }
       String data = modCompra.getDataEmissao();
       connReceb.desconecta();
       
       return data;
    } 
      
   public String dataBR(String d){
      String dataNormal = d.substring(8,10) + "/" + d.substring(5,7) + "/" + d.substring(0,4);
      
      return dataNormal;
   }
      
      
   
   
   
   
   
   public void alterarRecebimentoAberto(ModeloRecebimento modReceb){
      try{
         connReceb.conexao();
         
         PreparedStatement pst = connReceb.conn.prepareStatement("UPDATE RECEBER_COMPRA "
                                                                     + "SET NOTA_FISCAL = ?, "
                                                                     + "SERIE_NF = UCASE(?),"
                                                                     + "STATUS = 'FECHADO' "
                                                                     + "WHERE CODIGO_RECEBIMENTO = ?");
         
         pst.setString(1, modReceb.getNotaFiscal());
         pst.setString(2, modReceb.getSerie());
         pst.setInt(3, modReceb.getCodigoRecebimento());
         pst.execute();
         
         JOptionPane.showMessageDialog(null, "Recebimento alterado");
      
      } 
      catch(SQLException e){
         JOptionPane.showMessageDialog(null, "Classe: ControleRecebimento\nFunção: alterarRecebimento()\nErro: " + e);
      
      }
      finally{
         connReceb.desconecta();
      }
   
   }
   
   
   
   
   
   
   
   
   
      
}
