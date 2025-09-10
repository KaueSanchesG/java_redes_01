package local.redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


// @author Kauê S. Gonçalves

public class Servidor {

    private static boolean isCPFValid(String cpf){
        return false;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(50000);
        System.out.println("Porta " + servidor.getLocalPort() + " aberta, iniciando servidor");

        Socket socket = servidor.accept();

        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());

        var cpf = entrada.readUTF();

        System.out.println("Entrada: " + cpf);

        var resposta = isCPFValid(cpf) ? "Este CPF é válido" : "Este CPF é inválido.";

        System.out.println(resposta);

        saida.writeUTF(resposta);

        System.out.println("Fechando conexão " + servidor.getLocalPort());

        entrada.close();
        saida.close();
        socket.close();
    }
}
