/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloCompra;
import modelo.ModeloFornecedor;
import modelo.ModeloFuncionario;
import modelo.ModeloItemCompra;
import modelo.ModeloRecebimento;

/**
 *
 * @author Gustavo M S 
 * 15/04/2016
 * 
 */

public class ControleConsultarRecebimento {
   ConexaoBanco connReceb = new ConexaoBanco();
    ModeloRecebimento modReceb = new ModeloRecebimento();
    ModeloFornecedor modForn = new ModeloFornecedor();
    ModeloItemCompra modItemCompra = new ModeloItemCompra();
    ModeloCompra modCompra = new ModeloCompra();
    ModeloFuncionario modFunc = new ModeloFuncionario(); 
   
    
     
   private String dataPadraoBR(String data){//data para usuario
      return data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
   }
     
    
    
    
   public ModeloRecebimento consultarRecebimento(int codigo){
    
       try{
          
        
          
          connReceb.conexao();
          
          connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, " +
                                    "FO.RAZAO_SOCIAL, FO.CNPJ, FO.ENDERECO, FO.N_ENDERECO, C.NOME_CIDADE, E.NOME_ESTADO, " +
                                    "CO.CODIGO_COMPRA, CO.DATA_COMPRA, R.NOTA_FISCAL, R.SERIE_NF, R.DATA_EMISSAO, " +
                                    "CO.VALOR_COMPRA, CO.TIPO_COMPRA " +
                                    "FROM RECEBER_COMPRA R " +
                                    
                                    "JOIN COMPRA CO ON R.CODIGO_COMPRA = CO.CODIGO_COMPRA " +
                                    "JOIN FORNECEDOR FO ON CO.CODIGO_FORNECEDOR = FO.CODIGO_FORNECEDOR " +
                                    "JOIN CIDADE C ON FO.CODIGO_CIDADE = C.CODIGO_CIDADE " +
                                    "JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO " +
                                    "WHERE R.CODIGO_RECEBIMENTO = '" + codigo + "';");
          
          connReceb.rs.first();
          buscaAtributosPesquisa();
      
       } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
         }
       //connReceb.desconecta();
       return modReceb;
    }
    
    
    public ModeloRecebimento primeiroRecebimento(){
    
       try{
          
      
          
          connReceb.conexao();
          
          connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, " +
                                    "FO.RAZAO_SOCIAL, FO.CNPJ, FO.ENDERECO, FO.N_ENDERECO, C.NOME_CIDADE, E.NOME_ESTADO, " +
                                    "CO.CODIGO_COMPRA, CO.DATA_COMPRA, R.NOTA_FISCAL, R.SERIE_NF, R.DATA_EMISSAO, " +
                                    "CO.VALOR_COMPRA, CO.TIPO_COMPRA " +
                                    "FROM RECEBER_COMPRA R " +
                            
                                    "JOIN COMPRA CO ON R.CODIGO_COMPRA = CO.CODIGO_COMPRA " +
                                    "JOIN FORNECEDOR FO ON CO.CODIGO_FORNECEDOR = FO.CODIGO_FORNECEDOR " +
                                    "JOIN CIDADE C ON FO.CODIGO_CIDADE = C.CODIGO_CIDADE " +
                                    "JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO ;");
          
          connReceb.rs.first();
          buscaAtributosPesquisa();
          
   
       } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
         }
       //connReceb.desconecta();
       return modReceb;
    }
    
    
    
    
    
    public ModeloRecebimento ultimoRecebimento(){
    
       try{
          
      
          
          connReceb.conexao();
          
          connReceb.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.DATA_RECEBIMENTO, " +
                                    "FO.RAZAO_SOCIAL, FO.CNPJ, FO.ENDERECO, FO.N_ENDERECO, C.NOME_CIDADE, E.NOME_ESTADO, " +
                                    "CO.CODIGO_COMPRA, CO.DATA_COMPRA, R.NOTA_FISCAL, R.SERIE_NF, R.DATA_EMISSAO, " +
                                    "CO.VALOR_COMPRA, CO.TIPO_COMPRA " +
                                    "FROM RECEBER_COMPRA R " +
                                    
                                    "JOIN COMPRA CO ON R.CODIGO_COMPRA = CO.CODIGO_COMPRA " +
                                    "JOIN FORNECEDOR FO ON CO.CODIGO_FORNECEDOR = FO.CODIGO_FORNECEDOR " +
                                    "JOIN CIDADE C ON FO.CODIGO_CIDADE = C.CODIGO_CIDADE " +
                                    "JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO ;");
          
          connReceb.rs.last();
           
          buscaAtributosPesquisa();
          
       } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
         }
       //connReceb.desconecta();
       return modReceb;
    }
    
    
    
    
    
    
     public ModeloRecebimento voltarRecebimento(){
    
       try{
          
        
          
          connReceb.conexao();
          
        
          connReceb.rs.previous();
           
          
          buscaAtributosPesquisa();
                 
          
        
       } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
         }
       
       return modReceb;
    }
    
    
     
      public ModeloRecebimento proximoRecebimento(){
    
       try{
          
          
          
          connReceb.conexao();
          
         
          
          connReceb.rs.next();
           
          buscaAtributosPesquisa();
          
         
       } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
         }
       
       return modReceb;
    }

      
      
      
      
      
      
     public void buscaAtributosPesquisa(){
     
         try{
          
          
           modReceb.setCompra(modCompra);
           modCompra.setFornecedor(modForn);
           modReceb.setFuncionario(modFunc);
          
          modReceb.setCodigoRecebimento(connReceb.rs.getInt("CODIGO_RECEBIMENTO"));
          modReceb.setDataRecebimento(dataPadraoBR(connReceb.rs.getString("DATA_RECEBIMENTO")));
          modReceb.getCompra().getFornecedor().setRazaoSocial(connReceb.rs.getString("RAZAO_SOCIAL"));
          modReceb.getCompra().getFornecedor().setCnpj(connReceb.rs.getString("CNPJ"));
          modReceb.getCompra().getFornecedor().setEndereco(connReceb.rs.getString("ENDERECO"));
          modReceb.getCompra().getFornecedor().setNumeroEndereco(connReceb.rs.getInt("N_ENDERECO"));
          modReceb.getCompra().getFornecedor().setCidade(connReceb.rs.getString("NOME_CIDADE"));
          modReceb.getCompra().getFornecedor().setEstado(connReceb.rs.getString("NOME_ESTADO"));
          modReceb.getCompra().setCodigoCompra(connReceb.rs.getInt("CODIGO_COMPRA"));
          modReceb.getCompra().setValorTotal(connReceb.rs.getDouble("VALOR_COMPRA"));
          modReceb.getCompra().setDataCompra(dataPadraoBR(connReceb.rs.getString("DATA_COMPRA")));
          modReceb.setNotaFiscal(connReceb.rs.getString("NOTA_FISCAL"));
          modReceb.setSerie(connReceb.rs.getString("SERIE_NF"));
          modReceb.setDataEmissao(dataPadraoBR(connReceb.rs.getString("DATA_EMISSAO")));
          modReceb.getCompra().setTipoCompra(connReceb.rs.getString("TIPO_COMPRA"));

         
         } catch(SQLException ec){
            JOptionPane.showMessageDialog(null, "consultarRecebimento : \n" + ec);
       
           }
       
       
    }
     
     
     
     
    
     
     
     
}
