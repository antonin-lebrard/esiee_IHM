/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;


import com.esiee.mbdaihm.datamodel.model.TagModel;
import com.esiee.mbdaihm.datamodel.model.TagsModel;

public class TagPanel extends javax.swing.JPanel {

    private String tag = "";

    public TagModel getTagModel() {
        return tagModel;
    }

    private TagModel tagModel;

    public String getTag() {
        return tag;
    }

    public TagPanel(TagModel model){
        initComponents();
        tag = "";
        if (model.isCountry())
            tag = model.getAssociatedCountry().getIsoCode();
        if (model.isIndicator())
            tag = model.getAssociatedIndicator().getCode();
        tagModel = model;
    }

    public TagPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tagName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        tagName.setText("sdfsdfdsf");
        tagName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagNameActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\student\\Desktop\\remove-icon-614x460.png")); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tagName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tagName)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tagNameActionPerformed(java.awt.event.ActionEvent evt){}
    
    private void jLabel1MouseClicked(java.awt.event.MouseEvent e) {
        TagsModel.getInstance().remove(tagModel);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField tagName;
    // End of variables declaration//GEN-END:variables
}
