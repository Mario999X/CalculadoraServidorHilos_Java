package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    // El puerto que usaremos
    private final static int PUERTO = 6969;

    public static void main(String[] args) {
        ServerSocket servidor;
        Socket cliente;

        // Necesitamos una pool de hilos para manejar a los clientes
        ExecutorService pool = Executors.newFixedThreadPool(10);

        System.out.println("Arrancando servidor...");
        try {
            // Tratamos de arrancar el servidor
            servidor = new ServerSocket(PUERTO);

            System.out.println("\tServidor esperando...");
            while (true) {

                cliente = servidor.accept();
                System.out.println("Peticion -> " + cliente.getInetAddress() + " --- " + cliente.getPort());
                GestorClientes gc = new GestorClientes(cliente);
                pool.execute(gc);

            }
            //pool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}