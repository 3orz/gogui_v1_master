// GoGuiMenuBar.java

package net.sf.gogui.gogui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import static net.sf.gogui.gogui.I18n.i18n;
import net.sf.gogui.gui.GuiMenu;
import net.sf.gogui.gui.Bookmark;
import net.sf.gogui.gui.Program;
import net.sf.gogui.gui.RecentFileMenu;
import net.sf.gogui.util.Platform;


//-----------------------------------------------

import javax.swing.ImageIcon;
import net.sf.gogui.gui.GuiUtil;
import net.sf.gogui.gui.GuiAction;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.Action;

import javax.swing.Icon;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//-----------------------------------------------




/** Menu bar for GoGui. */
public class GoGuiMenuBar
    extends JMenuBar
{
    /** Listener to events that are not handled by GoGuiActions. */
    public interface Listener
    {
        void actionGotoBookmark(int i);

        void actionAttachProgram(int i);
    }

    public GoGuiMenuBar(GoGuiActions actions,
                        RecentFileMenu.Listener recentListener,
                        RecentFileMenu.Listener recentGtpListener,
                        GoGuiMenuBar.Listener bookmarkListener)
    {
        m_listener = bookmarkListener;
        add(createMenuFile(actions, recentListener));
        add(createMenuGame(actions));
        add(createMenuProgram(actions));
        add(createMenuGo(actions));
        add(createMenuEdit(actions));
        add(createMenuView(actions));
        m_menuBookmarks = createMenuBookmarks(actions);
        add(m_menuBookmarks);
        add(createMenuTools(actions, recentGtpListener));
        add(createMenuHelp(actions));
    }

    public void addRecent(File file)
    {
        try
        {
            File canonicalFile = file.getCanonicalFile();
            if (canonicalFile.exists())
                file = canonicalFile;
        }
        catch (IOException e)
        {
        }
        m_recent.add(file);
    }

    public void addRecentGtp(File file)
    {
        try
        {
            File canonicalFile = file.getCanonicalFile();
            if (canonicalFile.exists())
                file = canonicalFile;
        }
        catch (IOException e)
        {
        }
        m_recentGtp.add(file);
    }

    public void setBookmarks(ArrayList<Bookmark> bookmarks)
    {
        for (int i = 0; i < m_bookmarkItems.size(); ++i)
            m_menuBookmarks.remove(m_bookmarkItems.get(i));
        if (m_bookmarksSeparator != null)
        {
            m_menuBookmarks.remove(m_bookmarksSeparator);
            m_bookmarksSeparator = null;
        }
        if (bookmarks.isEmpty())
            return;
        m_bookmarksSeparator = new JSeparator();
        m_menuBookmarks.add(m_bookmarksSeparator);
        for (int i = 0; i < bookmarks.size(); ++i)
        {
            Bookmark bookmark = bookmarks.get(i);
            JMenuItem item = new JMenuItem(bookmark.m_name);
            final int bookmarkIndex = i;
            item.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        m_listener.actionGotoBookmark(bookmarkIndex);
                    }
                });
            if (bookmark.m_file != null)
            {
                StringBuilder toolTip = new StringBuilder(256);
                toolTip.append(bookmark.m_file);
                if (bookmark.m_move > 0)
                {
                    toolTip.append(" (move ");
                    toolTip.append(bookmark.m_move);
                    toolTip.append(')');
                }
                if (! bookmark.m_variation.trim().equals(""))
                {
                    toolTip.append(" (variation ");
                    toolTip.append(bookmark.m_variation);
                    toolTip.append(')');
                }
                item.setToolTipText(toolTip.toString());
            }
            m_menuBookmarks.add(item);
            m_bookmarkItems.add(item);
        }
    }

    public void setPrograms(ArrayList<Program> programs)
    {
        m_menuAttach.setEnabled(! programs.isEmpty());
        for (int i = 0; i < m_programItems.size(); ++i)
            m_menuAttach.remove(m_programItems.get(i));
        if (programs.isEmpty())
            return;
        for (int i = 0; i < programs.size(); ++i)
        {
            Program program = programs.get(i);
            String[] mnemonicArray =
                { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
                  "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                  "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
            String text;
            String mnemonic;
            if (! Platform.isMac() && i < mnemonicArray.length)
            {
                mnemonic = mnemonicArray[i];
                text = mnemonic + ": " + program.m_label;
            }
            else
            {
                mnemonic = "";
                text = program.m_label;
            }
            JMenuItem item = new JMenuItem(text);
            if (! mnemonic.equals(""))
            {
                KeyStroke keyStroke = KeyStroke.getKeyStroke(mnemonic);
                int code = keyStroke.getKeyCode();
                item.setMnemonic(code);
            }
            final int index = i;
            item.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        m_listener.actionAttachProgram(index);
                    }
                });
            StringBuilder toolTip = new StringBuilder(128);
            if (program.m_name != null)
                toolTip.append(program.m_name);
            if (program.m_version != null && ! program.m_version.equals("")
                && program.m_version.length() < 40)
            {
                toolTip.append(' ');
                toolTip.append(program.m_version);
            }
            if (program.m_command != null)
            {
                toolTip.append(" (");
                toolTip.append(program.m_command);
                toolTip.append(')');
            }
            item.setToolTipText(toolTip.toString());
            m_menuAttach.add(item);
            m_programItems.add(item);
        }
    }

    public void update(boolean isProgramAttached, boolean isTreeShown,
                       boolean isShellShown)
    {
        if (isProgramAttached)
            m_recentGtp.updateEnabled();
        else
            m_recentGtp.getMenu().setEnabled(false);
        m_recent.updateEnabled();
        m_computerColor.setEnabled(isProgramAttached);
        m_menuViewTree.setEnabled(isTreeShown);
        m_menuViewShell.setEnabled(isShellShown);
    }

    private final Listener m_listener;

    private final GuiMenu m_menuBookmarks;

    private GuiMenu m_menuAttach;

    private GuiMenu m_menuViewTree;

    private GuiMenu m_menuViewShell;

    private JSeparator m_bookmarksSeparator;

    private RecentFileMenu m_recent;

    private RecentFileMenu m_recentGtp;

    private final ArrayList<JMenuItem> m_bookmarkItems
        = new ArrayList<JMenuItem>();

    private final ArrayList<JMenuItem> m_programItems
        = new ArrayList<JMenuItem>();

    private GuiMenu m_computerColor;

    private GuiMenu createBoardSizeMenu(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_BOARDSIZE"));
        ButtonGroup group = new ButtonGroup();
        menu.addRadioItem(group, actions.m_actionBoardSize9);
        menu.addRadioItem(group, actions.m_actionBoardSize11);
        menu.addRadioItem(group, actions.m_actionBoardSize13);
        menu.addRadioItem(group, actions.m_actionBoardSize15);
        menu.addRadioItem(group, actions.m_actionBoardSize17);
        menu.addRadioItem(group, actions.m_actionBoardSize19);
        menu.addRadioItem(group, actions.m_actionBoardSizeOther);
        return menu;
    }

    private GuiMenu createClockMenu(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_CLOCK"));
        menu.add(actions.m_actionClockStart);
        menu.add(actions.m_actionClockHalt);
        menu.add(actions.m_actionClockResume);
        menu.add(actions.m_actionSetTimeLeft);
        return menu;
    }

    private GuiMenu createComputerColorMenu(GoGuiActions actions)
    {
        ButtonGroup group = new ButtonGroup();
        GuiMenu menu = new GuiMenu(i18n("MEN_COMPUTER_COLOR"));
        menu.addRadioItem(group, actions.m_actionComputerBlack);
        menu.addRadioItem(group, actions.m_actionComputerWhite);
        menu.addRadioItem(group, actions.m_actionComputerBoth);
        menu.addRadioItem(group, actions.m_actionComputerNone);
        return menu;
    }

    private GuiMenu createHandicapMenu(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_HANDICAP"));
        ButtonGroup group = new ButtonGroup();
        menu.addRadioItem(group, actions.m_actionHandicapNone);
        menu.addRadioItem(group, actions.m_actionHandicap2);
        menu.addRadioItem(group, actions.m_actionHandicap3);
        menu.addRadioItem(group, actions.m_actionHandicap4);
        menu.addRadioItem(group, actions.m_actionHandicap5);
        menu.addRadioItem(group, actions.m_actionHandicap6);
        menu.addRadioItem(group, actions.m_actionHandicap7);
        menu.addRadioItem(group, actions.m_actionHandicap8);
        menu.addRadioItem(group, actions.m_actionHandicap9);
        return menu;
    }

    private GuiMenu createMenuBookmarks(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_BOOKMARKS"));
        menu.add(actions.m_actionAddBookmark);
        menu.add(actions.m_actionEditBookmarks);
        return menu;
    }

    private GuiMenu createMenuConfigureShell(GoGuiActions actions)
    {
        m_menuViewShell = new GuiMenu(i18n("MEN_SHELL"));
        m_menuViewShell.addCheckBoxItem(actions.m_actionToggleCompletion);
        m_menuViewShell.addCheckBoxItem(actions.m_actionToggleAutoNumber);
        m_menuViewShell.addCheckBoxItem(actions.m_actionToggleTimeStamp);
        return m_menuViewShell;
    }

    // Insert start date 15.06.2016 //////////////////////////////////////////
    // Add createMenuConfigureBoard()
    // Add createMenuConfigureInfoBar()
    // Insert 17.06.2016 
    // Add Fullscreen mode. Add Background.
    // Insert 20.06.2016
    // Add Method previewMenuBoard()
    // edit createmenu
    // edit previewMeniBoard() from : JMenuItem item = new JMenuItem(action); 
    //               to : JMenuItem item = new StayOpenMenuItem(action);

    private JMenuItem previewMenuBoard(String pic, GuiAction action)
    {

    ImageIcon icon;
    icon = GuiUtil.getIcon(pic,"board-pic menu icon");  
    JMenuItem item = new StayOpenMenuItem(action);
    item.setIcon(icon);
    return item;

    }


    private GuiMenu createMenuConfigureBoard(GoGuiActions actions)
    {
    GuiMenu menuBoard = new GuiMenu("Board");
        
    menuBoard.add(actions.m_actionBoardFullscreen);

    GuiMenu menuBackground = new GuiMenu("Backgroud");
    menuBackground.add(add(previewMenuBoard("menu-icon/icon-blue",actions.m_actionSetBGcolor1)));
    menuBackground.add(add(previewMenuBoard("menu-icon/icon-green",actions.m_actionSetBGcolor2)));
    menuBackground.add(add(previewMenuBoard("menu-icon/icon-pink",actions.m_actionSetBGcolor3)));
    menuBackground.add(add(previewMenuBoard("menu-icon/icon-w",actions.m_actionSetBGcolor4)));
    menuBackground.add(add(previewMenuBoard("menu-icon/icon-black",actions.m_actionSetBGcolor5)));

     menuBoard.add(menuBackground);
    

    GuiMenu menuTexture = new GuiMenu("Texture");
    ButtonGroup group = new ButtonGroup();

        /*menuBackground.addRadioItem(group,actions.m_actionBGpic1);
        menuBackground.addRadioItem(group,actions.m_actionBGpic2);
        menuBackground.addRadioItem(group,actions.m_actionBGpic3);
    menuBackground.addRadioItem(group,actions.m_actionBGcolor1);
    menuBackground.addRadioItem(group,actions.m_actionBGcolor2);
    menuBackground.addRadioItem(group,actions.m_actionBGcolor3);
    menuBackground.addRadioItem(group,actions.m_actionBGcolor4);
    menuBackground.addRadioItem(group,actions.m_actionBGcolor5);*/
    
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-wood",actions.m_actionBGpic1)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-wood",actions.m_actionBGpic2)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-marble",actions.m_actionBGpic3)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-blue",actions.m_actionBGcolor1)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-green",actions.m_actionBGcolor2)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-pink",actions.m_actionBGcolor3)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-w",actions.m_actionBGcolor4)));
    menuTexture.add(add(previewMenuBoard("menu-icon/icon-black",actions.m_actionBGcolor5)));
    
        menuBoard.add(menuTexture);



    GuiMenu menu = new GuiMenu("Theme");
    group = new ButtonGroup();
        menu.addRadioItem(group,actions.m_actionTheme1);
        menu.addRadioItem(group,actions.m_actionTheme2);
        menu.addRadioItem(group,actions.m_actionTheme3);
    menu.addRadioItem(group,actions.m_actionTheme4);
        menuBoard.add(menu);

