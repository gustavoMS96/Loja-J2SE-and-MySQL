package controle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.ModeloCompra;
import modelo.ModeloFornecedor;
import modelo.ModeloFuncionario;
import modelo.ModeloItemCompra;
import modelo.ModeloProduto;

public class ControleCompra {

   //instancias do Model
   ModeloItemCompra modItem = new ModeloItemCompra();
   ModeloCompra modCompra = new ModeloCompra();
   ModeloFuncionario modFunc = new ModeloFuncionario();
   ModeloFornecedor modForn = new ModeloFornecedor();
   ModeloProduto modProduto = new ModeloProduto();

   //instancias de conexao com banco
   ConexaoBanco connCompra = new ConexaoBanco();
   ConexaoBanco connVencimento = new ConexaoBanco();

   public static boolean control;//variavel para controlar a existencia de um registro pesquisado
   public static boolean existe = false;//variavel para existencia de um resgistro nos botoes de primeiro, proximo, anterior e ultimo

   public String dataBR(String d) {//data para usuario
      return d.substring(8, 10) + "/" + d.substring(5, 7) + "/" + d.substring(0, 4);
   }

   private String transformaData(String data) { // data para MySQL

      return data.substring(6, 10) + "-" + data.substring(3, 5) + "-" + data.substring(0, 2);
   }

   //defino a associação para cada modelo
   public void defineAssociacao() {
      modCompra.setItemCompra(modItem);
      modCompra.setFornecedor(modForn);
      modCompra.setFuncionario(modFunc);
      modItem.setProduto(modProduto);
   }

   public double decimalPadraoBanco(double valorErrado) {//altero o decimal para poder salvar no banco
      String valorObtido = String.valueOf(valorErrado);
      String correcao = valorObtido.replace(',', '.');
      double valorCorreto = Double.parseDouble(correcao);
      return valorCorreto;
   }

   /* public double decimalPadraoUsuario(double valorDoBanco){//apresento o valor com virgula para casa decimal
         String v = String.valueOf(valorDoBanco);
         String valorBR = v.replace('.',',');
         double valorCorrigido = Double.parseDouble(valorBR);
         return valorCorrigido;
   }*/
 /*
   Primeiro momento:
      - A compra é salva no banco, com a ausencia de itens, para poder ser continuada caso usuario precise se ausentar ou fazer outra tarefa
    */
   public void salvarCompra(ModeloCompra modCompra) {//salvo para inserir itens
      defineAssociacao();
      try {
         connCompra.conexao();

         PreparedStatement pst = connCompra.conn.prepareStatement("INSERT INTO COMPRA (CODIGO_FORNECEDOR, TIPO_COMPRA, DATA_EMISSAO) "
                 + "VALUES(?,?,?)");

         System.out.println(transformaData(modCompra.getDataEmissao()));
         pst.setInt(1, modCompra.getFornecedor().getCodigo());
         pst.setString(2, modCompra.getTipoCompra());
         pst.setString(3, transformaData(modCompra.getDataEmissao()));
         pst.execute();

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro em salvarCompra()\n\n" + ex);

      } finally {
         connCompra.desconecta();
      }
   }//salvarCompra

