/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleFornecedor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.ModeloFornecedor;


public class FrmFornecedor extends javax.swing.JFrame {
    
    /*
     
     * Aqui entancio as classes utilizadas pelo formulario
     
     */
    
    ConexaoBanco connFornecedor = new ConexaoBanco();
    ConexaoBanco connCidade = new ConexaoBanco();
    ConexaoBanco connEstado = new ConexaoBanco();
    ModeloFornecedor mod = new ModeloFornecedor();
    ControleFornecedor controle = new ControleFornecedor();
    Cores cor = new Cores();
    
    
    public FrmFornecedor() {
       try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
        //aqui abro as conexoes para pesquisas de outras tabelas paralelas
        connFornecedor.conexao();
        connCidade.conexao();
        connEstado.conexao();
        //aqui seleciono os registros da tabela Cidade para aparecer na ComboBox
        connCidade.executaaSQL("SELECT * FROM CIDADE ORDER BY NOME_CIDADE");
        initComponents();
        //PreencherTabela("SELECT * FROM FORNECEDORES F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
        
        //aqui delimito os caracteres para cada compo normal
        jfCodigo.setDocument(new LimiteCaracterNumerico(6));
        jfRazaoSocial.setDocument(new LimiteCampoLivre(150));
        jfNomeFantasia.setDocument(new LimiteCampoLivre(150));
        jfEmail.setDocument(new LimiteCampoLivre(100));
        jfEndereco.setDocument(new LimiteCaracter(50));
        jfNumero.setDocument(new LimiteCaracterNumerico(6));
        jfBairro.setDocument(new LimiteCaracter(50));
        jcbCidade.removeAllItems();//removo os itens da ComboBox
        //aqui formato as mascaras para campos especiais
        try{
           MaskFormatter cnpj = new MaskFormatter("##.###.###/####-##");
           jfCnpj.setFormatterFactory(new DefaultFormatterFactory(cnpj));
        } catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro ao definir mascara CNPJ:\n" + e);
          }
        
        try{
           MaskFormatter telefone = new MaskFormatter("(##)####-####");
           jfTelefone.setFormatterFactory(new DefaultFormatterFactory(telefone));
        } catch(Exception erro){
             JOptionPane.showMessageDialog(null,"Erro ao setar mascara Telefone:\n " + erro);
          }
        
        try{
           MaskFormatter ie = new MaskFormatter("###-#######");
           jfIe.setFormatterFactory(new DefaultFormatterFactory(ie));
        } catch(Exception erro){
             JOptionPane.showMessageDialog(null,"Erro ao setar mascara IE:\n " + erro);
          }
        
        //aqui seto os itens da ComboBox
        
