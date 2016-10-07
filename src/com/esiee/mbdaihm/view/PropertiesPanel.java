/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.dataaccess.wdi.RawWDIData;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.common.ModelListListener;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import com.esiee.mbdaihm.datamodel.listeners.SelectedCountryListener;
import com.esiee.mbdaihm.datamodel.model.TagModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PropertiesPanel extends javax.swing.JPanel implements SelectedCountryListener, ModelListListener<TagModel> {

    private List<Indicator> indicators = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();

    private static String year = "2012";

    private static final String WDI_FOLDER = "./data/WDI";

    public PropertiesPanel() {
        initComponents();
        DataManager.INSTANCE.addSelectedCountryListener(this);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        propertiesTextArea = new javax.swing.JTextArea();

        propertiesTextArea.setEditable(false);
        propertiesTextArea.setColumns(20);
        propertiesTextArea.setRows(5);
        jScrollPane1.setViewportView(propertiesTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea propertiesTextArea;

    @Override
    public void onSelectedCountryChange(Country country) {
        propertiesTextArea.setText(country.getName());
    }

    private void refresh(){
        //for (Indicator ind : indicators) {
            for (Country c : countries) {
                System.out.println(c.getName());
                List<RawWDIData> datas = WDIDataDecoder.decode(WDI_FOLDER, "PA.NUS.PPP.05");

                Optional<Double> opt = datas.stream()
                        .filter((RawWDIData rd) -> rd.countryCode.equals(c.getIsoCode()))
                        .map((RawWDIData rd) -> rd.getValueForYear(year))
                        .findFirst();
                opt.ifPresent(System.out::println);
            }
        //}
    }

    @Override
    public void onModelAdded(TagModel tag) {
        if (tag.isIndicator())
            indicators.add(tag.getAssociatedIndicator());
        else if (tag.isCountry())
            countries.add(tag.getAssociatedCountry());
        refresh();
    }

    @Override
    public void onModelRemoved(TagModel tag) {
        if (tag.isIndicator())
            indicators.remove(tag.getAssociatedIndicator());
        else if (tag.isCountry())
            countries.remove(tag.getAssociatedCountry());
        refresh();
    }
}
