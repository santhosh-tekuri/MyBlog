package santhosh;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public abstract class AutoCompleter{
    JList list = new JList();
    JPopupMenu popup = new JPopupMenu();
    JTextComponent textComp;
    private static final String AUTOCOMPLETER = "AUTOCOMPLETER"; //NOI18N

    public AutoCompleter(JTextComponent comp){
        textComp = comp;
        textComp.putClientProperty(AUTOCOMPLETER, this);
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);

        list.setFocusable( false );
        scroll.getVerticalScrollBar().setFocusable( false );
        scroll.getHorizontalScrollBar().setFocusable( false );

        popup.setBorder(BorderFactory.createLineBorder(Color.black));
        popup.add(scroll);

        KeyStroke downKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        defaultDownAction = textComp.getActionForKeyStroke(downKeyStroke);

        KeyStroke upKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        defaultUpAction = textComp.getActionForKeyStroke(upKeyStroke);

        if(textComp instanceof JTextField){
            textComp.registerKeyboardAction(showOrDownAction, downKeyStroke, JComponent.WHEN_FOCUSED);
            textComp.getDocument().addDocumentListener(documentListener);
        }else{
            textComp.registerKeyboardAction(showAction, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
            textComp.registerKeyboardAction(downAction, downKeyStroke, JComponent.WHEN_FOCUSED);
        }

        textComp.registerKeyboardAction(upAction, upKeyStroke, JComponent.WHEN_FOCUSED);
        textComp.registerKeyboardAction(hidePopupAction, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);

        popup.addPopupMenuListener(new PopupMenuListener(){
            public void popupMenuWillBecomeVisible(PopupMenuEvent e){
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
                textComp.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
            }

            public void popupMenuCanceled(PopupMenuEvent e){
            }
        });
        list.setRequestFocusEnabled(false);

        // Add mouse listener to allow user to make selection with the mouse
        list.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent arg0){
                hidePopup();
                acceptedListItem((String)list.getSelectedValue());
            }
        });
    }

    static Action acceptAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JComponent tf = (JComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            completer.hidePopup();
            completer.acceptedListItem((String)completer.list.getSelectedValue());
        }
    };

    DocumentListener documentListener = new DocumentListener(){
        public void insertUpdate(DocumentEvent e){
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    showPopup();
                }
            });
        }

        public void removeUpdate(DocumentEvent e){
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    showPopup();
                }
            });
        }

        public void changedUpdate(DocumentEvent e){}
    };

    private void showPopup(){
        if(!textComp.isShowing())
            return;
        popup.setVisible(false);
        if(textComp.isEnabled() && updateListData() && list.getModel().getSize()!=0){
            if(!(textComp instanceof JTextField))
                textComp.getDocument().addDocumentListener(documentListener);
            textComp.registerKeyboardAction(acceptAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
            int size = list.getModel().getSize();
            list.setVisibleRowCount(size<10 ? size : 10);

            try{
                int pos = Math.min(textComp.getCaret().getDot(), textComp.getCaret().getMark());
                Rectangle rect = textComp.getUI().modelToView(textComp, pos);
                popup.show(textComp, rect.x, rect.y+rect.height);
            } catch(BadLocationException e){
                // this should never happen!!!
                e.printStackTrace();
            }

        }else
            hidePopup();
        textComp.requestFocus();
    }

    private void hidePopup(){
        popup.setVisible(false);
        if(!(textComp instanceof JTextField))
            textComp.getDocument().removeDocumentListener(documentListener);
    }

    private ActionListener defaultDownAction;
    static Action showAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JComponent tf = (JComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            if(tf.isEnabled()){
                if(!completer.popup.isVisible())
                    completer.showPopup();
            }
        }
    };

    static Action downAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JComponent tf = (JComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            if(tf.isEnabled() && completer.popup.isVisible())
                completer.selectNextPossibleValue();
            else if(completer.defaultDownAction!=null)
                completer.defaultDownAction.actionPerformed(e);
        }
    };

    static Action showOrDownAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JComponent tf = (JComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            if(tf.isEnabled()){
                if(completer.popup.isVisible())
                    completer.selectNextPossibleValue();
                else
                    completer.showPopup();
            }
        }
    };

    private ActionListener defaultUpAction;
    static Action upAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JComponent tf = (JComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            if(tf.isEnabled()){
                if(completer.popup.isVisible())
                    completer.selectPreviousPossibleValue();
                else if(completer.defaultUpAction!=null)
                    completer.defaultUpAction.actionPerformed(e);
            }
        }
    };

    static Action hidePopupAction = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            JTextComponent tf = (JTextComponent)e.getSource();
            AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
            if(tf.isEnabled())
                completer.hidePopup();
        }
    };

    /**
     * Selects the next item in the classes.  It won't change the selection if the
     * currently selected item is already the last item.
     */
    protected void selectNextPossibleValue(){
        int si = list.getSelectedIndex();

        if(si < list.getModel().getSize() - 1){
            list.setSelectedIndex(si + 1);
            list.ensureIndexIsVisible(si + 1);
        }
    }

    /**
     * Selects the previous item in the classes.  It won't change the selection if the
     * currently selected item is already the first item.
     */
    protected void selectPreviousPossibleValue(){
        int si = list.getSelectedIndex();

        if(si > 0){
            list.setSelectedIndex(si - 1);
            list.ensureIndexIsVisible(si - 1);
        }
    }

    protected int getTokenStart(){
        return 0;
    }

    protected int getTokenEnd(){
        return textComp.getDocument().getLength();
    }

    // update classes model depending on the data in textfield
    protected abstract boolean updateListData();

    // user has selected some item in the classes. update textfield accordingly...
    protected abstract void acceptedListItem(String selected);

    public JList getJList(){
        return list;
    }
}