/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import gestion_biblio.Gestion_biblio;
import gestion_biblio.db_connection;
import gestion_biblio.Parametre;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import static java.awt.event.PaintEvent.UPDATE;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ~chaimae~
 */
public class Principal_A extends javax.swing.JFrame {
   ResultSet rs;
    db_connection db;
   
     ArrayList<ImageIcon> n=new ArrayList<ImageIcon>();
        ArrayList<JLabel> label=new ArrayList<JLabel>() ;
        ArrayList<String> statut=new ArrayList<String>();
        ArrayList<String> nom=new ArrayList<String>();
   
    /**
     * Creates new form Principale
     */
    public Principal_A() throws SQLException {   
       db = new db_connection(new Parametre().HOST_DB, new Parametre().USERNAME_DB, new Parametre().PASSWORD_DB, new Parametre().IPHOST, new Parametre().PORT);
        initComponents();
        tableetudiant(); 
        actualiseretudiant();
        tableemprunt() ;
        //tablemateriel();
        table_empruntsssss();
      
        jam();
        
        delaiRetour();
        pretSensible();
        table_historique();
        statistic_Materiel();
       // statistic_Materiel_etudiant();
          
       try {
           showImage();
       } catch (IOException ex) {
           Logger.getLogger(Principal_A.class.getName()).log(Level.SEVERE, null, ex);
       }
       
    }
    public void delaiRetour() { 
      rs = db.exécutionQuery("select M.id_etudiant,E.nom, E.prenom,E.email from etudiant E, emprunts M where E.identifiant=M.id_etudiant and DATEDIFF(M.date_rend,M.date_prend)> M.duree_reservation;");
        delaiRetour.setModel(new Gestion_biblio(rs));      
       }
       public void pretSensible() { 
        rs = db.exécutionQuery("select e.nom,e.prenom,e.email,m.nom_materiel,em.date_prend,em.date_rend,em.duree_reservation from etudiant e, emprunts em ,matériel m where e.identifiant=em.id_etudiant and m.identifiant=em.id_materiel and  DATEDIFF(SYSDATE(),em.date_rend)> 3");
        pretSensible.setModel(new Gestion_biblio(rs));      
       }
        
//and  DATEDIFF(em.date_rend,GETDATE()> 3;
       /*public void table_etudiant1() { 
       String c[] = {"nom_materiel","date_prend","date_rend"};
        rs = db.querySelect(c, "emprunts");
        table_etudiant1.setModel(new Gestion_biblio(rs));     
       }*/
       
       public void table_historique() {
        rs = db.exécutionQuery("SELECT m.nom_materiel,e.nom,e.prenom,em.date_prend,em.date_rend from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant");
        table_etudiant1.setModel(new Gestion_biblio(rs));  
        
    }
       
        public void table_empruntsssss() {
        rs = db.exécutionQuery("SELECT m.nom_materiel,e.nom,e.prenom from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant");
        table_mat.setModel(new Gestion_biblio(rs));  
        
    }
       
       
       
       /* public void statistic_Materiel_etudiant() { 
        rs = db.exécutionQuery("SELECT em.id_materiel,e.nom,m.nom_materiel,count(id_materiel) nombre_de_matériel from emprunts em,matériel m,etudiant e where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant  group by m.nom_materiel ;");
        statistique_mat_etud.setModel(new Gestion_biblio(rs));   
       }
        */
        
        public void actualiseretudiantstatistique(){
             
         }
       
      
        
    public void jam() {
        Date s = new Date();
        SimpleDateFormat tgl = new SimpleDateFormat("EEEE-dd-MMM-yyyy");
        SimpleDateFormat jam = new SimpleDateFormat("HH:mm");
        date.setText(jam.format(s));
        heure.setText(tgl.format(s));}
 
       public void tableetudiant() {
        String a[] = {"identifiant", "nom", "prenom", "email"};
        rs = db.querySelect(a, "etudiant");
        table_etudiant.setModel(new Gestion_biblio(rs));
    }
       /*public void tablemateriel() {    //   table matériel afficher nom
        String a[] = {"nom"};
        rs = db.querySelect(a, "matériel");
        table_mat.setModel(new Gestion_biblio(rs));
    }*/
       /*public void tableetudi() {  //   table etudiant afficher identifiant
        String a[] = { "identifiant"};
        rs = db.querySelect(a, "etudiant");
        table_etu.setModel(new Gestion_biblio(rs));
    }*/
       
        /*public void tablemateriele() {
        String a[] = {"Statut"};
        rs = db.querySelect(a, "matériel");
        table_mat.setModel(new Gestion_biblio(rs));
    }*/
       
       
       
      
        public void tableemprunt() {
        rs = db.exécutionQuery("SELECT em.idemprunts,m.nom_materiel,e.nom,e.prenom,em.date_prend,em.date_rend,em.duree_reservation,em.statut from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant");
        tab_emp.setModel(new Gestion_biblio(rs));  
    }
  
         
        public void actualiseretudiant() {
        txt_nome.setText("");
        txt_prenome.setText("");
        comptyyy.setSelectedItem("Typpppe");
        txt_rech.setText("");
        txt_emaile.setText("");
        pp3.setText("");
       
        
       }
        
        public void actualiseremprunt() {
        ref_etudiant.setText("");
        type_materiel.setText("");
        duree_reservation.setText("");
        date_rend.setText("");
        txt_emaile.setText("");
        date_prend.setText("");
        txt_rech1.setText("");
        pp1.setText("");
       
        
       }
        public void actualiserenmprr(){
         date_rend.setText("");    
        compy.setSelectedItem("Typpppe");
        }
        
        
        
        
        
        
        
        
        
        
        
