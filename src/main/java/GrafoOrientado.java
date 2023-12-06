import java.util.*;

public class GrafoOrientado extends Grafo {
    private ArrayList<Aresta> arestasUtilizadas = new ArrayList<Aresta>();

    public GrafoOrientado(String nome) {
        super(nome);

    }

    public boolean gerarArestas() {
        Scanner scanner = new Scanner(System.in);
        int codigoAresta = 1;
        for (Vertice vertice1 : vertices) {
            for (Vertice vertice2 : vertices) {
                boolean sair = false;

                while (!sair) {
                    System.out.println("\nO vertice '" + vertice1.getNome() + "'" +
                            " possui relacao com o vertice '" + vertice2.getNome() + "'" + " ? s/n");

                    char escolhaUsuario = scanner.next().charAt(0);

                    switch (escolhaUsuario) {
                        case 'n':
                            sair = true;
                            break;

                        case 's': {
                            System.out.println("Digite o peso da relacao");
                            float inputPeso = scanner.nextFloat();

                            modificaGrauDosVerticesMais(vertice1, vertice2);

                            Aresta aresta = new Aresta(inputPeso, vertice1, vertice2, Integer.toString(codigoAresta));
                            codigoAresta++;

                            adicionarAresta(aresta);

                            sair = true;
                            break;
                            /*
                             * System.out.
                             * println("escolha qual é o vertice EMISSOR desta relacao: ([1] ou [2])");
                             * System.out.println("1-" + vertice1.getNome() + "\n" +
                             * "2-" + vertice2.getNome());
                             * 
                             * char inputEmissor = scanner.next().charAt(0);
                             * 
                             * switch (inputEmissor) {
                             * case '1': {
                             * adicionarAresta(inputPeso, vertice1, vertice2);
                             * break;
                             * }
                             * 
                             * case '2':{
                             * adicionarAresta(inputPeso, vertice2, vertice1);
                             * break;
                             * }
                             * }
                             */
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
        if (emissor.getCode() == receptor.getCode()) {
            System.out.println("erro de duplicacao");
        }
        emissor.addGrauEmissao(1);
        receptor.addGrauRecepcao(1);
    }

    public void modificaGrauDosVerticesMenos(Vertice emissor, Vertice receptor) {
        if (emissor.getCode() == receptor.getCode()) {
            System.out.println("erro de duplicacao");
        }
        emissor.subGrauEmissao(1);
        receptor.subGrauRecepcao(1);
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
                grafoComponents += (nomeEmissor + " -> " + nomeReceptor + " [label= " + peso + "]"
                        + ";\n");
            } else {
                grafoComponents += (nomeEmissor + " -> " + nomeReceptor + ";\n");
            }

        }

        String grafoString = "digraph " + grafo.getNome() + "{\n" + grafoComponents + "}";

        return grafoString;
    }

    public String listarFechoTd(Vertice vertice) {
        ArrayList<Aresta> arestasDoVertice = encontrarArestasPorVerticeEmissor(vertice);

        if (arestasDoVertice.size() == 0) {
            return vertice.getNome();
        }

        StringBuilder listaFechoTd = new StringBuilder();

        for (Aresta aresta : arestasDoVertice) {
            if (arestaUtilizada(aresta))
                return "";

            arestasUtilizadas.add(aresta);
            String fechoTd = listarFechoTd(aresta.getReceptor());

            if (!fechoTd.isEmpty()) {
                if (listaFechoTd.length() > 0) {
                    listaFechoTd.append(", ");
                }
                listaFechoTd.append(fechoTd);
            }
        }

        if (listaFechoTd.length() > 0) {
            listaFechoTd.insert(0, vertice.getNome() + ", ");
        } else {
            listaFechoTd.append(vertice.getNome());
        }

        return listaFechoTd.toString();
    }

    public String listarFechoTi(Vertice vertice) {
        ArrayList<Aresta> arestasDoVertice = encontrarArestasPorVerticeReceptor(vertice);

        if (arestasDoVertice.size() == 0) {
            return vertice.getNome();
        }

        System.out.println(vertice.getNome());
        StringBuilder listaFechoTi = new StringBuilder();

        for (Aresta aresta : arestasDoVertice) {
            if (arestaUtilizada(aresta))
                return "";

            arestasUtilizadas.add(aresta);
            String fechoTi = listarFechoTi(aresta.getEmissor());

            if (!fechoTi.isEmpty()) {
                if (listaFechoTi.length() > 0) {
                    listaFechoTi.append(", ");
                }
                listaFechoTi.append(fechoTi);
            }
        }

        if (listaFechoTi.length() > 0) {
            listaFechoTi.insert(0, vertice.getNome() + ", ");
        } else {
            listaFechoTi.append(vertice.getNome());
        }

        return listaFechoTi.toString();
    }

    public boolean arestaUtilizada(Aresta arestaFecho) {
        for (Aresta aresta : this.arestasUtilizadas) {
            if (arestaFecho.getCode().equals(aresta.getCode())) {
                return true;
            }
        }

        return false;
    }

    public void limparArestasUtilizadas() {
        this.arestasUtilizadas.clear();
    }

    public HashMap<String, ArrayList<String>> gerarMatrizDeDistancia() {
        HashMap<String, ArrayList<String>> matrizDistancia = new LinkedHashMap<>();

        matrizDistancia.put("-", getNomeVertices());

        for (Vertice vertice : this.vertices) {
            matrizDistancia.put(vertice.getNome(), gerarLinhasMatrizDeDistancia(vertice));
        }

        // for (String key : matrizDistancia.keySet()) {
        //     ArrayList<String> values = matrizDistancia.get(key);
        //     System.out.println("Key: " + key + ", Values: " + values);
        // }

        return matrizDistancia;
    }

    private ArrayList<String> gerarLinhasMatrizDeDistancia(Vertice vertice){
        ArrayList<String> pesos = new ArrayList<>();
        ArrayList<Aresta> arestas = encontrarArestasPorVerticeEmissor(vertice);

        for(Vertice receptor : this.vertices){
            Aresta aresta = encontrarVerticeReceptorEmArestas(receptor, arestas);

            if(aresta != null){
                pesos.add(Float.toString(aresta.getPeso()));
            } else if(receptor.getCode().equals(vertice.getCode())){
                pesos.add("0");
            } else{
                pesos.add(null);
            }
        }

        return pesos;

    }

    public String arvoreGeradora(){
        return "a";
    }

}
