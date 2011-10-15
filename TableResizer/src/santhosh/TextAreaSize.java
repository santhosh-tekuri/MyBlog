package santhosh;

import javax.swing.*;

public class TextAreaSize{
    public static void main(String[] args){
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);

        textArea.setText("santhosh fdjfkljkdfjkldjfkldjfkldjfkld " +
                "fjdklfjdklsfjkldfjklds fjdkjfkldjfkdl fjdkfjkdljfdkl" +
                " jfdkjflkdjfkld fjkldjfdklfjdlkfjdslf jfkldsjfkldsjflkd" +
                " fdsjfkldjsfkldjflkds santhosh");
        textArea.setSize(64, 1);
        System.err.println("preferred height for width 64 \t:"+textArea.getPreferredSize().height);
        textArea.setSize(100, 1);
        System.err.println("preferred height for width 100 \t:"+textArea.getPreferredSize().height);
        textArea.setSize(200, 1);
        System.err.println("preferred height for width 200 \t:"+textArea.getPreferredSize().height);
    }
}
