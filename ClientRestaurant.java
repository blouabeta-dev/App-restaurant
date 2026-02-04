import java.awt.*;
import java.net.*;
import javax.swing.*;

public class ClientRestaurant {

    private static String platChoisi = "";
    private static String boissonChoisie = "Aucune";

    public static void main(String[] args) {
        accueil();
    }

    // ===== PAGE ACCUEIL =====
    public static void accueil() {

        JFrame frame = new JFrame("Restaurant Camerounais");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Bienvenue au Restaurant Camerounais", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JButton bouton = new JButton("Voir les plats");
        bouton.setBackground(new Color(34, 139, 34));
        bouton.setForeground(Color.WHITE);

        bouton.addActionListener(e -> {
            frame.dispose();
            pagePlats();
        });

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(bouton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ===== PAGE PLATS =====
    public static void pagePlats() {

        JFrame frame = new JFrame("Choisissez un plat");
        frame.setSize(600, 550);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JScrollPane scroll = new JScrollPane(listPanel);

        String[][] plats = {
                {"Ndolé", "1500"},
                {"Taro sauce jaune", "2500"},
                {"Poulet DG", "3000"},
                {"Ekwang", "2500"},
                {"Kondrès", "1500"},
                {"Eau fufu et eru", "2500"},
                {"Poisson braisé", "2000"},
                {"Cornchaff", "2000"},
                {"Sanga", "1500"},
                {"Mets de pistache", "2000"},
                {"Nkui", "3500"},
                {"Banane malaxée", "1500"}
        };

        final BulleItem[] selected = {null};

        for (String[] plat : plats) {
            String nom = plat[0];
            int prix = Integer.parseInt(plat[1]);

            final BulleItem[] current = new BulleItem[1];

            current[0] = new BulleItem(nom, prix, () -> {
                if (selected[0] != null) {
                    selected[0].setSelected(false);
                }

                current[0].setSelected(true);
                selected[0] = current[0];
                platChoisi = nom;
            });

            listPanel.add(current[0]);
            listPanel.add(Box.createVerticalStrut(12));
        }

        JButton continuer = new JButton("Continuer");
        continuer.setBackground(new Color(34, 139, 34));
        continuer.setForeground(Color.WHITE);
        continuer.setFont(new Font("Arial", Font.BOLD, 16));

        continuer.addActionListener(e -> {
            if (platChoisi.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un plat !");
            } else {
                frame.dispose();
                pageBoissons();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(continuer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // ===== PAGE BOISSONS =====
    public static void pageBoissons() {

        JFrame frame = new JFrame("Choisissez une boisson");
        frame.setSize(600, 550);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JScrollPane scroll = new JScrollPane(listPanel);

        String[][] boissons = {
                {"Aucune", "0"},
                {"Bière", "700"},
                {"Top", "500"},
                {"Eau minérale", "400"},
                {"Vin de palme", "400"},
                {"Guinness", "900"},
                {"Djino", "500"},
                {"Foléré", "200"},
                {"Gingembre", "300"},
                {"Vimto", "500"},
                {"Bil-bil", "150"}
        };

        final BulleItem[] selected = {null};

        for (String[] boisson : boissons) {
            String nom = boisson[0];
            int prix = Integer.parseInt(boisson[1]);

            final BulleItem[] current = new BulleItem[1];

            current[0] = new BulleItem(nom, prix, () -> {
                if (selected[0] != null) {
                    selected[0].setSelected(false);
                }

                current[0].setSelected(true);
                selected[0] = current[0];
                boissonChoisie = nom;
            });

            listPanel.add(current[0]);
            listPanel.add(Box.createVerticalStrut(12));
        }

        JButton commander = new JButton("Commander");
        commander.setBackground(new Color(34, 139, 34));
        commander.setForeground(Color.WHITE);
        commander.setFont(new Font("Arial", Font.BOLD, 16));

        commander.addActionListener(e -> {
            envoyerCommande();
            frame.dispose();
        });

        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(commander, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // ===== ENVOI UDP =====
    public static void envoyerCommande() {
        try {
            DatagramSocket socket = new DatagramSocket();

            String message = "PLAT=" + platChoisi + ";BOISSON=" + boissonChoisie;
            byte[] buffer = message.getBytes();

            InetAddress address = InetAddress.getByName("localhost");

            DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length, address, 5000);

            socket.send(packet);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket responsePacket =
                    new DatagramPacket(receiveBuffer, receiveBuffer.length);

            socket.receive(responsePacket);

            String response = new String(
                    responsePacket.getData(),
                    0,
                    responsePacket.getLength()
            );

            JOptionPane.showMessageDialog(null, response);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