//-- edit 21.06
//--    menuBoard.addCheckBoxItem(actions.m_actionShadow);  --//

    JMenuItem item = new StayOpenCheckBoxMenuItem(actions.m_actionShadow);
    menuBoard.add(item);

//-- edit end

    menuBoard.addCheckBoxItem(actions.m_actionToggleShowCursor);
    menuBoard.addCheckBoxItem(actions.m_actionToggleShowGrid);
    menuBoard.addCheckBoxItem(actions.m_actionToggleShowLastMove);
    menuBoard.addCheckBoxItem(actions.m_actionToggleShowMoveNumbers);
    return menuBoard;
    }

    private GuiMenu createMenuConfigureInfoBar(GoGuiActions actions)
    {
    GuiMenu menu = new GuiMenu("Info Panal");
    menu.addCheckBoxItem(actions.m_actionToggleBeepAfterMove);
    menu.addCheckBoxItem(actions.m_actionToggleShowToolbar);
    menu.addCheckBoxItem(actions.m_actionToggleShowInfoPanel);
    menu.addCheckBoxItem(actions.m_actionToggleCommentMonoFont);
    return menu;
    }
    // Insert end... //////////////////////////////////////////////////

    private GuiMenu createMenuConfigureTree(GoGuiActions actions)
    {
	m_menuViewTree = new GuiMenu(i18n("MEN_TREE"));
        GuiMenu menuLabel = new GuiMenu(i18n("MEN_TREE_LABELS"));
        ButtonGroup group = new ButtonGroup();

    	// Insert start date 15.06.2016
	GuiMenu menuVarLabels = new GuiMenu(i18n("MEN_VARIATION_LABELS"));
        menuVarLabels.addRadioItem(group,actions.m_actionShowVariationsChildren);
        menuVarLabels.addRadioItem(group,actions.m_actionShowVariationsSiblings);
        menuVarLabels.addRadioItem(group, actions.m_actionShowVariationsNone);
        m_menuViewTree.add(menuVarLabels);
   	// Insert end...

        group = new ButtonGroup();
        menuLabel.addRadioItem(group, actions.m_actionTreeLabelsNumber);
        menuLabel.addRadioItem(group, actions.m_actionTreeLabelsMove);
        menuLabel.addRadioItem(group, actions.m_actionTreeLabelsNone);
        m_menuViewTree.add(menuLabel);
        GuiMenu menuSize = new GuiMenu(i18n("MEN_TREE_SIZE"));
        group = new ButtonGroup();
        menuSize.addRadioItem(group, actions.m_actionTreeSizeLarge);
        menuSize.addRadioItem(group, actions.m_actionTreeSizeNormal);
        menuSize.addRadioItem(group, actions.m_actionTreeSizeSmall);
        menuSize.addRadioItem(group, actions.m_actionTreeSizeTiny);
        m_menuViewTree.add(menuSize);
        m_menuViewTree.addCheckBoxItem(actions.m_actionToggleShowSubtreeSizes);
        return m_menuViewTree;
    }

    private GuiMenu createMenuEdit(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_EDIT"));
        menu.add(actions.m_actionFind);
        menu.add(actions.m_actionFindNext);
        menu.add(actions.m_actionFindNextComment);
        menu.addSeparator();
        menu.add(actions.m_actionMakeMainVariation);
        menu.add(actions.m_actionDeleteSideVariations);
        menu.add(actions.m_actionKeepOnlyPosition);
        menu.add(actions.m_actionTruncate);
        menu.add(actions.m_actionTruncateChildren);
        menu.addSeparator();
        menu.addCheckBoxItem(actions.m_actionSetupBlack);
        menu.addCheckBoxItem(actions.m_actionSetupWhite);
        return menu;
    }

    private GuiMenu createMenuExport(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_EXPORT"));
        menu.add(actions.m_actionExportSgfPosition);
        menu.add(actions.m_actionExportLatexMainVariation);
        menu.add(actions.m_actionExportLatexPosition);
        menu.add(actions.m_actionExportPng);
        menu.add(actions.m_actionExportTextPosition);
        menu.add(actions.m_actionExportTextPositionToClipboard);
        return menu;
    }

    private GuiMenu createMenuFile(GoGuiActions actions,
                                       RecentFileMenu.Listener listener)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_FILE"));
        menu.add(actions.m_actionOpen);
        menu.add(createRecentMenu(listener));
        menu.add(actions.m_actionSave);
        menu.add(actions.m_actionSaveAs);
        menu.addSeparator();
        menu.add(createMenuImport(actions));
        menu.add(createMenuExport(actions));
        menu.addSeparator();
        menu.add(actions.m_actionPrint);
        if (! Platform.isMac())
        {
            menu.addSeparator();
            menu.add(actions.m_actionQuit);
        }
        return menu;
    }

    private GuiMenu createMenuGame(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_GAME"));
        menu.add(actions.m_actionNewGame);
        menu.addSeparator();
        menu.add(createBoardSizeMenu(actions));
        menu.add(createHandicapMenu(actions));
        menu.add(actions.m_actionGameInfo);
        menu.addSeparator();
        m_computerColor = createComputerColorMenu(actions);
        menu.add(m_computerColor);
        menu.addSeparator();
        menu.add(actions.m_actionPass);
        menu.add(createClockMenu(actions));
        menu.add(actions.m_actionScore);
        menu.add(actions.m_actionResign);
        return menu;
    }

    private GuiMenu createMenuGo(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_GO"));
        menu.add(actions.m_actionBeginning);
        menu.add(actions.m_actionBackwardTen);
        menu.add(actions.m_actionBackward);
        menu.add(actions.m_actionForward);
        menu.add(actions.m_actionForwardTen);
        menu.add(actions.m_actionEnd);
        menu.add(actions.m_actionGotoMove);
        menu.addSeparator();
        menu.add(actions.m_actionNextVariation);
        menu.add(actions.m_actionPreviousVariation);
        menu.add(actions.m_actionNextEarlierVariation);
        menu.add(actions.m_actionPreviousEarlierVariation);
        menu.add(actions.m_actionBackToMainVariation);
        menu.add(actions.m_actionGotoVariation);
        return menu;
    }

    private GuiMenu createMenuHelp(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_HELP"));
        menu.add(actions.m_actionHelp);
        menu.add(actions.m_actionAbout);

////////////////////////// 26 . 08 . 2559 ////////////////

        menu.add(actions.m_actionIgs);
        menu.add(actions.m_actionKgsServer);
        menu.add(actions.m_actionRemote);


///////////////////////////////


        return menu;
    }

    private GuiMenu createMenuImport(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_IMPORT"));
        menu.add(actions.m_actionImportTextPosition);
        menu.add(actions.m_actionImportTextPositionFromClipboard);
        menu.add(actions.m_actionImportSgfFromClipboard);
        return menu;
    }

    private GuiMenu createMenuProgram(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_PROGRAM"));
        m_menuAttach = new GuiMenu(i18n("MEN_ATTACH"));
        m_menuAttach.setEnabled(false);
        menu.add(m_menuAttach);
        menu.add(actions.m_actionDetachProgram);
        menu.addSeparator();
        menu.add(actions.m_actionPlaySingleMove);
        menu.add(actions.m_actionInterrupt);
        menu.addSeparator();
        menu.add(actions.m_actionNewProgram);
        menu.add(actions.m_actionEditPrograms);
        return menu;
    }

    private GuiMenu createMenuTools(GoGuiActions actions,
                                    RecentFileMenu.Listener listener)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_TOOLS"));
        menu.add(actions.m_actionShowTree);
        menu.add(actions.m_actionShowAnalyzeDialog);
        menu.add(actions.m_actionShowShell);
        menu.addSeparator();
        menu.add(actions.m_actionReattachProgram);
        menu.add(actions.m_actionReattachWithParameters);
        menu.add(actions.m_actionSnapshotParameters);
        menu.add(actions.m_actionRestoreParameters);
        menu.add(actions.m_actionSaveParameters);
        menu.addSeparator();
        menu.add(actions.m_actionSaveLog);
        menu.add(actions.m_actionSaveCommands);
        menu.add(actions.m_actionSendFile);
        m_recentGtp = new RecentFileMenu(i18n("MEN_SEND_RECENT"),
                                         "net/sf/gogui/recentgtpfiles",
                                         listener);
        menu.add(m_recentGtp.getMenu());
        return menu;
    }

     

    private GuiMenu createMenuView(GoGuiActions actions)
    {
        GuiMenu menu = new GuiMenu(i18n("MEN_VIEW"));
/////////////////////////////////INSERT_START DATE 15 June 2016////////////////////
    //comment: add createMenuConfBoard and Bar on menu

    menu.add(createMenuConfigureBoard(actions));
    menu.add(createMenuConfigureInfoBar(actions));

/////////////////////////////////INSERT_END////////////////////////////////////////
/////////////////////////////////Remove 15 June 2016 //////////////////////////////
    //Delete_start date 15 June 2016
        /*menu.addCheckBoxItem(actions.m_actionToggleShowToolbar);
        menu.addCheckBoxItem(actions.m_actionToggleShowInfoPanel);
        menu.addSeparator();
        menu.addCheckBoxItem(actions.m_actionToggleShowCursor);
        menu.addCheckBoxItem(actions.m_actionToggleShowGrid);
        menu.addCheckBoxItem(actions.m_actionToggleShowLastMove);
        menu.addCheckBoxItem(actions.m_actionToggleShowMoveNumbers);
        menu.addCheckBoxItem(actions.m_actionToggleBeepAfterMove);
        menu.addCheckBoxItem(actions.m_actionToggleCommentMonoFont);
        GuiMenu menuVarLabels = new GuiMenu(i18n("MEN_VARIATION_LABELS"));
        ButtonGroup group = new ButtonGroup();
        menuVarLabels.addRadioItem(group,
                                   actions.m_actionShowVariationsChildren);
        menuVarLabels.addRadioItem(group,
                                   actions.m_actionShowVariationsSiblings);
        menuVarLabels.addRadioItem(group, actions.m_actionShowVariationsNone);
        menu.add(menuVarLabels);
        menu.addSeparator();*/
/////////////////////////////////Remove end ///////////////////////////////////////
    
        menu.add(createMenuConfigureTree(actions));
        menu.add(createMenuConfigureShell(actions));
        return menu;
    }
    

    private GuiMenu createRecentMenu(RecentFileMenu.Listener listener)
    {
        m_recent = new RecentFileMenu(i18n("MEN_OPEN_RECENT"),
                                      "net/sf/gogui/recentfiles", listener);
        return m_recent.getMenu();
    }
}



