// GameInfoPanel.java

package net.sf.gogui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.MessageFormat;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.sf.gogui.game.ConstClock;
import net.sf.gogui.game.ConstGameInfo;
import net.sf.gogui.game.ConstGameTree;
import net.sf.gogui.game.ConstNode;
import net.sf.gogui.game.Clock;
import net.sf.gogui.game.Game;
import net.sf.gogui.game.StringInfoColor;
import net.sf.gogui.go.BlackWhiteSet;
import net.sf.gogui.go.ConstBoard;
import net.sf.gogui.go.GoColor;
import static net.sf.gogui.go.GoColor.BLACK;
import static net.sf.gogui.go.GoColor.BLACK_WHITE;
import static net.sf.gogui.go.GoColor.WHITE_BLACK;
import static net.sf.gogui.gui.I18n.i18n;
import net.sf.gogui.util.StringUtil;




import java.awt.*;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import java.net.*;

/** Panel displaying information about the current position. */
public class GameInfoPanel
    extends JPanel

{

    static String status_panel;
    static String status_panel2;
    net.sf.gogui.gogui.IgsServer bb;
    public GameInfoPanel(Game game) 
    {
        setBorder(GuiUtil.createEmptyBorder());

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        add(main);
        main.setOpaque(false);


        JPanel abc = new JPanel();
        abc.setLayout(new BoxLayout(abc,BoxLayout.Y_AXIS));
        abc.setOpaque(false);

        java.awt.GridBagConstraints gridBagConstraints;
        jComboBox = new JComboBox();
        jComboBox2 = new JComboBox();
        JPanel panel_list = new JPanel();
        panel_list.setLayout(new BoxLayout(panel_list,BoxLayout.X_AXIS));
        panel_list.setOpaque(false);

        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Human","Pachi","Gnu Go","Goshi","KGS (Server)","IGS (Server)" }));
        jComboBox.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBox.setPreferredSize(new java.awt.Dimension(100, 25));
        panel_list.add(jComboBox);

        JLabel gap = new JLabel("  ");
        JLabel gap2 = new JLabel("   ");
        panel_list.add(gap);


        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Human","Pachi","Gnu Go","Goshi","KGS (Server)","IGS (Server)" }));
        jComboBox2.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBox2.setPreferredSize(new java.awt.Dimension(100, 25));
        panel_list.add(jComboBox2);

        abc.add(panel_list);
        abc.add(gap2);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, GuiUtil.PAD, GuiUtil.PAD));
        abc.add(panel);
        add(abc,BorderLayout.CENTER);
        //add(panel,BorderLayout.CENTER);
        m_game = game;        

        for (GoColor c : WHITE_BLACK)
        {
            Box box = Box.createVerticalBox();
            box.setOpaque(false);
            panel.add(box);
            panel.setOpaque(false);
            
            ImageIcon icon;
            if (c == BLACK)
                icon = GuiUtil.getIcon("gogui-black-32x32", i18n("LB_BLACK"));
            else
                icon = GuiUtil.getIcon("gogui-white-32x32", i18n("LB_WHITE"));

            Image image;

            URL url;
            ClassLoader classLoader = getClass().getClassLoader();

            if( c == BLACK)
                url = classLoader.getResource("net/sf/gogui/images/profile/mos.png");
            else
                url = classLoader.getResource("net/sf/gogui/images/profile/pachi-program-2.png");

            

            image = Toolkit.getDefaultToolkit().getImage(url);
            ImageIcon hi = GuiUtil.scaleToRound(80,80,image);
            JLabel thumb = new JLabel();
            thumb.setIcon(hi);

            JLabel labelIm = new JLabel();
            labelIm.setIcon(hi);

        	JPanel pane = new JPanel();
       		 
      		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            pane.add(labelIm);
      		pane.setOpaque(false);
            thumb.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel pane2 = new JPanel();
            JPanel pane3 = new JPanel();
            pane2.setOpaque(false);
            pane3.setOpaque(false);
       		
      		pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));
      		pane2.add(thumb);
      		box.add(pane2);
      
            box.setOpaque(false);
            box.add(GuiUtil.createFiller());
            m_clock.set(c, new GuiClock(c));
            m_clock.get(c).setAlignmentX(Component.CENTER_ALIGNMENT);
            box.add(m_clock.get(c));
            GoColor otherColor = c.otherColor();
            m_prisoners.set(otherColor, new Prisoners(otherColor));
            box.add(m_prisoners.get(otherColor));
        }


        Clock.Listener listener = new Clock.Listener() {
                public void clockChanged()
                {
                    SwingUtilities.invokeLater(m_updateTime);
                }
            };
        game.setClockListener(listener);


        main.add(abc,BorderLayout.NORTH);

    URL url;
    ClassLoader classLoader = getClass().getClassLoader();
    

        panel_info = new javax.swing.JPanel();
        panel_info_me = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panel_info_competitor = new javax.swing.JPanel();
        panel_profile = new javax.swing.JPanel();
        panel_profile_me = new javax.swing.JPanel();
        jTextField_me_name = new javax.swing.JTextField();
        jTextField_me_rank = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        panel_profile_icon = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panel_profile_competitor = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        panel_network = new javax.swing.JPanel();
        panel_network_info = new javax.swing.JPanel();
        panel_network_me = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jTextField_host_me = new javax.swing.JTextField();
        jTextField_port_me = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        panel_network_icon = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panel_network_competitor = new javax.swing.JPanel();
        jTextField_host_competitor = new javax.swing.JTextField();
        jTextField_port_competitor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        panel_login = new javax.swing.JPanel();
        panel_login_me = new javax.swing.JPanel();
        // jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        panel_login_icon = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panel_login_competitor = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        // jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        panel_room = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();

        panel_dummy0 = new javax.swing.JPanel();
        panel_dummy1 = new javax.swing.JPanel();
        panel_dummy2 = new javax.swing.JPanel();
        panel_dummy3 = new javax.swing.JPanel();

        //JPanel panel_main = new JPanel(new BorderLayout());
        JPanel panel_main = new JPanel();
        panel_main.setLayout(new BoxLayout(panel_main, BoxLayout.Y_AXIS));        
