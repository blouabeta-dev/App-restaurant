import java.net.*;
import java.util.*;

public class ServeurRestaurant {

    private static Map<String, Integer> plats = new HashMap<>();
    private static Map<String, Integer> boissons = new HashMap<>();

    public static void main(String[] args) throws Exception {

        // ===== Base de données intégrée =====
        plats.put("Ndolé", 1500);
        plats.put("Taro sauce jaune", 2500);
        plats.put("Poulet DG", 3000);
        plats.put("Ekwang", 2500);
        plats.put("Kondrès", 1500);
        plats.put("Eau fufu et eru", 2500);
        plats.put("Poisson braisé", 2000);
        plats.put("Cornchaff", 2000);
        plats.put("Sanga", 1500);
        plats.put("Mets de pistache", 2000);
        plats.put("Nkui", 3500);
        plats.put("Banane malaxée", 1500);

        boissons.put("Aucune", 0);
        boissons.put("Bière", 700);
        boissons.put("Top", 500);
        boissons.put("Eau minérale", 400);
        boissons.put("Vin de palme", 400);
        boissons.put("Guinness", 900);
        boissons.put("Djino", 500);
        boissons.put("Foléré", 200);
        boissons.put("Gingembre", 300);
        boissons.put("Vimto", 500);
        boissons.put("Bil-bil", 150);

        DatagramSocket socket = new DatagramSocket(5000);
        System.out.println("=== Serveur UDP en écoute sur le port 5000 ===");

        byte[] buffer = new byte[1024];

        while (true) {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());

            // 🔥 Affichage connexion client
            System.out.println("\n--- Nouveau client ---");
            System.out.println("Adresse IP : " + packet.getAddress());
            System.out.println("Port : " + packet.getPort());
            System.out.println("Message reçu : " + message);

            String[] parts = message.split(";");
            String plat = parts[0].split("=")[1];
            String boisson = parts[1].split("=")[1];

            int prixPlat = plats.getOrDefault(plat, 0);
            int prixBoisson = boissons.getOrDefault(boisson, 0);
            int total = prixPlat + prixBoisson;

            String reponse =
                    "Commande validée\n" +
                    "Plat: " + plat + " - " + prixPlat + " FCFA\n" +
                    "Boisson: " + boisson + " - " + prixBoisson + " FCFA\n" +
                    "TOTAL = " + total + " FCFA\n";

            byte[] sendData = reponse.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    packet.getAddress(),
                    packet.getPort()
            );

            socket.send(sendPacket);
        }
    }
}