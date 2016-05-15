
package modelo;


public class ModeloSubGrupo {
    
    private int codigo;
    private String nomeSubGrupo;
    private ModeloGrupo grupo;

    
   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      this.codigo = codigo;
   }

  
   

   public ModeloGrupo getGrupo() {
      return grupo;
   }

   public void setGrupo(ModeloGrupo grupo) {
      this.grupo = grupo;
   }

   public String getNomeSubGrupo() {
      return nomeSubGrupo;
   }

   public void setNomeSubGrupo(String nomeSubGrupo) {
      this.nomeSubGrupo = nomeSubGrupo;
   }
    
   
    
    
}
