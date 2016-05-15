
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleCliente;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.ModeloCliente;

/**
 *
 * @author Gustavo
 */
public class FrmCliente extends javax.swing.JFrame {
   ConexaoBanco connCliente = new ConexaoBanco();
   ConexaoBanco connEstado = new ConexaoBanco();
   ConexaoBanco connCidade = new ConexaoBanco();
   ConexaoBanco connOrgaoEmissor = new ConexaoBanco();
   ConexaoBanco connEstadoCivil = new ConexaoBanco();
   ControleCliente controle = new ControleCliente(); 
   ModeloCliente mod = new ModeloCliente(); 
   
 

    public FrmCliente() {
       try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
        connCidade.conexao();
        connCliente.conexao();
        connOrgaoEmissor.conexao();
        connEstadoCivil.conexao();
        
       
        
        initComponents();
      
        connCidade.executaaSQL("SELECT * FROM CIDADE ORDER BY NOME_CIDADE");
        connOrgaoEmissor.executaaSQL("SELECT * FROM ORGAO_EMISSOR ORDER BY NOME_ORGAO");
        connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL ORDER BY NOME_ESTADO_CIVIL");
        
     //Limite caracteres TextFields   
        jfBairro.setDocument(new LimiteCaracter(50));
        jfEmail.setDocument(new LimiteCampoLivre(100));
        jfEndereco.setDocument(new LimiteCaracter(50));
        jfNome.setDocument(new LimiteCaracter(100));
        jfNumero.setDocument(new LimiteCaracterNumerico(6));
        jfRG.setDocument(new LimiteCaracterNumerico(12));
        
     //Removendo itens das ComboBox
        jcbCidade.removeAllItems();
        jcbEstadoCivil.removeAllItems();
        jcbOrgaoEmissor.removeAllItems();
        jcbSexo.removeAllItems();
        
        
     //Definindo mascaras dos campos especiais   
        
        try{
           MaskFormatter cpf = new MaskFormatter("###.###.###-##");
           jfCPF.setFormatterFactory(new DefaultFormatterFactory(cpf));
        } catch(ParseException ex){
             JOptionPane.showMessageDialog(null,"Erro ao setar mascara para CPF\n" + ex);
          }
        
        try{
           MaskFormatter nascimento = new MaskFormatter("##/##/####");
           jfNascimento.setFormatterFactory(new DefaultFormatterFactory(nascimento));
        } catch(ParseException ex){
             JOptionPane.showMessageDialog(null,"Erro ao definir mascara de nascimento\n" + ex);
          }
        
        
        
        try{
           MaskFormatter celular = new MaskFormatter("(##)####-####");
           jfCelular.setFormatterFactory(new DefaultFormatterFactory(celular));
        } catch(ParseException ex){
             JOptionPane.showMessageDialog(null, "Erro ao definir mascara de telefone\n" + ex);
          }
        
        try{
           MaskFormatter cep = new MaskFormatter("#####-###");
           jfCEP.setFormatterFactory(new DefaultFormatterFactory(cep));
        } catch(ParseException ex){
             JOptionPane.showMessageDialog(null, "Erro ao definir mascara de telefone\n" + ex);
          }
        
      //Adicionando items as ComboBox
        
        try{
           jcbSexo.addItem("");
           jcbSexo.addItem("M");
           jcbSexo.addItem("F");
        } catch(Exception ex){
             JOptionPane.showMessageDialog(null, "Erro ao definir mascara de Sexo\n" + ex);
          }
        
        try{
           connCidade.rs.first();
           jcbCidade.addItem("");
           do{
              jcbCidade.addItem(""+connCidade.rs.getString("NOME_CIDADE"));
           } while(connCidade.rs.next());
        } catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro ao carregar dados da tabela CIDADE:\n " + ex);
          }
        