//*****HERE********
        main.add(panel_main, BorderLayout.CENTER);

        //Panel Information
        panel_info.setLayout(new java.awt.BorderLayout());
        panel_info_me.setPreferredSize(new java.awt.Dimension(175, 144));
        panel_info_me.setLayout(new java.awt.GridBagLayout());


//*****HERE********
        panel_main.add(panel_info);

        //Panel Profile
        panel_profile.setPreferredSize(new java.awt.Dimension(350, 120));
        panel_profile.setLayout(new java.awt.BorderLayout());
        panel_profile_me.setMinimumSize(new java.awt.Dimension(120, 60));
        panel_profile_me.setPreferredSize(new java.awt.Dimension(120, 60));
        panel_profile_me.setLayout(new java.awt.GridBagLayout());


        
        jTextField_me_name.setText("Name");
        jTextField_me_name = setJTextField(jTextField_me_name);

        jTextField_me_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_me_name.setText(jTextField_me_name.getText());
                jTextField_me_name.getCaret().setVisible(false);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panel_profile_me.add(jTextField_me_name, gridBagConstraints);

        jTextField_me_rank.setText("Rank");
        jTextField_me_rank = setJTextField(jTextField_me_rank);
        jTextField_me_rank.setBackground(new Color(250,250,250,70));
        jTextField_me_rank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_me_rank.setText(jTextField_me_rank.getText());
                jTextField_me_rank.getCaret().setVisible(false);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        panel_profile_me.add(jTextField_me_rank, gridBagConstraints);

