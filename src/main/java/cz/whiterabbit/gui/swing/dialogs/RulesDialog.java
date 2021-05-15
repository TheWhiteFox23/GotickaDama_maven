package cz.whiterabbit.gui.swing.dialogs;

import cz.whiterabbit.gui.swing.customComponents.CustomLabel;
import cz.whiterabbit.gui.swing.customComponents.CustomMenuButton;
import cz.whiterabbit.gui.swing.customComponents.CustomScrollPane;

import javax.swing.*;
import java.awt.*;

public class RulesDialog  extends  GeneralDialog{
    private JLabel text;
    private String rulesText;
    private JButton okButton;

    public RulesDialog(JFrame frame) {
        super(frame);
        init();
    }

    private void init(){
        okButton = new CustomMenuButton("Close");

        setSize(new Dimension(800,700));
        setModal(true);

        rulesText = "<html>\n" +
                "    <h1>Gotická dáma</h1>\n" +
                "    <p>Jedná se o tradiční německou hru, která je známá také pod názvy Altdeutsche Dame neboDamm-Spel</p>.\n" +
                "    <h3>Cíl hry</h3>\n" +
                "    <ul>\n" +
                "        <li>Zajmout všechny soupeřovy kameny.\n" +
                "    </ul>\n" +
                "    <h3>Výchozí situace</h3>\n" +
                "    <ul>\n" +
                "        <li>Hraje se na desce 8x8 polí.</li>\n" +
                "        <li>Na začátku hry stojí figurky ve dvou krajníchřadách (vizobrázek).</li>\n" +
                "    </ul>\n" +
                "<h3>Pravidla hry</h3>\n" +
                "<ul>\n" +
                "    <li>Hráči se v tazích pravidelně střídají.</li>\n" +
                "    <li>Hráč na tahu smí posunout svůj obyčejný kámen o jedno pole na sousední volné pole a tosměrem vpřed, do stran nebo diagonálně vpřed. Nesmí diagonálně ani ortogonálně vzad.</li>\n" +
                "    <li>Obyčejný kámen může také zajmout přeskočením kámen soupeře. Soupeřův kámen musíležet na sousedním poli a bezprostředně za tímto přeskakovaným kamenem musí být volnépole. Kámen je při realizaci skoku přemístěn na toto volné pole a přeskočený soupeřůvkámen je odstraněn z desky. Obyčejný kámen smí skákat také pouze vpřed, diagonálněvpřed a do stran.</li>\n" +
                "    <li>Vícenásobné skoky jsou dovoleny.</li>\n" +
                "    <li>Pokud obyčejný kámen ukončí svůj tah v poslednířadě na protější straně desky, je povýšennadámu.</li>\n" +
                "    <li>Dáma se smí pohybovat všemi směry (i vzad) o libovolný počet volných polí. Dáma můžezajímat kámen soupeře přeskočením, přičemž může přeskočit libovolný počet volných polípřed kamenem, zajímaný kámen a libovolný počet volných polí za tímto kamenem (podobnějako v klasické dámě).</li>\n" +
                "    <li>Zajímání soupeřových kamenů je povinné a hráč musí zajmout co nejvíce kamenů, pokudmá více možností jak skákat.</li>\n" +
                "    <li>Pokud některý hráč nemůže provést žádný tah, pokračuje ve hře jeho soupeř.</li>\n" +
                "</ul>\n" +
                "<h3>Konec hry</h3>\n" +
                "<ul>\n" +
                "    <li>Hráč, který zajme všechny soupeřovy kameny vyhrává.</li>\n" +
                "    <li>Pokud po 30 tahů není zajat žádný kámen, vyhrává hráč, kterému zbývá více kamenů.</li>\n" +
                "</ul>\n" +
                "\n" +
                "\n" +
                "</html>";
        text = new CustomLabel(rulesText);
        layoutComponents();
        initializeListeners();
    }

    private void layoutComponents() {
        JPanel holderPanel = new JPanel();
        holderPanel.setOpaque(false);

        holderPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(10,10,10,10);
        holderPanel.add(text, gc);

        gc.gridy++;
        gc.fill = 0;
        holderPanel.add(okButton, gc);

        setLayout(new BorderLayout());
        add(holderPanel, BorderLayout.CENTER);
    }

    private void initializeListeners(){
        okButton.addActionListener(x -> {
            dispose();
        });
    }
}
