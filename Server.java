package org.example.javanetworking;

import org.example.javanetworking.Player.*;
import org.example.javanetworking.DTO.*;
import org.example.javanetworking.CricketManagementSystem.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 12349;
    private CricketManagementSystem cms;
    private List<SocketWrapper> clientSockets;
    private List<Thread> clientThreads;
    private List<Player> transferMarket;
    private List<Player> PlayerList;
    List<BuyReq> bid;
    private List<Player> MarketPlace;
    public Server() throws Exception {
        cms = new CricketManagementSystem();
        clientSockets = new ArrayList<>();
        clientThreads = new ArrayList<>();
        transferMarket = new ArrayList<>();
        PlayerList = new ArrayList<>();
        PlayerList = cms.getAllPlayers();
        bid = new ArrayList<>();
        MarketPlace=new ArrayList<>();
        System.out.println("Server initialized.");
    }

    private void handleClient(Socket clientSocket) throws Exception {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        synchronized (clientSockets) {
            clientSockets.add(socketWrapper);
        }
        System.out.println("Client connected and SocketWrapper created.");

        Object receivedObject = socketWrapper.read();
        if (receivedObject instanceof String) {
            String credentials = (String) receivedObject;
            System.out.println("Received credentials: " + credentials);

            String[] userCredentials = credentials.split(",");
            String username = userCredentials[0].trim();
            String password = userCredentials[1].trim();

            if (validateCredentials(username, password)) {
                System.out.println("Credentials validated for user: " + username);
                socketWrapper.write("SUCCESS");
                socketWrapper.flush();
           while (true) {
                    receivedObject = socketWrapper.read();
               System.out.println("Received obj: "+ receivedObject.getClass().getName());
                    if (receivedObject == null) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                    if (receivedObject instanceof String) {
                        String request = (String) receivedObject;
                        if(request.equals("Show All")) {
                            synchronized (PlayerList) {
                                socketWrapper.write(PlayerList);
                                socketWrapper.flush();
                                System.out.println("Sent full PlayerList to client.");
                            }
                        }
                    }
               if (receivedObject instanceof Player) {
                   Player player = (Player) receivedObject;
                   System.out.println("Transfer request received for: " + player.getName());
                   synchronized (MarketPlace) {
                       boolean status = true;
                       for (Player p : MarketPlace) {
                           if (p.getName().equalsIgnoreCase(player.getName())) {
                               System.out.println("Player " + player.getName() + " already exists");
                               status = false;
                               break;
                           }
                       }
                       if (status) {
                           MarketPlace.add(player);
                           System.out.println("Player " + player.getName() + " added to the transfer market.");
                       }
                       for (Player p : MarketPlace) {
                           System.out.println(p);
                       }
                       if (MarketPlace.isEmpty()) {
                           System.out.println("Transfer market is empty.");
                       }

                       // Synchronized block for writing to the socket
                       synchronized (socketWrapper) {
                           try {
                               if (status) {
                                   socketWrapper.write("Player sent to transfer market");
                               } else {
                                   socketWrapper.write("Player already in transfer market");
                               }
                               socketWrapper.flush();
                           } catch (IOException e) {
                               System.err.println("Error sending response to client: " + e.getMessage());
                           }
                       }
                   }
               }

               if (receivedObject instanceof TransferReq) {
                   System.out.println("Inside confirm transfer");
                   TransferReq transferReq = (TransferReq) receivedObject;
                   Player player = transferReq.p;
                   String offeredClub = transferReq.NewClub;

                   synchronized (MarketPlace) {
                       MarketPlace.removeIf(p -> player.getName().equalsIgnoreCase(p.getName()));
                       System.out.println("Removed from MarketPlace if present.");
                   }
                   synchronized (PlayerList) {
                       PlayerList.removeIf(p -> player.getName().equalsIgnoreCase(p.getName()));
                       System.out.println("Removed from PlayerList if present.");
                   }
                   synchronized (bid) {
                       bid.removeIf(p -> player.getName().equalsIgnoreCase(p.getPlayer().getName()));
                       System.out.println("Removed from PlayerList if present.");
                   }
                   Player updatedPlayer = new Player(
                           player.getName(),
                           player.getCountry(),
                           player.getAge(),
                           player.getHeight(),
                           offeredClub,
                           player.getPosition(),
                           player.getNumber(),
                           player.getSalary()
                   );

                   synchronized (PlayerList) {
                       PlayerList.add(updatedPlayer);
                       System.out.println("Added updated player to PlayerList: " + updatedPlayer);
                   }

                   System.out.println("Transfer completed for: " + player.getName() + " to " + offeredClub);
               }

               if (receivedObject instanceof String) {
                   String request = (String) receivedObject;
                   System.out.println("Request received: " + request);

                   if (request.equalsIgnoreCase("SHOW_TRANSFER")) {
                       synchronized (MarketPlace) {
                           System.out.println("Received request for transfer market list.");

                           if (MarketPlace == null) {
                               System.out.println("MarketPlace is null!");
                           } else if (MarketPlace.isEmpty()) {
                               System.out.println("Marketplace is empty before sending.");
                           } else {
                               System.out.println("Marketplace is not empty. Contains " + MarketPlace.size() + " players."); // New debug
                               for (Player p : MarketPlace) {
                                   System.out.println("Player in MarketPlace: " + p);
                               }
                           }
                           socketWrapper.write(MarketPlace);
                           socketWrapper.flush();
                           System.out.println("Sent transfer market (players only) to client.");
                       }
                   }

               }
               if (receivedObject instanceof String) {
                   String request = (String) receivedObject;
                   System.out.println("Request received: " + request);

                   if (request.equalsIgnoreCase("SHOW_OFFER")) {
                       synchronized (bid) {
                           System.out.println("Received request for offer market list.");
                           if (bid == null) {
                               System.out.println("MarketPlace is null!");
                           } else if (bid.isEmpty()) {
                               System.out.println("Marketplace is empty before sending.");
                           } else {
                               System.out.println("Marketplace is not empty. Contains " + bid.size() + " players.");
                           }
                           socketWrapper.write(bid);
                           socketWrapper.flush();
                           System.out.println("Sent transfer market (players only) to client.");
                       }
                   }

               }
                else if (receivedObject instanceof BuyReq) {
                        BuyReq buyReq = (BuyReq) receivedObject;
                        System.out.println("Buy request received: " + buyReq);
                        synchronized (bid) {
                            bid.add(buyReq);
                            System.out.println("Buy request added to the bid list.");
                            for (BuyReq buy : bid) {
                                System.out.println("Buy request details: " + buy.getClub() + " " + buy.getPlayer() + " " + buy.getAmount());
                            }
                        }
                    }
                }
            } else {
                System.out.println("Invalid credentials for user: " + username);
                socketWrapper.write("FAILURE");
                socketWrapper.flush();
            }
        } else {
            System.err.println("Invalid data received during authentication.");
        }

        socketWrapper.closeConnection();
        System.out.println("Connection closed with client.");
    }
    private boolean validateCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/rawnakmubtasimrafid/Desktop/JavaNetworking Where transferlist works/src/main/java/org/example/javanetworking/loginhandler.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    String fileUsername = credentials[0].trim();
                    String filePassword = credentials[1].trim();
                    if (fileUsername.equalsIgnoreCase(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading loginhandler.txt: " + e.getMessage());
        }
        return false;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started and waiting for clients to connect...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            new Thread(() -> {
                try {
                    handleClient(clientSocket);
                } catch (Exception e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
}
