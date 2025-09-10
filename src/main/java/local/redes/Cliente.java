package local.redes;

import java.io.*;
import java.net.Socket;

// @author Kauê S. Gonçalves

public class Cliente {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 50000);
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        DataInputStream entrada = new DataInputStream(socket.getInputStream());

        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Digite o cpf");
        var cpf = teclado.readLine();

        saida.writeUTF(cpf);
        System.out.println(entrada.readUTF());

        saida.close();
        entrada.close();
        socket.close();
    }
}
