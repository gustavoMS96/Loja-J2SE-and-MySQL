
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloCliente;

public class ControleCliente {
    
    ConexaoBanco connCliente = new ConexaoBanco();
    ModeloCliente modCliente = new ModeloCliente();
    
	
	
	private String setaDataBanco(String data){//Seto padrão MySQL
		return data.substring(6) + "-" + data.substring(5,7) + "-" + data.substring(0,2); 
	}
	
	private String setaDataBr(String data){//seto padrão BR
		return data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
	}
	
	
    public void InserirCliente(ModeloCliente modCliente){
       
       try{
          connCliente.conexao();
          PreparedStatement pst = connCliente.conn.prepareStatement("INSERT INTO CLIENTE(NOME_CLIENTE, RG, CODIGO_ORGAO_EMISSOR, CPF, SEXO, CODIGO_ESTADO_CIVIL, TELEFONE, CEP, ENDERECO, NUMERO_ENDERECO, BAIRRO, CODIGO_CIDADE, CODIGO_ESTADO, EMAIL, DATA_NASCIMENTO) VALUES(UCASE(?), ?, ?, ?, ?, ?, ?, ?, UCASE(?), ?, UCASE(?), ?, ?, LCASE(?), ?)"); 
          
          pst.setString(1, modCliente.getNome());
          pst.setLong(2, modCliente.getRg());
          pst.setInt(3, modCliente.getCodigoOrgaoEmissor());
          pst.setString(4, modCliente.getCpf());
          pst.setString(5, modCliente.getSexo());
          pst.setInt(6, modCliente.getCodigoEstadoCivil());
          pst.setString(7, modCliente.getTelefone());
          pst.setString(8, modCliente.getCep());
          pst.setString(9, modCliente.getEndereco());
          pst.setInt(10, modCliente.getNumeroCasa());
          pst.setString(11, modCliente.getBairro());
          pst.setInt(12, modCliente.getCodigoCidade());
          pst.setInt(13, modCliente.getCodigoEstado());
          pst.setString(14, modCliente.getEmail());
          pst.setString(15, setaDataBanco(modCliente.getNascimento()));
          pst.execute();
          
          //JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao inserir cliente:\n" + ex);
         }
        
        connCliente.desconecta();
    }
    
    public void ExcluirCliente(ModeloCliente mod){
       connCliente.conexao();
       
       try{
          PreparedStatement pst = connCliente.conn.prepareStatement("DELETE FROM CLIENTE WHERE CODIGO_CLIENTE = ?");
          pst.setInt(1, modCliente.getCodigoCliente());
          pst.execute();
          JOptionPane.showMessageDialog(null, "Sucesso ao EXCLUIR:\n");
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao EXCLUIR:\n" + e);
         }
       connCliente.desconecta();
    }
   
    
    public void AlterarCliente(ModeloCliente mod){
       connCliente.conexao();
       
       try{
          PreparedStatement pst = connCliente.conn.prepareStatement("UPDATE CLIENTE SET NOME_CLIENTE = UCASE(?), RG = ?, CODIGO_ORGAO_EMISSOR = ?, CPF = ?, SEXO =?, CODIGO_ESTADO_CIVIL = ?, TELEFONE = ?, CEP = ?, ENDERECO = UCASE(?), NUMERO_ENDERECO = ?, BAIRRO = UCASE(?), CODIGO_CIDADE = ?, CODIGO_ESTADO = ?, EMAIL = LCASE(?), DATA_NASCIMENTO = ? WHERE CODIGO_CLIENTE = ?");
          pst.setString(1, modCliente.getNome());
          pst.setLong(2, modCliente.getRg());
          pst.setInt(3, modCliente.getCodigoOrgaoEmissor());
          pst.setString(4, modCliente.getCpf());
          pst.setString(5, modCliente.getSexo());
          pst.setInt(6, modCliente.getCodigoEstadoCivil());
          pst.setString(7, modCliente.getTelefone());
          pst.setString(8, modCliente.getCep());
          pst.setString(9, modCliente.getEndereco());
          pst.setInt(10, modCliente.getNumeroCasa());
          pst.setString(11, modCliente.getBairro());
          pst.setInt(12, modCliente.getCodigoCidade());
          pst.setInt(13, modCliente.getCodigoEstado());
          pst.setString(14, modCliente.getEmail());
          pst.setString(15, setaDataBanco(modCliente.getNascimento()));
          pst.setInt(16, modCliente.getCodigoCliente());
          
          pst.execute();
          
          JOptionPane.showMessageDialog(null, "Cliente alterado!");
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao ALTERAR:\n" + ex);
         }
       connCliente.desconecta();
       
    }
    
    
    
