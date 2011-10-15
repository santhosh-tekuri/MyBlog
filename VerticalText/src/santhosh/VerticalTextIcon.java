package santhosh;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.DefaultMetalTheme;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

public class VerticalTextIcon implements Icon, SwingConstants{
    private Font font = UIManager.getFont("Label.font");
    private FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);

    private String text = "";
    private int width, height;
    private boolean clockwize;

    public VerticalTextIcon(String text, boolean clockwize){
        this.text = text;
        width = SwingUtilities.computeStringWidth(fm, text);
        height = fm.getHeight();
        this.clockwize = clockwize;
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D)g;
        Font oldFont = g.getFont();
        Color oldColor = g.getColor();
        AffineTransform oldTransform = g2.getTransform();

        g.setFont(font);
        g.setColor(Color.black);
        if(clockwize){
            g2.translate(x+getIconWidth(), y);
            g2.rotate(Math.PI/2);
        }else{
            g2.translate(x, y+getIconHeight());
            g2.rotate(-Math.PI/2);
        }
        g.drawString(text, 0, fm.getLeading()+fm.getAscent());

        g.setFont(oldFont);
        g.setColor(oldColor);
        g2.setTransform(oldTransform);
    }

    public int getIconWidth(){
        return height;
    }

    public int getIconHeight(){
        return width;
    }


    public static JTabbedPane createTabbedPane(int tabPlacement){
        switch(tabPlacement){
            case JTabbedPane.LEFT:
            case JTabbedPane.RIGHT:
                Object textIconGap = UIManager.get("TabbedPane.textIconGap");
                Insets tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
                UIManager.put("TabbedPane.textIconGap", new Integer(1));
                UIManager.put("TabbedPane.tabInsets", new Insets(tabInsets.left, tabInsets.top, tabInsets.right, tabInsets.bottom));
                JTabbedPane tabPane = new JTabbedPane(tabPlacement);
                UIManager.put("TabbedPane.textIconGap", textIconGap);
                UIManager.put("TabbedPane.tabInsets", tabInsets);
                return tabPane;
            default:
                return new JTabbedPane(tabPlacement);
        }
    }

    public static void addTab(JTabbedPane tabPane, String text, Component comp){
        if(comp==null){
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.add(new JScrollPane(new JTree()));
            comp = panel;
        }
        int tabPlacement = tabPane.getTabPlacement();
        switch(tabPlacement){
            case JTabbedPane.LEFT:
            case JTabbedPane.RIGHT:
                tabPane.addTab(null, new VerticalTextIcon(text, tabPlacement==JTabbedPane.RIGHT), comp);
                return;
            default:
                tabPane.addTab(text, null, comp);
        }
    }

    private static void addTabs(JTabbedPane tabPane){
        tabPane.removeAll();
        addTab(tabPane, "Bookmarks", null);
        addTab(tabPane, "Thumbnails", null);
        addTab(tabPane, "Comments", null);
        addTab(tabPane, "Signature", null);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(new MotifLookAndFeel());
        } catch(Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Adobe like TabbbedPane - santhosh@in.fiorano.com");

        boolean left = true;
        final JTabbedPane tabPane = createTabbedPane(left ? JTabbedPane.LEFT : JTabbedPane.RIGHT);
        addTabs(tabPane);

        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(new JLabel("TabPlacement : "), BorderLayout.WEST);
        JComboBox combo = new JComboBox(new String[]{"Left", "Right"});
        comboPanel.add(combo);
        combo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tabPane.setTabPlacement(tabPane.getTabPlacement()==JTabbedPane.LEFT  ? JTabbedPane.RIGHT :JTabbedPane.LEFT );
                addTabs(tabPane);
            }
        });

        JPanel contents = (JPanel)frame.getContentPane();
        contents.add(comboPanel, BorderLayout.NORTH);
        contents.add(tabPane);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}