   /*
   
      - Após informar Data de Emissao(faturamento da nf), e os prazos com o padrão xxx-xxx-xxx-xxx com zeros a esquerda caso necessario
    */
   public void salvarVencimento(ModeloCompra modCompra) {

      String dataEmissao = transformaData(modCompra.getDataEmissao());
      //JOptionPane.showMessageDialog(null, dataEmissao);
      String prazo = modCompra.getPrazo();

      defineAssociacao();

      try {

         connCompra.conexao();

         PreparedStatement pst1 = connCompra.conn.prepareStatement("INSERT INTO VENCIMENTO_COMPRA(PRAZO, DATA_EMISSAO, CODIGO_COMPRA) "
                 + "VALUES(?,?,?)");
         pst1.setString(1, modCompra.getPrazo());
         pst1.setString(2, transformaData(modCompra.getDataEmissao()));
         pst1.setInt(3, modCompra.getCodigoCompra());

         pst1.execute();

         connCompra.executaaSQL("SELECT CODIGO_VENCIMENTO FROM VENCIMENTO_COMPRA WHERE CODIGO_COMPRA = '" + modCompra.getCodigoCompra() + "' AND PRAZO = '" + modCompra.getPrazo() + "';");
         connCompra.rs.first();
         int codigo = connCompra.rs.getInt("CODIGO_VENCIMENTO");

         PreparedStatement pst = connCompra.conn.prepareStatement("UPDATE VENCIMENTO_COMPRA SET DATA_VENCIMENTO = (SELECT DATE_ADD('" + dataEmissao + "', INTERVAL '" + prazo + "' DAY)) "
                 + "WHERE CODIGO_VENCIMENTO = '" + codigo + "';");

         pst.execute();
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro em salvarCompra()\n\n" + ex);

      } finally {
         connCompra.desconecta();
      }

   }

   /**
    * *********
    * Neste momento após ter os itens inseridos para a compra, será alterado seu
    * status para 'AGUARDANDO', entrando no processo de aprovação por um
    * superior *********
    */
   public void confirmaCompra(ModeloCompra modCompra) {
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      defineAssociacao();
      try {
         connCompra.conexao();
         PreparedStatement pst = connCompra.conn.prepareStatement("UPDATE COMPRA SET "
                 + "SITUACAO = 'AGUARDANDO', "
                 + "VALOR_COMPRA = ? "
                 + "WHERE CODIGO_COMPRA = ?");
         pst.setDouble(1, modCompra.getValorTotal());
         pst.setInt(2, modCompra.getCodigoCompra());

         connCompra.conexao();
         PreparedStatement pst1 = connCompra.conn.prepareStatement("UPDATE ITEM_COMPRA SET "
                 + "STATUS = 'AGUARDANDO' "
                 + "WHERE CODIGO_COMPRA = ? AND STATUS != 'CANCELADO'");
         pst1.setInt(1, modCompra.getCodigoCompra());
         // pst1.execute();

         switch (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja confirmar a compra?")) {
            case 0:
               pst.execute();
               pst1.execute();
               break;
            case 1:
               JOptionPane.showMessageDialog(null, "Operação cancelada");
               break;
            case 2:
               break;
         }

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro ao confirmar compra\n" + ex);

      } finally {
         connCompra.desconecta();
      }
   }//confirmaCompra

   /**
    * *************
    * Neste momento a compra é cancelada, sendo mudado seu status para
    * "CANCELADO" *************
    */
   public void excluiCompra(int codigo) {//altera status da compra para cancelado e inativa os itens de compra
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      //defineAssociacao();
      try {
         connCompra.conexao();

         PreparedStatement pst = connCompra.conn.prepareStatement("UPDATE COMPRA SET "
                 + "SITUACAO = 'CANCELADO' "
                 + "WHERE CODIGO_COMPRA = '" + codigo + "';");

         PreparedStatement pst2 = connCompra.conn.prepareStatement("UPDATE ITEM_COMPRA SET "
                 + "STATUS = 'CANCELADO' "
                 + "WHERE CODIGO_COMPRA = '" + codigo + "';");

         PreparedStatement pst1 = connCompra.conn.prepareStatement("DELETE FROM VENCIMENTO_COMPRA "
                 + "WHERE CODIGO_COMPRA = '" + codigo + "';");

         switch (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a compra?")) {
            case 0:
               pst.execute();
               pst1.execute();
               pst2.execute();
               JOptionPane.showMessageDialog(null, "Compra excluida");
               // pst1.execute();
               break;
            case 1:
               JOptionPane.showMessageDialog(null, "Operação cancelada");
               break;
            case 2:
               break;
         }

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro ao cancelar compra\n" + ex);

      } finally {
         connCompra.desconecta();
      }
   }//excluiCompra

