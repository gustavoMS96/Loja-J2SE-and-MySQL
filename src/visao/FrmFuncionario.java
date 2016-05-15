

package visao;


import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleFuncionario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.ModeloFuncionario;


public class FrmFuncionario extends javax.swing.JFrame {
   ConexaoBanco connCargo = new ConexaoBanco();
   ConexaoBanco connOrgao = new ConexaoBanco();
   ConexaoBanco connEstadoCivil = new ConexaoBanco();
   ConexaoBanco connCidade = new ConexaoBanco();
   ConexaoBanco connFunc = new ConexaoBanco();
   ModeloFuncionario mod = new ModeloFuncionario();   
   ControleFuncionario controle = new ControleFuncionario();
   
   public FrmFuncionario() {
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      initComponents();
      jcbOrgao.removeAllItems();
      jcbCargo.removeAllItems();
      jcbCidade.removeAllItems();
      jcbEstadoCivil.removeAllItems();
      jcbSexo.removeAllItems();
      
      connCargo.conexao();
      connOrgao.conexao();
      connEstadoCivil.conexao();
      connCidade.conexao();
      connFunc.conexao();
      
      connCargo.executaaSQL("SELECT * FROM CARGO ORDER BY CARGO");
      connOrgao.executaaSQL("SELECT * FROM ORGAO_EMISSOR ORDER BY NOME_ORGAO");
      connCidade.executaaSQL("SELECT * FROM CIDADE ORDER BY NOME_CIDADE");
      connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL ORDER BY NOME_ESTADO_CIVIL");
      
      jfNome.setDocument(new LimiteCaracter(100));
      jfRg.setDocument(new LimiteCaracterNumerico(10));
      jfEndereco.setDocument(new LimiteCaracter(50));
      jfNumero.setDocument(new LimiteCaracterNumerico(6));
      jfBairro.setDocument(new LimiteCaracter(50));
      jfEmail.setDocument(new LimiteCampoLivre(100));
      
      
        try{
         MaskFormatter nasci = new MaskFormatter("##/##/####");
         jfNascimento.setFormatterFactory(new DefaultFormatterFactory(nasci));
      } catch(Exception erro){
           JOptionPane.showMessageDialog(null, "Erro ao definir mascara de CPF:\n" + erro);
        }
      
      try{
         MaskFormatter cpf = new MaskFormatter("###.###.###-##");
         jfCpf.setFormatterFactory(new DefaultFormatterFactory(cpf));
      } catch(Exception erro){
           JOptionPane.showMessageDialog(null, "Erro ao definir mascara de CPF:\n" + erro);
        }
      
      try{
         MaskFormatter cep = new MaskFormatter("#####-###");
         jfCep.setFormatterFactory(new DefaultFormatterFactory(cep));
      } catch(Exception erro){
           JOptionPane.showMessageDialog(null, "Erro ao definir mascara de CEP:\n" + erro);
        }
      
      try{
         MaskFormatter pis = new MaskFormatter("###########");
         jfPis.setFormatterFactory(new DefaultFormatterFactory(pis));
      } catch(Exception ex){
           JOptionPane.showMessageDialog(null, "Erro ao definir mascara de Pis:\n" + ex);
        }
      
      try{
         MaskFormatter tel = new MaskFormatter("(##)####-####");
         jfTelefone.setFormatterFactory(new DefaultFormatterFactory(tel));
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "Erro ao definir mascara de Telefone:\n" + e);
        }
      
      
      try{
         jcbSexo.addItem("");
         jcbSexo.addItem("M");
         jcbSexo.addItem("F");
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "Erro ao definir ComboBox Sexo:\n" + e);
        }
      
      try{
         connOrgao.rs.first();
         jcbOrgao.addItem("");
         
         do{
            jcbOrgao.addItem(""+connOrgao.rs.getString("NOME_ORGAO"));
         }while(connOrgao.rs.next());
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar comboBox Orgao Emissor:\n" + e);
        }
      
      try{
         connCidade.rs.first();
         jcbCidade.addItem("");
         
         do{
            jcbCidade.addItem("" + connCidade.rs.getString("NOME_CIDADE"));
         } while(connCidade.rs.next());
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar ComboBox Cidade:\n" + e);
        }
      
      
      try{
         connCargo.rs.first();
         jcbCargo.addItem("");
         
         do{
            jcbCargo.addItem("" + connCargo.rs.getString("CARGO"));
         } while(connCargo.rs.next());
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar ComboBox Cargo:\n" + e);
        }
      
      
      try{
         connEstadoCivil.rs.first();
         jcbEstadoCivil.addItem("");
         
         do{
            jcbEstadoCivil.addItem("" + connEstadoCivil.rs.getString("NOME_ESTADO_CIVIL")); 
         } while(connEstadoCivil.rs.next());
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar ComboBox Estado Civil:\n" + e);
        }
      
      
   }//FrmFuncionario

   
   
   private void HabilitarCampos(){
      jbProcurar.setEnabled(false);
      jfCodigo.setEnabled(true);
      jfCodigo.setEditable(false);
      jfNascimento.setEnabled(true);
      jfNome.setEnabled(true);
      jfRg.setEnabled(true);
      jcbOrgao.setEnabled(true);
      jfCpf.setEnabled(true);
      jcbEstadoCivil.setEnabled(true);
      jcbSexo.setEnabled(true);
      jcbCargo.setEnabled(true);
      jfStatus.setEnabled(true);
      jfSalario.setEnabled(true);
      jfPis.setEnabled(true);
      jfEndereco.setEnabled(true);
      jfNumero.setEnabled(true);
      jfBairro.setEnabled(true);
      jfCep.setEnabled(true);
      jcbCidade.setEnabled(true);
      jfTelefone.setEnabled(true);
      jfEmail.setEnabled(true);
      
      jbSalvar.setEnabled(true);
      jbNovo.setEnabled(false);
      jbLimpar.setEnabled(true);
      
      jfNome.grabFocus();
   }
   
   
   private void LimparCampos(){
      jbNovo.grabFocus();
      jfCodigo.grabFocus();
      jfCodigo.setEnabled(true);
      jbProcurar.setEnabled(true);
      jfCodigo.setEditable(true);
      jfNome.setEnabled(false);
      jfRg.setEnabled(false);
      jcbOrgao.setEnabled(false);
      jfCpf.setEnabled(false);
      jcbEstadoCivil.setEnabled(false);
      jcbSexo.setEnabled(false);
      jcbCargo.setEnabled(false);
      jfStatus.setEnabled(false);
      jfSalario.setEnabled(false);
      jfPis.setEnabled(false);
      jfEndereco.setEnabled(false);
      jfNumero.setEnabled(false);
      jfBairro.setEnabled(false);
      jfCep.setEnabled(false);
      jcbCidade.setEnabled(false);
      jfTelefone.setEnabled(false);
      jfEmail.setEnabled(false);
      jfNascimento.setEnabled(false);
      
      jfNascimento.setText(null);
      jfCodigo.setText(null);
      jfNome.setText(null);
      jfRg.setText(null);
      jcbOrgao.setSelectedItem("");
      jfCpf.setText(null);
      jcbEstadoCivil.setSelectedItem("");
      jcbSexo.setSelectedItem("");
      jcbCargo.setSelectedItem("");
      jfStatus.setSelected(false);
      jfSalario.setText(null);
      jfPis.setText(null);
      jfEndereco.setText(null);
      jfNumero.setText(null);
      jfBairro.setText(null);
      jfCep.setText(null);
      jcbCidade.setSelectedItem("");
      jfEstado.setText(null);
      jfTelefone.setText(null);
      jfEmail.setText(null);
      
      
      
      
      
      jbSalvar.setEnabled(false);
      jbNovo.setEnabled(true);
      jbLimpar.setEnabled(false);
      jbAlterar.setEnabled(false);
      jbExcluir.setEnabled(false);
   }
   
   
   
   private void HabilitarAlteracao(){

      jfNascimento.setEnabled(true);
      jfNome.setEnabled(true);
      jfRg.setEnabled(true);
      jcbOrgao.setEnabled(true);
      jfCpf.setEnabled(true);
      jcbEstadoCivil.setEnabled(true);
      jcbSexo.setEnabled(true);
      jcbCargo.setEnabled(true);
      jfStatus.setEnabled(true);
      jfSalario.setEnabled(true);
      jfPis.setEnabled(true);
      jfEndereco.setEnabled(true);
      jfNumero.setEnabled(true);
      jfBairro.setEnabled(true);
      jfCep.setEnabled(true);
      jcbCidade.setEnabled(true);
      jfTelefone.setEnabled(true);
      jfEmail.setEnabled(true);
      jbSalvar.setEnabled(false);
      jbAlterar.setEnabled(true);
      jbLimpar.setEnabled(true);
      jbExcluir.setEnabled(true);      
      jfNome.grabFocus();
   
   
   }
   
   
   
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanel1 = new javax.swing.JPanel();
      jPanel2 = new javax.swing.JPanel();
      jLabel4 = new javax.swing.JLabel();
      jfCpf = new javax.swing.JFormattedTextField();
      jcbOrgao = new javax.swing.JComboBox();
      jLabel3 = new javax.swing.JLabel();
      jfRg = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jfNome = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jcbSexo = new javax.swing.JComboBox();
      jLabel11 = new javax.swing.JLabel();
      jcbEstadoCivil = new javax.swing.JComboBox();
      jLabel20 = new javax.swing.JLabel();
      jfNascimento = new javax.swing.JFormattedTextField();
      jPanel3 = new javax.swing.JPanel();
      jLabel8 = new javax.swing.JLabel();
      jcbCargo = new javax.swing.JComboBox();
      jLabel9 = new javax.swing.JLabel();
      jfSalario = new javax.swing.JFormattedTextField();
      jLabel10 = new javax.swing.JLabel();
      jfStatus = new javax.swing.JCheckBox();
      jLabel6 = new javax.swing.JLabel();
      jfPis = new javax.swing.JFormattedTextField();
      jPanel4 = new javax.swing.JPanel();
      jLabel12 = new javax.swing.JLabel();
      jLabel13 = new javax.swing.JLabel();
      jLabel14 = new javax.swing.JLabel();
      jLabel15 = new javax.swing.JLabel();
      jLabel16 = new javax.swing.JLabel();
      jfEndereco = new javax.swing.JTextField();
      jfNumero = new javax.swing.JTextField();
      jfBairro = new javax.swing.JTextField();
      jcbCidade = new javax.swing.JComboBox();
      jfEstado = new javax.swing.JTextField();
      jLabel17 = new javax.swing.JLabel();
      jfCep = new javax.swing.JFormattedTextField();
      jPanel5 = new javax.swing.JPanel();
      jLabel18 = new javax.swing.JLabel();
      jfTelefone = new javax.swing.JFormattedTextField();
      jLabel19 = new javax.swing.JLabel();
      jfEmail = new javax.swing.JTextField();
      jbNovo = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jbAnterior = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbSalvar = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();
      jbExcluir = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();
      jLabel5 = new javax.swing.JLabel();
      jfCodigo = new javax.swing.JTextField();
      jbProcurar = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Formulário de Funcionário");
      setBackground(new java.awt.Color(229, 229, 233));
      setResizable(false);

      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados pessoais"));

      jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel4.setText("Orgão Emissor");

      jfCpf.setEnabled(false);
      jfCpf.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      jcbOrgao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbOrgao.setEnabled(false);

      jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel3.setText("CPF");

      jfRg.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfRg.setEnabled(false);

      jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel2.setText("RG");

      jfNome.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfNome.setEnabled(false);

      jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel1.setText("Nome");

      jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel7.setText("Sexo");

      jcbSexo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbSexo.setEnabled(false);

      jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel11.setText("Estado Civil");

      jcbEstadoCivil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbEstadoCivil.setEnabled(false);

      jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel20.setText("Data Nascimento");

      jfNascimento.setEnabled(false);
      jfNascimento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel2Layout.createSequentialGroup()
                  .add(jLabel1)
                  .add(200, 200, 200)
                  .add(jLabel2)
                  .add(151, 151, 151)
                  .add(jLabel4))
               .add(jPanel2Layout.createSequentialGroup()
                  .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 216, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jfRg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 158, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jcbOrgao, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .add(22, 22, 22)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel3)
               .add(jfCpf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(23, 23, 23)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel11)
               .add(jcbEstadoCivil, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(30, 30, 30)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel7)
               .add(jcbSexo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(29, 29, 29)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
               .add(jLabel20, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .add(jfNascimento))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .add(20, 20, 20)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel1)
               .add(jLabel2)
               .add(jLabel3)
               .add(jLabel4)
               .add(jLabel7)
               .add(jLabel11)
               .add(jLabel20))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfRg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfCpf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbOrgao, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbEstadoCivil, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbSexo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfNascimento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(26, Short.MAX_VALUE))
      );

      jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Função"));

      jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel8.setText("Cargo");

      jcbCargo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbCargo.setEnabled(false);

      jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel9.setText("Salário");

      jfSalario.setEnabled(false);
      jfSalario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel10.setText("Ativo");

      jfStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfStatus.setEnabled(false);

      jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel6.setText("PIS");

      jfPis.setEnabled(false);
      jfPis.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfPis.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jfPisActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel8)
                     .add(jLabel9))
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel3Layout.createSequentialGroup()
                        .add(179, 179, 179)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jfStatus)
                           .add(jLabel10)))
                     .add(jPanel3Layout.createSequentialGroup()
                        .add(120, 120, 120)
                        .add(jLabel6))
                     .add(jPanel3Layout.createSequentialGroup()
                        .add(120, 120, 120)
                        .add(jfPis, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 137, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
               .add(jcbCargo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfSalario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(26, 26, 26))
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel8)
               .add(jLabel10))
            .add(11, 11, 11)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jcbCargo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfStatus))
            .add(26, 26, 26)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel9)
               .add(jLabel6))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfSalario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfPis, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço"));

      jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel12.setText("Endereço");

      jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel13.setText("Número");

      jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel14.setText("Bairro");

      jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel15.setText("Cidade");

      jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel16.setText("Estado");

      jfEndereco.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEndereco.setEnabled(false);

      jfNumero.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfNumero.setEnabled(false);

      jfBairro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfBairro.setEnabled(false);

      jcbCidade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jcbCidade.setEnabled(false);
      jcbCidade.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jcbCidadeFocusLost(evt);
         }
      });

      jfEstado.setEditable(false);
      jfEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEstado.setEnabled(false);

      jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel17.setText("CEP");

      jfCep.setEnabled(false);
      jfCep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .add(31, 31, 31)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(jPanel4Layout.createSequentialGroup()
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel12)
                     .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 364, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(18, 18, 18)
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel13)
                        .add(0, 0, Short.MAX_VALUE))
                     .add(jfNumero)))
               .add(jPanel4Layout.createSequentialGroup()
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel17)
                     .add(jfCep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(38, 38, 38)
                  .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel15)
                        .add(0, 0, Short.MAX_VALUE))
                     .add(jcbCidade, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .add(22, 22, 22)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 221, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel14)
               .add(jLabel16)
               .add(jfBairro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 221, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(82, 82, 82))
      );
      jPanel4Layout.setVerticalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel12)
               .add(jLabel13)
               .add(jLabel14))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfBairro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel15)
               .add(jLabel16)
               .add(jLabel17))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfEstado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jcbCidade, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfCep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(26, 26, 26))
      );

      jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Contato"));

      jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel18.setText("Telefone");

      jfTelefone.setEnabled(false);
      jfTelefone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel19.setText("Email");

      jfEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEmail.setEnabled(false);

      org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
      jPanel5.setLayout(jPanel5Layout);
      jPanel5Layout.setHorizontalGroup(
         jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel18)
               .add(jfTelefone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(60, 60, 60)
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jLabel19)
               .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 289, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(41, Short.MAX_VALUE))
      );
      jPanel5Layout.setVerticalGroup(
         jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel5Layout.createSequentialGroup()
            .add(19, 19, 19)
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel18)
               .add(jLabel19))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfTelefone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(36, Short.MAX_VALUE))
      );

      jbNovo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.setEnabled(false);
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
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

      jbProximo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jbUltimo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jbSalvar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbSalvar.setText("Salvar");
      jbSalvar.setEnabled(false);
      jbSalvar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSalvarActionPerformed(evt);
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

      jbSair.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
      jbSair.setText("Sair");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel5.setText("Código");

      jfCodigo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

      jbProcurar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbProcurar.setText("Procurar");
      jbProcurar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProcurarActionPerformed(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(32, 32, 32)
                  .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(49, 49, 49)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                           .add(jbLimpar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                           .add(jbNovo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(54, 54, 54)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jPanel1Layout.createSequentialGroup()
                              .add(jbSalvar)
                              .add(18, 18, 18)
                              .add(jbAlterar)
                              .add(18, 18, 18)
                              .add(jbExcluir)
                              .add(42, 42, 42)
                              .add(jbSair))
                           .add(jPanel1Layout.createSequentialGroup()
                              .add(jbPrimeiro)
                              .add(18, 18, 18)
                              .add(jbAnterior)
                              .add(18, 18, 18)
                              .add(jbProximo)
                              .add(18, 18, 18)
                              .add(jbUltimo))))
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel5)
                        .add(18, 18, 18)
                        .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbProcurar)))
                  .add(0, 87, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(18, 18, 18)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
               .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(18, 18, 18)
                  .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(44, 44, 44)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jbAnterior)
                     .add(jbProximo)
                     .add(jbUltimo)
                     .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbNovo)
                        .add(jbPrimeiro)))
                  .add(31, 31, 31)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jbLimpar)
                     .add(jbSalvar)
                     .add(jbAlterar)
                     .add(jbExcluir)
                     .add(jbSair, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .add(18, 18, 18)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel5)
               .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jbProcurar))
            .addContainerGap(21, Short.MAX_VALUE))
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );

      setSize(new java.awt.Dimension(1269, 585));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jfPisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfPisActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jfPisActionPerformed

   private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
      LimparCampos();
      HabilitarCampos();
     
      
      try{
         connFunc.conexao();
         connFunc.executaaSQL("SELECT MAX(CODIGO_FUNCIONARIO) FROM FUNCIONARIO");
         connFunc.rs.last();
         
         jfCodigo.setText(String.valueOf(connFunc.rs.getInt("MAX(CODIGO_FUNCIONARIO)") + 1));
         
         
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "FrmFuncionario\nErro ao carregar codigo disponivel\n" + e);
        }
      
   }//GEN-LAST:event_jbNovoActionPerformed

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed

      dispose();
      FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
      frmMenu.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      LimparCampos();
      jfCodigo.setText(null);
      
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jcbCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbCidadeFocusLost

      try{
      
      connCidade.conexao();
      connCidade.executaaSQL("SELECT * FROM ESTADO E JOIN CIDADE C ON E.CODIGO_ESTADO = C.CODIGO_ESTADO WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem() + "'");
      connCidade.rs.first();
      
      jfEstado.setText(connCidade.rs.getString("NOME_ESTADO"));
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar Estado:\n" + e);
        }
      
      //connCidade.desconecta();
      
   }//GEN-LAST:event_jcbCidadeFocusLost

   private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

      if(jfNome.getText().trim().isEmpty()     || jfRg.getText().trim().isEmpty()        || jfCpf.getText().trim().isEmpty()            ||
         jcbOrgao.getSelectedItem().equals("") || jcbSexo.getSelectedItem().equals("")   || jcbEstadoCivil.getSelectedItem().equals("") ||
         jcbCargo.getSelectedItem().equals("") || jfSalario.getText().trim().isEmpty()   || jfPis.getText().trim().isEmpty()            ||
         jfEndereco.getText().trim().isEmpty() || jfNumero.getText().trim().isEmpty()    || jfBairro.getText().trim().isEmpty()         || 
         jfCep.getText().trim().isEmpty()      || jcbCidade.getSelectedItem().equals("") || jfTelefone.getText().trim().isEmpty()       ||
         jfNascimento.getText().trim().isEmpty()
         ){
             JOptionPane.showMessageDialog(null, "Preecha todos os campos");
             jfNome.grabFocus();
          }
      
      else{
         
         try{
            connFunc.conexao();
            
            connCidade.executaaSQL("SELECT * FROM CIDADE C JOIN ESTADO E ON E.CODIGO_ESTADO = C.CODIGO_ESTADO WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem() + "'");
            
            connCidade.rs.first();
            
            connOrgao.executaaSQL("SELECT * FROM ORGAO_EMISSOR WHERE NOME_ORGAO = '" + jcbOrgao.getSelectedItem() + "'");
            connOrgao.rs.first();
            
            connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL WHERE NOME_ESTADO_CIVIL = '" + jcbEstadoCivil.getSelectedItem() + "'");
            connEstadoCivil.rs.first();
            
            connCargo.executaaSQL("SELECT * FROM CARGO WHERE CARGO = '" + jcbCargo.getSelectedItem() + "'");
            connCargo.rs.first();
            
            mod.setNome(jfNome.getText());
            mod.setRg(Long.parseLong(jfRg.getText()));
            mod.setCodigoOrgaoEmissor(connOrgao.rs.getInt("CODIGO_ORGAO_EMISSOR"));
            mod.setCpf(jfCpf.getText());
            mod.setCodigoEstadoCivil(connEstadoCivil.rs.getInt("CODIGO_ESTADO_CIVIL"));
            mod.setSexo((String)jcbSexo.getSelectedItem());
            mod.setCodigoCargo(connCargo.rs.getInt("CODIGO_CARGO"));
            
            if(jfStatus.isSelected()){
               mod.setStatus("S");
            } else {
                 mod.setStatus("N");
              }
            
           mod.setSalario(Double.parseDouble(jfSalario.getText())); 
           mod.setPis(Long.parseLong(jfPis.getText()));
           
           String endereco = jfEndereco.getText();
           String enderecoPadrao = endereco.toUpperCase();
           jfEndereco.setText(enderecoPadrao);
           
           mod.setEndereco(jfEndereco.getText());
           mod.setNumeroEndereco(Integer.parseInt(jfNumero.getText()));
           
           String bairro = jfBairro.getText();
           String bairroUpper = bairro.toUpperCase();
           jfBairro.setText(bairroUpper);
           
           mod.setBairro(jfBairro.getText());
           mod.setCep(jfCep.getText());
           mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
           mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
           mod.setTelefone(jfTelefone.getText());
           
           String emailNormal = jfEmail.getText();
           String emailPadrao = emailNormal.toLowerCase();
           jfEmail.setText(emailPadrao);
           
           mod.setEmail(jfEmail.getText());
           
           mod.setNascimento(dataPadraoBanco(jfNascimento.getText()));
           controle.salvar(mod);
         
           dispose();
           FrmCadastroUsuario frm = new FrmCadastroUsuario();
           frm.setVisible(true);
                
           frm.recebeCodigo(Integer.parseInt(jfCodigo.getText()));
           frm.recebeNome(jfNome.getText());
           
           
         } catch(SQLException e){
              JOptionPane.showMessageDialog(null, "FrmFuncionario\n\nErro ao setar valores:\n" + e);
           } 

      }
      
      
   }//GEN-LAST:event_jbSalvarActionPerformed

   
   public void pegaCodigo(int codigo){
      jfCodigo.setText(String.valueOf(codigo));
      
      mod = controle.procurar(codigo);
      
      buscaSelect();
      
      HabilitarAlteracao();
   }
   
   
   public void buscaSelect(){
      
      jfCodigo.setText("" + mod.getCodigoFuncionario());
      jfNome.setText(mod.getNome());
      jfRg.setText("" + mod.getRg());
      jcbOrgao.setSelectedItem(mod.getNomeOrgaoEmissor());
      jfCpf.setText(mod.getCpf());
      jcbEstadoCivil.setSelectedItem(mod.getNomeEstadoCivil());
      jcbSexo.setSelectedItem(mod.getSexo());
      jfNascimento.setText(dataPadraoBrasil(mod.getNascimento()));
      jcbCargo.setSelectedItem(mod.getCargo());
      
      if(mod.getStatus().equals("S")){
         jfStatus.setSelected(true);
      } else{
           jfStatus.setSelected(false);
        }
      
      jfSalario.setText("" + mod.getSalario());
      jfPis.setText("" + mod.getPis());
      jfEndereco.setText(mod.getEndereco());
      jfNumero.setText("" + mod.getNumeroEndereco());
      jfBairro.setText(mod.getBairro());
      jfCep.setText(mod.getCep());
      jcbCidade.setSelectedItem(mod.getCidade());
      jfEstado.setText(mod.getEstado());
      jfTelefone.setText(mod.getTelefone());
      jfEmail.setText(mod.getEmail());
   
   }
   
   
   public String dataPadraoBanco(String dataN){
      String dataB = dataN.substring(6) + "-" + dataN.substring(3,5) + "-" + dataN.substring(0,2);
      return dataB;
   }
   
   public String dataPadraoBrasil(String data){
      String d = data.substring(8) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
      return d;
   }
   
   
   private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed

      try{
         connFunc.conexao();
         
         mod.setCodigoFuncionario(Integer.parseInt(jfCodigo.getText()));
         
         controle.excluir(mod);
      } catch(Exception ex){
           JOptionPane.showMessageDialog(null, "FrmFuncionario\n\nErro ao excluir funcionario:\n" + ex);
        }
   }//GEN-LAST:event_jbExcluirActionPerformed

   private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed

      mod = controle.primeiro();
      
      buscaSelect();
      HabilitarAlteracao();
      
   }//GEN-LAST:event_jbPrimeiroActionPerformed

   private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed

      if(jfNome.getText().trim().isEmpty()     || jfRg.getText().trim().isEmpty()        || jfCpf.getText().trim().isEmpty()            ||
         jcbOrgao.getSelectedItem().equals("") || jcbSexo.getSelectedItem().equals("")   || jcbEstadoCivil.getSelectedItem().equals("") ||
         jcbCargo.getSelectedItem().equals("") || jfSalario.getText().trim().isEmpty()   || jfPis.getText().trim().isEmpty()            ||
         jfEndereco.getText().trim().isEmpty() || jfNumero.getText().trim().isEmpty()    || jfBairro.getText().trim().isEmpty()         || 
         jfCep.getText().trim().isEmpty()      || jcbCidade.getSelectedItem().equals("") || jfTelefone.getText().trim().isEmpty()       ||
         jfNascimento.getText().trim().isEmpty()
         ){
             JOptionPane.showMessageDialog(null, "Preecha todos os campos");
             jfNome.grabFocus();
          }
      
      else{
         
         try{
            connFunc.conexao();
            
            connCidade.executaaSQL("SELECT * FROM CIDADE C JOIN ESTADO E ON E.CODIGO_ESTADO = C.CODIGO_ESTADO WHERE NOME_CIDADE = '" + jcbCidade.getSelectedItem() + "'");
            
            connCidade.rs.first();
            
            connOrgao.executaaSQL("SELECT * FROM ORGAO_EMISSOR WHERE NOME_ORGAO = '" + jcbOrgao.getSelectedItem() + "'");
            connOrgao.rs.first();
            
            connEstadoCivil.executaaSQL("SELECT * FROM ESTADO_CIVIL WHERE NOME_ESTADO_CIVIL = '" + jcbEstadoCivil.getSelectedItem() + "'");
            connEstadoCivil.rs.first();
            
            connCargo.executaaSQL("SELECT * FROM CARGO WHERE CARGO = '" + jcbCargo.getSelectedItem() + "'");
            connCargo.rs.first();
            mod.setCodigoFuncionario(Integer.parseInt(jfCodigo.getText()));
            mod.setNome(jfNome.getText());
            mod.setRg(Long.parseLong(jfRg.getText()));
            mod.setCodigoOrgaoEmissor(connOrgao.rs.getInt("CODIGO_ORGAO_EMISSOR"));
            mod.setCpf(jfCpf.getText());
            mod.setCodigoEstadoCivil(connEstadoCivil.rs.getInt("CODIGO_ESTADO_CIVIL"));
            mod.setSexo((String)jcbSexo.getSelectedItem());
            mod.setCodigoCargo(connCargo.rs.getInt("CODIGO_CARGO"));
            
            if(jfStatus.isSelected()){
               mod.setStatus("S");
            } else {
                 mod.setStatus("N");
              }
            
           mod.setSalario(Double.parseDouble(jfSalario.getText())); 
           mod.setPis(Long.parseLong(jfPis.getText()));
           
           String endereco = jfEndereco.getText();
           String enderecoPadrao = endereco.toUpperCase();
           jfEndereco.setText(enderecoPadrao);
           
           mod.setEndereco(jfEndereco.getText());
           mod.setNumeroEndereco(Integer.parseInt(jfNumero.getText()));
           
           String bairro = jfBairro.getText();
           String bairroUpper = bairro.toUpperCase();
           jfBairro.setText(bairroUpper);
           
           mod.setBairro(jfBairro.getText());
           mod.setCep(jfCep.getText());
           mod.setCodigoCidade(connCidade.rs.getInt("CODIGO_CIDADE"));
           mod.setCodigoEstado(connCidade.rs.getInt("CODIGO_ESTADO"));
           mod.setTelefone(jfTelefone.getText());
           
           String emailNormal = jfEmail.getText();
           String emailPadrao = emailNormal.toLowerCase();
           jfEmail.setText(emailPadrao);
           
           mod.setEmail(jfEmail.getText());
           mod.setNascimento(dataPadraoBanco(jfNascimento.getText()));
           controle.alterar(mod);
           LimparCampos();
            
         } catch(SQLException e){
              JOptionPane.showMessageDialog(null, "FrmFuncionario\n\nErro ao setar valores:\n" + e);
           }
      
      }
      
   }//GEN-LAST:event_jbAlterarActionPerformed

   private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed

      mod = controle.ultimo();
      
      buscaSelect();
      HabilitarAlteracao();
   }//GEN-LAST:event_jbUltimoActionPerformed

   private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed

      mod = controle.anterior();
      
      buscaSelect();
      
      HabilitarAlteracao();
   }//GEN-LAST:event_jbAnteriorActionPerformed

   private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed

      mod = controle.Proximo();
     
      buscaSelect();
      
      HabilitarAlteracao();
   }//GEN-LAST:event_jbProximoActionPerformed

    private void jbProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcurarActionPerformed
        
        if(!jfCodigo.getText().trim().isEmpty()){
           mod = controle.procurar(Integer.parseInt(jfCodigo.getText()));
           buscaSelect();
           HabilitarAlteracao();
        } else{
             JOptionPane.showMessageDialog(null, "Você deve informar um código valido primeiro");
          }
        
        
    }//GEN-LAST:event_jbProcurarActionPerformed

   
   
   
   
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
         java.util.logging.Logger.getLogger(FrmFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmFuncionario().setVisible(true);
         }
      });
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel14;
   private javax.swing.JLabel jLabel15;
   private javax.swing.JLabel jLabel16;
   private javax.swing.JLabel jLabel17;
   private javax.swing.JLabel jLabel18;
   private javax.swing.JLabel jLabel19;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel20;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbAnterior;
   private javax.swing.JButton jbExcluir;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProcurar;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbSalvar;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JComboBox jcbCargo;
   private javax.swing.JComboBox jcbCidade;
   private javax.swing.JComboBox jcbEstadoCivil;
   private javax.swing.JComboBox jcbOrgao;
   private javax.swing.JComboBox jcbSexo;
   private javax.swing.JTextField jfBairro;
   private javax.swing.JFormattedTextField jfCep;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JFormattedTextField jfCpf;
   private javax.swing.JTextField jfEmail;
   private javax.swing.JTextField jfEndereco;
   private javax.swing.JTextField jfEstado;
   private javax.swing.JFormattedTextField jfNascimento;
   private javax.swing.JTextField jfNome;
   private javax.swing.JTextField jfNumero;
   private javax.swing.JFormattedTextField jfPis;
   private javax.swing.JTextField jfRg;
   private javax.swing.JFormattedTextField jfSalario;
   private javax.swing.JCheckBox jfStatus;
   private javax.swing.JFormattedTextField jfTelefone;
   // End of variables declaration//GEN-END:variables
}
