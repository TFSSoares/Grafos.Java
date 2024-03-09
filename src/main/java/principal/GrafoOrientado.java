package principal;
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

    public void criarCopiaReduzida(){

        ArrayList<Vertice> copiaVertices = new ArrayList<Vertice>();
        ArrayList<String> novosVertices = new ArrayList<String>();
        copiaVertices = getVertices();
        String FTD = new String();
        String FTI = new String();
        String intersecao = new String();


        for (Vertice vertice : copiaVertices) {
             FTD = listarFechoTd(vertice);
            limparArestasUtilizadas();
             FTI = listarFechoTi(vertice);
            limparArestasUtilizadas();

            Set<String> conjuntoFTD = new HashSet<>(Arrays.asList(FTD.split(", ")));
            Set<String> conjuntoFTI = new HashSet<>(Arrays.asList(FTI.split(", ")));
        
            conjuntoFTD.retainAll(conjuntoFTI);
            intersecao = String.join(", ", conjuntoFTD);
    
            novosVertices.add(intersecao);
        }

        ArrayList<String> novosVerticesFiltrado = filtrarArrayList(novosVertices);

        //criar o novo grafo reduzido
        GrafoOrientado grafoReduzido = new GrafoOrientado(this.getNome() + "REDUZIDO");
        
        
        //criar os vértices do grafo reduzido
        int codigo = 1;
        for (String nome : novosVerticesFiltrado ){
            Vertice verticeAglomerado = new Vertice(nome, Integer.toString(codigo));
            grafoReduzido.adicionarVertice(verticeAglomerado);
            codigo++;
        }


        //criar as arestas do grafo reduzido
        ArrayList<Aresta> copiaArestasOriginal = this.getArestas();
        
        //filtra o arraylist copiaArestasOriginal para restar apenas as arestas entre componentes f-conexas
        filtrarArestas(copiaArestasOriginal, novosVerticesFiltrado);

        Map<String, Vertice> mapeamentoVertices = new HashMap<>();
        int codigoMap = 1;
        for (String nome : novosVerticesFiltrado) {
            Vertice verticeAglomerado = new Vertice(nome, Integer.toString(codigoMap));
            mapeamentoVertices.put(nome, verticeAglomerado);
            grafoReduzido.adicionarVertice(verticeAglomerado);
            codigoMap++;
        }
        
        // Cria e insere as novas arestas no grafo reduzido
        int codigoNovaAresta = 1;
        for (Aresta aresta : copiaArestasOriginal) {
            Vertice emissorOriginal = aresta.getEmissor();
            Vertice receptorOriginal = aresta.getReceptor();
        
            // Verifica se os vértices originais da aresta estão no mapeamento
            if (emissorOriginal != null && receptorOriginal != null &&
                mapeamentoVertices.containsKey(emissorOriginal.getNome()) &&
                mapeamentoVertices.containsKey(receptorOriginal.getNome())) {
                
                Vertice novoEmissor = mapeamentoVertices.get(emissorOriginal.getNome());
                Vertice novoReceptor = mapeamentoVertices.get(receptorOriginal.getNome());
        
                // Verifica se os novos vértices não são nulos
                if (novoEmissor != null && novoReceptor != null) {
                    Aresta novaAresta = new Aresta(aresta.getPeso(), novoEmissor, novoReceptor, Integer.toString(codigoNovaAresta));
                    grafoReduzido.adicionarAresta(novaAresta);
                    codigoNovaAresta++;
                }
            }
        }

        ColecaoDeGrafos.addGrafo(grafoReduzido);

        System.out.println(" ");
        System.out.println("===> Novo grafo reduzido criado");
        System.out.println(" ");
        
    }





    private static ArrayList<String> filtrarArrayList(ArrayList<String> novosVertices) {
        Set<String> conjuntosUnicos = new HashSet<>();
        ArrayList<String> arrayListFiltrado = new ArrayList<>();

        for (String str : novosVertices) {
            // Converter cada string para um conjunto ordenado de elementos
            String[] elementos = str.split(", ");
            Arrays.sort(elementos);
            String conjuntoOrdenado = String.join(", ", elementos);

            // Adicionar apenas conjuntos únicos à lista filtrada
            if (conjuntosUnicos.add(conjuntoOrdenado)) {
                arrayListFiltrado.add(str);
            }
        }

        return arrayListFiltrado;
    }



    private static void filtrarArestas(ArrayList<Aresta> copiaArestasOriginal, ArrayList<String> novosVerticesFiltrado) {
        Iterator<Aresta> iterator = copiaArestasOriginal.iterator();

        while (iterator.hasNext()) {
            Aresta arestaIndividual = iterator.next();

            for (String nomeAglomerado : novosVerticesFiltrado) {
                // Verifica se todos os caracteres do nome da aresta estão presentes em nomeAglomerado
                if (contemTodosCaracteres(nomeAglomerado, arestaIndividual.getNome())) {
                    iterator.remove(); // Remove a aresta se a condição for atendida
                    break; // Não é necessário continuar verificando os outros aglomerados
                }
            }
        }
    }



    private static boolean contemTodosCaracteres(String aglomerado, String nomeAresta) {
        // Verifica se todos os caracteres do nome da aresta estão presentes no aglomerado
        for (char c : nomeAresta.toCharArray()) {
            if (aglomerado.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }


}