   /*
   Neste momento salvo os itens da compra respectiva, 
    */
   public void salvarItem(ModeloCompra modCompra) {
      defineAssociacao();

      try {

         connCompra.conexao();
         PreparedStatement pst = connCompra.conn.prepareStatement("INSERT INTO ITEM_COMPRA(CODIGO_COMPRA, CODIGO_PRODUTO, QUANTIDADE, VALOR_ITEM, VALOR_TOTAL_ITEM, FRETE, STATUS, CODIGO_ITEM) "
                 + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

         pst.setInt(1, modCompra.getCodigoCompra());//modCompra.getCompra().getCodigoCompra());
         pst.setInt(2, modCompra.getItemCompra().getProduto().getCodigoProduto());
         pst.setInt(3, modCompra.getItemCompra().getQuantidade());
         pst.setDouble(4, decimalPadraoBanco(modCompra.getItemCompra().getValorItem()));//caso usuario digitar ',' irá trocar pelo '.' que é aceito no banco
         pst.setDouble(5, modCompra.getItemCompra().getValorTotalItem());
         pst.setString(6, modCompra.getItemCompra().getFrete());
         pst.setString(7, modCompra.getItemCompra().getStatus());
         pst.setInt(8, modCompra.getItemCompra().getCodigoItem());
         pst.execute();

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro ao salvar item:\n" + ex);
      } finally {
         connCompra.desconecta();
      }

   }//salvarItem

   /*
    altero os dados do item da compra
    */
   public void alteraItem(ModeloCompra modCompra) {
      defineAssociacao();
      try {
         connCompra.conexao();

         PreparedStatement pst = connCompra.conn.prepareStatement("UPDATE ITEM_COMPRA SET CODIGO_PRODUTO = ?, "
                 + "QUANTIDADE = ?, "
                 + "VALOR_ITEM = ?, "
                 + "VALOR_TOTAL_ITEM = ?, "
                 + "FRETE = ? "
                 + "WHERE CODIGO_ITEM = ? ");

         pst.setInt(1, modCompra.getItemCompra().getProduto().getCodigoProduto());
         pst.setInt(2, modCompra.getItemCompra().getQuantidade());
         pst.setDouble(3, decimalPadraoBanco(modCompra.getItemCompra().getValorItem()));
         pst.setDouble(4, modCompra.getItemCompra().getValorTotalItem());
         pst.setString(5, modCompra.getItemCompra().getFrete());
         pst.setInt(6, modCompra.getItemCompra().getCodigoItem());

         pst.execute();

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro ao alterar item da compra\n\n" + e);
      } finally {
         connCompra.desconecta();
      }

   }

   /*
   Mudo status do item para cancelado, novamente, nada é de fato excluido, apenas muda o status
    */
   public void excluirItem(int codigo) {
      UIManager.put("OptionPane.yesButtonText", "Sim");
      UIManager.put("OptionPane.noButtonText", "Não");
      defineAssociacao();
      try {

         connCompra.conexao();
         PreparedStatement pst1 = connCompra.conn.prepareStatement("UPDATE ITEM_COMPRA SET "
                 + "STATUS = 'CANCELADO' "
                 + "WHERE CODIGO_ITEM = '" + codigo + "';");

         pst1.execute();

         switch (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o item?")) {
            case 0:
               pst1.execute();
               break;
            case 1:
               JOptionPane.showMessageDialog(null, "Operação cancelada");
               break;
            case 2:
               break;
         }

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n\nErro ao cancelar compra\n" + ex);

      } finally {
         connCompra.desconecta();
      }
   }

   /*
      Busca do primeiro registro do banco
    */
   public ModeloCompra primeiro() {
      defineAssociacao();
      connCompra.conexao();
      try {

         connCompra.executaaSQL("SELECT * FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR ORDER BY CODIGO_COMPRA ASC;");
         if (connCompra.rs.first()) {
            existe = true;
            defineAtributos();
         }

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "ControleCompra\nErro ao carregar primeiro registro\n" + e);
      }

      return modCompra;
   }

