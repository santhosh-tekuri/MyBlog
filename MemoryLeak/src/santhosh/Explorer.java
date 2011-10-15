package santhosh;

import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;

/**
 * standalone ExplorerPanel implementation
 *
 * @author Santhosh Kumar T
 */
public class Explorer extends JPanel implements ExplorerManager.Provider, Lookup.Provider, HelpCtx.Provider {
    private ExplorerManager manager;
    private Lookup lookup;

    public Explorer() {
        setLayout(new BorderLayout());
        manager = new ExplorerManager();
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); ///NOI18N
        map.put("forceDelete", ExplorerUtils.actionDelete(manager, true)); //NOI18N

        // ...but add e.g.:
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("control C"), DefaultEditorKit.copyAction);   //NOI18N
        keys.put(KeyStroke.getKeyStroke("control X"), DefaultEditorKit.cutAction);    //NOI18N
        keys.put(KeyStroke.getKeyStroke("control V"), DefaultEditorKit.pasteAction);  //NOI18N
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");                         //NOI18N
        keys.put(KeyStroke.getKeyStroke("shift DELETE"), "forceDelete");              //NOI18N

        // ...and initialization of lookup variable
        lookup = ExplorerUtils.createLookup (manager, map);
    }

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void addNotify() {
        super.addNotify();
        ExplorerUtils.activateActions(manager, true);
    }

    public void removeNotify() {
        ExplorerUtils.activateActions(manager, false);
        super.removeNotify();
    }

    /*-------------------------------------------------[ HelpCtx ]---------------------------------------------------*/

    /** Get context help for an explorer window.
    * Looks at the manager's node selection.
    * @return the help context
    * @see #getHelpCtx(Node[],HelpCtx)
    */
    public HelpCtx getHelpCtx () {
        return getHelpCtx (getExplorerManager ().getSelectedNodes (), HelpCtx.DEFAULT_HELP);
    }

    /** Utility method to get context help from a node selection.
    * Tries to find context helps for selected nodes.
    * If there are some, and they all agree, uses that.
    * In all other cases, uses the supplied generic help.
    * @param sel a list of nodes to search for help in
    * @param def the default help to use if they have none or do not agree
    * @return a help context
    */
    public static HelpCtx getHelpCtx (Node[] sel, HelpCtx def) {
        HelpCtx result = null;
        for (int i = 0; i < sel.length; i++) {
            HelpCtx attempt = sel[i].getHelpCtx ();
            if (attempt != null && ! attempt.equals (HelpCtx.DEFAULT_HELP)) {
                if (result == null || result.equals (attempt)) {
                    result = attempt;
                } else {
                    // More than one found, and they conflict. Get general help on the Explorer instead.
                    result = null;
                    break;
                }
            }
        }
        if (result != null)
            return result;
        else
            return def;
    }
}
