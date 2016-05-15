
package modelo;


public class ModeloCompra {
   private int codigoCompra;
   private double valorTotal;
   private String dataCompra;
   private String situacao;
   private String tipoCompra;
   private String observacao;
   private String dataEmissao;
   private String dataVencimento;
   private String prazo;
   //private String prazoString;
   private ModeloFuncionario funcionario;
   private ModeloItemCompra itemCompra;
   private ModeloFornecedor fornecedor;
   private ModeloRecebimento recebimento;
   
   
   
   
    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

   public ModeloFuncionario getFuncionario() {
      return funcionario;
   }

   public void setFuncionario(ModeloFuncionario funcionario) {
      this.funcionario = funcionario;
   }

   public ModeloItemCompra getItemCompra() {
      return itemCompra;
   }

   public void setItemCompra(ModeloItemCompra itemCompra) {
      this.itemCompra = itemCompra;
   }

   public ModeloFornecedor getFornecedor() {
      return fornecedor;
   }

   public void setFornecedor(ModeloFornecedor fornecedor) {
      this.fornecedor = fornecedor;
   }

   public int getCodigoCompra() {
      return codigoCompra;
   }

   public void setCodigoCompra(int codigoCompra) {
      this.codigoCompra = codigoCompra;
   }

   public double getValorTotal() {
      return valorTotal;
   }

   public void setValorTotal(double valorTotal) {
      this.valorTotal = valorTotal;
   }

   public String getDataCompra() {
      return dataCompra;
   }

   public void setDataCompra(String dataCompra) {
      this.dataCompra = dataCompra;
   }

   public String getSituacao() {
      return situacao;
   }

   public void setSituacao(String situacao) {
      this.situacao = situacao;
   }

   public String getTipoCompra() {
      return tipoCompra;
   }

   public void setTipoCompra(String tipoCompra) {
      this.tipoCompra = tipoCompra;
   }

   public String getDataEmissao() {
      return dataEmissao;
   }

   public void setDataEmissao(String dataEmissao) {
      this.dataEmissao = dataEmissao;
   }

   public String getDataVencimento() {
      return dataVencimento;
   }

   public void setDataVencimento(String dataVencimento) {
      this.dataVencimento = dataVencimento;
   }

   public String getPrazo() {
      return prazo;
   }

   public void setPrazo(String prazo) {
      this.prazo = prazo;
   }

   public ModeloRecebimento getRecebimento() {
      return recebimento;
   }

   public void setRecebimento(ModeloRecebimento recebimento) {
      this.recebimento = recebimento;
   }

/*   public String getPrazoString() {
      return prazoString;
   }

   public void setPrazoString(String prazoString) {
      this.prazoString = prazoString;
   }
*/
  
   
   
}