   /*
      Busca do ultimo registro do banco
    */
   public ModeloCompra ultimo() {
      defineAssociacao();
      connCompra.conexao();
      try {

         connCompra.executaaSQL("SELECT * FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR ORDER BY CODIGO_COMPRA ASC;");
         if (connCompra.rs.last()) {
            existe = true;
            defineAtributos();
         }

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "ControleCompra\nErro ao carregar ultimo registro\n" + e);
      }

      return modCompra;
   }

   /*
      Busca do proximo registro do banco com base no indice já selecionado na tela
    */
   public ModeloCompra proximo() {
      defineAssociacao();
      connCompra.conexao();
      try {
         if (connCompra.rs.next()) {
            existe = true;
            defineAtributos();
         }

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "ControleCompra\nErro ao carregar proximo registro\n" + e);
      }

      return modCompra;
   }

   /*
      Busca do registro anterior do banco com base no indice já selecionado na tela
    */
   public ModeloCompra anterior() {

      defineAssociacao();
      connCompra.conexao();
      try {
         if (connCompra.rs.previous()) {
            existe = true;
            defineAtributos();
         }

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "ControleCompra\nErro ao carregar primeiro registro\n" + e);
      }

      return modCompra;
   }

   /*
    Busco e formato a exibição dos prazos para a compra    
    */
   //acrescentar 0 a esquerda caso prazo tenha 2 ou 1 caracteres
   public ModeloCompra buscaPrazos(int codigo) {

      try {
         connVencimento.conexao();
         connVencimento.executaaSQL("SELECT PRAZO FROM VENCIMENTO_COMPRA "
                 + "WHERE CODIGO_COMPRA = '" + codigo + "' ORDER BY PRAZO ASC;");
         connVencimento.rs.first();

         String prazo = "";

         do {
            prazo = prazo + String.valueOf(connVencimento.rs.getInt("PRAZO")) + "-";
         } while (connVencimento.rs.next());

         prazo = prazo.substring(0, prazo.length() - 1);

         modCompra.setPrazo(prazo);
         //System.out.println(prazo);
      } catch (SQLException e) {
         System.out.println("ControleCompra\nbuscaPrazo()\n" + e);
      }

      return modCompra;
   }

   //defino os atributos que foram obtidos do select do banco
   private void defineAtributos() {
      try {
         modCompra.setCodigoCompra(connCompra.rs.getInt("CODIGO_COMPRA"));
         modCompra.setDataCompra(dataBR(connCompra.rs.getString("DATA")));
         modCompra.setDataEmissao(dataBR(connCompra.rs.getString("DATA_EMISSAO")));
         modCompra.getFornecedor().setRazaoSocial(connCompra.rs.getString("RAZAO_SOCIAL"));
         modCompra.setSituacao(connCompra.rs.getString("SITUACAO"));
         double valor = connCompra.rs.getDouble("VALOR_COMPRA");
         if (valor == 0.0) {
            modCompra.setValorTotal(0.0);
         } else {
            modCompra.setValorTotal(connCompra.rs.getDouble("VALOR_COMPRA"));//aqui mudo a forma como o valor é apresentado. De 'ponto' separando casa decimal passa a ser 'virgula'
         }

         modCompra.setTipoCompra(connCompra.rs.getString("TIPO_COMPRA"));
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\ndefineAtributos()\nErro ao definir os atributos\n" + ex);

      }
   }

   //procuro uma determinada compra
   public ModeloCompra procuraCompra(int codigo) {
      defineAssociacao();
      try {
         connCompra.conexao();
         connVencimento.conexao();

         connCompra.executaaSQL("SELECT * FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR WHERE C.CODIGO_COMPRA = '" + codigo + "'");

         if (!connCompra.rs.first() == true) {
            connCompra.desconecta();
            control = true;

         } else {
            defineAtributos();

         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "ControleCompra\n procuraCompra()\nErro ao carregar compra especifica\n" + ex);
      }

      connCompra.desconecta();
      return modCompra;

   }

}
