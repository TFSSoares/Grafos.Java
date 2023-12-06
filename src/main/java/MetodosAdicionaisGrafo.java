import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class MetodosAdicionaisGrafo {
    private static int codigoVertice = 1;
    private static int codigoAresta = 1;

    public static void carregarGrafoString(Scanner scanner) {
        String path = "../../../Meus grafos/";
        ArrayList<String> grafosNomes = pegarNomeGrafos(path);

        boolean sair = false;
        while (!sair) {
            mostrarNomeGrafo(grafosNomes);
            System.out.println("0- voltar");
            System.out.println("\n Digite o número associado ao grafo que deseja abrir");
            String escolhaUsuario = scanner.next();

            System.out.println(escolhaUsuario);
            if(escolhaUsuario.equals("0")) return;

            String escolhaEncontrada = encontrarGrafoEscolhido(grafosNomes, escolhaUsuario);

            if (escolhaEncontrada.length() > 0) {
                if(verificaSeGrafoJaEstaAberto(escolhaEncontrada)){
                    System.out.println("\nO grafo já está aberto");
                    return;
                }
                sair = true;
                File arquivo = new File(path + escolhaEncontrada);

                try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    ArrayList<String> linhas = new ArrayList<String>();
                    String linha;

                    while ((linha = reader.readLine()) != null) {
                        linhas.add(linha);
                    }

                    gerarGrafo(escolhaEncontrada, linhas);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("\nGrafo não encontrado\n");
            }

        }
    }

    public static ArrayList<String> pegarNomeGrafos(String path) {
        File diretorio = new File(path);
        File[] arquivos = diretorio.listFiles();

        ArrayList<String> grafosNomes = new ArrayList<String>();

        if (arquivos != null) {
            int codigo = 1;
            for (File arquivo : arquivos) {
                String nomeArquivo = arquivo.getName();

                String[] partes = nomeArquivo.split("\\.");

                String codigoString = Integer.toString(codigo);

                if (partes.length > 0) {
                    grafosNomes.add(codigoString + "- " + partes[0]);
                } else {
                    grafosNomes.add(codigoString + "- " + nomeArquivo);
                }
                codigo++;
            }
        } else {
            System.out.println("\nNão existem grafo no diretório");
        }

        return grafosNomes;
    }

    public static void mostrarNomeGrafo(ArrayList<String> grafosNomes) {
        System.out.println("Você possui os grafos:\n");
        for (String nome : grafosNomes) {
            System.out.println(nome);
        }
    }

    public static boolean verificaSeGrafoJaEstaAberto(String nomeArquivo){
        String nomeGrafo = nomeArquivo.split("\\.")[0];
        boolean grafoExiste = ColecaoDeGrafos.grafoExiste(nomeGrafo);

        return grafoExiste;
    }

    public static String encontrarGrafoEscolhido(ArrayList<String> grafosNomes, String codigo) {
        for (String nome : grafosNomes) {
            String[] partesNome = nome.split("- ");
            if (partesNome[0].equals(codigo)) {
                return partesNome[1] + ".dot";
            }
        }

        return "";
    }

    public static void gerarGrafo(String escolhaEncontrada, ArrayList<String> linhas) {
        String nomeGrafo = escolhaEncontrada.split("\\.")[0];
        String tipoGrafo = linhas.get(0).split(" ")[0];

        if (tipoGrafo.equals("graph")) {
            // Gerar grafo NÃO orientado
            System.out.println("Criando grafo não orientado");
            GrafoNaoOrientado novoGrafoNaoOrientado = new GrafoNaoOrientado(nomeGrafo);

            // Criando os vertices
            for (int i = 1; i < (linhas.size() - 1); i++) {
                String linha = linhas.get(i);

                 if(verticeSolitario(linha)){
                    addVerticeAoGrafo(linha.split(";")[0],Integer.toString(codigoVertice),
                        novoGrafoNaoOrientado);
                    continue;
                }
                // pegando o primeiro vertice da linha atual
                String verticeEmissor = linha.split(" -- ")[0];
                // pegando o segundo vertice da linha atual
                String restante = linha.split(" -- ")[1].split(" ")[0];
                String verticeReceptor = restante.split(";")[0];
                // pegando o peso da linha atual
                String peso;

                if (existePeso(linha))
                    peso = linha.split("= ")[1].split("]")[0];
                else
                    peso = "0.0";

                gerandoComponentes(i, verticeEmissor, verticeReceptor, peso, novoGrafoNaoOrientado);
            }

            ColecaoDeGrafos.addGrafo(novoGrafoNaoOrientado);
            codigoVertice = 1;
            codigoAresta = 1;

        } else if (tipoGrafo.equals("digraph")) {
            // Gerar grafo orientado
            System.out.println("Criando grafo orientado");
            GrafoOrientado novoGrafoOrientado = new GrafoOrientado(nomeGrafo);

            // Criando os vertices
            for (int i = 1; i < (linhas.size() - 1); i++) {
                String linha = linhas.get(i);

                if(verticeSolitario(linha)){
                    addVerticeAoGrafo(linha.split(";")[0],Integer.toString(codigoVertice),
                        novoGrafoOrientado);
                    continue;
                }
                // pegando o primeiro vertice da linha atual
                String verticeEmissor = linha.split(" -> ")[0];
                // pegando o segundo vertice da linha atual
                String restante = linha.split(" -> ")[1].split(" ")[0];
                String verticeReceptor = restante.split(";")[0];
                // pegando o peso da linha atual
                String peso;

                if (existePeso(linha))
                    peso = linha.split("= ")[1].split("]")[0];
                else
                    peso = "0.0";

                gerandoComponentes(i, verticeEmissor, verticeReceptor, peso, novoGrafoOrientado);
            }

            ColecaoDeGrafos.addGrafo(novoGrafoOrientado);
            codigoVertice = 1;
            codigoAresta = 1;
        } else {
            System.out.println("Arquivo digitado de maneira incorreta");
        }
    }

    public static boolean existePeso(String linha) {
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '[') {
                return true;
            }
        }

        return false;
    }

    public static boolean verticeSolitario(String linha){
         for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '-') {
                return false;
            }
        }

        return true;
    }

    public static void gerandoComponentes(int index, String verticeEmissor, String verticeReceptor, String peso,
            Grafo grafo) {
        // criando o primeiro vertice
        if (index == 1) {

            gerarPrimeiroVertice(verticeEmissor, verticeReceptor, peso, grafo);

        } else {
            // verificar se o vértice emissor ja existe
            Boolean verticeEmissorJaExiste = grafo.verticeExiste(verticeEmissor);

            if (!verticeEmissorJaExiste) {
                addVerticeAoGrafo(verticeEmissor, Integer.toString(codigoVertice),
                        grafo);
            }

            // verificar se o vértice receptor ja existe
            Boolean verticeReceptorJaExiste = grafo.verticeExiste(verticeReceptor);

            if (!verticeReceptorJaExiste) {
                addVerticeAoGrafo(verticeReceptor, Integer.toString(codigoVertice),
                        grafo);
            }

            // criando a aresta
            Vertice emissor = grafo.encontrarVerticePorNome(verticeEmissor);
            Vertice receptor = grafo.encontrarVerticePorNome(verticeReceptor);

            gerarAresta(Float.parseFloat(peso), emissor, receptor, grafo);

        }
    }

    public static void gerarPrimeiroVertice(String verticeEmissor, String verticeReceptor, String peso, Grafo grafo) {
        addVerticeAoGrafo(verticeEmissor, Integer.toString(codigoVertice), grafo);

        if (!verticeEmissor.equals(verticeReceptor)) {
            addVerticeAoGrafo(verticeReceptor, Integer.toString(codigoVertice), grafo);
            gerarAresta(Float.parseFloat(peso), grafo.encontrarVerticePorNome(verticeEmissor),
                    grafo.encontrarVerticePorNome(verticeReceptor), grafo);

        } else {
            gerarAresta(Float.parseFloat(peso), grafo.encontrarVerticePorNome(verticeEmissor),
                    grafo.encontrarVerticePorNome(verticeEmissor), grafo);
        }

    }

    public static void gerarAresta(float peso, Vertice emissor, Vertice receptor, Grafo grafo) {
        Aresta aresta = new Aresta(peso, emissor, receptor, Integer.toString(codigoAresta));
        grafo.modificaGrauDosVerticesMais(emissor, receptor);
        grafo.adicionarAresta(aresta);
        codigoAresta++;
    }

    public static void addVerticeAoGrafo(String nomeVertice, String codVertice, Grafo grafo) {
        Vertice vertice = new Vertice(nomeVertice, codVertice);
        codigoVertice++;
        grafo.adicionarVertice(vertice);
    }

}
