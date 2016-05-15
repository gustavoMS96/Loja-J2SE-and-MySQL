
package controle;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel{
    private ArrayList linhas = null;//linhas que vai ser passado para a classe
    private String[] colunas = null;//vetor com as colunas
    
    public ModeloTabela(ArrayList lin, String[] col){
        
        setLinhas(lin);
        setColunas(col);
    }
    
    public ArrayList getLinhas(){
        return linhas;
    }
    
    public void setLinhas(ArrayList dados){
        linhas = dados;
    }
    
    public String[] getColunas(){
        return colunas;
    }
    
    public void setColunas(String[] nomes){
       colunas = nomes; 
    }
    
    //metodo para contagem do numero de colunas
    public int getColumnCount(){
        return colunas.length;//retorna numero de colunas (string)
    }
    
    public int getRowCount(){
        return linhas.size();//tamanho do array
    }
    
    public String getColumnName(int numCol){
       return colunas[numCol];
    }
    
    public Object getValueAt(int numLin, int numCol){
        Object[] linha = (Object[])getLinhas().get(numLin);
        return linha[numCol];
        
    }
    
    
}