//*****HERE********
        panel_profile.add(panel_profile_me, java.awt.BorderLayout.LINE_START);

        panel_profile_icon.setPreferredSize(new java.awt.Dimension(50, 100));
        panel_profile_icon.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("/home/parnmet/Desktop/gogui-with-jago/src/net/sf/gogui/images/yin-yang-hi.png")); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 100));
        panel_profile_icon.add(jLabel2, java.awt.BorderLayout.CENTER);

        panel_profile.add(panel_profile_icon, java.awt.BorderLayout.CENTER);
        panel_profile_competitor.setPreferredSize(new java.awt.Dimension(120, 40));
        panel_profile_competitor.setLayout(new java.awt.GridBagLayout());

        
        jTextField3 = setJTextField(jTextField3);
        jTextField3.setText("Name");
        // // PromptSupport.setPrompt("Name", jTextField3);
        // PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, jTextField3);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3.setText(jTextField3.getText());
                jTextField3.getCaret().setVisible(false);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panel_profile_competitor.add(jTextField3, gridBagConstraints);

        jTextField4.setText("Rank");
        jTextField4 = setJTextField(jTextField4);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4.setText(jTextField4.getText());
                jTextField4.getCaret().setVisible(false);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        panel_profile_competitor.add(jTextField4, gridBagConstraints);

        panel_profile.add(panel_profile_competitor, java.awt.BorderLayout.LINE_END);
        

        //panel_main.add(panel_profile,BorderLayout.CENTER);
        panel_main.add(panel_profile);

        // getContentPane().add(panel_profile, java.awt.BorderLayout.CENTER);

        panel_network.setPreferredSize(new java.awt.Dimension(350, 300));
        panel_network.setLayout(new java.awt.BorderLayout());

        panel_network_info.setPreferredSize(new java.awt.Dimension(350, 120));
        panel_network_info.setLayout(new java.awt.BorderLayout());

        panel_network_me.setPreferredSize(new java.awt.Dimension(120, 123));
        panel_network_me.setLayout(new java.awt.GridBagLayout());

        jTextField_host_me.setText("Host");
        jTextField_host_me = setJTextField(jTextField_host_me);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jTextField_host_me.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    net.sf.gogui.gogui.IgsServer aa = new net.sf.gogui.gogui.IgsServer();
                    String host = jTextField_host_me.getText();
                    aa.setHost(host);
                }
                catch(Exception e){}
            }
        });
        panel_network_me.add(jTextField_host_me, gridBagConstraints);

        jTextField_port_me.setText("Port");
        jTextField_port_me = setJTextField(jTextField_port_me);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jTextField_port_me.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    net.sf.gogui.gogui.IgsServer aa = new net.sf.gogui.gogui.IgsServer();
                    String port = jTextField_port_me.getText();
                    aa.setPort(port);
                }
                catch(Exception e){}
            }
        });
        panel_network_me.add(jTextField_port_me, gridBagConstraints);

//*****HERE********
        //Panel Server status
        panel_dummy0.setPreferredSize(new java.awt.Dimension(120, 123));
        panel_dummy0.setLayout(new java.awt.GridBagLayout());
        // panel_network_info.add(panel_dummy0, java.awt.BorderLayout.LINE_START);
        panel_network_info.add(panel_network_me, java.awt.BorderLayout.LINE_START);

        panel_network_icon.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("/home/parnmet/Desktop/gogui-with-jago/src/net/sf/gogui/images/power_lightning.png")); // NOI18N
        jLabel1.setMinimumSize(new java.awt.Dimension(60, 128));
        jLabel1.setPreferredSize(new java.awt.Dimension(60, 90));
        panel_network_icon.add(jLabel1, java.awt.BorderLayout.CENTER);

        panel_network_info.add(panel_network_icon, java.awt.BorderLayout.CENTER);

        panel_network_competitor.setPreferredSize(new java.awt.Dimension(120, 123));
        panel_network_competitor.setLayout(new java.awt.GridBagLayout());

        jTextField_host_competitor.setText("Host");
        jTextField_host_competitor = setJTextField(jTextField_host_competitor);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jTextField_host_competitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    net.sf.gogui.gogui.IgsServer aa = new net.sf.gogui.gogui.IgsServer();
                    String host = jTextField_host_competitor.getText();
                    aa.setHost(host);
                }
                catch(Exception e){}
            }
        });
        panel_network_competitor.add(jTextField_host_competitor, gridBagConstraints);

        jTextField_port_competitor.setText("Port");
        jTextField_port_competitor = setJTextField(jTextField_port_competitor);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jTextField_port_competitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    net.sf.gogui.gogui.IgsServer aa = new net.sf.gogui.gogui.IgsServer();
                    String port = jTextField_port_competitor.getText();
                    aa.setPort(port);
                }
                catch(Exception e){}
            }
        });
        panel_network_competitor.add(jTextField_port_competitor, gridBagConstraints);

        panel_network_info.add(panel_network_competitor, java.awt.BorderLayout.LINE_END);

