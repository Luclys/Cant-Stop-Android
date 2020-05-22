package com.luclys.cantstopmobile;

import io.socket.client.IO;
import io.socket.client.Socket;

public final class Client {
    private static Client singleton = null;
    private Socket mSocket;

    private Client() {
        super();
    }

    /**
     * Permet de récupérer le singleton, s'il n'existe pas déjà, il est créé.
     *
     * @return Le singleton de com.luclys.cantstopmobile.Client.
     */
    public static Client getInstance() {
        if (Client.singleton == null) {
            synchronized (Client.class) {
                if (Client.singleton == null) {
                    Client.singleton = new Client();
                }
            }
        }
        return Client.singleton;
    }


    /**
     * Permet de récupérer la socket de Socket.io généralement obtenue en passant par getInstance().
     *
     * @return La socket utilisée par le com.luclys.cantstopmobile.Client.
     * @see io.socket.engineio.client.Socket
     */
    public Socket getmSocket() {
        return mSocket;
    }

    /**
     * Ajoute une mSocket au client
     * @param s la socket à associer
     */
    void setmSocket(Socket s) {
        mSocket = s;
    }

    /**
     * @param ip L'adresse IP du serveur auquel on veut se connecter.
     * @return String qui informe sur la l'état de la connexion.
     */
    public String connecter(String ip) {
        String result = null;
        if (this.mSocket == null) {
            try {
                mSocket = IO.socket("http://" + ip + ":10101");
                mSocket.connect();

            } catch (Exception e) {
                result = ("Erreur inattendue");
                e.printStackTrace();
                this.deconnecter();
            }
            if (result == null) {
                result = ("Tentative de connexion...");
            }
        } else {
            result = ("Déconnectez vous d'abord.");
        }
        return result;
    }

    /**
     * @return string sur l'état de la déconnexion.
     */
    public String deconnecter() {
        if (this.mSocket != null) {
            try {
                mSocket.disconnect();
                this.mSocket = null;
                singleton = null;
                return ("Déconnecté.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ("Connectez vous d'abord.");
        }
        return ("Erreur de déconnexion.");
    }

    /**
     * @param event code string de l'event.
     * @param arg   contenu à envoyer (arguments).
     * @return string sur l'état de la connexion.
     */
    public String envoyerEvent(String event, Object arg) {
        if (this.mSocket != null) {
            try {
                mSocket.emit(event, arg);
                return ("Message Envoyé.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ("Connectez vous d'abord avant d'envoyer un message.");
        }
        return ("Erreur de l'envoi.");
    }

}