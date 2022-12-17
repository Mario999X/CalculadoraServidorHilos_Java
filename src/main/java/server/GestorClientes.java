package server;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Operacion;
import models.mensajes.Request;
import models.mensajes.Response;
import models.mensajes.TypeResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GestorClientes implements Runnable {
    private final Socket cliente;

    private static String resultado;

    public GestorClientes(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        // Generamos canales de entrada-salida
        try {
            DataInputStream receiveRequest = new DataInputStream(cliente.getInputStream());
            DataOutputStream sendResponse = new DataOutputStream(cliente.getOutputStream());

            // Leemos el dato del cliente
            ObjectMapper obj = new ObjectMapper();
            // Es necesario hacer esto debido a la naturaleza generica de Request<T>
            JavaType type = obj.getTypeFactory().constructParametricType(Request.class, Operacion.class);

            Request<Operacion> request = obj.readValue(receiveRequest.readUTF(), type);
            System.out.println(request);

            Operacion operacion = request.getContent();

            switch (request.getRequestType()) {
                case SUMA -> resultado = String.valueOf(operacion.getNum1() + operacion.getNum2());
                case RESTA -> resultado = String.valueOf(operacion.getNum1() - operacion.getNum2());
                case MULTIPLICACION -> resultado = String.valueOf(operacion.getNum1() * operacion.getNum2());
                case DIVISION -> {
                    if (operacion.getNum2() > 0) {
                        resultado = String.valueOf(operacion.getNum1() / operacion.getNum2());
                    } else {
                        resultado = "0";
                    }
                }
            }

            System.out.println(resultado);
            Response<String> response = new Response<>(resultado, TypeResponse.OK);

            String jsonResponse = obj.writeValueAsString(response);
            sendResponse.writeUTF(jsonResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            cliente.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
