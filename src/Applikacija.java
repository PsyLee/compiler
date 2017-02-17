
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Applikacija extends javax.swing.JFrame {

    JFrame rimal = new JFrame("Rimal");
    TextArea text_rimal = new TextArea();
    JFrame pravila = new JFrame("Правила");
    TextArea pravila_t = new TextArea();
    JFrame izrabotile = new JFrame("Изработиле");
    TextArea izrab = new TextArea();

    public Applikacija() {

        initComponents();
        this.setTitle("RoboL апликација");
        this.jTextArea1.setEditable(false);
        this.jTextArea2.setText("");
        this.jTextArea3.setText("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("okolina_def.txt")), "UTF-8"));
            while (br.ready()) {
                // text += br.readLine() + "\n";
//                System.out.println(br.readLine());
                this.jTextArea2.append(br.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        BufferedReader br2 = null;
        try {
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("roboL_def.txt")), "UTF-8"));
            while (br2.ready()) {
                // text += br.readLine() + "\n";
                this.jTextArea3.append(br2.readLine() + "\n");
            }
        } catch (Exception e) {
        }
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel1.setText("Внесете код за околина:");

        jLabel2.setText("Внесете код во RoboL:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jButton1.setText("Компајлирај");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Прикажи RIMAL код");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(60, 60, 60)
                .addComponent(jButton1)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jLabel3.setText("Грешки:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jMenu1.setText("Опции");

        jMenuItem1.setText("Вчитај Default код ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Нов код");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem5.setText("Сними код");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Вчитај снимен код");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Помош");

        jMenuItem3.setText("Правила");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Изработиле");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        //    this.jTextArea2.setFont(new Font("Arial", Font.PLAIN, 14));
        //    this.jTextArea3.setFont(new Font("Arial", Font.PLAIN, 14));
        this.jTextArea2.setText("");
        this.jTextArea3.setText("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("okolina_def.txt")), "UTF-8"));
            while (br.ready()) {
                // text += br.readLine() + "\n";
//                System.out.println(br.readLine());
                this.jTextArea2.append(br.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        BufferedReader br2 = null;
        try {
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("roboL_def.txt")), "UTF-8"));
            while (br2.ready()) {
                // text += br.readLine() + "\n";
                this.jTextArea3.append(br2.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        this.jTextArea2.setCaretPosition(0);
        this.jTextArea3.setCaretPosition(0);
        br = null;
        br2 = null;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.jTextArea1.setText("");
        this.jTextArea2.setText("");
        this.jTextArea3.setText("");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        text_rimal.setText("");
        text_rimal.setEditable(false);
        rimal.setSize(300, 500);
        rimal.setLocationRelativeTo(this);
        rimal.setResizable(false);
        BufferedReader br2 = null;
        //       text_rimal.setFont(new Font("Arial", Font.PLAIN, 14));
        try {
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("rimal.rml")), "UTF-8"));
            while (br2.ready()) {
                // text += br.readLine() + "\n";
                text_rimal.append(br2.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        rimal.add(text_rimal);
        text_rimal.setCaretPosition(0);
        rimal.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        //     izrab.setFont(new Font("Arial", Font.PLAIN, 14));
        izrab.setText("Стефан Дејановски\nАлександар Павлевски\nВладимир Стојковски\nФидан Фидановски\nФилип Богдановски");
        izrab.setEditable(false);
        izrabotile.add(izrab);
        izrabotile.setSize(200, 200);
        izrabotile.setLocationRelativeTo(this);
        izrabotile.setResizable(false);

        izrabotile.setVisible(true);

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //    pravila_t.setFont(new Font("Arial", Font.PLAIN, 14));
        pravila_t.setText("Внимавјте при менување на датотеките треба да ги снимите со UTF-8 енкодирање.\n"
                + "Кодот за Rimal се прикажува откако ќе се компајлира програмата."
                + "\nНе е дозволено телепортирање надвор од мапата.\n"
                + "Доколку еднаш излезете од мапа нема враќање.");
        pravila_t.setEditable(false);
        pravila.setSize(600, 150);
        pravila.setLocationRelativeTo(this);
        pravila.setResizable(false);
        pravila.add(pravila_t);

        pravila.setVisible(true);
   }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            try {
                File file = new File("okolina_def.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(jTextArea2.getText());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                File file = new File("roboL_def.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(jTextArea3.getText());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //      this.jTextArea1.setFont(new Font("Arial", Font.PLAIN, 14));
            BufferedReader br2 = null;
            this.jTextArea1.setText("");
            try {
                br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("greski.txt")), "UTF-8"));
                while (br2.ready()) {
                    // text += br.readLine() + "\n";
                    this.jTextArea1.append(br2.readLine() + "\n");
                }
            } catch (Exception e) {
            }
            this.jTextArea1.setCaretPosition(0);
            br2 = null;

            Lexer lex = new Lexer("proba.txt");
            while (true) {
                if (!lex.imaGreski()) {
                    break;
                }
                LanParser pars = new LanParser("главна", lex.getLista(), 28, 34);
                pars.parsiraj();
                break;
            }
            Lexer lexCG = new Lexer("proba.txt");
            CodeGenerator CG = new CodeGenerator(lexCG.getLista());
            CG.fja();
            Lexer lexNK = new Lexer("proba.txt");
            NizaKomandi NK = new NizaKomandi(lexNK.getLista());
            NK.fja();
            

                Points ps = new Points();
                ps.setVisible(true);

//            jPanel2.removeAll();
//            jPanel2.add(new Animacija());
//            jPanel2.updateUI();
//            jPanel2.revalidate();
//            jPanel2.repaint();


        } catch (IOException ex) {
            Logger.getLogger(Applikacija.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

        File kod = new File("kod_snimen.rml");
        if (!kod.exists()) {
            try {
                kod.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Applikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File okolina = new File("okolina_snimen.rml");
        if (!okolina.exists()) {
            try {
                okolina.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Applikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            PrintWriter writer2 = new PrintWriter(okolina, "UTF-8");
            writer2.println(this.jTextArea2.getText());
            writer2.close();
        } catch (Exception e) {
        }

        try {
            PrintWriter writer = new PrintWriter(kod, "UTF-8");
            writer.println(this.jTextArea3.getText());
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Applikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Applikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        this.jTextArea2.setText("");
        this.jTextArea3.setText("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("okolina_snimen.rml")), "UTF-8"));
            while (br.ready()) {
                // text += br.readLine() + "\n";
//                System.out.println(br.readLine());
                this.jTextArea2.append(br.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        BufferedReader br2 = null;
        try {
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("kod_snimen.rml")), "UTF-8"));
            while (br2.ready()) {
                // text += br.readLine() + "\n";
                this.jTextArea3.append(br2.readLine() + "\n");
            }
        } catch (Exception e) {
        }

        this.jTextArea2.setCaretPosition(0);
        this.jTextArea3.setCaretPosition(0);
        br = null;
        br2 = null;
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Applikacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Applikacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Applikacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Applikacija.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Applikacija().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    // End of variables declaration//GEN-END:variables
}