//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
// Insert Start 20.06.2016 
// add class StayOpenMenuItem for prevent close menubar on clicked



class StayOpenMenuItem extends JMenuItem {

  private static MenuElement[] path;

  {
    getModel().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        if (getModel().isArmed() && isShowing()) {
          path = MenuSelectionManager.defaultManager().getSelectedPath();
        }
      }
    });
  }

  /**
   * @see JMenuItem#JMenuItem()
   */
  public StayOpenMenuItem() {
    super();
  }

  /**
   * @see JMenuItem#JMenuItem(javax.swing.Action)
   */
  public StayOpenMenuItem(Action a) {
    super(a);
  }

  /**
   * @see JMenuItem#JMenuItem(javax.swing.Icon)
   */
  public StayOpenMenuItem(Icon icon) {
    super(icon);
  }

  /**
   * @see JMenuItem#JMenuItem(java.lang.String)
   */
  public StayOpenMenuItem(String text) {
    super(text);
  }

  /**
   * @see JMenuItem#JMenuItem(java.lang.String, javax.swing.Icon)
   */
  public StayOpenMenuItem(String text, Icon icon) {
    super(text, icon);
  }

  /**
   * @see JMenuItem#JMenuItem(java.lang.String, int)
   */
  public StayOpenMenuItem(String text, int mnemonic) {
    super(text, mnemonic);
  }

  /**
   * Overridden to reopen the menu.
   *
   * @param pressTime the time to "hold down" the button, in milliseconds
   */
  @Override
  public void doClick(int pressTime) {
    super.doClick(pressTime);
    MenuSelectionManager.defaultManager().setSelectedPath(path);
  }
}