        try{
           connOrgaoEmissor.rs.first();
           jcbOrgaoEmissor.addItem("");
           do{
              jcbOrgaoEmissor.addItem(""+connOrgaoEmissor.rs.getString("NOME_ORGAO"));
           } while(connOrgaoEmissor.rs.next());
        } catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro ao carregar dados da tabela ORGAO_EMISSOR:\n" + ex);
          }
        
        try{
           connEstadoCivil.rs.first();
           jcbEstadoCivil.addItem("");
           do{
              jcbEstadoCivil.addItem(""+connEstadoCivil.rs.getString("NOME_ESTADO_CIVIL"));
           } while(connEstadoCivil.rs.next());
        } catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Erro ao carregar dados da tabela ESTADO_CIVIL:\n" + ex);
          }
        
        
        
        
    }
    
    private void habilitarCampos(){
       
       jfBairro.setEnabled(true);
       jfEmail.setEnabled(true);
       jfEndereco.setEnabled(true);
       jfNome.setEnabled(true);
       jfNumero.setEnabled(true);
       jfRG.setEnabled(true);
       jfCPF.setEnabled(true);
       jfNascimento.setEnabled(true);
       jfCelular.setEnabled(true);
       jfCEP.setEnabled(true);
       jfCPF.grabFocus();
       jcbCidade.setEnabled(true);
       jcbEstadoCivil.setEnabled(true);
       jcbOrgaoEmissor.setEnabled(true);
       jcbSexo.setEnabled(true);
       
       jbSalvar.setEnabled(true);
       jbLimpar.setEnabled(true);
       jbNovo.setEnabled(false);
    }
    
    private void limparCampos(){
       jfCodigo.setText(null);
       jfBairro.setText(null);
       jfEmail.setText(null);
       jfEndereco.setText(null);
       jfNome.setText(null);
       jfNumero.setText(null);
       jfRG.setText(null);
       jfCPF.setText(null);
       jfNascimento.setText(null);
       jfCelular.setText(null);
       jfCEP.setText(null);
       jfEstado.setText(null);
       jcbCidade.setSelectedItem("");
       jcbEstadoCivil.setSelectedItem("");
       jcbOrgaoEmissor.setSelectedItem("");
       jcbSexo.setSelectedItem("");
       
       jbSalvar.setEnabled(false);
       jbExcluir.setEnabled(false);
       jbAlterar.setEnabled(false);
       jbNovo.setEnabled(true);
       
       jfBairro.setEnabled(false);
       jfEmail.setEnabled(false);
       jfEndereco.setEnabled(false);
       jfNome.setEnabled(false);
       jfNumero.setEnabled(false);
       jfRG.setEnabled(false);
       jfCPF.setEnabled(false);
       jfNascimento.setEnabled(false);
       jfCelular.setEnabled(false);
       jfCEP.setEnabled(false);
       
       jcbCidade.setEnabled(false);
       jcbEstadoCivil.setEnabled(false);
       jcbOrgaoEmissor.setEnabled(false);
       jcbSexo.setEnabled(false);
      
    }
    
    private void habilitarCamposAlteracao(){
       jbAlterar.setEnabled(true);
       jbExcluir.setEnabled(true);
       jbLimpar.setEnabled(true);
       
       jfBairro.setEnabled(true);
       jfEmail.setEnabled(true);
       jfEndereco.setEnabled(true);
       jfNome.setEnabled(true);
       jfNumero.setEnabled(true);
       jfRG.setEnabled(true);
       jfCPF.setEnabled(true);
       jfNascimento.setEnabled(true);
       jfCelular.setEnabled(true);
       jfCEP.setEnabled(true);
       
       jcbCidade.setEnabled(true);
       jcbEstadoCivil.setEnabled(true);
       jcbOrgaoEmissor.setEnabled(true);
       jcbSexo.setEnabled(true);
       
       
    }
    
   
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel8 = new javax.swing.JLabel();
      jPanelFundo = new javax.swing.JPanel();
      jLabel1 = new javax.swing.JLabel();
      jfCPF = new javax.swing.JFormattedTextField();
      jLabel2 = new javax.swing.JLabel();
      jfNome = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      jfNascimento = new javax.swing.JFormattedTextField();
      jLabel4 = new javax.swing.JLabel();
      jfRG = new javax.swing.JTextField();
      jLabel5 = new javax.swing.JLabel();
      jcbOrgaoEmissor = new javax.swing.JComboBox();
      jcbSexo = new javax.swing.JComboBox();
      jLabel6 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jcbEstadoCivil = new javax.swing.JComboBox();
      jLabel10 = new javax.swing.JLabel();
      jfEmail = new javax.swing.JTextField();
      jLabel11 = new javax.swing.JLabel();
      jfCEP = new javax.swing.JFormattedTextField();
      jLabel12 = new javax.swing.JLabel();
      jfEndereco = new javax.swing.JTextField();
      jLabel13 = new javax.swing.JLabel();
      jfNumero = new javax.swing.JTextField();
      jLabel14 = new javax.swing.JLabel();
      jfBairro = new javax.swing.JTextField();
      jLabel15 = new javax.swing.JLabel();
      jcbCidade = new javax.swing.JComboBox();
      jLabel16 = new javax.swing.JLabel();
      jfEstado = new javax.swing.JTextField();
      jLabel17 = new javax.swing.JLabel();
      jfCelular = new javax.swing.JFormattedTextField();
      jbNovo = new javax.swing.JButton();
      jbSalvar = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();
      jbExcluir = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jbAnterior = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();
      jLabel9 = new javax.swing.JLabel();
      jfCodigo = new javax.swing.JTextField();
      jbLimpar = new javax.swing.JButton();
      jButton1 = new javax.swing.JButton();

      jLabel8.setText("jLabel8");

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Cadastro de Cliente\n");
      setBackground(new java.awt.Color(229, 229, 233));
      setName("FormCliente"); // NOI18N
      setResizable(false);

      jPanelFundo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro de Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
      jPanelFundo.setForeground(new java.awt.Color(10, 59, 118));

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("CPF");

      jfCPF.setEnabled(false);
      jfCPF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Nome");

      jfNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfNome.setEnabled(false);
      jfNome.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            jfNomeKeyPressed(evt);
         }
      });

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Nascimento");

      jfNascimento.setEnabled(false);
      jfNascimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("RG");

      jfRG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfRG.setEnabled(false);

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Orgão Emissor");

      jcbOrgaoEmissor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbOrgaoEmissor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbOrgaoEmissor.setEnabled(false);

      jcbSexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbSexo.setEnabled(false);

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel6.setText("Sexo");

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("Estado Civil");

      jcbEstadoCivil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbEstadoCivil.setEnabled(false);

      jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel10.setText("Email");

      jfEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEmail.setEnabled(false);

      jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel11.setText("CEP");

      jfCEP.setEnabled(false);
      jfCEP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel12.setText("Endereço");

      jfEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEndereco.setEnabled(false);

      jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel13.setText("Número");

      jfNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfNumero.setEnabled(false);

      jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel14.setText("Bairro");

      jfBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfBairro.setEnabled(false);

      jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel15.setText("Cidade");

      jcbCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbCidade.setEnabled(false);
      jcbCidade.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jcbCidadeFocusLost(evt);
         }
      });

      jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel16.setText("Estado");

      jfEstado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEstado.setEnabled(false);

      jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel17.setText("Celular:");

      jfCelular.setEnabled(false);
      jfCelular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbSalvar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbSalvar.setText("Salvar");
      jbSalvar.setEnabled(false);
      jbSalvar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSalvarActionPerformed(evt);
         }
      });

      jbAlterar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAlterar.setText("Alterar");
      jbAlterar.setEnabled(false);
      jbAlterar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarActionPerformed(evt);
         }
      });

      jbExcluir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbExcluir.setText("Excluir");
      jbExcluir.setEnabled(false);
      jbExcluir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbExcluirActionPerformed(evt);
         }
      });

      jbPrimeiro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbPrimeiro.setText("<<");
      jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPrimeiroActionPerformed(evt);
         }
      });

      jbAnterior.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAnterior.setText("<");
      jbAnterior.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAnteriorActionPerformed(evt);
         }
      });

      jbProximo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jbUltimo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbSair.setText("sair");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel9.setText("Código");

      jfCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigo.setEnabled(false);

      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jButton1.setText("criar tela para cartão do cliente");

      org.jdesktop.layout.GroupLayout jPanelFundoLayout = new org.jdesktop.layout.GroupLayout(jPanelFundo);
      jPanelFundo.setLayout(jPanelFundoLayout);
      jPanelFundoLayout.setHorizontalGroup(
         jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanelFundoLayout.createSequentialGroup()
            .add(22, 22, 22)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanelFundoLayout.createSequentialGroup()
                  .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel15)
                     .add(jcbCidade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(47, 47, 47)
                  .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel16)
                     .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanelFundoLayout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(jLabel17))
                     .add(jPanelFundoLayout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jfCelular, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
               .add(jPanelFundoLayout.createSequentialGroup()
                  .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jfCEP)
                     .add(jfNascimento)
                     .add(jLabel1)
                     .add(jfCPF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jLabel3)
                     .add(jLabel11))
                  .add(47, 47, 47)
                  .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jPanelFundoLayout.createSequentialGroup()
                        .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jPanelFundoLayout.createSequentialGroup()
                              .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jLabel4)
                                 .add(jfRG, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                 .add(jLabel12))
                              .add(23, 23, 23)
                              .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jLabel5)
                                 .add(jcbOrgaoEmissor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                           .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(27, 27, 27)
                        .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                           .add(jPanelFundoLayout.createSequentialGroup()
                              .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jLabel6)
                                 .add(jcbSexo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                 .add(jLabel13)
                                 .add(jfNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                 .add(jLabel10))
                              .add(20, 20, 20)
                              .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jcbEstadoCivil, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                 .add(jfBairro)
                                 .add(jPanelFundoLayout.createSequentialGroup()
                                    .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                       .add(jLabel14)
                                       .add(jLabel7))
                                    .add(0, 0, Short.MAX_VALUE))))
                           .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 389, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                     .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 700, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jLabel2)))
               .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                  .add(jPanelFundoLayout.createSequentialGroup()
                     .add(jbSalvar)
                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                     .add(jbAlterar)
                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                     .add(jbExcluir)
                     .add(248, 248, 248)
                     .add(jbPrimeiro)
                     .add(18, 18, 18)
                     .add(jbAnterior)
                     .add(18, 18, 18)
                     .add(jbProximo)
                     .add(18, 18, 18)
                     .add(jbUltimo)
                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 78, Short.MAX_VALUE)
                     .add(jbSair))
                  .add(jPanelFundoLayout.createSequentialGroup()
                     .add(209, 209, 209)
                     .add(jButton1)
                     .add(102, 102, 102)
                     .add(jbNovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(18, 18, 18)
                     .add(jbLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jLabel9)
                     .add(18, 18, 18)
                     .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(20, Short.MAX_VALUE))
      );
      jPanelFundoLayout.setVerticalGroup(
         jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFundoLayout.createSequentialGroup()
            .addContainerGap()
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
               .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                  .add(jLabel9)
                  .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(jbNovo)
                  .add(jButton1))
               .add(jbLimpar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(18, 18, 18)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel1)
               .add(jLabel2))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfCPF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel3)
               .add(jLabel4)
               .add(jLabel5)
               .add(jLabel6)
               .add(jLabel7))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfNascimento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfRG, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbOrgaoEmissor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbSexo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbEstadoCivil, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel11)
               .add(jLabel12)
               .add(jLabel13)
               .add(jLabel14))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfCEP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfBairro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel10)
               .add(jLabel15)
               .add(jLabel16)
               .add(jLabel17))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbCidade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfCelular, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 67, Short.MAX_VALUE)
            .add(jPanelFundoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jbSalvar)
               .add(jbAlterar)
               .add(jbExcluir)
               .add(jbPrimeiro)
               .add(jbAnterior)
               .add(jbProximo)
               .add(jbUltimo)
               .add(jbSair))
            .addContainerGap())
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelFundo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanelFundo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      setSize(new java.awt.Dimension(974, 481));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
      habilitarCampos();
   }//GEN-LAST:event_jbNovoActionPerformed

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      dispose();
      FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
      frmMenu.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   private void jblimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jblimparCamposActionPerformed
      limparCampos();
   }//GEN-LAST:event_jblimparCamposActionPerformed

   private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

      if(jfCPF.getText().trim().isEmpty()         || jfNome.getText().trim().isEmpty()
         || jfRG.getText().trim().isEmpty()       || jcbOrgaoEmissor.getSelectedItem().equals("")
         || jcbSexo.getSelectedItem().equals("")  || jcbEstadoCivil.getSelectedItem().equals("")
         || jfCEP.getText().trim().isEmpty()      || jfEndereco.getText().trim().isEmpty()
         || jfNumero.getText().trim().isEmpty()   || jfBairro.getText().trim().isEmpty()
         || jcbCidade.getSelectedItem().equals("")|| jfEstado.getText().trim().isEmpty()
         || jfCelular.getText().trim().isEmpty()  
         || jfNascimento.getText().trim().isEmpty()  ){

         JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
         jfCPF.grabFocus();

      } else{
         try{
            
            
            connCidade.executaaSQL("SELECT * FROM CIDADE C JOIN ESTADO E ON C.CODIGO_ESTADO = E.CODIGO_ESTADO WHERE NOME_CIDADE = '"+ jcbCidade.getSelectedItem() + "'");
            connCidade.rs.first();

            connOrgaoEmissor.executaaSQL("SELECT * FROM ORGAO_EMISSOR WHERE NOME_ORGAO = '" + jcbOrgaoEmissor.getSelectedItem() + "'");
            connOrgaoEmissor.rs.first();

            connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL WHERE NOME_ESTADO_CIVIL = '" + jcbEstadoCivil.getSelectedItem() + "'");
            connEstadoCivil.rs.first();

            mod.setCpf(jfCPF.getText());
            mod.setNome(jfNome.getText());
            mod.setRg(Long.parseLong(jfRG.getText()));
            mod.setCodigoOrgaoEmissor(connOrgaoEmissor.rs.getInt("CODIGO_ORGAO_EMISSOR"));
            mod.setSexo((String) jcbSexo.getSelectedItem());
            mod.setNascimento(jfNascimento.getText());
            mod.setCodigoEstadoCivil(connEstadoCivil.rs.getInt("CODIGO_ESTADO_CIVIL"));
            mod.setCep(jfCEP.getText());
            mod.setEndereco(jfEndereco.getText());
            mod.setNumeroCasa(Integer.parseInt(jfNumero.getText()));
            mod.setBairro(jfBairro.getText());
            mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
            mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
            mod.setTelefone(jfCelular.getText());
            mod.setEmail(jfEmail.getText());

            controle.InserirCliente(mod);
            limparCampos();
         } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao setar dados e salvar registro!\n" + e);
         }

      }

   }//GEN-LAST:event_jbSalvarActionPerformed

   
   
   
   
   
   
   
   
   
   private void jcbCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbCidadeFocusLost

      try {
         connEstado.conexao();
         connEstado.executaaSQL("SELECT * FROM CIDADE C JOIN ESTADO E ON C.CODIGO_ESTADO"
            + " = E.CODIGO_ESTADO WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem()+"'");
         connEstado.rs.first();
         jfEstado.setText(connEstado.rs.getString("NOME_ESTADO"));

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao carregar estado a partir de combobox\n" + ex);
      }

   }//GEN-LAST:event_jcbCidadeFocusLost

   
   
   private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
      limparCampos();
      mod = controle.Primeiro();
      
     
      buscaAtributosPesquisa();
      habilitarCamposAlteracao();
      
   }//GEN-LAST:event_jbPrimeiroActionPerformed

   private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed

      if(jfCPF.getText().trim().isEmpty()         || jfNascimento.getText().trim().isEmpty()     || jfNome.getText().trim().isEmpty()
         || jfRG.getText().trim().isEmpty()       || jcbOrgaoEmissor.getSelectedItem().equals("")
         || jcbSexo.getSelectedItem().equals("")  || jcbEstadoCivil.getSelectedItem().equals("")
         || jfCEP.getText().trim().isEmpty()      || jfEndereco.getText().trim().isEmpty()
         || jfNumero.getText().trim().isEmpty()   || jfBairro.getText().trim().isEmpty()
         || jcbCidade.getSelectedItem().equals("")|| jfEstado.getText().trim().isEmpty()
         || jfCelular.getText().trim().isEmpty()  ){

         JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
         jfCPF.grabFocus();

      } else{
           try{
              connCliente.conexao();
              connCidade.conexao();
              connOrgaoEmissor.conexao();
              connEstadoCivil.conexao();
           
              connCidade.executaaSQL("SELECT * FROM CIDADE WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem() + "'");
              connCidade.rs.first();
           
              connOrgaoEmissor.executaaSQL("SELECT * FROM ORGAO_EMISSOR WHERE NOME_ORGAO = '" + jcbOrgaoEmissor.getSelectedItem() + "'");
              connOrgaoEmissor.rs.first();
           
              connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL WHERE NOME_ESTADO_CIVIL = '" + jcbEstadoCivil.getSelectedItem() + "'");
              connEstadoCivil.rs.first(); 
           
              mod.setCodigoCliente(Integer.parseInt(jfCodigo.getText()));
              mod.setNome(jfNome.getText());
              mod.setRg(Long.parseLong(jfRG.getText()));
              mod.setCpf(jfCPF.getText());
              mod.setCodigoOrgaoEmissor(connOrgaoEmissor.rs.getInt("CODIGO_ORGAO_EMISSOR"));
              mod.setNascimento(jfNascimento.getText());
              mod.setSexo(""+jcbSexo.getSelectedItem());
              mod.setCodigoEstadoCivil(connEstadoCivil.rs.getInt("CODIGO_ESTADO_CIVIL"));
              mod.setTelefone(jfCelular.getText());
              mod.setCep(jfCEP.getText());
              mod.setEndereco(jfEndereco.getText());
              mod.setNumeroCasa(Integer.parseInt(jfNumero.getText()));
              mod.setBairro(jfBairro.getText());
              mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
              mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
              mod.setEmail(jfEmail.getText());          
              
              controle.AlterarCliente(mod);
              limparCampos();
              
           } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao setar e alterar registro:\n" + e);
             }
           
        }
   }//GEN-LAST:event_jbAlterarActionPerformed

   private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
      try{
         mod.setCodigoCliente(Integer.parseInt(jfCodigo.getText()));
         
         controle.ExcluirCliente(mod);
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "Erro ao definir codigo para exclusão:\n" + e);
        }
   }//GEN-LAST:event_jbExcluirActionPerformed

   private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed
