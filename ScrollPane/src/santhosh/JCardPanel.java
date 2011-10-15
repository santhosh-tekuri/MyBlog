package santhosh;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class JCardPanel extends JComponent implements ActionListener{
    JToolBar toolbar = new JToolBar();
    ButtonGroup bgroup = new ButtonGroup();

    public JCardPanel(){
        setLayout(new MyCardLayout());
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        toolbar.setMargin(new Insets(0, 0, 0, 0));
        toolbar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
    }

    /*-------------------------------------------------[ Containment ]---------------------------------------------------*/

    protected void addImpl(Component comp, Object constraints, int index){
        JToggleButton button = new JToggleButton((String)constraints);
        button.addActionListener(this);
        button.setMargin(new Insets(0, 0, 0, 0));
        bgroup.add(button);
        if(getComponentCount()!=0)
            toolbar.addSeparator();
        else
            button.setSelected(true);
        toolbar.add(button, index);
        super.addImpl(comp, constraints, index);
    }

    public void remove(int index){
        JToggleButton button = (JToggleButton)toolbar.getComponent(index+2);
        button.removeActionListener(this);
        toolbar.remove(button);
        if(index!=0)
            toolbar.remove(index*2);
        super.remove(index);
    }

    public void remove(Component comp){
        remove(Arrays.asList(getComponents()).indexOf(comp)*2);
    }

    public void removeAll(){
        toolbar.removeAll();
        super.removeAll();
    }

    /*-------------------------------------------------[ ActionListener ]---------------------------------------------------*/

    public void actionPerformed(ActionEvent e){
        ((CardLayout)getLayout()).show(this, e.getActionCommand());
    }

    /*-------------------------------------------------[ Realization ]---------------------------------------------------*/

    public void addNotify() {
        super.addNotify();
        configureEnclosingScrollPane();
    }

    public void removeNotify() {
        unconfigureEnclosingScrollPane();
        super.removeNotify();
    }

    /*-------------------------------------------------[ JScrollPane Related ]---------------------------------------------------*/

    protected void configureEnclosingScrollPane() {
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane)gp;
                scrollPane.setLayout(new MyScrollPaneLayout());
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }
                scrollPane.add(MyScrollPaneLayout.HORIZONTAL_LEFT, toolbar);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.getHorizontalScrollBar().setPreferredSize(toolbar.getPreferredSize());

                Border border = scrollPane.getBorder();
                if (border == null || border instanceof UIResource) {
                    scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray));
                }
            }
        }
    }

    protected void unconfigureEnclosingScrollPane() {
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane)gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }
                scrollPane.setColumnHeaderView(null);
            }
        }
    }

    /*-------------------------------------------------[ CardLayout ]---------------------------------------------------*/

    // why we need to subclass CardLayout
    // 1. we want to update the button selection when card is switched
    // 2. cardpanel's preferred size is maximum of all components preferred size
    //    in our implementation we want it to be same as the visible card's preferred
    //    size
    class MyCardLayout extends CardLayout{
        public void show(Container parent, String name){
            super.show(parent, name);
            updateToolbar();
        }

        public void first(Container parent){
            super.first(parent);
            updateToolbar();
        }

        public void next(Container parent){
            super.next(parent);
            updateToolbar();
        }

        public void previous(Container parent){
            super.previous(parent);
            updateToolbar();
        }

        public void last(Container parent){
            super.last(parent);
            updateToolbar();
        }

        private void updateToolbar(){
            int count = getComponentCount();
            for(int i = 0; i<count; i++){
                Component comp = getComponent(i);
                if(comp.isVisible()){
                    JToggleButton button = (JToggleButton)toolbar.getComponent(i*2);
                    button.setSelected(true);
                    return;
                }
            }
        }

        public Dimension preferredLayoutSize(Container parent){
            int count = getComponentCount();
            for(int i = 0; i<count; i++){
                Component comp = getComponent(i);
                if(comp.isVisible())
                    return comp.getPreferredSize();
            }
            return new Dimension(100, 100); // fallback
        }
    }

    /*-------------------------------------------------[ Test ]---------------------------------------------------*/

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

JCardPanel cardPanel = new JCardPanel();

URL url = JCardPanel.class.getResource("test.html");

try{
JEditorPane design = new JEditorPane(url);
cardPanel.add("Design", design);

JTextArea source = new JTextArea(design.getText());
source.setEditable(false);
cardPanel.add("Source", source);

JEditorPane preview = new JEditorPane(url);
preview.setEditable(false);
cardPanel.add("Preview", preview);
} catch(IOException e){
    e.printStackTrace();
}

        JFrame frame = new JFrame("JCardPanel - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contents = (JPanel)frame.getContentPane();
        contents.add(new JScrollPane(cardPanel));
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}