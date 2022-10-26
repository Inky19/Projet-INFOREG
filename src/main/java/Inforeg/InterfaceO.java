package Inforeg;

/*=============================================
Classe InterfaceO
Auteur : Béryl CASSEL
Date de création : 08/03/2022
Date de dernière modification : 08/03/2022
=============================================*/
import Inforeg.Graph.GraphO;
import Inforeg.Draw.Draw;
import Inforeg.Algo.Connexe;
import static Inforeg.Interface.TRAITEMENT_MODE;
import static Inforeg.Interface.activeTraitement;
import static Inforeg.Interface.mode;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InterfaceO extends Interface implements Connexe {

    public InterfaceO(Draw d) {
        super(d);
    }

    /**
     * Actions
     */
    public final AbstractAction Dijkstra = new AbstractAction() {
        {
            putValue(Action.NAME, "Dijkstra");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
            putValue(Action.SHORT_DESCRIPTION, "Applique l'algorithme de Dijkstra pour trouver \n"
                    + "le plus court chemin entre 2 sommets \n"
                    + "-Cliquez sur le nœud de départ \n"
                    + "-Cliquez sur le nœud d'arrivée \n"
                    + "(CTRL+D)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mode == Interface.TRAITEMENT_MODE) {
                activeTraitement = Interface.DIJKSTRA_TRAITEMENT;
                d.reinit();
                d.repaint();
                JOptionPane.showMessageDialog(null, "Sélectionnez un sommet de départ et un sommet d'arrivée pour calculer le plus court chemin entre les deux s'il existe.",
                        "Dijkstra - PCC", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    };

    public final AbstractAction FordFulkerson = new AbstractAction() {
        {
            putValue(Action.NAME, "Ford Fulkerson");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
            putValue(Action.SHORT_DESCRIPTION, "Applique l'algorithme de Ford Fulkerson pour calculer \n"
                    + "le flot maximal entre 2 sommets \n"
                    + "-Cliquez sur le nœud source \n"
                    + "-Cliquez sur le nœud de sortie \n"
                    + "(CTRL+F)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent ea) {
            activeTraitement = Interface.FORD_FULKERSON_TRAITEMENT;
            d.reinit();
            d.repaint();
            JOptionPane.showMessageDialog(null, "Sélectionnez un sommet source et un sommet cible pour calculer le flot maximal entre les deux.",
                    "Ford-Fulkerson - Flot maximal", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    public final AbstractAction ConnexiteO = new AbstractAction() {
        {
            putValue(Action.NAME, "Connexité");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
            putValue(Action.SHORT_DESCRIPTION, "Vérifie si le graphe est fortement connexe \n"
                    + "(CTRL+L)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent ea) {
            if (mode == Interface.TRAITEMENT_MODE) {
                if (connexe((GraphO) d.getG())) {
                    JOptionPane.showMessageDialog(d, "Le graphe est fortement connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(d, "Le graphe n'est pas fortement connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    };
   
    public final AbstractAction ExportGraphO = new AbstractAction() {
        {
            putValue(Action.NAME, "Export Matrice d'Adjacence");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
            putValue(Action.SHORT_DESCRIPTION, "Affiche la matrice d'adjacence du graphe (CTRL+A)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent ea) {
            d.exportGraphe();
            JOptionPane.showMessageDialog(d, "La matrice d'adjacence du graphe orienté est :\n\n" + d.getG().afficher(), "Matrice d'adjacence", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * JPanel pour les boutons 
     *
     */
    @Override
    public void initToolBar() {
        super.initToolBar();

        JLabel l1 = new JLabel("  Édition");
        JLabel l2 = new JLabel("  Mode");
        //On crée un ButtonGroup pour que seul l'un puisse être activé à la fois 
        ButtonGroup groupMode = new ButtonGroup();
        groupMode.add(edition);
        groupMode.add(traitement);
        groupMode.add(deplacement);
        ButtonGroup groupAction = new ButtonGroup();
        groupAction.add(select);
        groupAction.add(noeud);
        groupAction.add(arc);
        groupAction.add(label);
        //On ajoute les éléments au JPanel
        toolBarButtons.add(l2);
        toolBarButtons.addSeparator();
        toolBarButtons.add(deplacement);
        toolBarButtons.add(edition);
        toolBarButtons.add(traitement);
        toolBarButtons.addSeparator();
        toolBarButtons.add(l1);
        toolBarButtons.addSeparator();
        toolBarButtons.add(select);
        toolBarButtons.add(noeud);
        toolBarButtons.add(arc);
        toolBarButtons.add(label);
        //pane.add(Box.createVerticalGlue());

        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
        JLabel l = new JLabel("  Traitement");
        toolBarButtons.add(l);
        toolBarButtons.addSeparator();

        //Action Listener
        ActionListener toolGroupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == select) {
                    activeTool = SELECT_TOOL;
                } else if (ae.getSource() == noeud) {
                    activeTool = NOEUD_TOOL;
                } else if (ae.getSource() == arc) {
                    activeTool = ARC_TOOL;
                } else if (ae.getSource() == label) {
                    activeTool = LABEL_TOOL;
                }

            }
        };
        ActionListener modeGroupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == edition) {
                    mode = EDITION_MODE;
                    select.setEnabled(true);
                    noeud.setEnabled(true);
                    arc.setEnabled(true);
                    label.setEnabled(true);
                    Dijkstra.setEnabled(false);
                    FordFulkerson.setEnabled(false);
                    ConnexiteO.setEnabled(false);
                } else if (ae.getSource() == traitement) {
                    d.reinit();
                    d.repaint();
                    mode = TRAITEMENT_MODE;
                    select.setEnabled(false);
                    noeud.setEnabled(false);
                    arc.setEnabled(false);
                    label.setEnabled(false);
                    Dijkstra.setEnabled(true);
                    FordFulkerson.setEnabled(true);
                    ConnexiteO.setEnabled(true);
                    d.exportGraphe();
                } else if (ae.getSource() == deplacement) {
                    d.reinit();
                    d.repaint();
                    mode = DEPLACEMENT_MODE;
                    select.setEnabled(false);
                    noeud.setEnabled(false);
                    arc.setEnabled(false);
                    label.setEnabled(false);
                    Dijkstra.setEnabled(false);
                    FordFulkerson.setEnabled(false);
                    ConnexiteO.setEnabled(false);
                    d.exportGraphe();
                }
            }
        };

        select.addActionListener(toolGroupListener);
        //select.setSelected(true);//select activé au démarrage
        noeud.addActionListener(toolGroupListener);
        arc.addActionListener(toolGroupListener);
        label.addActionListener(toolGroupListener);
        edition.addActionListener(modeGroupListener);
        deplacement.addActionListener(modeGroupListener);
        //edition.setSelected(true);//edition activé au démarrage
        traitement.addActionListener(modeGroupListener);
        //toolBarButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBarButtons.setAlignmentX(FlowLayout.CENTER);
        toolBarButtons.setFloatable(false);
        toolBarButtons.setBorderPainted(true);

    }

    @Override
    public void addToolBar() {
        toolBarButtons.add(Dijkstra);
        toolBarButtons.addSeparator();
        toolBarButtons.add(FordFulkerson);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ConnexiteO);      
    }

    @Override
    public void addMenuBar() {
        JMenu traitMenu = new JMenu("Traitement");
        traitMenu.add(Dijkstra);
        traitMenu.add(FordFulkerson);
        traitMenu.add(ConnexiteO);
        menuBar.add(traitMenu);
        exporter.add(new JMenuItem(ExportGraphO));
    }

}
