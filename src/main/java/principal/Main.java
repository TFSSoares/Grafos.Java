package principal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Tela tela = new Tela();
        Navegar nav = new Navegar();
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                tela.telaPrincipal();
                char escolhaUsuario = scanner.next().charAt(0);

                if (escolhaUsuario == '0')
                    break;

                nav.navTelaPrincipal(escolhaUsuario, tela);
            }
        } finally {
            System.out.println("\nSaindo... Ate a proxima!");
            scanner.close();
        }

    }
}
