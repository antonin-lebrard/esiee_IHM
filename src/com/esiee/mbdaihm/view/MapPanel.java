package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.Geometry;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;



public class MapPanel extends JPanel
{

    private static int LON_RANGE = 360;

    private double zoom = 1.0;
    private double translateX = 0.0;
    private double translateY = 0.0;
    private double cursorX = 0.0;
    private double cursorY = 0.0;

    private Point2D.Double lastClickPoint = new Point2D.Double();

    private ArrayList<Boolean> isCountrySelected = new ArrayList<>();

    private AffineTransform curTransform;

    private MouseInputAdapter mouseController = new MouseInputAdapter() {
        private int precX = 0;
        private int precY = 0;
        public void mouseClicked(MouseEvent e) {
            lastClickPoint = mouseToWorld(e.getX(),e.getY());
            calculateSelectedCountry();
            repaint();
        }
        public void mousePressed(MouseEvent e) {
            precX = e.getX();
            precY = e.getY();
        }
        public void mouseDragged(MouseEvent e) {
            translateX += e.getX() - precX;
            translateY += e.getY() - precY;
            precX = e.getX();
            precY = e.getY();
            repaint();
        }
        public void mouseMoved(MouseEvent e) {
            cursorX = e.getX();
            cursorY = e.getY();
            //DrawingPanel.this.repaint();
        }
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (zoom < 1.1 && e.getWheelRotation() > 0)
                return;
            zoom *= 1 + -((double)e.getWheelRotation() / 5.0);
            repaint();
        }
    };

    public MapPanel() {
        super();
        this.setBackground(Color.white);
        this.addMouseWheelListener(mouseController);
        this.addMouseListener(mouseController);
        this.addMouseMotionListener(mouseController);

        for (Country c : DataManager.INSTANCE.getCountries()){
            Geometry o = c.getGeometry();
            isCountrySelected.add(false);
            for (com.esiee.mbdaihm.datamodel.countries.Polygon poly : o.getPolygons()){
                if (poly.points.length < 1)
                    continue;
                Path2D.Double path = new Path2D.Double();
                path.moveTo(poly.points[0].lon, poly.points[0].lat);
                for (int i = 1; i < poly.points.length; i++)
                    path.lineTo(poly.points[i].lon, poly.points[i].lat);
                path.lineTo(poly.points[0].lon, poly.points[0].lat);
                o.paths.add(path);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        curTransform = g2d.getTransform();
        curTransform.translate((getWidth() / 2.0) + translateX, (getHeight() / 2.0) + translateY);
        curTransform.scale(getFactorGeoWToWidth() * zoom, (-getFactorGeoWToWidth()) * zoom);
        g2d.setStroke(new BasicStroke(1.0f / (float)(getFactorGeoWToWidth() * zoom)));
        g2d.transform(curTransform);

        for (int i = 0; i < DataManager.INSTANCE.getCountries().size(); i++) {
            for (Path2D.Double path : DataManager.INSTANCE.getCountries().get(i).getGeometry().paths){
                if (isCountrySelected.get(i))
                    g2d.fill(path);
                else
                    g2d.draw(path);
            }
        }
    }

    private double getFactorGeoWToWidth(){
        return getWidth() / ((double)LON_RANGE);
    }

    private Point2D.Double mouseToWorld(double x, double y) {
        try {
            return (Point2D.Double) curTransform.inverseTransform(new Point2D.Double(x, y), new Point2D.Double());
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void calculateSelectedCountry(){
        for (int i = 0; i < isCountrySelected.size(); i++)
            isCountrySelected.set(i, false);
        for (int i = 0; i < DataManager.INSTANCE.getCountries().size(); i++) {
            for (int j = 0; j < DataManager.INSTANCE.getCountries().get(i).getGeometry().paths.size(); j++) {
                if (DataManager.INSTANCE.getCountries().get(i).getGeometry().paths.get(j).contains(lastClickPoint)) {
                    isCountrySelected.set(i, true);
                    DataManager.INSTANCE.setCountrySelected(DataManager.INSTANCE.getCountries().get(i));
                }
            }
        }
    }
}

