import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BulleItem extends JPanel {

    private boolean selected = false;

    public BulleItem(String nom, int prix, Runnable onSelect) {

        setPreferredSize(new Dimension(220, 70));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        setLayout(new BorderLayout());
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel labelNom = new JLabel(nom);
        labelNom.setFont(new Font("Arial", Font.BOLD, 14));
        labelNom.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        labelNom.setOpaque(true);
        labelNom.setBackground(Color.WHITE);

        JLabel labelPrix = new JLabel(prix + " FCFA", SwingConstants.CENTER);
        labelPrix.setFont(new Font("Arial", Font.BOLD, 14));
        labelPrix.setOpaque(true);
        labelPrix.setBackground(new Color(255, 140, 0));
        labelPrix.setForeground(Color.WHITE);
        labelPrix.setPreferredSize(new Dimension(90, 70));

        add(labelNom, BorderLayout.CENTER);
        add(labelPrix, BorderLayout.EAST);

        setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 2, true));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onSelect.run();
            }
        });
    }

    public void setSelected(boolean value) {
        selected = value;
        setBorder(
            BorderFactory.createLineBorder(
                value ? Color.GREEN : new Color(255, 140, 0),
                value ? 3 : 2,
                true
            )
        );
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
}
