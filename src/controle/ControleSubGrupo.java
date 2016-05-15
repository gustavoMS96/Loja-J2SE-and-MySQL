/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloGrupo;
import modelo.ModeloSubGrupo;

/**
 *
 * @author Gustavo
 */
public class ControleSubGrupo {
    ModeloSubGrupo modSubGrupo = new ModeloSubGrupo();
    ModeloGrupo modGrupo = new ModeloGrupo();
    ConexaoBanco connSubGrupo = new ConexaoBanco();
     
     
    public void Inserir(ModeloSubGrupo modSubGrupo){
       connSubGrupo.conexao();
       modSubGrupo.setGrupo(modGrupo);
       try{
          PreparedStatement pst = connSubGrupo.conn.prepareStatement("INSERT INTO SUBGRUPO_PRODUTO (CODIGO_GRUPO, NOME_SUBGRUPO) VALUES(?,UCASE(?))");
          pst.setInt(1, modSubGrupo.getGrupo().getCodigo());
          pst.setString(2, modSubGrupo.getNomeSubGrupo());
          
          pst.execute();
          
          //JOptionPane.showMessageDialog(null, "Registro salvo com sucesso");
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao salver registro:\n" + e);
         }
       connSubGrupo.desconecta();
    }
    
    
    public void Alterar(ModeloSubGrupo modSubGrupo){
        modSubGrupo.setGrupo(modGrupo);
       try{
          connSubGrupo.conexao();
          
          PreparedStatement pst = connSubGrupo.conn.prepareStatement("UPDATE SUBGRUPO_PRODUTO SET CODIGO_GRUPO = ? , NOME_SUBGRUPO = UCASE(?) WHERE CODIGO_SUBGRUPO = ?");
          pst.setInt(1, modSubGrupo.getGrupo().getCodigo());
          pst.setString(2, modSubGrupo.getNomeSubGrupo());
          pst.setInt(3, modSubGrupo.getCodigo());
          
          pst.execute();
          
         // JOptionPane.showMessageDialog(null, "Registro alterado com sucesso");
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao alterar registro:\n" + e);
         }
       connSubGrupo.desconecta();
    } 
        
    
    public void Excluir(ModeloSubGrupo modSubGrupo){
        modSubGrupo.setGrupo(modGrupo);
       try{
          connSubGrupo.conexao();
          PreparedStatement pst = connSubGrupo.conn.prepareStatement("DELETE FROM SUBGRUPO_PRODUTO WHERE CODIGO_SUBGRUPO = ?");
          pst.setInt(1, modSubGrupo.getCodigo());
          
          pst.execute();
          
        //  JOptionPane.showMessageDialog(null, "Registro excluido com sucesso");
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao excluir registro:\n" + e);
         }
       connSubGrupo.desconecta();
        
        
    
    }
    
    
    
    
    
    
    public ModeloSubGrupo Primeiro(){
        modSubGrupo.setGrupo(modGrupo);
       try{
          connSubGrupo.conexao();
          connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO ORDER BY CODIGO_SUBGRUPO;");
          connSubGrupo.rs.first();
          
          modSubGrupo.setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
          modSubGrupo.getGrupo().setNomeGrupo(connSubGrupo.rs.getString("NOME_GRUPO"));
          modSubGrupo.setNomeSubGrupo(connSubGrupo.rs.getString("NOME_SUBGRUPO"));
       
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar primeiro registro:\n" + ex);
         }
       return modSubGrupo;
    }
    
    public ModeloSubGrupo Ultimo(){
        modSubGrupo.setGrupo(modGrupo);
       try{
          
          
          connSubGrupo.conexao();
          connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO ORDER BY CODIGO_SUBGRUPO;");
          connSubGrupo.rs.last();
          
          modSubGrupo.setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
          modSubGrupo.getGrupo().setNomeGrupo(connSubGrupo.rs.getString("NOME_GRUPO"));
          modSubGrupo.setNomeSubGrupo(connSubGrupo.rs.getString("NOME_SUBGRUPO"));
       
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar primeiro registro:\n" + ex);
         }
       return modSubGrupo;
    }
    
    public ModeloSubGrupo Anterior(){
        modSubGrupo.setGrupo(modGrupo);
       try{
          connSubGrupo.conexao();
          //connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO");
          connSubGrupo.rs.previous();
          
          modSubGrupo.setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
          modSubGrupo.getGrupo().setNomeGrupo(connSubGrupo.rs.getString("NOME_GRUPO"));
          modSubGrupo.setNomeSubGrupo(connSubGrupo.rs.getString("NOME_SUBGRUPO"));
       
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar primeiro registro:\n" + ex);
         }
       return modSubGrupo;
    }
    
    
    public ModeloSubGrupo Proximo(){
        modSubGrupo.setGrupo(modGrupo);
       try{
          connSubGrupo.conexao();
          //connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO");
          connSubGrupo.rs.next();
          
          modSubGrupo.setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
          modSubGrupo.getGrupo().setNomeGrupo(connSubGrupo.rs.getString("NOME_GRUPO"));
          modSubGrupo.setNomeSubGrupo(connSubGrupo.rs.getString("NOME_SUBGRUPO"));
       
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar primeiro registro:\n" + ex);
         }
       return modSubGrupo;
    }
    
        
    

    
}