           public void showImage() throws SQLException, IOException{
           db= new db_connection(new Parametre().HOST_DB, new Parametre().USERNAME_DB, new Parametre().PASSWORD_DB, new Parametre().IPHOST, new Parametre().PORT);
       int i=0;
        String b[] = {"identifiant","nom_materiel","photo","Statut"};
        
        //ArrayList <ImageIcon> ii=new ArrayList<ImageIcon>();
        ResultSet rs1 = db.querySelect(b, "matériel");
       //  HashMap<ImageIcon,String> map=new HashMap<ImageIcon,String>();  
       
       
        while(rs1.next()){ 
             byte[] imagedata=rs1.getBytes("photo");
              ImageIcon format=new ImageIcon(imagedata);
              statut.add(rs1.getString("Statut"));
              nom.add(rs1.getString("nom_materiel"));
              n.add(format);
              
              //map.put(format,rs1.getString("Statut"));
            
            i++;
               //rs1.close();
               
               //jPanel3.add(jLabel4,new GridLayout());
               
               
             /*  
            InputStream f=rs1.getBinaryStream("photo");
            BufferedImage im=ImageIO.read(rs1.getBinaryStream("photo"));
            BufferedImage ii[]={};
            ii[1]=im;*/
             //Blob ii=rs1.getBlob(3);
             //byte barr[]=ii.getBytes(1,(int)ii.length());
        
            
            
           // jTable1.setModel(getImage(barr));
            
              
       // 
        
        //System.out.println(o);
        //InputStream f=rs1.getBinaryStream("photo");
        //BufferedImage im=ImageIO.read(rs1.getBinaryStream("photo"));
        //System.out.println(new ImageIcon(im));
              // ii.add(o,new ImageIcon(im));
               //System.out.println(ii);
              // ii[o]=new ImageIcon(im);
               
               //j[i]= new JLabel();
               //j[i].setIcon(new ImageIcon(im));
       
      //JLabel j= new JLabel();
      //j.setIcon(ii[i]);
      
       
       // jLabel4.setIcon(new ImageIcon(ii[1]));
        
        //jPanel6.add(jLabel1);
        
       // i++;
       }//end of while
        for(ImageIcon  pic:n  ){
            
           
               Image mm=pic.getImage();
               Image img2=mm.getScaledInstance(l2.getWidth(),        l2.getHeight(), Image.SCALE_SMOOTH);
               ImageIcon image=new ImageIcon(img2);
               JLabel l=new JLabel();
               l.setIcon(image);
               label.add(l);
            }
        
        l1.setIcon(new ImageIcon(n.get(0).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l2.setIcon(new ImageIcon(n.get(1).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l3.setIcon(new ImageIcon(n.get(2).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l4.setIcon(new ImageIcon(n.get(3).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l5.setIcon(new ImageIcon(n.get(4).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l6.setIcon(new ImageIcon(n.get(5).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l7.setIcon(new ImageIcon(n.get(6).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l8.setIcon(new ImageIcon(n.get(7).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l9.setIcon(new ImageIcon(n.get(8).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l10.setIcon(new ImageIcon(n.get(9).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l11.setIcon(new ImageIcon(n.get(10).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l12.setIcon(new ImageIcon(n.get(11).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l13.setIcon(new ImageIcon(n.get(12).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l14.setIcon(new ImageIcon(n.get(13).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l15.setIcon(new ImageIcon(n.get(14).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l16.setIcon(new ImageIcon(n.get(15).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        l17.setIcon(new ImageIcon(n.get(16).getImage().getScaledInstance(l1.getWidth(),        l1.getHeight(), Image.SCALE_SMOOTH)));
        
    
        
      
        }
        



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        msg = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton33 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        heure = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        mvt = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        historique = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_etudiant1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        com_re1 = new javax.swing.JComboBox<>();
        txt_rech2 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        stats = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        statistique_mat = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        statistique_mat_etud = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        com_re2 = new javax.swing.JComboBox<>();
        txt_rech3 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txt_rech5 = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel165 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        retard = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        pretSensible = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        delaiRetour = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        l4 = new javax.swing.JLabel();
        l2 = new javax.swing.JLabel();
        l3 = new javax.swing.JLabel();
        l8 = new javax.swing.JLabel();
        l9 = new javax.swing.JLabel();
        l5 = new javax.swing.JLabel();
        l10 = new javax.swing.JLabel();
        l13 = new javax.swing.JLabel();
        l14 = new javax.swing.JLabel();
        l15 = new javax.swing.JLabel();
        l1 = new javax.swing.JLabel();
        l11 = new javax.swing.JLabel();
        l16 = new javax.swing.JLabel();
        l7 = new javax.swing.JLabel();
        l6 = new javax.swing.JLabel();
        l12 = new javax.swing.JLabel();
        l17 = new javax.swing.JLabel();
        etudiant = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        txt_rech = new javax.swing.JTextField();
        com_re = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        pp3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_prenome = new javax.swing.JTextField();
        txt_nome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_etudiant = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txt_emaile = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ref_etudiant = new javax.swing.JTextField();
        type_materiel = new javax.swing.JTextField();
        duree_reservation = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Enregistrer = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_mat = new javax.swing.JTable();
        jLabel163 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        pp1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        comptyyy = new javax.swing.JComboBox<>();
        txt_rech1 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        date_prend = new javax.swing.JFormattedTextField();
        idemprunt = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tab_emp = new javax.swing.JTable();
        jLabel164 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        compy = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        date_rend = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        msg.setLocationByPlatform(true);
        msg.setMinimumSize(new java.awt.Dimension(300, 200));
        msg.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                msgWindowClosed(evt);
            }
        });
        msg.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("jLabel3");
        msg.getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setText("jLabel2");
        msg.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel9.setFont(new java.awt.Font("Webdings", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("");
        jLabel9.setToolTipText("");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(1210, 0, 40, 40);

        jButton33.setBackground(new java.awt.Color(0, 0, 0));
        jButton33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton33.setForeground(new java.awt.Color(255, 255, 255));
        jButton33.setText("    Déconnexion  ");
        jButton33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton33);
        jButton33.setBounds(1200, 0, 140, 40);

        jTabbedPane1.setBackground(new java.awt.Color(56, 68, 84));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 1000));

        jPanel1.setBackground(new java.awt.Color(56, 68, 84));
        jPanel1.setLayout(null);

        heure.setBackground(new java.awt.Color(255, 255, 255));
        heure.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        heure.setForeground(new java.awt.Color(255, 204, 102));
        jPanel1.add(heure);
        heure.setBounds(10, 0, 240, 30);

        date.setBackground(new java.awt.Color(0, 102, 102));
        date.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        date.setForeground(new java.awt.Color(255, 204, 102));
        jPanel1.add(date);
        date.setBounds(240, 0, 140, 30);

        mvt.setBackground(new java.awt.Color(255, 204, 102));
        mvt.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        mvt.setForeground(new java.awt.Color(255, 204, 102));
        mvt.setText("Bienvenue dans votre bibliothèque");
        mvt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                mvtPropertyChange(evt);
            }
        });
        jPanel1.add(mvt);
        mvt.setBounds(180, 40, 880, 100);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/image/Library-Management-Banner.png"))); // NOI18N
        jPanel1.add(jLabel18);
        jLabel18.setBounds(470, 90, 1030, 620);

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Interface/image/home.png")), jPanel1); // NOI18N

        jTabbedPane3.setBackground(new java.awt.Color(212, 211, 220));
        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jTabbedPane3.setOpaque(true);
        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        historique.setBackground(new java.awt.Color(56, 68, 84));
        historique.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                historiqueAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        historique.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_etudiant1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "identifiant", "nom", "prenom", "email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_etudiant1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_etudiant1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table_etudiant1);

        historique.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 1200, 190));

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("recherche par identifiant de etudiant :");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, -1, 30));

        com_re1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        com_re1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "identifiant", " ", " " }));
        jPanel6.add(com_re1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 190, 30));

        txt_rech2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rech2ActionPerformed(evt);
            }
        });
        jPanel6.add(txt_rech2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 190, 30));

        jLabel43.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("");
        jPanel6.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 30, 40));

        jButton11.setBackground(new java.awt.Color(0, 0, 0));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("    Rechercher");
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 130, 40));

        historique.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 570, 180));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator4.setPreferredSize(new java.awt.Dimension(1, 2));
        historique.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 320, 1360, 30));

        jTabbedPane3.addTab("Historique", historique);

        stats.setBackground(new java.awt.Color(56, 68, 84));
        stats.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                statsAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        stats.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(153, 153, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Statistique du matériel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        statistique_mat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nom du matériel", "Nombre d'emprunt"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(statistique_mat);

        jPanel8.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 31, 1050, 168));

        stats.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 1090, 260));

        jPanel9.setBackground(new java.awt.Color(153, 153, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Statistique du matériel d'étudiant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        statistique_mat_etud.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CIN", "Nom matériel", "Nombre Emprunt"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(statistique_mat_etud);

        jPanel9.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 610, 172));

        stats.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 770, 230));

