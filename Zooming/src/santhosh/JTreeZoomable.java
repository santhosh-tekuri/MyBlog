package santhosh;

import javax.swing.*;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author Santhosh Kumar T
 */
public class JTreeZoomable extends ComponentZoomable{
    public JTreeZoomable(Component comp){
        super(comp);
        JTree tree = (JTree)comp;

        BasicTreeUI ui = (BasicTreeUI)tree.getUI();
        ui.setExpandedIcon(new ScaledIcon(ui.getExpandedIcon()));
        ui.setCollapsedIcon(new ScaledIcon(ui.getCollapsedIcon()));

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)tree.getCellRenderer();
        renderer.setOpenIcon(new ScaledIcon(renderer.getOpenIcon()));
        renderer.setClosedIcon(new ScaledIcon(renderer.getClosedIcon()));
        renderer.setLeafIcon(new ScaledIcon(renderer.getLeafIcon()));
    }

    public void zoom(float factor){
        super.zoom(factor);
        JTree tree = (JTree)comp;
        BasicTreeUI ui = (BasicTreeUI)tree.getUI();
        ((ScaledIcon)ui.getExpandedIcon()).setFactor(factor);
        ((ScaledIcon)ui.getCollapsedIcon()).setFactor(factor);

        int rowHeight = tree.getRowHeight();
        int scaledRowHeight = apply(rowHeight, factor);
        if(scaledRowHeight>=1)
            tree.setRowHeight(scaledRowHeight);

        ui.setLeftChildIndent(apply(ui.getLeftChildIndent(), factor));
        ui.setRightChildIndent(apply(ui.getRightChildIndent(), factor));

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)tree.getCellRenderer();
        ((ScaledIcon)renderer.getOpenIcon()).setFactor(factor);
        ((ScaledIcon)renderer.getClosedIcon()).setFactor(factor);
        ((ScaledIcon)renderer.getLeafIcon()).setFactor(factor);
    }
}
