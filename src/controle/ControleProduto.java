
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.ModeloGrupo;
import modelo.ModeloProduto;
import modelo.ModeloSubGrupo;


public class ControleProduto {
   ModeloGrupo modGrupo = new ModeloGrupo();
   ModeloSubGrupo modSubGrupo = new ModeloSubGrupo();
   ModeloProduto modProduto = new ModeloProduto();
   ConexaoBanco connProduto = new ConexaoBanco();
    //criar instancias da associaçao
   public void Inserir(ModeloProduto mod){
       UIManager.put("OpitonPane.yesButton", "Sim");
       UIManager.put("OpitonPane.noButton", "Não");
       
       modProduto.setGrupo(modGrupo);
       modProduto.setSubGrupo(modSubGrupo);
       try{ 
          connProduto.conexao();
       
          PreparedStatement pst = connProduto.conn.prepareStatement("INSERT INTO PRODUTO (DESCRICAO, ESTOQUE_MINIMO, ESTOQUE, CODIGO_GRUPO, CODIGO_SUBGRUPO, ESTOQUE_MAXIMO, GARANTIA) VALUES(UCASE(?),?,?,?,?,?,?)");
          pst.setString(1, modProduto.getProduto());
          pst.setInt(2, modProduto.getEstoqueMinimo());
          pst.setInt(3, modProduto.getEstoqueAtual());
          pst.setInt(4, modProduto.getGrupo().getCodigo());
          pst.setInt(5, modProduto.getSubGrupo().getCodigo());
          pst.setInt(6, modProduto.getEstoqueMaximo());
          pst.setInt(7, modProduto.getGarantia());
          
          switch(JOptionPane.showConfirmDialog(null, "Deseja salvar o produto?")){
             case 0:
                pst.execute();
                JOptionPane.showMessageDialog(null, "Sucesso ao salvar registro!");             
                break;
             case 1:
                JOptionPane.showMessageDialog(null, "Operação cancelada");
                break;
             case 2:
                break;
          }
       
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao salvar registro:\n" + ex);
       
         }
       
       connProduto.desconecta();
       
    }
    
    
    public void Excluir(ModeloProduto modProduto){
       connProduto.conexao();
       UIManager.put("OpitonPane.yesButton", "Sim");
       UIManager.put("OpitonPane.noButton", "Não");
       modProduto.setGrupo(modGrupo);
       modProduto.setSubGrupo(modSubGrupo);
       
       try{
          PreparedStatement pst = connProduto.conn.prepareStatement("DELETE FROM PRODUTO WHERE CODIGO_PRODUTO = ?");
          pst.setInt(1,modProduto.getCodigoProduto());
          
          
          switch(JOptionPane.showConfirmDialog(null, "Deseja Excluir o produto?")){
             case 0:
                pst.execute();
                JOptionPane.showMessageDialog(null, "Sucesso ao excluir registro!");             
                break;
             case 1:
                JOptionPane.showMessageDialog(null, "Operação cancelada");
                break;
             case 2:
                break;
          }
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao excluir registro:\n" + ex);
         }
       connProduto.conexao();
    }
    
    
    public void Alterar(ModeloProduto modProduto){
       UIManager.put("OptionPane.yesButton", "Sim");
       UIManager.put("OptionPane.noButton", "Não");
       modProduto.setGrupo(modGrupo);
       modProduto.setSubGrupo(modSubGrupo);
       try{
          connProduto.conexao();
          
          PreparedStatement pst = connProduto.conn.prepareStatement("UPDATE PRODUTO SET DESCRICAO = UCASE(?), ESTOQUE_MINIMO = ?, CODIGO_GRUPO = ?, CODIGO_SUBGRUPO = ?, ESTOQUE_MAXIMO = ?, GARANTIA = ? WHERE CODIGO_PRODUTO = ?");
          
          pst.setString(1, modProduto.getProduto());
          pst.setInt(2, modProduto.getEstoqueMinimo());
          pst.setInt(3, modProduto.getGrupo().getCodigo());
          pst.setInt(4, modProduto.getSubGrupo().getCodigo());
          pst.setDouble(5, modProduto.getEstoqueMaximo());
          pst.setInt(6, modProduto.getCodigoProduto());
          pst.setInt(7, modProduto.getGarantia());
          
          switch(JOptionPane.showConfirmDialog(null, "Deseja Alterar o produto?")){
             case 0:
                pst.execute();
                JOptionPane.showMessageDialog(null, "Sucesso ao Alterar registro!");             
                break;
             case 1:
                JOptionPane.showMessageDialog(null, "Operação cancelada");
                break;
             case 2:
                break;
          }
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao alterar registro:\n" + ex);
         }
       
    }
    
    
    public ModeloProduto Primeiro(){
       
       try{
          connProduto.conexao();
          
          connProduto.executaaSQL("SELECT * FROM PRODUTO P "
                                  + "JOIN GRUPO_PRODUTO G ON P.CODIGO_GRUPO = G.CODIGO_GRUPO "
                                  + "JOIN SUBGRUPO_PRODUTO SP ON P.CODIGO_SUBGRUPO = SP.CODIGO_SUBGRUPO ORDER BY CODIGO_PRODUTO; ");
       
          connProduto.rs.first();
          
        
         setaAtributosPesquisa();
         
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao carregar primeiro registro:\n" + ex);
         }
       
       
    
       return modProduto;
    }
    