        jPanel15.setBackground(new java.awt.Color(153, 0, 0));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("recherche par catégorie :");
        jPanel15.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, 30));

        com_re2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        com_re2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "identifiant", " ", " " }));
        jPanel15.add(com_re2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 190, 30));

        txt_rech3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rech3ActionPerformed(evt);
            }
        });
        jPanel15.add(txt_rech3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 190, 30));

        jLabel44.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("");
        jPanel15.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 30, 40));

        jButton15.setBackground(new java.awt.Color(0, 0, 0));
        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("    Rechercher");
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 130, 40));

        stats.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 570, 180));

        jPanel16.setBackground(new java.awt.Color(204, 204, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.setOpaque(false);
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("recherche par identifiant de etudiant :");
        jPanel16.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 410, 30));

        txt_rech5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rech5ActionPerformed(evt);
            }
        });
        jPanel16.add(txt_rech5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 190, 30));

        jButton16.setBackground(new java.awt.Color(0, 0, 0));
        jButton16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("    Rechercher");
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 160, 40));

        stats.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, 430, 230));

        jLabel165.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(255, 255, 255));
        jLabel165.setText("");
        stats.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 10, 30, 40));

        jButton14.setBackground(new java.awt.Color(0, 0, 0));
        jButton14.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("   Actualiser");
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        stats.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 120, 40));

        jButton4.setText("graph");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        stats.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 410, 100, 30));

        jTabbedPane3.addTab("Statistiques", stats);

        retard.setBackground(new java.awt.Color(56, 68, 84));
        retard.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                retardAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        retard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(153, 153, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pretSensible.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id  materiel", "Date de retour"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane8.setViewportView(pretSensible);

        jPanel10.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 950, 210));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("      Prêts sensibles aux retards  ");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, -1, -1));

        retard.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 990, 300));

        jPanel11.setBackground(new java.awt.Color(153, 153, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        delaiRetour.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        delaiRetour.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nom", "Prenom"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(delaiRetour);

        jPanel11.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 800, 143));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Etudiants qui ne respect pas le délai des retours de prêts");
        jPanel11.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, -1, -1));

        retard.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 990, 210));

        jTabbedPane3.addTab("Retards et Prets sensibles", retard);

        jTabbedPane1.addTab("Gestion des comptes", jTabbedPane3);

        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setBackground(new java.awt.Color(153, 153, 255));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jTabbedPane2.setOpaque(true);
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(56, 68, 84));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(56, 68, 84));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        l4.setText("jLabel2");
        l4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l4MouseClicked(evt);
            }
        });
        jPanel5.add(l4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 80, 100));

        l2.setText("jLabel2");
        l2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l2MouseClicked(evt);
            }
        });
        jPanel5.add(l2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 80, 100));

        l3.setText("jLabel2");
        l3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l3MouseClicked(evt);
            }
        });
        jPanel5.add(l3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 80, 100));

        l8.setText("jLabel2");
        l8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l8MouseClicked(evt);
            }
        });
        jPanel5.add(l8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 80, 100));

        l9.setText("jLabel2");
        l9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l9MouseClicked(evt);
            }
        });
        jPanel5.add(l9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 80, 100));

        l5.setText("jLabel2");
        l5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l5MouseClicked(evt);
            }
        });
        jPanel5.add(l5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 80, 100));

        l10.setText("jLabel2");
        l10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l10MouseClicked(evt);
            }
        });
        jPanel5.add(l10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 80, 100));

        l13.setText("jLabel2");
        l13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l13MouseClicked(evt);
            }
        });
        jPanel5.add(l13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 80, 100));

        l14.setText("jLabel2");
        l14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l14MouseClicked(evt);
            }
        });
        jPanel5.add(l14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 80, 100));

        l15.setText("jLabel2");
        l15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l15MouseClicked(evt);
            }
        });
        jPanel5.add(l15, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 80, 100));

        l1.setText("jLabel2");
        l1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l1MouseClicked(evt);
            }
        });
        jPanel5.add(l1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 80, 100));

        l11.setText("jLabel2");
        l11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l11MouseClicked(evt);
            }
        });
        jPanel5.add(l11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 80, 100));

        l16.setText("jLabel2");
        l16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l16MouseClicked(evt);
            }
        });
        jPanel5.add(l16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 80, 100));

        l7.setText("jLabel2");
        l7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l7MouseClicked(evt);
            }
        });
        jPanel5.add(l7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 80, 100));

        l6.setText("jLabel2");
        l6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l6MouseClicked(evt);
            }
        });
        jPanel5.add(l6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 80, 100));

        l12.setText("jLabel2");
        l12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l12MouseClicked(evt);
            }
        });
        jPanel5.add(l12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 80, 100));

        l17.setText("jLabel2");
        l17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l17MouseClicked(evt);
            }
        });
        jPanel5.add(l17, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 80, 100));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane2.addTab("Vérifier la disponibilité", jPanel3);

        etudiant.setBackground(new java.awt.Color(56, 68, 84));
        etudiant.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                etudiantAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        etudiant.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Webdings", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("");
        etudiant.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 510, 30, 40));

        jLabel38.setFont(new java.awt.Font("Wingdings 2", 0, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("");
        etudiant.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 510, -1, 40));

        jLabel37.setFont(new java.awt.Font("Wingdings 2", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("");
        etudiant.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 510, 20, 40));

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Ajouter");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        etudiant.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 130, 40));

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Modifier");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        etudiant.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 130, 40));

        jButton8.setBackground(new java.awt.Color(0, 0, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Supprimer");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        etudiant.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 510, 130, 40));

        jButton9.setBackground(new java.awt.Color(0, 0, 0));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("  Rechercher");
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        etudiant.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 410, 140, 40));

        txt_rech.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rechActionPerformed(evt);
            }
        });
        etudiant.add(txt_rech, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 180, 30));

        com_re.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        com_re.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "identifiant", "nom", "prenom", "email", " ", " " }));
        etudiant.add(com_re, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, 190, 30));

        jLabel20.setBackground(new java.awt.Color(153, 153, 255));
        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 255));
        jLabel20.setText("recherche par catégorie :");
        etudiant.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 260, 210, 30));

        pp3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        pp3.setForeground(new java.awt.Color(0, 204, 51));
        etudiant.add(pp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 250, 50));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setOpaque(true);
        jLabel6.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel6AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        etudiant.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, 330, 200));

        txt_prenome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_prenomeActionPerformed(evt);
            }
        });
        etudiant.add(txt_prenome, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 180, 30));

        txt_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomeActionPerformed(evt);
            }
        });
        etudiant.add(txt_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 180, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nom :");
        etudiant.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 100, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Prénom :");
        etudiant.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 100, 30));

        jLabel162.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(255, 255, 255));
        jLabel162.setText("");
        etudiant.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 10, 30, 40));

        jButton12.setBackground(new java.awt.Color(0, 0, 0));
        jButton12.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("   Actualiser");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        etudiant.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, 120, 40));

        table_etudiant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "identifiant", "nom", "prenom", "email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_etudiant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_etudiantMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_etudiant);

        etudiant.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 1100, 150));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Email :");
        etudiant.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 100, 30));

        txt_emaile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emaileActionPerformed(evt);
            }
        });
        etudiant.add(txt_emaile, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 180, 30));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/image/digital-librar_1.png"))); // NOI18N
        jLabel22.setText("jLabel17");
        etudiant.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 240, 530, 410));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setPreferredSize(new java.awt.Dimension(1, 2));
        etudiant.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 220, 1370, 130));

        jTabbedPane2.addTab("Créer les comptes  des étudiants", etudiant);

        jPanel4.setBackground(new java.awt.Color(56, 68, 84));
        jPanel4.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel4AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nom Etudiant  :");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 200, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dates prend le matériel :");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 220, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nom du matériel :");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 220, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Durée de réservation:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 220, 30));
        jPanel4.add(ref_etudiant, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 160, 30));
        jPanel4.add(type_materiel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 160, 30));
        jPanel4.add(duree_reservation, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 160, 30));

        jLabel36.setFont(new java.awt.Font("Wingdings 2", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("");
        jPanel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 540, 20, 40));

        jLabel35.setFont(new java.awt.Font("Webdings", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("");
        jPanel4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 30, 40));

        jLabel33.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, 30, 40));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("       Crée un nouveaux étudiant ");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, 260, 40));

        Enregistrer.setBackground(new java.awt.Color(0, 0, 0));
        Enregistrer.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        Enregistrer.setForeground(new java.awt.Color(255, 255, 255));
        Enregistrer.setText("Enregister");
        Enregistrer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnregistrerActionPerformed(evt);
            }
        });
        jPanel4.add(Enregistrer, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, 140, 40));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Supprimer");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 480, 140, 40));

        table_mat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "nom", "nom", "prenom"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_mat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_matMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_mat);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 700, 130));

        jLabel163.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(255, 255, 255));
        jLabel163.setText("");
        jPanel4.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, 30, 40));

        jButton13.setBackground(new java.awt.Color(0, 0, 0));
        jButton13.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("   Actualiser");
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 10, 120, 40));

        pp1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        pp1.setForeground(new java.awt.Color(0, 204, 51));
        jPanel4.add(pp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 220, 30));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setOpaque(false);
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comptyyy.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        comptyyy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "identifiant", "nom_materiel", " " }));
        jPanel12.add(comptyyy, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 190, 30));

        txt_rech1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rech1ActionPerformed(evt);
            }
        });
        jPanel12.add(txt_rech1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 190, 30));

        jButton10.setBackground(new java.awt.Color(0, 0, 0));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("  Rechercher");
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 130, 40));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("recherche par catégorie :");
        jPanel12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, -1, 30));

        jPanel4.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 310, 430, 220));

        try {
            date_prend.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        date_prend.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date_prend.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel4.add(date_prend, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 160, 30));

        idemprunt.setBackground(new java.awt.Color(56, 68, 84));
        idemprunt.setForeground(new java.awt.Color(255, 204, 102));
        idemprunt.setBorder(null);
        jPanel4.add(idemprunt, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 160, 30));

        jTabbedPane2.addTab("Enregistrer des emprunts", jPanel4);

        jPanel2.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1410, 710));

        jTabbedPane1.addTab("Gestion d'emprunts", jPanel2);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setAutoscrolls(true);
        jPanel7.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel7AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setPreferredSize(new java.awt.Dimension(1, 2));
        jPanel7.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 330, 1360, 20));

        tab_emp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "identifiant", "id_etudiant", "nom_materiel", "date_prend", "date_rend", "duree_reservation", "statut"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_empMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tab_emp);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 1270, 220));

        jLabel164.setFont(new java.awt.Font("Webdings", 0, 24)); // NOI18N
        jLabel164.setForeground(new java.awt.Color(255, 255, 255));
        jLabel164.setText("");
        jPanel7.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 30, 40));

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Actualiser");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 10, 130, 40));

        jPanel13.setBackground(new java.awt.Color(56, 68, 84));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        compy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        compy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Non disponible" }));
        jPanel13.add(compy, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 180, 40));

        jLabel1.setBackground(new java.awt.Color(0, 51, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Statut :");
        jPanel13.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 90, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Modifier");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 150, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Dates rend le matériel :");
        jPanel13.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 220, 30));

        try {
            date_rend.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        date_rend.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel13.add(date_rend, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 180, 30));

        jPanel7.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 330));

        jPanel14.setBackground(new java.awt.Color(56, 68, 84));
        jPanel7.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 1340, 370));

        jTabbedPane1.addTab("Gestion des retours", jPanel7);
        jPanel7.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 1360, 760);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_etudiantMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_etudiantMouseClicked
        //txt_iden.setText(String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(),0)));
        txt_nome.setText(String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(),1)));
        txt_prenome.setText(String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(),2)));
        txt_emaile.setText(String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(),3)));
        
        
    }//GEN-LAST:event_table_etudiantMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        actualiseretudiant();
        tableetudiant();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txt_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomeActionPerformed

    private void txt_prenomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_prenomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_prenomeActionPerformed

    private void jLabel6AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel6AncestorAdded

    }//GEN-LAST:event_jLabel6AncestorAdded

    private void txt_rechActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rechActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rechActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (txt_rech.getText().equals("")  ) {
            JOptionPane.showMessageDialog(this, "SVP entrer vos donneés");
        } else {
            if (com_re.getSelectedItem().equals("identifiant")) {
                rs = db.querySelectAll("etudiant", "identifiant LIKE '%" + txt_rech.getText() + "%' ");
                table_etudiant.setModel(new Gestion_biblio(rs));
            } else if (com_re.getSelectedItem().equals("nom")) {
                rs = db.querySelectAll("etudiant", "nom LIKE '%" + txt_rech.getText() + "%' ");
                table_etudiant.setModel(new Gestion_biblio(rs));
            } else if (com_re.getSelectedItem().equals("prenom")) {
                rs = db.querySelectAll("etudiant", "prenom LIKE '%" + txt_rech.getText() + "%' ");
                table_etudiant.setModel(new Gestion_biblio(rs));
            } else if (com_re.getSelectedItem().equals("email")) {
                rs = db.querySelectAll("etudiant", "email LIKE '%" + txt_rech.getText() + "%' ");
                table_etudiant.setModel(new Gestion_biblio(rs));

            }
            
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

         if (txt_nome.getText().equals("") || txt_prenome.getText().equals("")
            || txt_emaile.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complètes");
        }else{
            String id = String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(), 0));
            if (JOptionPane.showConfirmDialog(this, "Etes-vous sûr que vous voulez supprimer ?", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                db.queryDelete("etudiant", "identifiant=" + id);
            } else {
                return;
            }
            tableetudiant();
            pp3.setText("Supprimer avec succès");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (txt_nome.getText().equals("") || txt_prenome.getText().equals("")|| txt_emaile.getText().equals("") 
        ) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complètes");
        } else {
            String[] colon = {"nom","prenom","email"};
            String[] inf = {txt_nome.getText(), txt_prenome.getText(), txt_emaile.getText()};
            String id = String.valueOf(table_etudiant.getValueAt(table_etudiant.getSelectedRow(), 0));
            System.out.println(db.queryUpdate("etudiant", colon, inf, "identifiant='" + id + "'"));
            tableetudiant();
            actualiseretudiant(); 
            pp3.setText("Modifié avec succès");}
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (  txt_nome.getText().equals("") || txt_prenome.getText().equals("")|| txt_emaile.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complètes");
        } else {
            String[] colon = {"nom","prenom","email"};
            String[] inf = {txt_nome.getText(), txt_prenome.getText(), txt_emaile.getText()};
            System.out.println(db.queryInsert("etudiant", colon, inf));
            tableetudiant();
            actualiseretudiant();
            pp3.setText("ajouter avec succès");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void EnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnregistrerActionPerformed
  
        if ( ref_etudiant.getText().equals("") || type_materiel.getText().equals("")|| duree_reservation.getText().equals("")||date_prend.getText().equals("")|| compy.getSelectedItem().equals("typppe")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complètes");
        } else {  
            String[] colon = {"id_etudiant", "id_materiel","date_prend","date_rend","duree_reservation","statut"};
            String[] inf = { ref_etudiant.getText(), type_materiel.getText(), duree_reservation.getText(),date_prend.getText(),compy.getSelectedItem().toString()};
            System.out.println(db.queryInsert("emprunts", colon, inf));
            tableemprunt();
            //actualiseretudiant();
            pp1.setText("ajouter avec succès");
        }        
    }//GEN-LAST:event_EnregistrerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       etudiant l=new etudiant();
        l.setVisible(true);
        this.dispose();

          
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel7AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel7AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7AncestorAdded

    private void jPanel4AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel4AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4AncestorAdded

    private void etudiantAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_etudiantAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_etudiantAncestorAdded

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     
        if (JOptionPane.showConfirmDialog(null, "confirmer la modification","modification !!!",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
        db.exécutionUpdate("UPDATE emprunts em,matériel m SET em.statut='"+compy.getSelectedItem().toString()+"',em.date_rend='"+date_rend.getText()+"',m.Statut='"+compy.getSelectedItem().toString()+"' where m.identifiant=em.id_materiel and  idemprunts = "+ idemprunt.getText());  
        tableemprunt();  
        }
                             
                
         
       
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tab_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_empMouseClicked
        idemprunt.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 0)));
        ref_etudiant.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 2)));
        type_materiel.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 1)));
        duree_reservation.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 6)));
        date_prend.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 4)));
        date_rend.setText(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 5)));
        compy.setSelectedItem(String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 7)));
    }//GEN-LAST:event_tab_empMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         if (ref_etudiant.getText().equals("") || type_materiel.getText().equals("") || duree_reservation.getText().equals("") || date_rend.getText().equals("") || date_prend.getText().equals("") 
            || compy.getSelectedItem().equals("Typpppe")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complètes");
        }else{
            String id = String.valueOf(tab_emp.getValueAt(tab_emp.getSelectedRow(), 0));
            if (JOptionPane.showConfirmDialog(this, "Etes-vous sûr que vous voulez supprimer ?", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                db.queryDelete("emprunts", "idemprunts=" + id);
            } else {
                return;
            }
            tableemprunt();
            pp1.setText("Supprimer avec succès");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mvtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_mvtPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_mvtPropertyChange

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         final int MAX_X = 450;
          final int MIN_X = 50;
          
          Thread animation = new Thread(new Runnable(){
              @Override
              public void run() {
                    int x = 20;
                    int y = 30; 
                    while (true){
                          mvt.setLocation(x, y);
                          x +=10;
                          
                          try{
                              Thread.sleep(200);
                          }catch(Exception e){
                              
                              
                          }
              }
                  
                  
                  
              }   
          });
          animation.start(); 
    }//GEN-LAST:event_formWindowOpened

    private void txt_emaileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emaileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emaileActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
       actualiseremprunt();
      
      table_empruntsssss();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void table_matMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_matMouseClicked
    ref_etudiant.setText(String.valueOf(table_mat.getValueAt(table_mat.getSelectedRow(), 1))); 
    type_materiel.setText(String.valueOf(table_mat.getValueAt(table_mat.getSelectedRow(), 0)));
    }//GEN-LAST:event_table_matMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
         if (txt_rech1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer vos donneés");
        } else {
            if (comptyyy.getSelectedItem().equals("identifiant")) {
                rs = db.exécutionQuery("SELECT m.nom_materiel,e.nom,e.prenom from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant and em.id_etudiant=" +txt_rech1.getText());
                // rs = db.exécutionQuery("SELECT m.nom_materiel,e.nom,e.prenom from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant and m.nom_materiel=" +txt_rech1.getText());
                table_mat.setModel(new Gestion_biblio(rs));   }
        }  
       
       
       
       
       
    }//GEN-LAST:event_jButton10ActionPerformed

    private void txt_rech1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rech1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rech1ActionPerformed

    private void l4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l4MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(3));
        jLabel3.setText("Statut du matériel : "+statut.get(3));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l4MouseClicked

    private void l2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l2MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(1));
        jLabel3.setText("Statut du matériel : "+statut.get(1));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l2MouseClicked

    private void l3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l3MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(2));
        jLabel3.setText("Statut du matériel : "+statut.get(2));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l3MouseClicked

    private void l8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l8MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(7));
        jLabel3.setText("Statut du matériel : "+statut.get(7));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l8MouseClicked

    private void l9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l9MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(8));
        jLabel3.setText("Statut du matériel : "+statut.get(8));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l9MouseClicked

    private void l5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l5MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(4));
        jLabel3.setText("Statut du matériel : "+statut.get(4));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l5MouseClicked

    private void l10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l10MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(9));
        jLabel3.setText("Statut du matériel : "+statut.get(9));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l10MouseClicked

    private void l13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l13MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(12));
        jLabel3.setText("Statut du matériel : "+statut.get(12));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l13MouseClicked

    private void l14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l14MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(13));
        jLabel3.setText("Statut du matériel : "+statut.get(13));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l14MouseClicked

    private void l15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l15MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(14));
        jLabel3.setText("Statut du matériel : "+statut.get(14));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l15MouseClicked

    private void l1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l1MouseClicked

        jLabel2.setText("\n Nom du matériel : "+nom.get(0));
        jLabel3.setText("Statut du matériel : "+statut.get(0));
        msg.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_l1MouseClicked

    private void l11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l11MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(10));
        jLabel3.setText("Statut du matériel : "+statut.get(10));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l11MouseClicked

    private void l16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l16MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(15));
        jLabel3.setText("Statut du matériel : "+statut.get(15));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l16MouseClicked

    private void l7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l7MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(6));
        jLabel3.setText("Statut du matériel : "+statut.get(6));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l7MouseClicked

    private void l6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l6MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(5));
        jLabel3.setText("Statut du matériel : "+statut.get(5));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l6MouseClicked

    private void l12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l12MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(11));
        jLabel3.setText("Statut du matériel : "+statut.get(11));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l12MouseClicked

    private void l17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l17MouseClicked
        jLabel2.setText("\n Nom du matériel : "+nom.get(16));
        jLabel3.setText("Statut du matériel : "+statut.get(16));
        msg.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_l17MouseClicked

    private void msgWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_msgWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_msgWindowClosed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       actualiserenmprr();
        tableemprunt(); 
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed

        LOGIN l=new LOGIN();
        l.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton33ActionPerformed

    private void table_etudiant1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_etudiant1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_etudiant1MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
      if (txt_rech2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer vos donneés");
        } else {
            if (com_re1.getSelectedItem().equals("identifiant")) {
                rs = db.exécutionQuery("SELECT m.nom_materiel,e.nom,e.prenom,em.date_prend,em.date_rend from etudiant e,matériel m,emprunts em  where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant and em.id_etudiant=" +txt_rech2.getText());
                table_etudiant1.setModel(new Gestion_biblio(rs));   }  }        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void txt_rech2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rech2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rech2ActionPerformed

    private void historiqueAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_historiqueAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_historiqueAncestorAdded

    private void statsAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_statsAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_statsAncestorAdded

    private void retardAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_retardAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_retardAncestorAdded

    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane3MouseClicked

    private void txt_rech3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rech3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rech3ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void txt_rech5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rech5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rech5ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
       if (txt_rech5.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer vos donneés");
        } else {
            
           
               rs = db.exécutionQuery("SELECT em.id_etudiant,e.nom,m.nom_materiel,count(id_materiel) nombre_de_matériel from emprunts em,matériel m,etudiant e where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant and em.id_etudiant="+txt_rech5.getText()+"  group by em.id_etudiant,m.nom_materiel ;" );
               statistique_mat_etud.setModel(new Gestion_biblio(rs));
           try {
               graph();
           } catch (SQLException ex) {
               Logger.getLogger(Principal_A.class.getName()).log(Level.SEVERE, null, ex);
           }
               
           
       }          
    }//GEN-LAST:event_jButton16ActionPerformed

    
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
     actualiseretudiantstatistique();
    // statistic_Materiel_etudiant();     
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       try {
           graph2();
           // TODO add your handling code here:
       } catch (SQLException ex) {
           Logger.getLogger(Principal_A.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_jButton4ActionPerformed
    public void graph() throws SQLException {
      ArrayList<String> s=new ArrayList<String>();
        ArrayList   t =new ArrayList();
        String s3="";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int count=0;
                   rs = db.exécutionQuery("SELECT em.id_etudiant,e.nom,m.nom_materiel,count(id_materiel) nombre_de_matériel from emprunts em,matériel m,etudiant e where m.identifiant=em.id_materiel and e.identifiant=em.id_etudiant and em.id_etudiant="+txt_rech5.getText()+"  group by em.id_etudiant,m.nom_materiel ;" );
                   while(rs.next()){ 
                       int s1=rs.getInt("nombre_de_matériel");
                  String s2 =rs.getString("m.nom_materiel");
                 s3 =rs.getString("e.nom");
                  s.add(s2);
                  t .add(s1);
                  count=count+1;
                   }
                   for(int i=0;i<count;i++){
                       dataset.setValue((int) t.get(i), "Nombre", s.get(i));
                   }
                  
                  
                  //dataset.setValue(s1, "Nombre", s2);
                  JFreeChart chart = ChartFactory.createBarChart(s3, "Nom du matériel","Nombre", dataset, PlotOrientation.VERTICAL,false,true,false);
                  CategoryPlot p = chart.getCategoryPlot();
                  p.setRangeGridlinePaint(Color.black);
                  
                  //jPanel8.add(chart);
                  ChartFrame frame = new ChartFrame("",chart);
                  frame.setVisible(true);
                  frame.setSize(350,250);
                  
               
    }
              public void statistic_Materiel() throws SQLException { 
        rs = db.exécutionQuery("SELECT id_materiel,m.nom_materiel,count(id_materiel) nombre_de_matéraux_les_plus_empruntés from emprunts em,matériel m where m.identifiant=em.id_materiel group by id_materiel,m.nom_materiel  HAVING count(id_materiel)>2;");
        statistique_mat.setModel(new Gestion_biblio(rs));  
       
       }
              public void graph2() throws SQLException{
                  
                   ArrayList<String> s=new ArrayList<String>();
        ArrayList   t =new ArrayList();
        String s3="";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int count=0;
                   rs= db.exécutionQuery("SELECT id_materiel,m.nom_materiel,count(id_materiel) nombre_de_matéraux_les_plus_empruntés from emprunts em,matériel m where m.identifiant=em.id_materiel group by id_materiel,m.nom_materiel  HAVING count(id_materiel)>2;");
                   while(rs.next()){ 
                       int s1=rs.getInt("nombre_de_matéraux_les_plus_empruntés");
                  String s2 =rs.getString("m.nom_materiel");
                 
                  s.add(s2);
                  t .add(s1);
                  count=count+1;
                   }
                   for(int i=0;i<count;i++){
                       dataset.setValue((int) t.get(i), "Nombre", s.get(i));
                   }
                  
                  
                  //dataset.setValue(s1, "Nombre", s2);
                  JFreeChart chart = ChartFactory.createBarChart("Statistiques des matériaux", "Nom du matériel","Nombre", dataset, PlotOrientation.VERTICAL,false,true,false);
                  CategoryPlot p = chart.getCategoryPlot();
                  p.setRangeGridlinePaint(Color.black);
                  
                  //jPanel8.add(chart);
                  ChartFrame frame = new ChartFrame("",chart);
                  frame.setVisible(true);
                  frame.setSize(350,250);
                   
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
            java.util.logging.Logger.getLogger(Principal_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal_A().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal_A.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Enregistrer;
    private javax.swing.JComboBox<String> com_re;
    private javax.swing.JComboBox<String> com_re1;
    private javax.swing.JComboBox<String> com_re2;
    private javax.swing.JComboBox<String> comptyyy;
    private javax.swing.JComboBox<String> compy;
    private javax.swing.JLabel date;
    private javax.swing.JFormattedTextField date_prend;
    private javax.swing.JFormattedTextField date_rend;
    private javax.swing.JTable delaiRetour;
    private javax.swing.JTextField duree_reservation;
    private javax.swing.JPanel etudiant;
    private javax.swing.JLabel heure;
    private javax.swing.JPanel historique;
    private javax.swing.JTextField idemprunt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l10;
    private javax.swing.JLabel l11;
    private javax.swing.JLabel l12;
    private javax.swing.JLabel l13;
    private javax.swing.JLabel l14;
    private javax.swing.JLabel l15;
    private javax.swing.JLabel l16;
    private javax.swing.JLabel l17;
    private javax.swing.JLabel l2;
    private javax.swing.JLabel l3;
    private javax.swing.JLabel l4;
    private javax.swing.JLabel l5;
    private javax.swing.JLabel l6;
    private javax.swing.JLabel l7;
    private javax.swing.JLabel l8;
    private javax.swing.JLabel l9;
    private javax.swing.JDialog msg;
    private javax.swing.JLabel mvt;
    private javax.swing.JLabel pp1;
    private javax.swing.JLabel pp3;
    private javax.swing.JTable pretSensible;
    private javax.swing.JTextField ref_etudiant;
    private javax.swing.JPanel retard;
    private javax.swing.JTable statistique_mat;
    private javax.swing.JTable statistique_mat_etud;
    private javax.swing.JPanel stats;
    private javax.swing.JTable tab_emp;
    private javax.swing.JTable table_etudiant;
    private javax.swing.JTable table_etudiant1;
    private javax.swing.JTable table_mat;
    private javax.swing.JTextField txt_emaile;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_prenome;
    private javax.swing.JTextField txt_rech;
    private javax.swing.JTextField txt_rech1;
    private javax.swing.JTextField txt_rech2;
    private javax.swing.JTextField txt_rech3;
    private javax.swing.JTextField txt_rech5;
    private javax.swing.JTextField type_materiel;
    // End of variables declaration//GEN-END:variables
}