limparCampos();
      mod = controle.Anterior();
      
     
     buscaAtributosPesquisa();
      habilitarCamposAlteracao();
   }//GEN-LAST:event_jbAnteriorActionPerformed

   private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
     limparCampos(); 
      mod = controle.Proximo();
      
      
      buscaAtributosPesquisa();
      
      habilitarCamposAlteracao();      
   }//GEN-LAST:event_jbProximoActionPerformed

   private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
      limparCampos();
      mod = controle.Ultimo();

      buscaAtributosPesquisa();
      habilitarCamposAlteracao();      
   }//GEN-LAST:event_jbUltimoActionPerformed

   private void jfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jfNomeKeyPressed
      //jfNome.setText(jfNome.getText().toUpperCase());// TODO add your handling code here:
   }//GEN-LAST:event_jfNomeKeyPressed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      limparCampos();
   }//GEN-LAST:event_jbLimparActionPerformed

   public void buscaAtributosPesquisa(){
   
      jfCodigo.setText(""+mod.getCodigoCliente());
      jfCPF.setText(mod.getCpf());
      jfNome.setText(mod.getNome());
      jfNascimento.setText(mod.getNascimento());
      jfRG.setText(""+mod.getRg());
      jcbOrgaoEmissor.setSelectedItem(mod.getNomeOrgaoEmissor());
      jcbSexo.setSelectedItem(mod.getSexo());
      jcbEstadoCivil.setSelectedItem(mod.getNomeEstadoCivil());
      jfCEP.setText(mod.getCep());
      jfEndereco.setText(mod.getEndereco());
      jfNumero.setText(""+mod.getNumeroCasa());
      jfBairro.setText(mod.getBairro());
      jcbCidade.setSelectedItem(mod.getCidade());
      jfEstado.setText(mod.getEstado());
      jfCelular.setText(mod.getTelefone());
      jfEmail.setText(mod.getEmail());
   }
   
   
   
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
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCliente().setVisible(true);
            }
        });
    }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel14;
   private javax.swing.JLabel jLabel15;
   private javax.swing.JLabel jLabel16;
   private javax.swing.JLabel jLabel17;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanelFundo;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbAnterior;
   private javax.swing.JButton jbExcluir;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbSalvar;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JComboBox jcbCidade;
   private javax.swing.JComboBox jcbEstadoCivil;
   private javax.swing.JComboBox jcbOrgaoEmissor;
   private javax.swing.JComboBox jcbSexo;
   private javax.swing.JTextField jfBairro;
   private javax.swing.JFormattedTextField jfCEP;
   private javax.swing.JFormattedTextField jfCPF;
   private javax.swing.JFormattedTextField jfCelular;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTextField jfEmail;
   private javax.swing.JTextField jfEndereco;
   private javax.swing.JTextField jfEstado;
   private javax.swing.JFormattedTextField jfNascimento;
   private javax.swing.JTextField jfNome;
   private javax.swing.JTextField jfNumero;
   private javax.swing.JTextField jfRG;
   // End of variables declaration//GEN-END:variables
}
