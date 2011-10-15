package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Zooming {
    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        JTabbedPane tabPane = new JTabbedPane();

        tabPane.addTab("Test", new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                g.drawRect(100, 100, 100, 100);
                Graphics2D g2d = (Graphics2D)g;
                AffineTransform original = g2d.getTransform();
                AffineTransform transform = new AffineTransform();
                transform.scale(2, 2);
                Point2D src = new Point(50, 50);
                Point2D dst = new Point();
                transform.transform(src, dst);
                System.out.println(dst);
                g2d.setTransform(transform);
                g.setColor(Color.RED);
                g2d.drawLine(50, 60, 200, 200);
                g2d.drawRect(50, 60, 200, 200);
                g.setColor(Color.BLACK);
                g2d.setTransform(original);
            }
        });
        JTextArea textArea = new JTextArea("jflkdjflkdsjflskdjflkd");
        textArea.addMouseWheelListener(new ZoomingListener(new ComponentZoomable(textArea)));
        tabPane.addTab("JTextArea", new JScrollPane(textArea));

        JTable table = new JTable(){
            // overriden to make the height of scroll match viewpost height if smaller
            public boolean getScrollableTracksViewportHeight() {
                return getPreferredSize().height < getParent().getHeight();
            }            
        };
        String columns[] = new String[]{
            "Column1",
            "Column2",
            "Column3",
            "Column4",
        };
        String data[][] = new String[][]{
            { "test", "this is simple test", "This is really very long sentence!"},
            { "test", "this is simple test", "This is really very long sentence!"},
            { "test", "this is simple test", "This is really very long sentence!"},
        };
        table.setModel(new DefaultTableModel(data, columns));
        table.addMouseWheelListener(new ZoomingListener(new JTableZoomable(table)));
        tabPane.addTab("JTable", new JScrollPane(table));

        JTree tree = new JTree();
        tree.addMouseWheelListener(new ZoomingListener(new JTreeZoomable(tree)));
        tabPane.addTab("JTree", new JScrollPane(tree));

        JFrame frame = new JFrame();
        frame.getContentPane().add(tabPane);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}