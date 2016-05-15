
package modelo;


public class ModeloProduto {
   private int codigoProduto;
   private String produto;
   //private float preco;
   private int estoqueMinimo;
   private int estoqueAtual;
   private int estoqueMaximo;
   private int garantia;
   private ModeloGrupo grupo; 
   private ModeloSubGrupo subGrupo;
   
   
   
   public int getCodigoProduto() {
      return codigoProduto;
   }

   public void setCodigoProduto(int codigoProduto) {
      this.codigoProduto = codigoProduto;
   }

   public String getProduto() {
      return produto;
   }

   public void setProduto(String produto) {
      this.produto = produto;
   }

 /*  public float getPreco() {
      return preco;
   }

   public void setPreco(float preco) {
      this.preco = preco;
   }*/

   public int getEstoqueMinimo() {
      return estoqueMinimo;
   }

   public void setEstoqueMinimo(int estoqueMinimo) {
      this.estoqueMinimo = estoqueMinimo;
   }

   public int getEstoqueAtual() {
      return estoqueAtual;
   }

   public void setEstoqueAtual(int estoqueAtual) {
      this.estoqueAtual = estoqueAtual;
   }

   public int getEstoqueMaximo() {
      return estoqueMaximo;
   }

   public void setEstoqueMaximo(int estoqueMaximo) {
      this.estoqueMaximo = estoqueMaximo;
   }

   public ModeloGrupo getGrupo() {
      return grupo;
   }

   public void setGrupo(ModeloGrupo grupo) {
      this.grupo = grupo;
   }

   public ModeloSubGrupo getSubGrupo() {
      return subGrupo;
   }

   public void setSubGrupo(ModeloSubGrupo subGrupo) {
      this.subGrupo = subGrupo;
   }

   public int getGarantia() {
      return garantia;
   }

   public void setGarantia(int garantia) {
      this.garantia = garantia;
   }
   
  
   
}
