
package controle;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloFornecedor;

public class ControleFornecedor {
   
   ConexaoBanco connFornecedor = new ConexaoBanco();
   ModeloFornecedor mod = new ModeloFornecedor();
   
   public void Salvar(ModeloFornecedor mod){
      try{
         connFornecedor.conexao();
         
         PreparedStatement pst = connFornecedor.conn.prepareStatement("INSERT INTO FORNECEDOR (RAZAO_SOCIAL, CNPJ, IE, TELEFONE, ENDERECO, N_ENDERECO, BAIRRO, CODIGO_CIDADE, CODIGO_ESTADO, NOME_FANTASIA, STATUS,EMAIL) VALUES(UCASE(?),?,?,?,UCASE(?),?,UCASE(?),?,?,UCASE(?),?,?)");      
         pst.setString(1, mod.getRazaoSocial());
         pst.setString(2, mod.getCnpj());
         pst.setString(3, mod.getIe());
         pst.setString(4, mod.getTelefone());
         pst.setString(5, mod.getEndereco());
         pst.setInt(6, mod.getNumeroEndereco());
         pst.setString(7, mod.getBairro());
         pst.setInt(8, mod.getCodigoCidade());
         pst.setInt(9, mod.getCodigoEstado());
         pst.setString(10, mod.getNome());
         pst.setString(11, mod.getStatus());  
         pst.setString(12,mod.getEmail());
         pst.execute();
         
         //JOptionPane.showMessageDialog(null, "Fornecedor salvo.");
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro ao inserir fornecedor:\n" + ex);
        }
      
      connFornecedor.desconecta();
   }
   
   
   public void Excluir(ModeloFornecedor mod){
      connFornecedor.conexao();
        try {
           PreparedStatement pst = connFornecedor.conn.prepareStatement("DELETE FROM FORNECEDOR WHERE CODIGO_FORNECEDOR = ? ");
           pst.setInt(1, mod.getCodigo());
           pst.execute();
           
           JOptionPane.showMessageDialog(null, "Sucesso ao excluir!");
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Erro ao excluir:\n" + ex);
        }
      connFornecedor.desconecta();
   }
   
   public void Alterar(ModeloFornecedor mod){
      connFornecedor.conexao();
      
      try{
         PreparedStatement pst = connFornecedor.conn.prepareStatement("UPDATE FORNECEDOR SET RAZAO_SOCIAL = UCASE(?), CNPJ = ?, IE = ?, TELEFONE = ?, ENDERECO = UCASE(?), N_ENDERECO = ?, BAIRRO = UCASE(?), CODIGO_CIDADE = ?, CODIGO_ESTADO = ?, STATUS = ?, EMAIL = ?, NOME_FANTASIA = UCASE(?) WHERE CODIGO_FORNECEDOR = ?;");
         pst.setString(1, mod.getRazaoSocial());
         pst.setString(2, mod.getCnpj());
         pst.setString(3, mod.getIe());
         pst.setString(4, mod.getTelefone());
         pst.setString(5, mod.getEndereco());
         pst.setInt(6, mod.getNumeroEndereco());
         pst.setString(7, mod.getBairro());
         pst.setInt(8, mod.getCodigoCidade());
         pst.setInt(9, mod.getCodigoEstado());
         pst.setString(10, mod.getStatus());  
         pst.setString(11, mod.getEmail());
         pst.setString(12,mod.getNome());
         pst.setInt(13, mod.getCodigo());
         
         pst.execute();
         
         JOptionPane.showMessageDialog(null, "Sucesso ao alterar registro");
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro ao alterar registro:\n" + ex);
        }
      connFornecedor.desconecta();
      
   }
   
   
    
   public ModeloFornecedor Primeiro(){
      connFornecedor.conexao();
      
      try{
         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO;");
         connFornecedor.rs.first();
        
         setaAtributosPesquisa();
         
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar primeiro registro:\n" + e);
        }
      //connFornecedor.desconecta();
      return mod;
      
      
   }
   
   
   public ModeloFornecedor Ultimo(){
      connFornecedor.conexao();
      
      try{
         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
         connFornecedor.rs.last();
         
         setaAtributosPesquisa();
         
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar primeiro registro:\n" + e);
        }
      //connFornecedor.desconecta();
       
       
      return mod;
   }
   
   
   public ModeloFornecedor Anterior(){
      connFornecedor.conexao();
      
      try{
         connFornecedor.rs.previous();
         
         setaAtributosPesquisa();
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro ao carregar registro anterior:\n" + ex);   
        }
      //connFornecedor.desconecta();
      return mod;       
   }
   
   public ModeloFornecedor Proximo(){
      connFornecedor.conexao();
      try{
         connFornecedor.rs.next();
         setaAtributosPesquisa();
      
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro ao carregar proximo registro:\n" + ex);
        } 
      //connFornecedor.desconecta();
      return mod;
      
   }    
   
   
   public void setaAtributosPesquisa(){
      try{
         mod.setCodigo(connFornecedor.rs.getInt("CODIGO_FORNECEDOR"));
         //  JOptionPane.showMessageDialog(null, mod.getCodigo());
         mod.setRazaoSocial(connFornecedor.rs.getString("RAZAO_SOCIAL"));
         mod.setNome(connFornecedor.rs.getString("NOME_FANTASIA"));
         mod.setCnpj(connFornecedor.rs.getString("CNPJ"));
         mod.setIe(connFornecedor.rs.getString("IE"));
         mod.setTelefone(connFornecedor.rs.getString("TELEFONE"));
         mod.setEndereco(connFornecedor.rs.getString("ENDERECO"));
         mod.setNumeroEndereco(connFornecedor.rs.getInt("N_ENDERECO"));
         mod.setBairro(connFornecedor.rs.getString("BAIRRO"));
         mod.setCidade(connFornecedor.rs.getString("NOME_CIDADE"));
         mod.setEstado(connFornecedor.rs.getString("NOME_ESTADO"));
         mod.setEmail(connFornecedor.rs.getString("EMAIL"));
         mod.setStatus(connFornecedor.rs.getString("STATUS"));
      } catch(SQLException e){
         JOptionPane.showMessageDialog(null, "ControleFornecedor\nsetaAtributos\nErro" + e);
        }
   }
   
   
   
   
   
}
