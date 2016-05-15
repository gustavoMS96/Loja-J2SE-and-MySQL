
package modelo;

public class ModeloRecebimento {
    private int codigoRecebimento;
    private String dataRecebimento;
    private int codigoCompra;
    private String notaFiscal;
    private String serie;
    private String observacao;
    private String dataEmissao;
    
   
    private ModeloCompra compra;
    private ModeloFuncionario funcionario;

    
    
   public int getCodigoRecebimento() {
      return codigoRecebimento;
   }

   public void setCodigoRecebimento(int codigoRecebimento) {
      this.codigoRecebimento = codigoRecebimento;
   }

   public String getDataRecebimento() {
      return dataRecebimento;
   }

   public void setDataRecebimento(String dataRecebimento) {
      this.dataRecebimento = dataRecebimento;
   }

   public int getCodigoCompra() {
      return codigoCompra;
   }

   public void setCodigoCompra(int codigoCompra) {
      this.codigoCompra = codigoCompra;
   }

   public String getNotaFiscal() {
      return notaFiscal;
   }

   public void setNotaFiscal(String notaFiscal) {
      this.notaFiscal = notaFiscal;
   }

   public String getSerie() {
      return serie;
   }

   public void setSerie(String serie) {
      this.serie = serie;
   }

  

   public String getObservacao() {
      return observacao;
   }

   public void setObservacao(String observacao) {
      this.observacao = observacao;
   }

  

   public ModeloCompra getCompra() {
      return compra;
   }

   public void setCompra(ModeloCompra compra) {
      this.compra = compra;
   }

   public ModeloFuncionario getFuncionario() {
      return funcionario;
   }

   public void setFuncionario(ModeloFuncionario funcionario) {
      this.funcionario = funcionario;
   }

   public String getDataEmissao() {
      return dataEmissao;
   }

   public void setDataEmissao(String dataEmissao) {
      this.dataEmissao = dataEmissao;
   }
    
    
    
   
   //funções para tratar datas, tanto para formato mysql quanto brasileiro
   
   private String dataBanco(String data){//transformo no padrao aceito pelo banco
       
      String dataBanco = data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
       
       return dataBanco;
  }
   
   
  private String dataPadraoBR(String data){//transformo no padrao aceito pelos brasileiros
       String dataNova = data.substring(8) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
       return dataNova;
  }
   
   
}
