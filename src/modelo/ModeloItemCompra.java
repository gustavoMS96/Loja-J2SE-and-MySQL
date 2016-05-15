
package modelo;


public class ModeloItemCompra {
   private int codigoItem;
   private int quantidade;
   private double valorItem;
   private double valorTotalItem;
   private String pagamento;
   private String frete;
   private String status;
   
   private ModeloCompra compra;
   private ModeloProduto produto;

   public int getCodigoItem() {
      return codigoItem;
   }

   public void setCodigoItem(int codigoItem) {
      this.codigoItem = codigoItem;
   }

   public int getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(int quantidade) {
      this.quantidade = quantidade;
   }

   public double getValorItem() {
      return valorItem;
   }

   public void setValorItem(double valorItem) {
      this.valorItem = valorItem;
   }

   public double getValorTotalItem() {
      return valorTotalItem;
   }

   public void setValorTotalItem(double valorTotalItem) {
      this.valorTotalItem = valorTotalItem;
   }

   

   public String getPagamento() {
      return pagamento;
   }

   public void setPagamento(String pagamento) {
      this.pagamento = pagamento;
   }

   public String getFrete() {
      return frete;
   }

   public void setFrete(String frete) {
      this.frete = frete;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public ModeloCompra getCompra() {
      return compra;
   }

   public void setCompra(ModeloCompra compra) {
      this.compra = compra;
   }

   public ModeloProduto getProduto() {
      return produto;
   }

   public void setProduto(ModeloProduto produto) {
      this.produto = produto;
   }
   
   
  

  
   
}
