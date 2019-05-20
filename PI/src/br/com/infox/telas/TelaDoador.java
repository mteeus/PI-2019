/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
// A linha abaixo impota recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author anima
 */
public class TelaDoador extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaDoador
     */
    public TelaDoador() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // método para adicionar usuários
    private void adicionar() {
        String sql = "insert into tbdoador(nomedoador, enddoador , bairrodoador, cepdoador, fonedoador, emaildoador, observacao) values(?,?,?,?,?,?,?)";       // Qunado consertar o campo DATA mude a instrução para o SQL na linha de cima ---> datanasc e adicione o ?
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtDoaNome.getText());
            pst.setString(2, txtDoaEndereco.getText());
            pst.setString(3, txtDoaBairro.getText());
            pst.setString(4, txtDoaCep.getText());
            pst.setString(5, txtDoaFone.getText());
            pst.setString(6, txtDoaEmail.getText());
            //    pst.setString(7, txtDoaNiver.getText()); quando alterar modificar os números dos campos
            pst.setString(7, jtaDoaObservacao.getText());
            //Validação dos campos obrigatórios.
            //  
            if ((txtDoaNome.getText().isEmpty()) || (txtDoaFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");
            } else {

                //A linha abaixo atualiza os tabela usuário com os dados do formulário
                //A estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                //A linha abaixo serve como o entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!!");
                    txtDoaNome.setText(null);
                    txtDoaEndereco.setText(null);
                    txtDoaBairro.setText(null);
                    txtDoaCep.setText(null);
                    txtDoaFone.setText(null);
                    txtDoaEmail.setText(null);
                    //    txtDoaNiver.setText(null);
                    jtaDoaObservacao.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Métoopara pesquisar clientes pelo nome com filtro
    private void pesquisar_doador() {
        String sql = "select * from tbdoador where nomedoador like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //Pasando o conteúdo da caixa de pesquisa para o ineterrogação.
            //atenção ao porcentagem que é a continuação da String sql.
            pst.setString(1, txtDoaPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblDoador.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos() {
        int setar = tblDoador.getSelectedRow();
        txtDoaId.setText(tblDoador.getModel().getValueAt(setar, 0).toString());
        txtDoaNome.setText(tblDoador.getModel().getValueAt(setar, 1).toString());
        txtDoaEndereco.setText(tblDoador.getModel().getValueAt(setar, 2).toString());
        txtDoaBairro.setText(tblDoador.getModel().getValueAt(setar, 3).toString());
        txtDoaCep.setText(tblDoador.getModel().getValueAt(setar, 4).toString());
        txtDoaFone.setText(tblDoador.getModel().getValueAt(setar, 5).toString());
        txtDoaEmail.setText(tblDoador.getModel().getValueAt(setar, 6).toString());
        //txtDoaNiver.setText(tblDoador.getModel().getValueAt(setar,7).toString());
        jtaDoaObservacao.setText(tblDoador.getModel().getValueAt(setar, 7).toString());

        //A linha abaixo desabilita o botão adicionar
        btnAdicionar.setEnabled(false);
    }

    // Criando o método para alterar os dados do Doador   
    private void alterar() {
        String sql = "update tbdoador set nomedoador=?, enddoador=?, bairrodoador=?, cepdoador=?, fonedoador=?, emaildoador=?, observacao=? where iddoador=?";
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setstrnig(1, txtDoaId.getText());
            pst.setString(1, txtDoaNome.getText());
            pst.setString(2, txtDoaEndereco.getText());
            pst.setString(3, txtDoaBairro.getText());
            pst.setString(4, txtDoaCep.getText());
            pst.setString(5, txtDoaFone.getText());
            //    pst.setString(6, txtDoaNiver.getText());quando alterar modificar os números dos campos
            pst.setString(6, txtDoaEmail.getText());
            pst.setString(7, jtaDoaObservacao.getText());
            pst.setString(8, txtDoaId.getText());
            if ((txtDoaNome.getText().isEmpty()) || (txtDoaFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");
            } else {

                //A linha abaixo atualiza os tabela doador com os dados do formulário
                //A estrutura abaixo é usada para confirmar a alteração dos dados do usuário na tabela
                int adicionado = pst.executeUpdate();
                //A linha abaixo serve como o entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Doador alterados com sucesso!!");
                    txtDoaNome.setText(null);
                    txtDoaEndereco.setText(null);
                    txtDoaBairro.setText(null);
                    txtDoaCep.setText(null);
                    txtDoaFone.setText(null);
                    txtDoaEmail.setText(null);
                    jtaDoaObservacao.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

//Método responsavel pela remoção de doadores
    private void remover() {
        //A estrutura abaixo confirma a remoção do doadores
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este doadores", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbdoador where iddoador=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtDoaId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Doador removido com sucesso.");
                    txtDoaNome.setText(null);
                    txtDoaEndereco.setText(null);
                    txtDoaBairro.setText(null);
                    txtDoaCep.setText(null);
                    txtDoaFone.setText(null);
                    txtDoaEmail.setText(null);
                    jtaDoaObservacao.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDoaNome = new javax.swing.JTextField();
        txtDoaEndereco = new javax.swing.JTextField();
        txtDoaBairro = new javax.swing.JTextField();
        txtDoaCep = new javax.swing.JTextField();
        txtDoaFone = new javax.swing.JTextField();
        txtDoaEmail = new javax.swing.JTextField();
        txtDoaNiver = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaDoaObservacao = new javax.swing.JTextArea();
        txtDoaPesquisar = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoador = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDoaId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(730, 472));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jLabel1.setText("* Nome:");

        jLabel2.setText("Endereço:");

        jLabel3.setText("Bairro:");

        jLabel4.setText("CEP:");

        jLabel5.setText("* Fone:");

        jLabel6.setText("Email:");

        jLabel7.setText("Nascimento :");

        jLabel8.setText("Observação:");

        jtaDoaObservacao.setColumns(20);
        jtaDoaObservacao.setRows(5);
        jScrollPane1.setViewportView(jtaDoaObservacao);

        txtDoaPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoaPesquisarActionPerformed(evt);
            }
        });
        txtDoaPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDoaPesquisarKeyReleased(evt);
            }
        });

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create1.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update1.png"))); // NOI18N
        btnAlterar.setToolTipText("Alterar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete1.png"))); // NOI18N
        btnRemover.setToolTipText("Remover");
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jLabel9.setText("* Campos obrigatórios");

        tblDoador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDoador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoadorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDoador);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search.png"))); // NOI18N

        jLabel11.setText("Id Benfeitor");

        txtDoaId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtDoaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jLabel9))
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDoaId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDoaFone, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDoaEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDoaBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDoaNiver, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtDoaNome)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                            .addComponent(txtDoaCep, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDoaEmail))))
                .addContainerGap(92, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDoaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDoaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDoaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDoaEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtDoaBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDoaCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtDoaFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtDoaNiver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDoaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(170, 170, 170))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65))))
        );

        setBounds(0, 0, 730, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDoaPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoaPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDoaPesquisarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        //Método para adicionar doador.
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtDoaPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoaPesquisarKeyReleased
        // TODO add your handling code here:
        // chamar o método pesquisar clientes
        pesquisar_doador();
    }//GEN-LAST:event_txtDoaPesquisarKeyReleased

//evento que será usado para os campos da tabela (clicando com o MOUSE)
    private void tblDoadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoadorMouseClicked
        // TODO add your handling code here:
        // chamando o método setar_campos
        setar_campos();
    }//GEN-LAST:event_tblDoadorMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        //Método para alterar doadores.
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        //Chamando o método para remover Doador
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jtaDoaObservacao;
    private javax.swing.JTable tblDoador;
    private javax.swing.JTextField txtDoaBairro;
    private javax.swing.JTextField txtDoaCep;
    private javax.swing.JTextField txtDoaEmail;
    private javax.swing.JTextField txtDoaEndereco;
    private javax.swing.JTextField txtDoaFone;
    private javax.swing.JTextField txtDoaId;
    private javax.swing.JTextField txtDoaNiver;
    private javax.swing.JTextField txtDoaNome;
    private javax.swing.JTextField txtDoaPesquisar;
    // End of variables declaration//GEN-END:variables
}
