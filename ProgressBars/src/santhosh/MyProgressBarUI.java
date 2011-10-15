package santhosh;

import javax.swing.plaf.basic.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

// @author Santhosh Kumar - santhosh@in.fiorano.com
public class MyProgressBarUI extends BasicProgressBarUI implements ActionListener{
    private Color startColor = new Color(10, 36, 106);
    private Color endColor = new Color(192, 192, 192);

    private int x = 0, y = 0, delta = +1;
    private Timer timer = null;

    protected void startAnimationTimer() {
        if(timer==null)
            timer = new Timer(10, this);
        x = y = 0;
        delta = +1;
        timer.start();
    }

    protected void stopAnimationTimer() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent ae){
        // style1
        if(x==0)
            delta = +1;
        else if(x==progressBar.getWidth())
            delta = -1;
        x+=delta;

        progressBar.repaint();
    }

    public void paintIndeterminate(Graphics g, JComponent c) {
        if(delta > 0){
            GradientPaint redtowhite = new GradientPaint(0, 0, endColor, x, 0, startColor, false);
            ((Graphics2D)g).setPaint(redtowhite);
            ((Graphics2D)g).fill(new RoundRectangle2D.Double(0, 0, x, progressBar.getHeight() - 1, 0, 0));
        } else{
            GradientPaint redtowhite = new GradientPaint(x, 0, startColor, progressBar.getWidth(), 0, endColor, false);
            ((Graphics2D)g).setPaint(redtowhite);
            ((Graphics2D)g).fill(new RoundRectangle2D.Double(x, 0, progressBar.getWidth() - x, progressBar.getHeight() - 1, 0, 0));
        }
    }

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JProgressBar progressBar = new JProgressBar();
        JProgressBar myProgressBar = new JProgressBar();
        myProgressBar.setUI(new MyProgressBarUI());
        progressBar.setIndeterminate(true);
        myProgressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(progressBar, BorderLayout.NORTH);
        panel.add(myProgressBar, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(null, panel, "ProgressBars made intutive - santhosh@in.fiorano.com", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}