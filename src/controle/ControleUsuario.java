/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.ModeloFuncionario;

/**
 *
 * @author Gustavo M S
 */
public class ControleUsuario {
    
    ConexaoBanco connUser = new ConexaoBanco();
    ModeloFuncionario modFunc = new ModeloFuncionario();
    
    
    
    public void salvaUsuario(ModeloFuncionario modFunc, int codigo){
       try{
           
          connUser.conexao();
          PreparedStatement pst = connUser.conn.prepareStatement("UPDATE FUNCIONARIO SET LOGIN = ?, SENHA = ? WHERE CODIGO_FUNCIONARIO = '" + codigo + "'");
          pst.setString(1, modFunc.getLogin());
          pst.setString(2, modFunc.getSenha());
          
          pst.execute();
          
          JOptionPane.showMessageDialog(null, "Usuario e senha salvos");
          
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ControleUsuario\nErro ao salvar usuario do sistema\n" + e);
         } finally{
              connUser.desconecta();
           }
    
    }
    
    
    
    
    
    
    
}
