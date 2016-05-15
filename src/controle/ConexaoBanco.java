
package controle;

import java.sql.*;
import javax.swing.*;
public class ConexaoBanco {
    public Statement stm;//preparar e realizar pesquisas no banco de dados
    public ResultSet rs;//armazena o resultado de uma pesquisa
    private String driver = "com.mysql.jdbc.Driver";//responsavel por identificar o serviço de banco de dados
    private String caminho = "jdbc:mysql://localhost/SISTEMA_LOJA";// responsavel por setar a localização do banco de dados
    private String usuario = "root";//usados no banco
    private String senha = "12345";//usados no banco
    public Connection conn;//realiza a conexão com o banco de dados
    
    public void conexao(){
       try{
          System.setProperty("jdbc.Drivers", driver);//seta a propriedade do driver de conexao
          conn = DriverManager.getConnection(caminho, usuario, senha);//realiza conexão
         // JOptionPane.showMessageDialog(null, "Conectado com sucesso");
       } catch(SQLException ex){//caso houver erro retorna a mensagem
            JOptionPane.showMessageDialog(null, "ConexaoBanco\nErro de conexão" + ex);  
         }
    }
    
    
    
    public void executaaSQL(String sql) { 
   
       try {
          stm = conn.createStatement();
          rs = stm.executeQuery(sql);
           
       } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"ConexaoBanco\n\nERRO\n " + ex.getMessage());
         }
    }

    public void desconecta(){//desconecxao do banco
        try{
           conn.close();
        } catch(SQLException e){
             JOptionPane.showMessageDialog(null, "Fechado sem sucesso:" + e);  
          }
        
    }
    

    
}
