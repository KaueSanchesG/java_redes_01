package local.redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


// @author Kauê S. Gonçalves

public class Servidor {

    private static boolean isCPFValid(String cpf) {
        if (cpf.equals("00000000000") || cpf.length() != 11) return false;

        String nineDigits = cpf.substring(0, 9);
        String tenDigits = "";

        int firstCalc = 0;
        int secondCalc = 0;

        int firstDigit = -1;
        int secondDigit = -1;

        for (int i = 10; i > 1; i--) {
            int result = Character.getNumericValue(nineDigits.charAt(10 - i)) * i;
            firstCalc += result;
        }

        firstDigit = firstCalc % 11 < 2 ? 0 : 11 - (firstCalc % 11);

        tenDigits = nineDigits + firstDigit;

        for (int i = 11; i > 1; i--) {
            int result = Character.getNumericValue(tenDigits.charAt(11 - i)) * i;
            secondCalc += result;
        }

        secondDigit = secondCalc % 11 < 2 ? 0 : 11 - (secondCalc % 11);

        String foundCpf = nineDigits + firstDigit + secondDigit;
        return foundCpf.equals(cpf);
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
