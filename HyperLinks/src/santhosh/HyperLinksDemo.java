package santhosh;

import javax.swing.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class HyperLinksDemo extends JFrame{
    public HyperLinksDemo() throws Exception{
        super("Hyperlinks Demo - santhosh@in.fiorano.com");
        JEditorPane editor = new JEditorPane();
        editor.setPage(getClass().getResource("rule.html"));
        editor.setEditable(false);

        ActionMap actionMap = new ActionMap();
        actionMap.put("selectPeople", new SelectPeopleAction());
        editor.addHyperlinkListener(new ActionBasedHyperlinkListener(actionMap));

        JPanel contents = (JPanel)getContentPane();
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contents.add(new JScrollPane(editor));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    public static void main(String[] args) throws Exception{
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        new HyperLinksDemo().setVisible(true);
    }
}