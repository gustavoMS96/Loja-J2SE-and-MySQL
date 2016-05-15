

package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.ModeloFuncionario;


public class ControleFuncionario {
   
   ConexaoBanco connFunc = new ConexaoBanco();
   ModeloFuncionario modFuncionario = new ModeloFuncionario();
   
   
   private String setaDataBanco(String data){//Seto padrão MySQL
      return data.substring(6) + "-" + data.substring(5,7) + "-" + data.substring(0,2); 
   }
	
   private String setaDataBr(String data){//seto padrão BR
      return data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
   }
   
   private void defineUIManager(){
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
   }
   
   
   public void salvar(ModeloFuncionario modFuncionario){
      defineUIManager();
	  
      try{
         connFunc.conexao();
         
         PreparedStatement pst = connFunc.conn.prepareStatement("INSERT INTO FUNCIONARIO (NOME_FUNCIONARIO, RG, CODIGO_ORGAO_EMISSOR, CPF, DATA_NASCIMENTO, SEXO, CODIGO_ESTADO_CIVIL, TELEFONE, CEP, ENDERECO, NUMERO_ENDERECO, BAIRRO, CODIGO_CIDADE, CODIGO_ESTADO, EMAIL, CODIGO_CARGO, SALARIO, STATUS, PIS) VALUES(UCASE(?),?,?,?,?,?,?,?,?,UCASE(?),?,UCASE(?),?,?,?,?,?,?,?)");
         
         pst.setString(1, modFuncionario.getNome());
         pst.setLong(2, modFuncionario.getRg());
         pst.setInt(3, modFuncionario.getCodigoOrgaoEmissor());
         pst.setString(4, modFuncionario.getCpf());
         pst.setString(5, setaDataBanco(modFuncionario.getNascimento()));
         pst.setString(6, modFuncionario.getSexo());
         pst.setInt(7, modFuncionario.getCodigoEstadoCivil());
         pst.setString(8, modFuncionario.getTelefone());
         pst.setString(9, modFuncionario.getCep());
         pst.setString(10, modFuncionario.getEndereco());
         pst.setInt(11, modFuncionario.getNumeroEndereco());
         pst.setString(12, modFuncionario.getBairro());
         pst.setInt(13, modFuncionario.getCodigoCidade());
         pst.setInt(14, modFuncionario.getCodigoEstado());
         pst.setString(15, modFuncionario.getEmail());
         pst.setInt(16, modFuncionario.getCodigoCargo());
         pst.setDouble(17, modFuncionario.getSalario());
         pst.setString(18, modFuncionario.getStatus());
         pst.setLong(19, modFuncionario.getPis());
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja salvar o funcionario?")){
            case 0:
               pst.execute();
               JOptionPane.showMessageDialog(null, "Funcionario salvo.");
               break;
            case 1:
               JOptionPane.showMessageDialog(null, "Operação cancelada");
               break;
            case 2:
               break;
         }
         
       
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nErro ao salvar funcionario:\n" + ex);
        }
      
      
      connFunc.desconecta();
      
   
   }
   
   
   
   public void excluir(ModeloFuncionario modFuncionario){
      defineUIManager();
	  
      try{
         connFunc.conexao();
         
         PreparedStatement pst = connFunc.conn.prepareStatement("DELETE FROM FUNCIONARIO WHERE CODIGO_FUNCIONARIO = ?");
         pst.setInt(1, modFuncionario.getCodigoFuncionario());
         
         
         switch(JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o funcionario?")){
            
            case 0:
               pst.execute();
               JOptionPane.showMessageDialog(null, "Funcionario excluido");
               break;
            case 1:
               JOptionPane.showMessageDialog(null, "Operação cancelada");
               break;
            case 2:
               break;
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nErro ao excluir funcionario:\n" + ex);
        }
      connFunc.desconecta();
   }
   
   
   public void alterar(ModeloFuncionario modFuncionario){
      defineUIManager();
      
      try{
         connFunc.conexao();
         
         PreparedStatement pst = connFunc.conn.prepareStatement("UPDATE FUNCIONARIO SET NOME_FUNCIONARIO = UCASE(?), RG = ?, CODIGO_ORGAO_EMISSOR = ?, CPF = ?, DATA_NASCIMENTO = ?, SEXO = ?, CODIGO_ESTADO_CIVIL = ?, TELEFONE = ?, CEP = ?, ENDERECO = UCASE(?), NUMERO_ENDERECO = ?, BAIRRO = UCASE(?), CODIGO_CIDADE = ?, CODIGO_ESTADO = ?, EMAIL = ?, PIS = ?, STATUS = ?, SALARIO = ? WHERE CODIGO_FUNCIONARIO = ?");
         
         pst.setString(1, modFuncionario.getNome());
         pst.setLong(2, modFuncionario.getRg());
         pst.setInt(3, modFuncionario.getCodigoOrgaoEmissor());
         pst.setString(4, modFuncionario.getCpf());
         pst.setString(5, setaDataBanco(modFuncionario.getNascimento()));
         pst.setString(6, modFuncionario.getSexo());
         pst.setInt(7, modFuncionario.getCodigoEstadoCivil());
         pst.setString(8, modFuncionario.getTelefone());
         pst.setString(9, modFuncionario.getCep());
         pst.setString(10, modFuncionario.getEndereco());
         pst.setInt(11, modFuncionario.getNumeroEndereco());
         pst.setString(12, modFuncionario.getBairro());
         pst.setInt(13, modFuncionario.getCodigoCidade());
         pst.setInt(14, modFuncionario.getCodigoEstado());
         pst.setString(15, modFuncionario.getEmail());
         pst.setLong(16, modFuncionario.getPis());
         pst.setString(17, modFuncionario.getStatus());
         pst.setDouble(18, modFuncionario.getSalario());
         pst.setInt(19, modFuncionario.getCodigoFuncionario());
         
         switch(JOptionPane.showConfirmDialog(null, "Deseja alterar os dados?")){
            case 0:
               pst.execute();
               JOptionPane.showMessageDialog(null, "Alterado.");
            case 1:
               break;
            case 2:
               break; 
         }
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nalterar:\n" + ex);
        }
      
      connFunc.desconecta();
   }
   
   
   public ModeloFuncionario primeiro(){
      
      try{
         connFunc.conexao();
         
         connFunc.executaaSQL("SELECT * FROM FUNCIONARIO F "
                 + "JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE "
                 + "JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO "
                 + "JOIN ORGAO_EMISSOR O ON F.CODIGO_ORGAO_EMISSOR = O.CODIGO_ORGAO_EMISSOR "
                 + "JOIN ESTADO_CIVIL EC ON F.CODIGO_ESTADO_CIVIL = EC.CODIGO_ESTADO_CIVIL "
                 + "JOIN CARGO CA ON F.CODIGO_CARGO = CA.CODIGO_CARGO  ;");
         
         connFunc.rs.first();
         
         setaAtributos();
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nprimeiro:\n" + ex);
        }
      
      
      return modFuncionario;
   }
   
   
   public ModeloFuncionario ultimo(){
      
      try{
         connFunc.conexao();
         
         connFunc.executaaSQL("SELECT * FROM FUNCIONARIO F "
                 + "JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE "
                 + "JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO "
                 + "JOIN ORGAO_EMISSOR O ON F.CODIGO_ORGAO_EMISSOR = O.CODIGO_ORGAO_EMISSOR "
                 + "JOIN ESTADO_CIVIL EC ON F.CODIGO_ESTADO_CIVIL = EC.CODIGO_ESTADO_CIVIL "
                 + "JOIN CARGO CA ON F.CODIGO_CARGO = CA.CODIGO_CARGO  ;");
         
         connFunc.rs.last();
         
         setaAtributos();
         
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nultimo:\n" + ex);
        }
      
      
      return modFuncionario;
   }
   
   
   
   public ModeloFuncionario anterior(){
      
      try{
         connFunc.conexao();
         connFunc.rs.previous();
         
         setaAtributos();
       
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nanterior:\n" + ex);
        }
      
      
      return modFuncionario;
   }
   
   
   
   
   public ModeloFuncionario Proximo(){
      
      try{
         connFunc.conexao();
         connFunc.rs.next();
        
         setaAtributos();
        
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nproximo:\n" + ex);
        }
      
      
      return modFuncionario;
   }
   
   
   public ModeloFuncionario procurar(int codigo){
   try{
         connFunc.conexao();
         
         connFunc.executaaSQL("SELECT * FROM FUNCIONARIO F "
                 + "JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE "
                 + "JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO "
                 + "JOIN ORGAO_EMISSOR O ON F.CODIGO_ORGAO_EMISSOR = O.CODIGO_ORGAO_EMISSOR "
                 + "JOIN ESTADO_CIVIL EC ON F.CODIGO_ESTADO_CIVIL = EC.CODIGO_ESTADO_CIVIL "
                 + "JOIN CARGO CA ON F.CODIGO_CARGO = CA.CODIGO_CARGO WHERE CODIGO_FUNCIONARIO = '" + codigo + "';");
         
         connFunc.rs.first();
         
      
        
        setaAtributos();
        
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "ControleFuncionario\n\nprocurar:\n" + ex);
        }
      
      
      return modFuncionario;
   
   }
   
   
   public void setaAtributos(){
   
      try{ 
       
         modFuncionario.setCodigoFuncionario(connFunc.rs.getInt("CODIGO_FUNCIONARIO"));
         modFuncionario.setNome(connFunc.rs.getString("NOME_FUNCIONARIO"));
         modFuncionario.setRg(connFunc.rs.getLong("RG"));
         modFuncionario.setNomeOrgaoEmissor(connFunc.rs.getString("NOME_ORGAO"));
         modFuncionario.setCpf(connFunc.rs.getString("CPF"));
         modFuncionario.setNascimento(setaDataBr(connFunc.rs.getString("DATA_NASCIMENTO")));
         modFuncionario.setSexo(connFunc.rs.getString("SEXO"));
         modFuncionario.setNomeEstadoCivil(connFunc.rs.getString("NOME_ESTADO_CIVIL"));
         modFuncionario.setTelefone(connFunc.rs.getString("TELEFONE"));
         modFuncionario.setCep(connFunc.rs.getString("CEP"));
         modFuncionario.setEndereco(connFunc.rs.getString("ENDERECO"));
         modFuncionario.setNumeroEndereco(connFunc.rs.getInt("NUMERO_ENDERECO"));
         modFuncionario.setBairro(connFunc.rs.getString("BAIRRO"));
         modFuncionario.setCidade(connFunc.rs.getString("NOME_CIDADE"));
         modFuncionario.setEstado(connFunc.rs.getString("NOME_ESTADO"));
         modFuncionario.setEmail(connFunc.rs.getString("EMAIL"));
         modFuncionario.setCargo(connFunc.rs.getString("CARGO"));
         modFuncionario.setSalario(connFunc.rs.getDouble("SALARIO"));
         modFuncionario.setStatus(connFunc.rs.getString("STATUS"));         
         modFuncionario.setPis(connFunc.rs.getLong("PIS"));
      }catch(SQLException e){
          JOptionPane.showMessageDialog(null, "ControleFuncionario\nsetaAtributos()\n" + e);
       }
       
   }
   
   
}
