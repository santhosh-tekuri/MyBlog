package santhosh;

import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.Lookup;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

public class ExplorerPanel extends JPanel
        implements ExplorerManager.Provider, Lookup.Provider{

    private ExplorerManager manager;
    private Lookup lookup;

    public ExplorerPanel(){
        // same as before...
        manager = new ExplorerManager();
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false

        // ...but add e.g.:
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("control C"), DefaultEditorKit.copyAction);
        keys.put(KeyStroke.getKeyStroke("control X"), DefaultEditorKit.cutAction);
        keys.put(KeyStroke.getKeyStroke("control V"), DefaultEditorKit.pasteAction);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");

        // ...and initialization of lookup variable
        lookup = ExplorerUtils.createLookup(manager, map);
    }

    // ...method as before and getLookup
    public ExplorerManager getExplorerManager(){
        return manager;
    }

    public Lookup getLookup(){
        return lookup;
    }

    // ...methods as before, but replace componentActivated and
    // componentDeactivated with e.g.:
    public void addNotify(){
        super.addNotify();
        ExplorerUtils.activateActions(manager, true);
    }

    public void removeNotify(){
        ExplorerUtils.activateActions(manager, false);
        super.removeNotify();
    }
}