//*****HERE********
        panel_network.add(panel_network_info, java.awt.BorderLayout.PAGE_START);

        panel_login.setPreferredSize(new java.awt.Dimension(350, 120));
        panel_login.setLayout(new java.awt.BorderLayout());

        panel_login_me.setPreferredSize(new java.awt.Dimension(120, 181));
        panel_login_me.setLayout(new java.awt.GridBagLayout());


        jTextField14.setText("User");
        jTextField14 = setJTextField(jTextField14);

        jPasswordField1.setText("Password");
        jPasswordField1 = setJTextField(jPasswordField1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        panel_login_me.add(jPasswordField1, gridBagConstraints);
        
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    bb = new net.sf.gogui.gogui.IgsServer();
                    String user = jTextField14.getText();
                    String pwd = String.valueOf(jPasswordField1.getPassword());
                    bb.connectToServer();
                    bb.move(user);
                    bb.move(pwd);
                }
                catch(Exception e){}
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panel_login_me.add(jTextField14, gridBagConstraints);

//*****HERE********
        panel_login.add(panel_login_me, java.awt.BorderLayout.LINE_START);

        panel_login_icon.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon("/home/parnmet/Desktop/gogui-with-jago/src/net/sf/gogui/images/Connection-512.png")); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_login_icon.add(jLabel3, java.awt.BorderLayout.CENTER);

        panel_login.add(panel_login_icon, java.awt.BorderLayout.CENTER);

        panel_login_competitor.setPreferredSize(new java.awt.Dimension(120, 181));
        panel_login_competitor.setLayout(new java.awt.GridBagLayout());

        jTextField11.setText("User");
        jTextField11 = setJTextField(jTextField11);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        
        panel_login_competitor.add(jTextField11, gridBagConstraints);

        jPasswordField2.setText("Password");
        jPasswordField2 = setJTextField(jPasswordField2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;

        jPasswordField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    net.sf.gogui.gogui.IgsServer aa = new net.sf.gogui.gogui.IgsServer();
                    String user = jTextField11.getText();
                    String pwd = String.valueOf(jPasswordField2.getPassword());
                    // String pwd = jTextField12.getText();
                    aa.setUser(user);
                    aa.setPassword(pwd);
                    aa.connectToServer();
                }
                catch(Exception e){}
            }
        });
        
        panel_login_competitor.add(jPasswordField2, gridBagConstraints);

        panel_login_competitor.add(jLabel16, gridBagConstraints);
        panel_login.add(panel_login_competitor, java.awt.BorderLayout.LINE_END);
        panel_network.add(panel_login, java.awt.BorderLayout.CENTER);

        panel_room.setPreferredSize(new java.awt.Dimension(350, 80));
        panel_room.setLayout(new java.awt.GridBagLayout());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Room");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        panel_room.add(jLabel4, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Computer Go", "Computer Go", "Computer Go", "Computer Go" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(170, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        panel_room.add(jComboBox1, gridBagConstraints);

        //jLabel17.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        panel_room.add(jLabel17, gridBagConstraints);

        panel_network.add(panel_room, java.awt.BorderLayout.PAGE_END);

        //getContentPane().add(panel_network, java.awt.BorderLayout.PAGE_END);

//*****HERE********
        //panel_main.add(panel_network,BorderLayout.SOUTH);
        panel_info.setOpaque(false);
        panel_info_me.setOpaque(false);
        panel_info_competitor.setOpaque(false);

        panel_profile_me.setOpaque(false);
        panel_profile_icon.setOpaque(false);
        panel_profile_competitor.setOpaque(false);
        panel_profile.setOpaque(false);

        panel_network_me.setOpaque(false);
        panel_network_icon.setOpaque(false);
        panel_network_competitor.setOpaque(false);
        panel_network_info.setOpaque(false);

        panel_login_me.setOpaque(false);
        panel_login_icon.setOpaque(false);
        panel_login_competitor.setOpaque(false);
        panel_login.setOpaque(false);

        
        panel_room.setOpaque(false);
        panel_network.setOpaque(false);
        panel_main.setOpaque(false);
        panel_main.add(panel_network);

        //pack();

        jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                
                // Image image;
                // URL url;
                // ClassLoader classLoader = getClass().getClassLoader();
                // url = classLoader.getResource("net/sf/gogui/images/profile/mos.png");
                JComboBox jComboBox = (JComboBox) event.getSource();
                Object selected = jComboBox.getSelectedItem();
                status_panel = (String)jComboBox.getSelectedItem();
                if(selected.toString().equals("Human")){
                    if(status_panel2 =="Human" || status_panel2 =="Pachi" || status_panel2 =="GNU Go" || status_panel2 =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);

                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/mos.png");
                        
                        panel_network_me.setVisible(false);
                        panel_login_me.setVisible(false);
                    
                }
                else if(selected.toString().equals("Pachi")){
                    if(status_panel2 =="Human" || status_panel2 =="Pachi" || status_panel2 =="GNU Go" || status_panel2 =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/pachi-program-2.png");
                    panel_network_me.setVisible(false);
                    panel_login_me.setVisible(false);

                }
                else if(selected.toString().equals("GNU Go")){
                    if(status_panel2 =="Human" || status_panel2 =="Pachi" || status_panel2 =="GNU Go" || status_panel2 =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/gnu_icon.png");
                    panel_network_me.setVisible(false);
                    panel_login_me.setVisible(false);
                }
                else if(selected.toString().equals("Goshi")){
                    if(status_panel2 =="Human" || status_panel2 =="Pachi" || status_panel2 =="GNU Go" || status_panel2 =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/gosoft.png");
                    panel_network_me.setVisible(false);
                    panel_login_me.setVisible(false);
                }
                else if(selected.toString().equals("KGS (Server)")){
                    // url = classLoader.getResource("net/sf/gogui/images/profile/kgs_icon.png");
                    jTextField_host_me.setText("goserver.gokgs.com");
                    jTextField_port_me.setText("2379");
                    Font font = new Font("Angsana New", Font.PLAIN,10);
                    jTextField_host_me.setFont(font);
                    panel_network_icon.setVisible(true);
                    panel_login_icon.setVisible(true);
                    panel_network_me.setVisible(true);
                    panel_login_me.setVisible(true);
                    panel_room.setVisible(true);

                }
                else if(selected.toString().equals("IGS (Server)")){
                    // url = classLoader.getResource("net/sf/gogui/images/profile/igs_icon.png");
                    jTextField_host_me.setText("igs.joyjoy.net");
                    jTextField_port_me.setText("6969");
                    Font font = new Font("Angsana New", Font.PLAIN,15);
                    jTextField_host_me.setFont(font);
                    panel_network_icon.setVisible(true);
                    panel_login_icon.setVisible(true);
                    panel_network_me.setVisible(true);
                    panel_login_me.setVisible(true);
                    panel_room.setVisible(true);
                }
                // image = Toolkit.getDefaultToolkit().getImage(url);
                // ImageIcon hi = GuiUtil.scaleToRound(80,80,image);
                // JLabel thumb = new JLabel();
                // thumb.setIcon(hi);
                // JLabel labelIm = new JLabel();
                // labelIm.setIcon(hi);
                // pane.add(labelIm);
            
            
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                
                // Image image;
                // URL url;
                // ClassLoader classLoader = getClass().getClassLoader();
                // url = classLoader.getResource("net/sf/gogui/images/profile/mos.png");
                JComboBox jComboBox = (JComboBox) event.getSource();
                Object selected = jComboBox.getSelectedItem();
                status_panel2 = (String)jComboBox.getSelectedItem();
                if(selected.toString().equals("Human")){
                    if(status_panel =="Human" || status_panel =="Pachi" || status_panel =="GNU Go" || status_panel =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/mos.png");
                    panel_network_competitor.setVisible(false);
                    panel_login_competitor.setVisible(false);
                }
                else if(selected.toString().equals("Pachi")){
                    if(status_panel =="Human" || status_panel =="Pachi" || status_panel =="GNU Go" || status_panel =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/pachi-program-2.png");
                    panel_network_competitor.setVisible(false);
                    panel_login_competitor.setVisible(false);

                }
                else if(selected.toString().equals("GNU Go")){
                    if(status_panel =="Human" || status_panel =="Pachi" || status_panel =="GNU Go" || status_panel =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/gnu_icon.png");
                    panel_network_competitor.setVisible(false);
                    panel_login_competitor.setVisible(false);
                }
                else if(selected.toString().equals("Goshi")){
                    if(status_panel =="Human" || status_panel =="Pachi" || status_panel =="GNU Go" || status_panel =="Goshi" ){
                        panel_network_icon.setVisible(false);
                        panel_login_icon.setVisible(false);
                        panel_room.setVisible(false);
                    }
                    // url = classLoader.getResource("net/sf/gogui/images/profile/gosoft.png");
                    panel_network_competitor.setVisible(false);
                    panel_login_competitor.setVisible(false);
                }
                else if(selected.toString().equals("KGS (Server)")){
                    // url = classLoader.getResource("net/sf/gogui/images/profile/kgs_icon.png");
                    jTextField_host_competitor.setText("goserver.gokgs.com");
                    jTextField_port_competitor.setText("2379");
                    Font font = new Font("Angsana New", Font.PLAIN,10);
                    jTextField_host_competitor.setFont(font);
                    panel_network_icon.setVisible(true);
                    panel_login_icon.setVisible(true);
                    panel_network_competitor.setVisible(true);
                    panel_login_competitor.setVisible(true);
                    panel_room.setVisible(true);

                }
                else if(selected.toString().equals("IGS (Server)")){
                    // url = classLoader.getResource("net/sf/gogui/images/profile/igs_icon.png");
                    jTextField_host_competitor.setText("igs.joyjoy.net");
                    jTextField_port_competitor.setText("6969");
                    Font font = new Font("Angsana New", Font.PLAIN,15);
                    jTextField_host_competitor.setFont(font);
                    panel_network_icon.setVisible(true);
                    panel_login_icon.setVisible(true);
                    panel_network_competitor.setVisible(true);
                    panel_login_competitor.setVisible(true);
                    panel_room.setVisible(true);
                }
                // image = Toolkit.getDefaultToolkit().getImage(url);
                // ImageIcon hi = GuiUtil.scaleToRound(80,80,image);
                // JLabel thumb = new JLabel();
                // thumb.setIcon(hi);
                // JLabel labelIm = new JLabel();
                // labelIm.setIcon(hi);
                // pane.add(labelIm);
            
            
            }
        });
    }

///////////////////

    public void move(String eiei){

        bb.move(eiei);
    }




    public void update()
    {
        ConstBoard board = m_game.getBoard();
        ConstNode node = m_game.getCurrentNode();
        ConstGameTree tree = m_game.getTree();
        ConstGameInfo info = tree.getGameInfoConst(node);
        for (GoColor c : BLACK_WHITE)
        {
            String name = info.get(StringInfoColor.NAME, c);
            String rank = info.get(StringInfoColor.RANK, c);
            //updatePlayerToolTip(m_icon.get(c), name, rank, c);
            m_prisoners.get(c).setCount(board.getCaptured(c));
            updateTimeFromClock(m_game.getClock(), c);
        }
    }

    private class UpdateTimeRunnable
        implements Runnable
    {
        public void run()
        {
            for (GoColor c : BLACK_WHITE)
                updateTimeFromClock(m_game.getClock(), c);
        }
    }

    private final BlackWhiteSet<GuiClock> m_clock
        = new BlackWhiteSet<GuiClock>();

    private final BlackWhiteSet<JLabel> m_icon
        = new BlackWhiteSet<JLabel>();

    private final BlackWhiteSet<Prisoners> m_prisoners
        = new BlackWhiteSet<Prisoners>();

    private final Game m_game;

    private final UpdateTimeRunnable m_updateTime = new UpdateTimeRunnable();

    private void updatePlayerToolTip(JLabel label, String player, String rank,
                                     GoColor color)
    {
        assert color.isBlackWhite();
        StringBuilder buffer = new StringBuilder(128);
        if (color == BLACK)
            buffer.append(i18n("TT_INFOPANEL_PLAYER_BLACK"));
        else
            buffer.append(i18n("TT_INFOPANEL_PLAYER_WHITE"));
        buffer.append(" (");
        if (StringUtil.isEmpty(player))
            buffer.append(i18n("TT_INFOPANEL_UNKNOWN_NAME"));
        else
        {
            buffer.append(player);
            if (! StringUtil.isEmpty(rank))
            {
                buffer.append(' ');
                buffer.append(rank);
            }
        }
        buffer.append(')');
        label.setToolTipText(buffer.toString());
    }

    private void updateTimeFromClock(ConstClock clock, GoColor c)
    {
        assert c.isBlackWhite();
        String text = clock.getTimeString(c);
        m_clock.get(c).setText(text);
    }

    public JTextField setJTextField(javax.swing.JTextField textField) {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new java.awt.Dimension(120, 25));
        textField.setMinimumSize(new java.awt.Dimension(120, 25));
        textField.setBackground(new Color(250,250,250,70));
        textField.setBorder(null);
        return textField;
    }

    public JPasswordField setJTextField(javax.swing.JPasswordField textField) {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new java.awt.Dimension(120, 25));
        textField.setMinimumSize(new java.awt.Dimension(120, 25));
        textField.setBackground(new Color(250,250,250,70));
        textField.setBorder(null);
        return textField;
    }
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JPanel panel_network_me;
    private javax.swing.JPanel panel_network_competitor;
    private javax.swing.JPanel panel_network_icon;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    // private javax.swing.JTextField jTextField12;
    // private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField_host_me;
    private javax.swing.JTextField jTextField_port_me;
    private javax.swing.JTextField jTextField_host_competitor;
    private javax.swing.JTextField jTextField_port_competitor;
    private javax.swing.JTextField jTextField_me_name;
    private javax.swing.JTextField jTextField_me_rank;
    private javax.swing.JPanel panel_profile_icon;
    private javax.swing.JPanel panel_info_competitor;
    private javax.swing.JPanel panel_login_competitor;
    private javax.swing.JPanel panel_profile_competitor;
    private javax.swing.JPanel panel_info;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPanel panel_login_icon;
    private javax.swing.JPanel panel_info_me;
    private javax.swing.JPanel panel_login_me;
    private javax.swing.JPanel panel_profile_me;
    private javax.swing.JPanel panel_profile;
    private javax.swing.JPanel panel_room;
    private javax.swing.JPanel panel_network;
    private javax.swing.JPanel panel_network_info;
    private javax.swing.JComboBox jComboBox;
    private javax.swing.JComboBox jComboBox2;  
    private javax.swing.JPanel panel_dummy0;
    private javax.swing.JPanel panel_dummy1;
    private javax.swing.JPanel panel_dummy2;
    private javax.swing.JPanel panel_dummy3;
}