    public ModeloProduto Ultimo(){
       
       try{
          connProduto.conexao();
          
          connProduto.executaaSQL("SELECT * FROM PRODUTO P "
                                  + "JOIN GRUPO_PRODUTO G ON P.CODIGO_GRUPO = G.CODIGO_GRUPO "
                                  + "JOIN SUBGRUPO_PRODUTO SP ON P.CODIGO_SUBGRUPO = SP.CODIGO_SUBGRUPO ORDER BY CODIGO_PRODUTO; ");
       
          connProduto.rs.last();
          
       
        
        setaAtributosPesquisa();
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao carregar ultimo registro:\n" + ex);
         }
       
       
    
       return modProduto;
    }
    
    
    public ModeloProduto Anterior(){
       
       try{
          connProduto.conexao();
          
          connProduto.rs.previous();
          
   
      
            setaAtributosPesquisa();
      
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao carregar registro anterior:\n" + ex);
         }
       
       
    
       return modProduto;
    }
    
    
    public ModeloProduto Proximo(){
       
       try{
          connProduto.conexao();
          
          connProduto.rs.next();
      
         setaAtributosPesquisa();

       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ControleProduto\n\nErro ao carregar proximo registro:\n" + ex);
         }
       
       
    
       return modProduto;
    }

    
    
    
    public void setaAtributosPesquisa(){
       modProduto.setGrupo(modGrupo);
       modProduto.setSubGrupo(modSubGrupo);
       try{  
          modProduto.setCodigoProduto(connProduto.rs.getInt("CODIGO_PRODUTO"));
          modProduto.setProduto(connProduto.rs.getString("DESCRICAO"));
          modProduto.getGrupo().setNomeGrupo(connProduto.rs.getString("NOME_GRUPO"));
          modProduto.getSubGrupo().setNomeSubGrupo(connProduto.rs.getString("NOME_SUBGRUPO"));
          modProduto.setEstoqueMinimo(connProduto.rs.getInt("ESTOQUE_MINIMO"));
          modProduto.setEstoqueMaximo(connProduto.rs.getInt("ESTOQUE_MAXIMO"));
          modProduto.setGarantia(connProduto.rs.getInt("GARANTIA"));
       } catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "ControleProduto\nsetaAtributosPesquisa\nErro\n" + ex);
         }
       
    }
    
    
    
    
    
}
