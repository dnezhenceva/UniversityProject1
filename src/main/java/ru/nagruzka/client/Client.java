package ru.nagruzka.client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5555;

        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Привет от клиента!");
            String response = in.readLine();
            System.out.println("Ответ сервера: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
