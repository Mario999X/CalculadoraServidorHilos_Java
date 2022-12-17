package client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Operacion;
import models.mensajes.Request;
import models.mensajes.Response;
import models.mensajes.TypeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private final static int PUERTO = 6969;
    private static Request<Operacion> request;

    public static void main(String[] args) throws IOException {
        // Preguntamos por terminal/teclado los numeros que componen la operacion y el tipo de operacion
        Scanner input = new Scanner(System.in);
        System.out.println("Escriba el primer numero: ");
        int num1 = input.nextInt();
        System.out.println("Escriba el segundo numero: ");
        int num2 = input.nextInt();

        Operacion op = new Operacion(num1, num2);

        System.out.println("Escoga el tipo de operacion a realizar:\n1.SUMA\n2.Resta\n3.Multiplicacion\n4.Division ");
        int operacion = input.nextInt();
        input.close(); // No echaba de menos Scanner

        switch (operacion) {
            case 1 -> request = new Request<>(op, TypeRequest.SUMA);
            case 2 -> request = new Request<>(op, TypeRequest.RESTA);
            case 3 -> request = new Request<>(op, TypeRequest.MULTIPLICACION);
            case 4 -> request = new Request<>(op, TypeRequest.DIVISION);
            default -> {
                System.out.println("Opcion no valida");
                System.exit(1);
            }
        }

        // Nos conectamos al servidor
        Socket servidor = new Socket(InetAddress.getLocalHost(), PUERTO);

        // Creamos canales de entrada-salida
        DataInputStream receiveResponse = new DataInputStream(servidor.getInputStream());
        DataOutputStream sendRequest = new DataOutputStream(servidor.getOutputStream());

        // Enviamos el dato al servidor en formato json usando Jackson (lo mas parecido a json de Kotlin)
        ObjectMapper obj = new ObjectMapper();
        String jsonRequest = obj.writeValueAsString(request);

        sendRequest.writeUTF(jsonRequest);

        // Esperamos el resultado
        JavaType type = obj.getTypeFactory().constructParametricType(Response.class, String.class);

        Response<String> response = obj.readValue(receiveResponse.readUTF(), type);
        System.out.println("Resultado: " + response.getContent());

        servidor.close();
    }
}