        try{
           connCidade.rs.first();
           jcbCidade.addItem("");
           do{
              jcbCidade.addItem(""+connCidade.rs.getString("NOME_CIDADE"));
           }while(connCidade.rs.next());
        } catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro ao definir itens do ComboBox:\n" + ex);
          }
        
    }
   
    
    public void HabilitarCampos(){
       jfCodigo.setEnabled(true);
       jCheckStatus.setEnabled(true);
       jfRazaoSocial.setEnabled(true);
       jfRazaoSocial.grabFocus();
       jfNomeFantasia.setEnabled(true);
       jfCnpj.setEnabled(true);
       jfIe.setEnabled(true);
       jfEmail.setEnabled(true);
       jfTelefone.setEnabled(true);
       jfEndereco.setEnabled(true);
       jfNumero.setEnabled(true);
       jfBairro.setEnabled(true);
       jcbCidade.setEnabled(true);
       jbInserir.setEnabled(true);
       jbLimpar.setEnabled(true);
       jbNovo.setEnabled(false);
       
    }
    
    public void Limpar(){
       jfCodigo.setText(null); 
       jfRazaoSocial.setText(null);
       jfEmail.setText(null);
       jfRazaoSocial.grabFocus();
       jfCnpj.setText(null);
       jfIe.setText(null);
       jfTelefone.setText(null);
       jfEndereco.setText(null);
       jfNumero.setText(null);
       jfBairro.setText(null);
       
       jCheckStatus.setSelected(false);
       jfNomeFantasia.setText(null);
       
       jfCodigo.setEnabled(false);
       jfRazaoSocial.setEnabled(false);
       jfNomeFantasia.setEnabled(false);
       jfCnpj.setEnabled(false);
       jfIe.setEnabled(false);
       jCheckStatus.setEnabled(false);
       jfTelefone.setEnabled(false);
       jfEndereco.setEnabled(false);
       jfEmail.setEnabled(false);
       jfNumero.setEnabled(false);
       jfBairro.setEnabled(false);
       jcbCidade.setEnabled(false);
       jbInserir.setEnabled(false);
       jbLimpar.setEnabled(false);
       jbLimpar.setEnabled(false);
       jbInserir.setEnabled(false);
       jbAlterar.setEnabled(false);
       jbExcluir.setEnabled(false);
       jbNovo.setEnabled(true);
    }
   
    
    public void HabilitaAlteracao(){
       jfCodigo.setEnabled(true);
       jfRazaoSocial.setEnabled(true);
       jfRazaoSocial.grabFocus();
       jfNomeFantasia.setEnabled(true);
       jfCnpj.setEnabled(true);
       jfIe.setEnabled(true);
       jCheckStatus.setEnabled(true);
       jfTelefone.setEnabled(true);
       jfEndereco.setEnabled(true);
       jfNumero.setEnabled(true);
       jfBairro.setEnabled(true);
       jfEmail.setEnabled(true);
       jcbCidade.setEnabled(true);
       jbInserir.setEnabled(false);
       jbExcluir.setEnabled(true);
       jbAlterar.setEnabled(true);
       jbLimpar.setEnabled(true);
       jbNovo.setEnabled(false);
       
    }
    
    
   /* public void PreencherTabela(String sql){
       
       ArrayList dados = new ArrayList(); 
       
       String [] Colunas = new String[]{"Código", "Razão Social", "CNPJ", "I.E.", "Telefone", "Endereço", "Número", "Bairro", "Cidade", "Estado"};
       
       connFornecedor.conexao();
       connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
       
       try{
          connFornecedor.rs.first();
          
          do{
             dados.add(new Object[]{connFornecedor.rs.getInt("CODIGO_FORNECEDOR"), 
                 connFornecedor.rs.getString("RAZAO_SOCIAL"), connFornecedor.rs.getString("CNPJ"), 
                 connFornecedor.rs.getString("IE"), connFornecedor.rs.getString("TELEFONE"), 
                 connFornecedor.rs.getString("ENDERECO"), connFornecedor.rs.getInt("N_ENDERECO"),
                 connFornecedor.rs.getString("BAIRRO"),connFornecedor.rs.getString("NOME_CIDADE"),
                 connFornecedor.rs.getString("NOME_ESTADO") });
          }while(connFornecedor.rs.next()); 
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao preencher Tabela:\n" + e);
         }
          
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtFornecedor.setModel(modelo);
       jtFornecedor.getColumnModel().getColumn(0).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(0).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(1).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(1).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(2).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(2).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(3).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(3).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(4).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(4).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(5).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(5).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(6).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(6).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(7).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(7).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(8).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(8).setResizable(true);
       
       jtFornecedor.getColumnModel().getColumn(9).setPreferredWidth(50);
       jtFornecedor.getColumnModel().getColumn(9).setResizable(true);
       
       
       jtFornecedor.getTableHeader().setReorderingAllowed(true);
       jtFornecedor.setAutoResizeMode(jtFornecedor.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtFornecedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
       //connFornecedor.desconecta();
    }
       
    
    */
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanel1 = new javax.swing.JPanel();
      jfBairro = new javax.swing.JTextField();
      jfEstado = new javax.swing.JTextField();
      jfEndereco = new javax.swing.JTextField();
      jfNumero = new javax.swing.JTextField();
      jfTelefone = new javax.swing.JFormattedTextField();
      jfRazaoSocial = new javax.swing.JTextField();
      jfCnpj = new javax.swing.JFormattedTextField();
      jLabel10 = new javax.swing.JLabel();
      jbNovo = new javax.swing.JButton();
      jcbCidade = new javax.swing.JComboBox();
      jbLimpar = new javax.swing.JButton();
      jbInserir = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();
      jbExcluir = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jbAnterior = new javax.swing.JButton();
      jLabel2 = new javax.swing.JLabel();
      jLabel1 = new javax.swing.JLabel();
      jLabel9 = new javax.swing.JLabel();
      jbProximo = new javax.swing.JButton();
      jLabel6 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      jbSair = new javax.swing.JButton();
      jLabel4 = new javax.swing.JLabel();
      jbUltimo = new javax.swing.JButton();
      jLabel3 = new javax.swing.JLabel();
      jLabel8 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jLabel11 = new javax.swing.JLabel();
      jfNomeFantasia = new javax.swing.JTextField();
      jfIe = new javax.swing.JFormattedTextField();
      jCheckStatus = new javax.swing.JCheckBox();
      jLabel12 = new javax.swing.JLabel();
      jLabel13 = new javax.swing.JLabel();
      jfEmail = new javax.swing.JTextField();
      jfCodigo = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Cadastro de Fornecedor");
      setName("FormFornecedor"); // NOI18N
      setResizable(false);

      jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro de Fornecedor"));

      jfBairro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfBairro.setEnabled(false);
      jfBairro.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfBairroFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfBairroFocusLost(evt);
         }
      });

      jfEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEstado.setEnabled(false);
      jfEstado.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfEstadoFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfEstadoFocusLost(evt);
         }
      });

      jfEndereco.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEndereco.setEnabled(false);
      jfEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfEnderecoFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfEnderecoFocusLost(evt);
         }
      });

      jfNumero.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfNumero.setEnabled(false);
      jfNumero.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfNumeroFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfNumeroFocusLost(evt);
         }
      });

      jfTelefone.setEnabled(false);
      jfTelefone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfTelefone.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfTelefoneFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfTelefoneFocusLost(evt);
         }
      });

      jfRazaoSocial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfRazaoSocial.setEnabled(false);
      jfRazaoSocial.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfRazaoSocialFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfRazaoSocialFocusLost(evt);
         }
      });
      jfRazaoSocial.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jfRazaoSocialActionPerformed(evt);
         }
      });

      jfCnpj.setEnabled(false);
      jfCnpj.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfCnpj.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfCnpjFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfCnpjFocusLost(evt);
         }
      });

      jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel10.setText("Estado");

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jcbCidade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbCidade.setEnabled(false);
      jcbCidade.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jcbCidadeFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jcbCidadeFocusLost(evt);
         }
      });
      jcbCidade.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jcbCidadeMouseClicked(evt);
         }
         public void mouseEntered(java.awt.event.MouseEvent evt) {
            jcbCidadeMouseEntered(evt);
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
            jcbCidadeMouseExited(evt);
         }
         public void mousePressed(java.awt.event.MouseEvent evt) {
            jcbCidadeMousePressed(evt);
         }
         public void mouseReleased(java.awt.event.MouseEvent evt) {
            jcbCidadeMouseReleased(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.setEnabled(false);
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jbInserir.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbInserir.setText("Salvar");
      jbInserir.setEnabled(false);
      jbInserir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbInserirActionPerformed(evt);
         }
      });

      jbAlterar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbAlterar.setText("Alterar");
      jbAlterar.setEnabled(false);
      jbAlterar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarActionPerformed(evt);
         }
      });

      jbExcluir.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbExcluir.setText("Excluir");
      jbExcluir.setEnabled(false);
      jbExcluir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbExcluirActionPerformed(evt);
         }
      });

      jbPrimeiro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbPrimeiro.setText("<<");
      jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPrimeiroActionPerformed(evt);
         }
      });

      jbAnterior.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbAnterior.setText("<");
      jbAnterior.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAnteriorActionPerformed(evt);
         }
      });

      jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel2.setText("Razão Social");

      jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel1.setText("Código");

      jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel9.setText("Cidade");

      jbProximo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel6.setText("Endereço");

      jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel5.setText("Telefone");

      jbSair.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbSair.setText("Sair");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel4.setText("IE");

      jbUltimo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel3.setText("CNPJ");

      jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel8.setText("Bairro");

      jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel7.setText("Número");

      jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel11.setText("Nome Fantasia");

      jfNomeFantasia.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfNomeFantasia.setEnabled(false);

      jfIe.setEnabled(false);
      jfIe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      jCheckStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jCheckStatus.setText("Ativo");
      jCheckStatus.setEnabled(false);

      jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel12.setText("Status");

      jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel13.setText("Email");

      jfEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEmail.setEnabled(false);

      jfCodigo.setEditable(false);
      jfCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigo.setEnabled(false);

      org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .add(46, 46, 46)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jbNovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(33, 33, 33)
                  .add(jbLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jLabel13)
               .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jfBairro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jfCnpj, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(jLabel8)))
                     .add(jLabel1)
                     .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(18, 18, 18)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jLabel11)
                     .add(jcbCidade, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jLabel9)
                     .add(jLabel4)
                     .add(jfIe)
                     .add(jfNomeFantasia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .add(18, 18, 18)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(0, 0, Short.MAX_VALUE)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                     .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .add(jbPrimeiro)
                        .add(18, 18, 18)
                        .add(jbAnterior)
                        .add(18, 18, 18)
                        .add(jbProximo)
                        .add(18, 18, 18)
                        .add(jbUltimo)
                        .add(18, 18, 18)
                        .add(jbSair, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                     .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .add(jbInserir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jbAlterar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jbExcluir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel6)
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jLabel10)
                     .add(jfEndereco)
                     .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 261, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(25, 25, 25)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jfNumero, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                     .add(jLabel7)
                     .add(jLabel5)
                     .add(jfTelefone))
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(185, 185, 185)
                        .add(jLabel12)
                        .add(18, 18, 18)
                        .add(jCheckStatus)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jfRazaoSocial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 455, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2)))
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
            .add(30, 30, 30)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel1)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(29, 29, 29)
                  .add(jLabel3)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jfCnpj, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(26, 26, 26)
                  .add(jLabel8)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jfBairro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jLabel11)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jfNomeFantasia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(28, 28, 28)
                  .add(jLabel4)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jfIe, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(26, 26, 26)
                  .add(jLabel9)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jcbCidade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel2)
                     .add(jLabel12)
                     .add(jCheckStatus))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jfRazaoSocial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(28, 28, 28)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel6)
                     .add(jLabel7))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jfNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(26, 26, 26)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel10)
                     .add(jLabel5))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jfTelefone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 40, Short.MAX_VALUE)
            .add(jLabel13)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jbInserir)
               .add(jbAlterar)
               .add(jbExcluir)
               .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(22, 22, 22)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jbAnterior)
               .add(jbProximo)
               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                  .add(jbNovo)
                  .add(jbLimpar))
               .add(jbUltimo)
               .add(jbSair)
               .add(jbPrimeiro))
            .add(25, 25, 25))
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      setSize(new java.awt.Dimension(1051, 474));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
       dispose();
       FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
       frmMenu.setVisible(true);
    }//GEN-LAST:event_jbSairActionPerformed

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
        HabilitarCampos();
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
       Limpar();
    }//GEN-LAST:event_jbLimparActionPerformed

    private void jbInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbInserirActionPerformed
       
        if(jfRazaoSocial.getText().trim().isEmpty() || jfCnpj.getText().trim().isEmpty() 
             || jfIe.getText().trim().isEmpty() || jfTelefone.getText().trim().isEmpty() || jfEndereco.getText().trim().isEmpty()
             || jfEndereco.getText().trim().isEmpty() || jfNumero.getText().trim().isEmpty() || jfBairro.getText().trim().isEmpty() 
             || jcbCidade.getSelectedItem().equals("") || jfEmail.getText().trim().isEmpty())
        {
           JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
           jfRazaoSocial.grabFocus();
           
        } else{
             
             try{
                connFornecedor.conexao();
                connCidade.conexao();
                connCidade.executaaSQL("SELECT * FROM CIDADE WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem() + "'");
                connCidade.rs.first();
                mod.setRazaoSocial((jfRazaoSocial.getText()));
                mod.setNome(jfNomeFantasia.getText());
                mod.setCnpj(jfCnpj.getText());
                mod.setIe(jfIe.getText());
                mod.setTelefone(jfTelefone.getText());
                mod.setEndereco(jfEndereco.getText());
                mod.setNumeroEndereco(Integer.parseInt(jfNumero.getText()));
                mod.setBairro(jfBairro.getText());
                
                
                if(jCheckStatus.isSelected()){
                   mod.setStatus("S");
                } else{
                     mod.setStatus("N");
                  }
                
                mod.setEmail(jfEmail.getText());
                mod.setEstado(jfEstado.getText());
                mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
                mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
                controle.Salvar(mod);
             } catch(SQLException | NumberFormatException e){
                  JOptionPane.showMessageDialog(null, "Erro ao inserir registro:\n" + e);
               }
             connCidade.desconecta();
             connFornecedor.desconecta();
    
          }        
        
      //  PreencherTabela("SELECT * FROM FORNECEDORES F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
        Limpar();
    }//GEN-LAST:event_jbInserirActionPerformed

 
    private void jcbCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbCidadeMouseClicked
        
        
    }//GEN-LAST:event_jcbCidadeMouseClicked

    private void jcbCidadeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbCidadeMouseReleased
       
    }//GEN-LAST:event_jcbCidadeMouseReleased

    private void jcbCidadeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbCidadeMousePressed
       
    }//GEN-LAST:event_jcbCidadeMousePressed

    private void jcbCidadeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbCidadeMouseEntered
       
    }//GEN-LAST:event_jcbCidadeMouseEntered

    private void jcbCidadeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbCidadeMouseExited
       
    }//GEN-LAST:event_jcbCidadeMouseExited

    private void jcbCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbCidadeFocusLost
        
            
       try{
          connEstado.conexao();
          connEstado.executaaSQL("SELECT * FROM CIDADE C JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO WHERE NOME_CIDADE ='"+ jcbCidade.getSelectedItem() +"'");
          connEstado.rs.first();
          jfEstado.setText(connEstado.rs.getString("NOME_ESTADO"));
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao carregar estado:\n" + e);
         }
       jcbCidade.setBackground(cor.FieldLostFocus);
       
        
        
    }//GEN-LAST:event_jcbCidadeFocusLost

    private void jfRazaoSocialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfRazaoSocialFocusLost
       jfRazaoSocial.setBackground(cor.FieldLostFocus);       
    }//GEN-LAST:event_jfRazaoSocialFocusLost

    private void jfCnpjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCnpjFocusLost
       jfCnpj.setBackground(cor.FieldLostFocus); 
    }//GEN-LAST:event_jfCnpjFocusLost

    private void jfTelefoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfTelefoneFocusLost
       jfTelefone.setBackground(cor.FieldLostFocus);       
    }//GEN-LAST:event_jfTelefoneFocusLost

    private void jfEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEnderecoFocusLost
       jfEndereco.setBackground(cor.FieldLostFocus);
        
    }//GEN-LAST:event_jfEnderecoFocusLost

    private void jfNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfNumeroFocusLost
       jfNumero.setBackground(cor.FieldLostFocus);
       
    }//GEN-LAST:event_jfNumeroFocusLost

    private void jfBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfBairroFocusLost
       jfBairro.setBackground(cor.FieldLostFocus);    
        
    }//GEN-LAST:event_jfBairroFocusLost

    private void jfRazaoSocialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfRazaoSocialFocusGained
       jfRazaoSocial.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfRazaoSocialFocusGained

    private void jfCnpjFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCnpjFocusGained
        jfCnpj.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfCnpjFocusGained

    private void jfTelefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfTelefoneFocusGained
       jfTelefone.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfTelefoneFocusGained

    private void jfEnderecoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEnderecoFocusGained
       jfEndereco.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfEnderecoFocusGained

    private void jfNumeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfNumeroFocusGained
       jfNumero.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfNumeroFocusGained

    private void jfBairroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfBairroFocusGained
       jfBairro.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfBairroFocusGained

    private void jcbCidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbCidadeFocusGained
       jcbCidade.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jcbCidadeFocusGained

    private void jfEstadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEstadoFocusGained
       //jfEstado.setBackground(cor.FieldGainFocus);
    }//GEN-LAST:event_jfEstadoFocusGained

    private void jfEstadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEstadoFocusLost
      //jfEstado.setBackground(cor.FieldLostFocus);
    }//GEN-LAST:event_jfEstadoFocusLost

    
    private void setaAtributos(){
      jfCodigo.setText(""+mod.getCodigo());
      // JOptionPane.showMessageDialog(null, mod.getCodigo());
       jfRazaoSocial.setText(mod.getRazaoSocial());
       jfNomeFantasia.setText(mod.getNome());
       jfCnpj.setText(mod.getCnpj());
       jfIe.setText(mod.getIe());
       jfTelefone.setText(mod.getTelefone());
       jfEndereco.setText(mod.getEndereco());
       jfNumero.setText(""+mod.getNumeroEndereco());
       jfBairro.setText(mod.getBairro());
       jcbCidade.setSelectedItem(mod.getCidade());
       jfEstado.setText(mod.getEstado());
       jfEmail.setText(mod.getEmail());
       
       if(mod.getStatus().equals("S") ){
       
          jCheckStatus.setSelected(true);
       
       } else{
            jCheckStatus.setSelected(false);
         }
    
    }
    
    
    
    
    private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
       
       mod = controle.Primeiro();
       
      setaAtributos();
       
       
       HabilitaAlteracao();
    }//GEN-LAST:event_jbPrimeiroActionPerformed

    private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
       mod = controle.Ultimo();
       
       setaAtributos();
       
       HabilitaAlteracao();
    }//GEN-LAST:event_jbUltimoActionPerformed

    private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed
       mod = controle.Anterior();
       
       setaAtributos();
       
       HabilitaAlteracao();
    }//GEN-LAST:event_jbAnteriorActionPerformed

    private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
       mod = controle.Proximo();
       
       setaAtributos();
       HabilitaAlteracao();
    }//GEN-LAST:event_jbProximoActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
       
       connFornecedor.conexao(); 
        
       mod.setCodigo(Integer.parseInt(jfCodigo.getText()));
     /*  mod.setRazao_social(jfRazaoSocial.getText());        
       mod.setCnpj(jfCnpj.getText());
       jfNomeFantasia.setText(mod.getNome());
       mod.setIe(jfIe.getText());
       mod.setTelefone(jfTelefone.getText());
       mod.setEndereco(jfEndereco.getText());
       mod.setN_endereco(Integer.parseInt(jfNumero.getText()));
       mod.setBairro(jfBairro.getText());
       mod.setCidade((String)jcbCidade.getSelectedItem());
       mod.setEstado(jfEstado.getText());
       */
       controle.Excluir(mod);
       Limpar();
       // PreencherTabela("SELECT * FROM FORNECEDORES F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
       
       connFornecedor.desconecta();
       
    }//GEN-LAST:event_jbExcluirActionPerformed


    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
              
       if(jfRazaoSocial.getText().trim().isEmpty() || jfCnpj.getText().trim().isEmpty() 
             || jfIe.getText().trim().isEmpty() || jfTelefone.getText().trim().isEmpty() || jfEndereco.getText().trim().isEmpty()
             || jfEndereco.getText().trim().isEmpty() || jfNumero.getText().trim().isEmpty() || jfBairro.getText().trim().isEmpty()
             || jcbCidade.getSelectedItem().equals("")  )
       {
         JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
         jfRazaoSocial.grabFocus();
           
       } else{         
            
            try{
               connFornecedor.conexao();
               connCidade.conexao();
               connCidade.executaaSQL("SELECT * FROM CIDADE WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem()+ "'");
               connCidade.rs.first();
               
               mod.setCodigo(Integer.parseInt(jfCodigo.getText()));
               mod.setRazaoSocial((jfRazaoSocial.getText()));
               mod.setNome((jfNomeFantasia.getText()));
               mod.setCnpj(jfCnpj.getText());
               mod.setIe(jfIe.getText());
               mod.setTelefone(jfTelefone.getText());
               mod.setEndereco((jfEndereco.getText()));
               mod.setNumeroEndereco(Integer.parseInt(jfNumero.getText()));
               mod.setBairro((jfBairro.getText()));
               mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
               mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
               mod.setEmail((jfEmail.getText()));
               if(jCheckStatus.isSelected()){
                   mod.setStatus("S");
                } else{
                     mod.setStatus("N");
                  }
               
               controle.Alterar(mod);
               
               Limpar();
               // PreencherTabela("SELECT * FROM FORNECEDORES F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO");
               
            } catch(SQLException ex){
                 JOptionPane.showMessageDialog(null, "Erro ao alterar registro:\n" + ex);  
                 
              } 
            
            connFornecedor.desconecta();
            connCidade.desconecta();
          
          
          
          
          
         }

    }//GEN-LAST:event_jbAlterarActionPerformed

   private void jfRazaoSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfRazaoSocialActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jfRazaoSocialActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmFornecedor().setVisible(true);
            }
        });
    }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JCheckBox jCheckStatus;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbAnterior;
   private javax.swing.JButton jbExcluir;
   private javax.swing.JButton jbInserir;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JComboBox jcbCidade;
   private javax.swing.JTextField jfBairro;
   private javax.swing.JFormattedTextField jfCnpj;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTextField jfEmail;
   private javax.swing.JTextField jfEndereco;
   private javax.swing.JTextField jfEstado;
   private javax.swing.JFormattedTextField jfIe;
   private javax.swing.JTextField jfNomeFantasia;
   private javax.swing.JTextField jfNumero;
   private javax.swing.JTextField jfRazaoSocial;
   private javax.swing.JFormattedTextField jfTelefone;
   // End of variables declaration//GEN-END:variables
}
