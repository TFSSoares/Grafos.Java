package principal;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public abstract class Grafo {
    protected static int codGrafo = 1;

    private String nome;
    protected ArrayList<Vertice> vertices;
    protected ArrayList<Aresta> arestas;
    private String codigo;

    public Grafo(String nome) {
        this.nome = nome;
        this.codigo = Integer.toString(codGrafo);
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
        codGrafo++;
    }

    public abstract void modificaGrauDosVerticesMais(Vertice emissor, Vertice receptor);

    public abstract void modificaGrauDosVerticesMenos(Vertice emissor, Vertice receptor);

    public abstract boolean gerarArestas();

    public abstract String formatarGrafoEmString(Grafo grafo);

    public abstract HashMap<String, ArrayList<String>> gerarMatrizDeDistancia();

    public String getNome() {
        return this.nome;
    }

    public String getCodeNome() {
        return codigo + "- " + this.nome;
    }

    public String getOrdem() {
        return Integer.toString(this.vertices.size());
    }

    public String getCode() {
        return this.codigo;
    }

    public ArrayList<Vertice> getVertices() {
        return this.vertices;
    }

    public ArrayList<Aresta> getArestas() {
        return this.arestas;
    }

    public ArrayList<String> getNomeVertices() {
        ArrayList<String> nomeVertices = new ArrayList();

        for (Vertice vertice : this.vertices) {
            nomeVertices.add(vertice.getNome());
        }

        return nomeVertices;
    }

    public void adicionarVertice(Vertice vertice) {
        this.vertices.add(vertice);
    }

    public void adicionarAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    public void gerarVertices() {

        while (true) {
            System.out.println("\nInforme a quantidade de vertices que o grafo ira possuir\n");

            Scanner scanner = new Scanner(System.in);
            int numVertice = scanner.nextInt();

            if (numVertice > 0) {
                for (int i = 1; i <= numVertice; i++) {
                    System.out.println("\nDigite o nome do vertice " + i);

                    String nomeVertice = scanner.next();

                    Vertice vertice = new Vertice(nomeVertice, Integer.toString(i));

                    adicionarVertice(vertice);

                }
                break;

            } else {
                System.out.println("\nNao e possivel criar um grafo com " + numVertice + " vertices");
            }

        }

    }

    public boolean verificaVerticeSolitario(Vertice vertice) {
        for (Aresta aresta : arestas) {
            if (aresta.verificaExistenciaDeVertice(vertice)) {
                return false;
            }
        }

        return true;
    }

    // private boolean arestaExisteEntreVertices(Vertice vertice1, Vertice vertice2)
    // {
    // for (Aresta aresta : arestas) {
    // if (aresta.arestaExiste(vertice1, vertice2))
    // return true;
    // }

    // return false;
    // }

    public Vertice encontrarVerticeEmVertices(Vertice vertice, ArrayList<Vertice> vertices) {
        for (Vertice verticeEmVertices : vertices) {
            if (verticeEmVertices.getCode().equals(vertice.getCode())) {
                return verticeEmVertices;
            }
        }

        return null;
    }

    public ArrayList<Aresta> encontrarArestasPorVerticeEmissor(Vertice vertice) {
        ArrayList<Aresta> arestasEncontradas = new ArrayList<Aresta>();

        for (Aresta aresta : arestas) {
            if (vertice.getCode().equals(aresta.getEmissor().getCode())) {
                arestasEncontradas.add(aresta);
            }
        }

        return arestasEncontradas;
    }

    public ArrayList<Aresta> encontrarArestasPorVerticeReceptor(Vertice vertice) {
        ArrayList<Aresta> arestasEncontradas = new ArrayList<Aresta>();

        for (Aresta aresta : arestas) {
            if (vertice.getCode().equals(aresta.getReceptor().getCode())) {
                arestasEncontradas.add(aresta);
            }
        }

        return arestasEncontradas;
    }

    public Aresta encontrarVerticeReceptorEmArestas(Vertice vertice, ArrayList<Aresta> arestas) {
        Aresta arestaEncontrada = null;

        for (Aresta aresta : arestas) {
            if (vertice.getCode().equals(aresta.getReceptor().getCode())) {
                if (arestaEncontrada != null && arestaEncontrada.getPeso() > aresta.getPeso()) {
                    arestaEncontrada = aresta;
                } else if (arestaEncontrada == null) {
                    arestaEncontrada = aresta;
                }
            }
        }

        return arestaEncontrada;
    }

    public ArrayList<Aresta> encontrarTodasArestasDoVertice(Vertice vertice) {
        ArrayList<Aresta> arestasEncontradas = new ArrayList<Aresta>();

        for (Aresta aresta : this.arestas) {
            if (aresta.getEmissor().getNome().equals(vertice.getNome()) ||
                    aresta.getReceptor().getNome().equals(vertice.getNome())) {
                arestasEncontradas.add(aresta);
            }
        }
        
        return arestasEncontradas;
    }

    public void encontrarGrauVertice(Scanner scanner) {

        Vertice vertice = encontrarVertice(scanner);

        if (vertice != null) {
            if (vertice.getGrauEmissao() == 0 && vertice.getGrauRecepcao() == 0) {
                System.out.println("\nO grau do vertice " + vertice.getNome() + " e: " + vertice.getGrau());
            } else {
                System.out.println(
                        "\nO grau de EMISSAO vertice " + vertice.getNome() + " e: " + vertice.getGrauEmissao());
                System.out.println(
                        "\nO grau de RECEPCAO vertice " + vertice.getNome() + " e: " + vertice.getGrauRecepcao());
            }

        }

    }

    public Vertice encontrarVertice(Scanner scanner) {
        while (true) {
            System.out.println("\nO grafo informado possui os seguintes vertices:\n ");

            for (Vertice vertice : this.vertices) {
                System.out.println(vertice.getCode() + "- " + vertice.getNome());
            }

            System.out.println("\nInforme o codigo do vertice que voce deseja");
            String codVertice = scanner.next();

            if (codVertice.equals("0"))
                return null;

            Vertice vertice = encontraVerticePorCodigo(codVertice);

            if (vertice != null) {
                return vertice;
            }

            System.out.println("\nVertice nao encontrado");
        }
    }

    public Vertice encontraVerticePorCodigo(String codVertice) {
        for (Vertice vertice : this.vertices) {
            if (vertice.getCode().equals(codVertice)) {
                return vertice;
            }
        }

        return null;
    }

    public Aresta encontrarArestaPorCodigo(String codAresta) {
        for (Aresta aresta : this.arestas) {
            if (aresta.getCode().equals(codAresta)) {
                return aresta;
            }
        }

        return null;
    }

    public boolean verticeExiste(String nomeVertice) {
        for (Vertice vertice : this.vertices) {
            if (vertice.getNome().equals(nomeVertice)) {
                return true;
            }
        }

        return false;
    }

    public Vertice encontrarVerticePorNome(String nomeVertice) {
        for (Vertice vertice : this.vertices) {
            if (vertice.getNome().equals(nomeVertice)) {
                return vertice;
            }
        }

        return null;
    }

    public void salvarGrafo(String nomeGrafo, String grafoString) {
        String path = "./MeusGrafos/";
        String caminhoArquivo = path + nomeGrafo + ".dot";
        File arquivo = new File(caminhoArquivo);

        try {
            boolean salvo = arquivo.createNewFile();
            if (salvo) {
                editarGrafoString(arquivo, grafoString);
                System.out.println("\nGrafo " + nomeGrafo + " salvo com sucesso!");
            } else {
                System.out.println("\nJá existe um grafo com o mesmo nome salvo ");
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o grafo: " + e.getMessage());
        }
    }

    public void editarGrafoString(File arquivo, String grafoString) {
        try {
            FileWriter fw = new FileWriter(arquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(grafoString);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Erro ao criar o grafo: " + e.getMessage());
        }
    }

    public Aresta encontrarAresta(Scanner scanner) { // CONFIGURAR PRA RETORNAR ARESTA ESCOLHIDA

        while (true) {
            System.out.println("\nO grafo informado possui as seguintes arestas:\n ");

            for (Aresta aresta : this.arestas) {
                System.out.println(
                        aresta.getCode() + "- " + aresta.getEmissor().getNome() + aresta.getReceptor().getNome());
            }

            System.out.println("\nInforme o codigo da aresta que deseja selecionar");
            String codAresta = scanner.next();

            if (codAresta.equals("0")) {
                return null;
            }
            Aresta aresta = encontrarArestaPorCodigo(codAresta);

            if (aresta != null) {
                return aresta;
            }

            System.out.println("\nAresta nao encontrada");
        }

    }

    public void adicionarNovoVertice(Scanner scanner) {
        String nomeNovoVertice;
        String codigoNovoVertice;

        System.out.println("Digite o nome do novo vertice:");
        nomeNovoVertice = scanner.next();

        int codigoInt = vertices.size() + 1;

        codigoNovoVertice = Integer.toString(codigoInt);

        Vertice novoVertice = new Vertice(nomeNovoVertice, codigoNovoVertice);
        vertices.add(novoVertice);

        System.out.println("Vértice criado");

    }

    public void adicionarNovaAresta(Scanner scanner) {
        System.out.println("\nO grafo sendo editado possui os seguintes vertices:\n ");
        for (Vertice vertice : this.vertices) {
            System.out.println(vertice.getCode() + "- " + vertice.getNome());
        }

        System.out.println("Digite o CODIGO do vértice EMISSOR da nova aresta");
        String escolhaUsuario1 = scanner.next();
        Vertice emissor = encontraVerticePorCodigo(escolhaUsuario1);

        System.out.println("Digite o CODIGO do vértice RECEPTOR da nova aresta");
        String escolhaUsuario2 = scanner.next();
        Vertice receptor = encontraVerticePorCodigo(escolhaUsuario2);

        System.out.println("Digite o PESO dessa nova aresta");
        Float novopeso = scanner.nextFloat();

        int codigoInt = arestas.size() + 1;
        String novocodigo = Integer.toString(codigoInt);

        System.out.println("novo codigo dessa aresta criada: " + novocodigo);

        Aresta novaAresta = new Aresta(novopeso, emissor, receptor, novocodigo);
        arestas.add(novaAresta);

        modificaGrauDosVerticesMais(emissor, receptor);
        System.out.println("Nova aresta criada");
    }

    public void removerVertice(Vertice vertice) {
        // Criando um Iterator para percorrer as arestas e removê-las conforme
        // necessário
        Iterator<Aresta> iterator = this.arestas.iterator();

        while (iterator.hasNext()) {
            Aresta aresta = iterator.next();
            Vertice emissor = aresta.getEmissor();
            Vertice receptor = aresta.getReceptor();

            if (emissor.equals(vertice) || receptor.equals(vertice)) {
                modificaGrauDosVerticesMenos(emissor, receptor);
                iterator.remove(); // Remove a aresta usando o Iterator
            }
        }

        // Agora você pode deletar o vértice
        vertices.remove(vertice);

        System.out.println("Vértice deletado");
    }

    public void removerAresta(Aresta aresta) {
        modificaGrauDosVerticesMenos(aresta.getEmissor(), aresta.getReceptor());
        this.arestas.remove(aresta);
        System.out.println("Aresta deletada");
    }

    public void encontrarMenorCaminho(Vertice verticeEscolhido, HashMap<String, ArrayList<String>> matrizDistancia) {
        HashMap<String, Double> distancias = new HashMap<>();
        HashMap<String, String> anteriores = new HashMap<>();
        PriorityQueue<String> filaDeMenorDistancia = new PriorityQueue<>((v1, v2) -> {
            double dist1 = distancias.getOrDefault(v1, Double.MAX_VALUE);
            double dist2 = distancias.getOrDefault(v2, Double.MAX_VALUE);
            return Double.compare(dist1, dist2);
        });

        for (String vertice : matrizDistancia.keySet()) {
            if (vertice.equals(verticeEscolhido.getNome())) {
                distancias.put(vertice, 0.0);
            } else {
                distancias.put(vertice, Double.MAX_VALUE);
            }
            filaDeMenorDistancia.add(vertice);
            anteriores.put(vertice, null);
        }

        while (!filaDeMenorDistancia.isEmpty()) {
            String verticeAtual = filaDeMenorDistancia.poll();

            if (verticeAtual == "-")
                continue;

            ArrayList<String> vizinhanca = matrizDistancia.get("-");
            ArrayList<String> pesos = matrizDistancia.get(verticeAtual);

            if (vizinhanca != null && pesos != null) {
                for (int i = 0; i < vizinhanca.size(); i++) {
                    String vizinho = vizinhanca.get(i);
                    String distanceStr = pesos.get(i);

                    if (distanceStr != null) {
                        double alt = distancias.get(verticeAtual) + Double.parseDouble(distanceStr);
                        if (alt < distancias.get(vizinho)) {
                            distancias.put(vizinho, alt);
                            anteriores.put(vizinho, verticeAtual);
                            filaDeMenorDistancia.remove(vizinho);
                            filaDeMenorDistancia.add(vizinho);
                        }
                    }
                }
            }
        }

        for (String vertice : matrizDistancia.keySet()) {
            if (distancias.get(vertice) == Double.MAX_VALUE) {
                distancias.put(vertice, null);
            }
        }

        for (String vertice : matrizDistancia.keySet()) {
            if (vertice == "-")
                continue;
            System.out.println("A menor distância de " + verticeEscolhido.getNome() + " para " + vertice + " é: "
                    + distancias.get(vertice));
        }
    }

    public ArrayList<Aresta> ordenarArestasTopologicamente() {
        Stack<Vertice> stack = new Stack<>();
        Set<Vertice> visited = new HashSet<>();

        for (Vertice vertice : vertices) {
            if (!visited.contains(vertice)) {
                topologicalSortUtil(vertice, visited, stack);
            }
        }

        Set<String> addedEdges = new HashSet<>();
        ArrayList<Aresta> result = new ArrayList<>();

        while (!stack.isEmpty()) {
            Vertice vertice = stack.pop();
            ArrayList<Aresta> arestasDoVertice = encontrarTodasArestasDoVertice(vertice);

            for (Aresta aresta : arestasDoVertice) {
                if (!addedEdges.contains(aresta.getCode())) {
                    result.add(aresta);
                    addedEdges.add(aresta.getCode());
                }
            }
        }

        return result;
    }

    private void topologicalSortUtil(Vertice vertice, Set<Vertice> visited, Stack<Vertice> stack) {
        visited.add(vertice);

        ArrayList<Aresta> arestasDoVertice = encontrarTodasArestasDoVertice(vertice);
        for (Aresta aresta : arestasDoVertice) {
            Vertice vizinho = aresta.getReceptor();
            if (!visited.contains(vizinho)) {
                topologicalSortUtil(vizinho, visited, stack);
            }
        }

        stack.push(vertice);
    }

    public Vertice depthFirstSearch(String nomeVertice) {
        Set<Vertice> visited = new HashSet<>();
        for (Vertice vertice : vertices) {
            if (!visited.contains(vertice)) {
                Vertice result = depthFirstSearchUtil(vertice, nomeVertice, visited);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;  // Vertex not found
    }

    private Vertice depthFirstSearchUtil(Vertice vertice, String nomeVertice, Set<Vertice> visited) {
        visited.add(vertice);

        if (vertice.getNome().equals(nomeVertice)) {
            return vertice;  // Vertex found
        }

        ArrayList<Aresta> arestasDoVertice = encontrarTodasArestasDoVertice(vertice);
        for (Aresta aresta : arestasDoVertice) {
            Vertice vizinho = aresta.getReceptor();
            if (!visited.contains(vizinho)) {
                Vertice result = depthFirstSearchUtil(vizinho, nomeVertice, visited);
                if (result != null) {
                    return result;  // Vertex found
                }
            }
        }

        return null;  // Vertex not found
    }

}