    public ModeloCliente Primeiro(){
    
       try{
          connCliente.conexao();
          connCliente.executaaSQL("SELECT * FROM CLIENTE C JOIN CIDADE CI ON C.CODIGO_CIDADE = CI.CODIGO_CIDADE JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO JOIN ESTADO_CIVIL EC ON C.CODIGO_ESTADO_CIVIL = EC.CODIGO_ESTADO_CIVIL JOIN ORGAO_EMISSOR OE ON C.CODIGO_ORGAO_EMISSOR = OE.CODIGO_ORGAO_EMISSOR ORDER BY C.CODIGO_CLIENTE;");
          connCliente.rs.first();
          setaAtributosPesquisa();
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar primeiro registro" + ex);
         }
       
       
       return modCliente;
    }
    
   public ModeloCliente Anterior(){
    
       try{
          connCliente.conexao();
          connCliente.rs.previous();
          setaAtributosPesquisa();
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar registro anterior:\n" + ex);
         }
       
       
       return modCliente;
    }
   
   public ModeloCliente Proximo(){
    
       try{
          connCliente.conexao();
          connCliente.rs.next();
          setaAtributosPesquisa();
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar proximo registro" + ex);
         }
       
       
       return modCliente;
    }
   
   
   public ModeloCliente Ultimo(){
    
       try{
          connCliente.conexao();
          connCliente.executaaSQL("SELECT * FROM CLIENTE C JOIN CIDADE CI ON C.CODIGO_CIDADE = CI.CODIGO_CIDADE JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO JOIN ESTADO_CIVIL EC ON C.CODIGO_ESTADO_CIVIL = EC.CODIGO_ESTADO_CIVIL JOIN ORGAO_EMISSOR OE ON C.CODIGO_ORGAO_EMISSOR = OE.CODIGO_ORGAO_EMISSOR ORDER BY C.CODIGO_CLIENTE;");
          connCliente.rs.last();
          setaAtributosPesquisa();
          
       } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar ultimo registro:\n" + ex);
         }
       
       
       return modCliente;
    }
    
   
   public void setaAtributosPesquisa(){
       try{   
          modCliente.setCodigoCliente(connCliente.rs.getInt("CODIGO_CLIENTE"));
          modCliente.setNome(connCliente.rs.getString("NOME_CLIENTE"));
          modCliente.setRg(connCliente.rs.getLong("RG"));
          modCliente.setCpf(connCliente.rs.getString("CPF"));
          modCliente.setNomeOrgaoEmissor(connCliente.rs.getString("NOME_ORGAO"));
          modCliente.setNascimento(setaDataBr(connCliente.rs.getString("DATA_NASCIMENTO")));
          modCliente.setSexo(connCliente.rs.getString("SEXO"));
          modCliente.setNomeEstadoCivil(connCliente.rs.getString("NOME_ESTADO_CIVIL"));
          modCliente.setTelefone(connCliente.rs.getString("TELEFONE"));
          modCliente.setCep(connCliente.rs.getString("CEP"));
          modCliente.setEndereco(connCliente.rs.getString("ENDERECO"));
          modCliente.setNumeroCasa(connCliente.rs.getInt("NUMERO_ENDERECO"));
          modCliente.setBairro(connCliente.rs.getString("BAIRRO"));
          modCliente.setCidade(connCliente.rs.getString("NOME_CIDADE"));
          modCliente.setEstado(connCliente.rs.getString("NOME_ESTADO"));
          modCliente.setEmail(connCliente.rs.getString("EMAIL"));          
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"ControleCliente\nsetaAtributosPesquisa\nErro\n" + e);
         }
   }
   
}