//-------------------------------------------------------------------------



class StayOpenCheckBoxMenuItem extends JCheckBoxMenuItem {

  private static MenuElement[] path;

  {
    getModel().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        if (getModel().isArmed() && isShowing()) {
          path = MenuSelectionManager.defaultManager().getSelectedPath();
        }
      }
    });
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem()
   */
  public StayOpenCheckBoxMenuItem() {
    super();
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(Action)
   */
  public StayOpenCheckBoxMenuItem(Action a) {
    super(a);
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(Icon)
   */
  public StayOpenCheckBoxMenuItem(Icon icon) {
    super(icon);
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String)
   */
  public StayOpenCheckBoxMenuItem(String text) {
    super(text);
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, boolean)
   */
  public StayOpenCheckBoxMenuItem(String text, boolean selected) {
    super(text, selected);
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon)
   */
  public StayOpenCheckBoxMenuItem(String text, Icon icon) {
    super(text, icon);
  }

  /**
   * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon, boolean)
   */
  public StayOpenCheckBoxMenuItem(String text, Icon icon, boolean selected) {
    super(text, icon, selected);
  }

  /**
   * Overridden to reopen the menu.
   *
   * @param pressTime the time to "hold down" the button, in milliseconds
   */
  @Override
  public void doClick(int pressTime) {
    super.doClick(pressTime);
    MenuSelectionManager.defaultManager().setSelectedPath(path);
  }
}
