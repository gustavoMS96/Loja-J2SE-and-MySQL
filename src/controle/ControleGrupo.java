
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloGrupo;

public class ControleGrupo {
    
    ConexaoBanco connGrupo = new ConexaoBanco();
    ModeloGrupo mod = new ModeloGrupo();
    
    
    public void gravarRegistro(ModeloGrupo mod){
       
       try{ 
          connGrupo.conexao(); 
          PreparedStatement pst = connGrupo.conn.prepareStatement("INSERT INTO GRUPO_PRODUTO (NOME_GRUPO) VALUES(UCASE(?))");
          
          pst.setString(1, mod.getNomeGrupo());
          pst.execute();
          
          JOptionPane.showMessageDialog(null, "Registro salvo!");
          
          connGrupo.desconecta();
          
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao salvar registro:\n" + e );
         }
    }
    
    
    public void alterarRegistro(ModeloGrupo mod){
       connGrupo.conexao();
       try{
          PreparedStatement pst = connGrupo.conn.prepareStatement("UPDATE GRUPO_PRODUTO SET NOME_GRUPO = UCASE(?) WHERE CODIGO_GRUPO = ?");
          pst.setString(1, mod.getNomeGrupo());
          pst.setInt(2, mod.getCodigo());
          pst.execute();
          
          JOptionPane.showMessageDialog(null, "Sucesso ao alterar registro!" );
          connGrupo.desconecta();
       } catch(SQLException ex){
            
            JOptionPane.showMessageDialog(null, "Erro ao alterar registro:\n" + ex);
       
         } 
        
        
    }
    
    public void excluirRegistro(ModeloGrupo mod){
       connGrupo.conexao();
       
       try{
           
          PreparedStatement pst = connGrupo.conn.prepareStatement("DELETE FROM GRUPO_PRODUTO WHERE CODIGO_GRUPO = ?");
          pst.setInt(1, mod.getCodigo());
          pst.execute();
          
          JOptionPane.showMessageDialog(null, "Sucesso ao excluir registro!" );
          connGrupo.desconecta();
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao excluir registro:\n" + ex );
         }
    
    }
    
    
    public ModeloGrupo buscarPrimeiroRegistro(){
       
       connGrupo.conexao();
       
       try{
          connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");
          connGrupo.rs.first();
          
          defineAtributos();
          
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar primeiro registro:\n" + e);
         }
       return mod;
    }
    
    public ModeloGrupo buscarUltimoRegistro(){
       connGrupo.conexao();
       
       try{
          connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");
          connGrupo.rs.last();
          
          defineAtributos();
          
          
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar ultimo registro:\n" + e);
         }
       return mod;
    }
    
    
    public ModeloGrupo buscarRegistroAnterior(){
       connGrupo.conexao();
       
       try{
          
          connGrupo.rs.previous(); 
          
          defineAtributos();
          
          
       } catch(SQLException r){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar registro anterior:\n" + r);
         }
       return mod;
    }
    
    
    public ModeloGrupo buscarProximoRegistro(){
       connGrupo.conexao();
       
       try{
          connGrupo.rs.next();
          
          defineAtributos();
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao selecionar proximo registro:\n" + e);
         }
    return mod;
    }
    
    
    public void defineAtributos(){
       try{
         mod.setCodigo(connGrupo.rs.getInt("CODIGO_GRUPO"));
          mod.setNomeGrupo(connGrupo.rs.getString("NOME_GRUPO"));
       
       
       }catch(SQLException e){
          JOptionPane.showMessageDialog(null, "ControleGrupo\ndefineAtributos\n" + e);
       }
    }
    
}
