import java.io.IOException;
import java.net.*;
import javax.swing.ButtonGroup;

public class GUI extends javax.swing.JFrame {

    public static javax.swing.JLabel jLabel1;
    // Variables declaration - do not modify
    public static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;

    private javax.swing.JButton jButton3;
    private static DatagramSocket sock=null;

    public static int CTRLPORT= 30000;
    public static DatagramPacket packet;
    public static VOIPee voice;

    // End of variables declaration

    public GUI() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setForeground(java.awt.Color.darkGray);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jButton1.setBackground(new java.awt.Color(0, 204, 51));
        jButton1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jButton1.setText("CALL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jButton2.setText("SET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setToolTipText("Type Your Caller/Callers IP Address");
        jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("CALLER'S IDs");

        jRadioButton1.setBackground(new java.awt.Color(102, 102, 102));
        jRadioButton1.setText("GROUP CALL");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("GROUP ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton2)
                                                                .addGap(14, 14, 14)
                                                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField1)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                                                        .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 26, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ONLINE");

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setLabelFor(jPanel2);
        jLabel2.setText("STATES:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(259, 259, 259)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>



    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // set
       States.grpID=jTextField1.getText();
        jButton2.setBackground(new java.awt.Color(0,201,51));






    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // call button
        try{
            if(States.isgroupcall){
                if(States.waitforcall) {
                    String temp = jTextField2.getText();
                    if (temp.isEmpty()) return;
                    voice.groupcall(temp);
                    States.waitforcall=false;
                    States.oncall=true;
                    States.changeState("oncall");
                    jButton1.setText("END CALL");
                    jButton1.setBackground(new java.awt.Color(255,51,51));
                   }
                else if(States.ringing){
                    States.ringtone.stop();
                    try {
                        //                if(sock.isClosed()) sock = new DatagramSocket(CTRLPORT);
                        States.socket.send(packet);
                        byte[] req = "change state".getBytes();
                        DatagramPacket pack = new DatagramPacket(req, req.length, packet.getAddress(), 30000);
                        States.socket.send(pack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   voice.answergroup();
                    jTextField2.setText(packet.getAddress().toString().substring(1));
                    jTextField2.setEditable(false);


                    jLabel1.setBackground(new java.awt.Color(0, 153, 0));
                    jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("ONCALL");
                    States.ringing = false;
                    States.oncall = true;
                    States.changeState("oncall");

                    jButton1.setText("END CALL");
                    jButton1.setBackground(new java.awt.Color(255,51,51));



                }
                else if(States.oncall){
                     voice.groupend();


                    jLabel1.setBackground(new java.awt.Color(0, 153, 0));
                    jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("CALL END");

                    States.oncall = false;
                    States.waitforcall = true;
                    States.changeState("waitforcall");
                    System.out.println(States.state);
                    Thread.sleep(1000);

                    jLabel1.setText("ONLINE");


                    jButton1.setText("CALL");
                    jButton1.setBackground(new java.awt.Color(0,204,51));

                    jTextField2.setEditable(true);

                }
            }
            else {


                if(States.waitforcall) {

                	try{
                	 System.out.println("my ip: "+InetAddress.getLocalHost().toString());
                	}
                	catch(UnknownHostException e){
                		
                	}


                    String temp = jTextField2.getText();
                    if (temp.isEmpty()) return;
                    voice.call(temp);
                    States.waitforcall=false;
                    States.oncall=true;
                    States.changeState("oncall");
                    jButton1.setText("END CALL");
                    jButton1.setBackground(new java.awt.Color(255,51,51));

                }
                else if(States.ringing){
                    States.ringtone.stop();
                    try {
                        //                if(sock.isClosed()) sock = new DatagramSocket(CTRLPORT);
                        States.socket.send(packet);
                        byte[] req = "change state".getBytes();
                        DatagramPacket pack = new DatagramPacket(req, req.length, packet.getAddress(), 30000);
                        States.socket.send(pack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    voice.answer(packet.getAddress().toString().substring(1));
                    jTextField2.setText(packet.getAddress().toString().substring(1));
                    jTextField2.setEditable(false);


                    jLabel1.setBackground(new java.awt.Color(0, 153, 0));
                    jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("ONCALL");
                    States.ringing = false;
                    States.oncall = true;
                    States.changeState("oncall");

                    jButton1.setText("END CALL");
                    jButton1.setBackground(new java.awt.Color(255,51,51));


                }
                else if(States.oncall){

                    try {
                        voice.end(jTextField2.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    jLabel1.setBackground(new java.awt.Color(0, 153, 0));
                    jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("CALL END");

                    States.oncall = false;
                    States.waitforcall = true;
                    States.changeState("waitforcall");
                    System.out.println(States.state);
                    Thread.sleep(1000);

                    jLabel1.setText("ONLINE");


                    jButton1.setText("CALL");
                    jButton1.setBackground(new java.awt.Color(0,201,51));

                    jTextField2.setEditable(true);



                }
            }

        }
        catch(SocketException e1){
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // grup id
        jButton1.setBackground(new java.awt.Color(255,51,51));


    }

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // grup call
        if(jRadioButton1.isSelected()){
            States.isgroupcall = true;

        }else{
            States.isgroupcall = false;

        }


    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {

        // caller id
    }

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        //speak



    }

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // listen



    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // try
    }



    public static void main(String args[]) throws SocketException {


        voice = new VOIPee();
        sock = new DatagramSocket(CTRLPORT);
        voice.StartStates("127.0.0.1", sock);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }


}
