package principal;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class ColecaoDeGrafos {
    static ArrayList<Grafo> grafos = new ArrayList<Grafo>();

    public static void addGrafo(Grafo grafo) {
        grafos.add(grafo);
    }

    public static ArrayList<Grafo> getGrafos() {
        return grafos;
    }

    public static Grafo selecionarGrafoExistente(Scanner scanner) {
        while (true) {
            if (!mostrarGrafosExistentes())
                break;

            System.out.println("\nInfome o numero do grafo ou digite 0 para voltar");
            String codGrafo = scanner.next();

            if (codGrafo.equals("0"))
                break;

            Grafo grafo = encontrarGrafoPorCodigo(codGrafo);

            if (grafo != null) {
                return grafo;
            }

            System.out.println("\nGrafo nao encontrado");

        }

        return null;
    }

    public static Grafo encontrarGrafoPorCodigo(String codGrafo) {
        for (Grafo grafo : ColecaoDeGrafos.getGrafos()) {
            if (grafo.getCode().equals(codGrafo)) {
                return grafo;
            }
        }

        return null;
    }

    public static boolean grafoExiste(String grafoNome){
        for(Grafo grafo: grafos){
            if(grafo.getNome().equals(grafoNome)) {
                return true;
            }
        }

        return false;
    }

    public static boolean mostrarGrafosExistentes() {
        if (ColecaoDeGrafos.getGrafos().size() == 0) {
            System.out.println("\nVoce ainda nao possui grafos");
            return false;
        }

        System.out.println("\nVoce possui os grafos: \n");
        for (Grafo grafo : ColecaoDeGrafos.getGrafos()) {
            System.out.println(grafo.getCodeNome());
        }

        return true;
    }
}
