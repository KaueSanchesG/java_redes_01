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

        var nineDigits = cpf.substring(0, 9);
        var tenDigits = "";

        var firstCalc = 0;
        var secondCalc = 0;

        var firstDigit = -1;
        var secondDigit = -1;

        for (int i = 10; i > 1; i--) {
            var result = Character.getNumericValue(nineDigits.charAt(i - 2)) * i;
            firstCalc += result;
        }

        firstDigit = firstCalc % 11 < 2 ? 0 : 11 - (firstCalc % 11);

        tenDigits = firstDigit + nineDigits;

        for (int i = 11; i > 1; i--) {
            var result = Character.getNumericValue(tenDigits.charAt(i - 2)) * i;
            secondCalc += result;
        }

        secondDigit = secondCalc % 11 < 2 ? 0 : 11 - (secondCalc % 11);

        var foundCpf = nineDigits + firstDigit + secondDigit;
        return foundCpf.equals(cpf) ? true : false;

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
