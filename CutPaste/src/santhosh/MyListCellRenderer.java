package santhosh;

import javax.swing.*;
import java.awt.*;

public class MyListCellRenderer extends DefaultListCellRenderer{
    ImageIcon icon = new ImageIcon(getClass().getResource("user.gif"));
    Icon ghostIcon = new ImageIcon(ImageUtil.createGhostImage(icon.getImage()));

    public Component getListCellRendererComponent(JList list, Object value
                                                  , int index, boolean isSelected
                                                  , boolean cellHasFocus){
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        ListClipboardOwner owner = (ListClipboardOwner)list.getClientProperty(ListClipboardOwner.CLIP_BOARD_OWNER);
        setIcon(owner!=null && owner.getIndex()==index ? ghostIcon : icon);

        return this;
    }
}