class GuiClock
    extends JTextField
{
    public GuiClock(GoColor color)
    {
        super(COLUMNS);
        GuiUtil.setEditableFalse(this);
        setHorizontalAlignment(SwingConstants.CENTER);
        setMinimumSize(getPreferredSize());
        m_color = color;
        setText("00:00");
        setOpaque(false);
    }

    public final void setText(String text)
    {
        super.setText(text);
        String toolTip;
        if (m_color == BLACK)
            toolTip = i18n("TT_INFOPANEL_TIME_BLACK");
        else
            toolTip = i18n("TT_INFOPANEL_TIME_WHITE");
        if (text.length() > COLUMNS)
            toolTip = toolTip + " (" + text + ")";
        setToolTipText(toolTip);
    }

    private static final int COLUMNS = 8;

    private final GoColor m_color;
}

class Prisoners
    extends JPanel
{
    public Prisoners(GoColor color)
    {
        m_color = color;
        Icon icon;
        if (color == BLACK)
            icon = GuiUtil.getIcon("gogui-black-16x16", i18n("LB_BLACK"));
        else
            icon = GuiUtil.getIcon("gogui-white-16x16", i18n("LB_WHITE"));
        JLabel labelStone = new JLabel(icon);
        add(labelStone, BorderLayout.WEST);
        m_text = new JLabel();
        add(m_text, BorderLayout.CENTER);
        setCount(0);
        ////////////
        setOpaque(false);
        // setBackground(new Color(250,250,250,70));
    }

    public final void setCount(int n)
    {
        m_text.setText(Integer.toString(n));
        String tip;
        if (m_color == BLACK)
        {
            if (n == 1)
                tip = i18n("TT_INFOPANEL_PRISONER_BLACK_ONE");
            else
                tip = MessageFormat.format(i18n("TT_INFOPANEL_PRISONER_BLACK"), n);
        }
        else
        {
            if (n == 1)
                tip = i18n("TT_INFOPANEL_PRISONER_WHITE_ONE");
            else
                tip = MessageFormat.format(i18n("TT_INFOPANEL_PRISONER_WHITE"), n);
        }
        setToolTipText(tip);
    }

    private final JLabel m_text;

    private final GoColor m_color;

  
}

class AlphaContainer extends JComponent
{
    private JComponent component;

    public AlphaContainer(JComponent component)
    {
        this.component = component;
        setLayout( new BorderLayout() );
        setOpaque( false );
        component.setOpaque( false );
        add( component );
    }

    /**
     *  Paint the background using the background Color of the
     *  contained component
     */
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor( component.getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}