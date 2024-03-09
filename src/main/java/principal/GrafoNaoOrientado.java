package principal;
import java.util.*;

public class GrafoNaoOrientado extends Grafo {

    public GrafoNaoOrientado(String nome) {
        super(nome);

    }

    public boolean gerarArestas() {
        Scanner scanner = new Scanner(System.in);
        int codigoAresta = 1;

        for (int i = 0; i < vertices.size(); i++) {
            Vertice vertice1 = vertices.get(i);

            for (int j = i; j < vertices.size(); j++) {
                Vertice vertice2 = vertices.get(j);
                boolean sair = false;

                while (!sair) {

                    System.out.println("\nO vértice '" + vertice1.getNome() + "'" +
                            " possui relação com o vértice '" + vertice2.getNome() + "'" + " ? s/n");

                    char escolhaUsuario = scanner.next().charAt(0);

                    switch (escolhaUsuario) {
                        case 'n':
                            sair = true;
                            break;

                        case 's': {
                            System.out.println("\nDigite o peso da relação");
                            float inputPeso = scanner.nextFloat();

                            modificaGrauDosVerticesMais(vertice1, vertice2);

                            Aresta aresta = new Aresta(inputPeso, vertice1, vertice2, Integer.toString(codigoAresta));
                            codigoAresta++;

                            adicionarAresta(aresta);

                            sair = true;
                            break;
                        }
                        case '0': {
                            System.out.println("\nRetornando ao menu principal");
                            codGrafo--;
                            return false;
                        }

                        default: {
                            System.out.println("\nEntrada invalida, precione 's' para sim," +
                                    " 'n' para nao, ou '0' para sair");
                        }
                    }

                }

            }
        }
        return true;
    }

    public void modificaGrauDosVerticesMais(Vertice emissor, Vertice receptor) {
        if (!emissor.getCode().equals(receptor.getCode())) {
            emissor.addGrau(1);
            receptor.addGrau(1);
        } else
            emissor.addGrau(1);
    }

    public void modificaGrauDosVerticesMenos(Vertice emissor, Vertice receptor) {
        if (!emissor.getCode().equals(receptor.getCode())) {
            emissor.subGrau(1);
            receptor.subGrau(1);
        } else
            emissor.subGrau(1);
    }

    public String formatarGrafoEmString(Grafo grafo) {
        ArrayList<Aresta> arestas = grafo.getArestas();
        ArrayList<Vertice> vertices = grafo.getVertices();

        String grafoComponents = "";

        for (Vertice vertice : vertices) {
            if (grafo.verificaVerticeSolitario(vertice)) {
                grafoComponents += vertice.getNome() + ";\n";
            }
        }

        for (Aresta aresta : arestas) {
            String nomeEmissor = aresta.getEmissor().getNome();
            String nomeReceptor = aresta.getReceptor().getNome();
            String peso = Float.toString(aresta.getPeso());

            if (!peso.equals("0.0")) {
                grafoComponents += (nomeEmissor + " -- " + nomeReceptor + " [label= " + peso + "]"
                        + ";\n");
            } else {
                grafoComponents += (nomeEmissor + " -- " + nomeReceptor + ";\n");
            }

        }

        String grafoString = "graph " + grafo.getNome() + "{\n" + grafoComponents + "}";

        return grafoString;

    }

    public HashMap<String, ArrayList<String>> gerarMatrizDeDistancia() {
        HashMap<String, ArrayList<String>> matrizDistancia = new LinkedHashMap<>();

        matrizDistancia.put("-", getNomeVertices());

        for (Vertice vertice : this.vertices) {
            matrizDistancia.put(vertice.getNome(), gerarLinhasMatrizDeDistancia(vertice));
        }

        return matrizDistancia;
    }

    private ArrayList<String> gerarLinhasMatrizDeDistancia(Vertice vertice) {
        ArrayList<String> pesos = new ArrayList<>();
        ArrayList<Aresta> arestas = encontrarTodasArestasDoVertice(vertice);

        for (Vertice verticeAtual : this.vertices) {
            if (verticeAtual.getNome().equals(vertice.getNome())) {
                pesos.add("0");
                continue;
            }

            if (retornaPesos(verticeAtual, arestas) != null) {
                pesos.add(retornaPesos(verticeAtual, arestas));
            } else {
                pesos.add(null);
            }

        }

        return pesos;

    }

    private String retornaPesos(Vertice vertice, ArrayList<Aresta> arestas) {

        for (Aresta aresta : arestas) {

            if (aresta.getEmissor().getNome().equals(vertice.getNome()) ||
                    aresta.getReceptor().getNome().equals(vertice.getNome())) {
                return Float.toString(aresta.getPeso());

            }

        }

        return null;
    }

    // ----------------------------------------------
    public ArrayList<Aresta> gerarArvoreMinima() {
        ArrayList<Aresta> arvoreMinima = new ArrayList<>();
        Set<Vertice> verticesVisitados = new HashSet<>();
        ArrayList<Aresta> arestasOrdenadas = ordenarArestasPorPeso();
        Set<String> arestasVisitadas = new HashSet<>(); // Track visited edges

        for (Vertice vertice : this.vertices) {
            if (!verticesVisitados.contains(vertice)) {
                buscaFolhas(vertice, null, verticesVisitados, arvoreMinima, arestasOrdenadas, arestasVisitadas);
            }
        }

        return arvoreMinima;
    }

    private void buscaFolhas(
            Vertice verticeAtual,
            Vertice verticeFechaCiclo,
            Set<Vertice> verticesVisitados,
            ArrayList<Aresta> arvoreMinima,
            ArrayList<Aresta> arestasOrdenadas,
            Set<String> arestasVisitadas) {
        verticesVisitados.add(verticeAtual);

        for (Aresta aresta : arestasOrdenadas) {
            if (aresta.arestaConectaVertice(verticeAtual) && !arestasVisitadas.contains(aresta.getCode())) {
                Vertice verticeVizinho = (aresta.getEmissor().equals(verticeAtual)) ? aresta.getReceptor()
                        : aresta.getEmissor();

                if (!verticesVisitados.contains(verticeVizinho)) {
                    arvoreMinima.add(aresta);
                    arestasVisitadas.add(aresta.getCode());
                    buscaFolhas(verticeVizinho, verticeAtual, verticesVisitados, arvoreMinima, arestasOrdenadas,
                            arestasVisitadas);
                } else if (!verticeVizinho.equals(verticeFechaCiclo)) {
                    // This is a back edge, indicating a cycle
                    // Handle the cycle as needed (you can skip this edge)
                }
            }
        }
    }

    private ArrayList<Aresta> ordenarArestasPorPeso() {
        ArrayList<Aresta> arestasOrdenadas = new ArrayList<>(this.arestas);
        arestasOrdenadas.sort(Comparator.comparing(Aresta::getPeso));
        return arestasOrdenadas;
    }
}