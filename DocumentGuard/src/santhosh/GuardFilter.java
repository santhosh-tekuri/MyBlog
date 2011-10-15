package santhosh;

import javax.swing.text.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.ArrayList;

public class GuardFilter extends DocumentFilter{
    ArrayList pos = new ArrayList();

    public void addGuardedBlock(Position start, Position end) throws BadLocationException{
        pos.add(new Position[]{ start, end });
    }

    private boolean isGuarded(int offset, int length){
        int u1 = offset, u2 = offset + length;
        for(int i=0; i<pos.size(); i++){
            Position p[] = (Position[])pos.get(i);
            int g1 = p[0].getOffset()-1, g2 = p[1].getOffset()+1;

            // u1, g1, u2, g2 --> guarded
            if(g1<u2 && u2<g2)
                return true;

            // u1, g1, g2, u2 --> guarded
            if(u1<g1 && g2<u2)
                return true;

            // u1/g1, g2, u2 --> guarded
            if(u1==g1 && g2<u2)
                return true;

            // u1, g1, u2/g2 --> guarded
            if(u1<g1 && u2==g2)
                return true;

            // g1, u1, g2, u2 --> guarded
            if(g1<u1 && u1<g2)
                return true;

            // u1/g1, u2/g2 --> guarded
            if(u1==g1 && u2==g2)
                return true;
        }
        return false;
    }

    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
        System.err.println("insert");
        for(int i=0; i<pos.size(); i++){
            Position p[] = (Position[])pos.get(i);
            if(offset>=p[0].getOffset() && offset<=p[1].getOffset())
                return;
        }
        super.insertString(fb, offset, string, attr);
    }

    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException{
        if(!isGuarded(offset, length))
            super.replace(fb, offset, length, text, attrs);
    }

    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
        if(!isGuarded(offset, length))
            super.remove(fb, offset, length);
    }
}
