package principal;
import java.util.ArrayList;
import java.util.Scanner;

public class Navegar {
    public void navTelaPrincipal(char escolhaUsuario, Tela tela) {
        switch (escolhaUsuario) {
            case '1': {
                navTelaNovoGrafo(tela);
                break;
            }

            case '2': {
                navTelaAbrirGrafo(tela);
                break;
            }

            case '0':

                break;
        }
    }

    public void navTelaNovoGrafo(Tela tela) {
        tela.telaNovoGrafo();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);
        switch (escolhaUsuario) {
            case '1': {
                navTelaCriarGrafo(tela);
                break;
            }

            case '0':

                break;
        }
    }

    public void navTelaCriarGrafo(Tela tela) {
        tela.telaCriarGrafo();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);
        switch (escolhaUsuario) {
            case '1': {
                System.out.println("Digite o nome do grafo");
                String escolhaUsuario2 = scanner.next();
                GrafoOrientado grafoOrientado = new GrafoOrientado(escolhaUsuario2);
                grafoOrientado.gerarVertices();

                if (!grafoOrientado.gerarArestas())
                    break;

                ColecaoDeGrafos.addGrafo(grafoOrientado);

                System.out.println("\nGrafo criado com sucesso!");

                break;
            }
            case '2': {
                System.out.println("Digite o nome do grafo");
                String escolhaUsuario2 = scanner.next();

                GrafoNaoOrientado grafoNaoOrientado = new GrafoNaoOrientado(escolhaUsuario2);
                grafoNaoOrientado.gerarVertices();

                if (!grafoNaoOrientado.gerarArestas())
                    break;

                ColecaoDeGrafos.addGrafo(grafoNaoOrientado);

                System.out.println("\nGrafo criado com sucesso!");

                break;
            }

            case '0':

                navTelaNovoGrafo(tela);
                break;
        }
    }

    public void navTelaAbrirGrafo(Tela tela) {
        tela.telaAbrirGrafo();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);

        switch (escolhaUsuario) {
            case '1': {
                Grafo grafo = ColecaoDeGrafos.selecionarGrafoExistente(scanner);

                if (grafo != null && grafo instanceof GrafoNaoOrientado) {
                    navTelaInformacaoGrafoNaoOrientado(tela, grafo);
                } else if (grafo != null && grafo instanceof GrafoOrientado) {
                    navTelaInformacaoGrafoOrientado(tela, grafo);
                }
                break;
            }

            case '2': {
                MetodosAdicionaisGrafo.carregarGrafoString(scanner);

                break;
            }

            case '0': {
                break;
            }
        }

    }

    public void navTelaInformacaoGrafoNaoOrientado(Tela tela, Grafo grafo) {
        tela.telaInformacaoGrafoNaoOrientado();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);

        switch (escolhaUsuario) {
            case '1': {
                if (grafo != null)
                    System.out.println("\nO grafo informado possui ordem: " + grafo.getOrdem());

                navTelaInformacaoGrafoNaoOrientado(tela, grafo);
                break;
            }

            case '2': {
                grafo.encontrarGrauVertice(scanner);
                navTelaInformacaoGrafoNaoOrientado(tela, grafo);
                break;

            }

            case '3': {
                String grafoString = grafo.formatarGrafoEmString(grafo);
                grafo.salvarGrafo(grafo.getNome(), grafoString);
                break;
            }

            case '4': {
                navTelaEditarGrafo(tela, grafo);
                break;
            }

            case '5':{
                navTelaProblemas(tela, grafo);
                navTelaInformacaoGrafoNaoOrientado(tela, grafo);
                break;
            }

            case '6':{

                GrafoNaoOrientado grafoMinimo = new GrafoNaoOrientado(grafo.getNome()+ "Minimo");
                GrafoNaoOrientado novoGrafo = (GrafoNaoOrientado) grafo;

                ArrayList<Aresta> arvoreMinima = novoGrafo.gerarArvoreMinima();
                ArrayList<Vertice> vertices = grafo.getVertices();

//                for(Aresta aresta : arvoreMinima){
//                    System.out.println(aresta.getEmissor().getNome() + " -> " +
//                        aresta.getReceptor().getNome());
//                }

                for(Vertice vertice: vertices)
                    grafoMinimo.adicionarVertice(vertice);

                for(Aresta aresta: arvoreMinima){
                    grafoMinimo.adicionarAresta(aresta);
                }

                ColecaoDeGrafos.addGrafo(grafoMinimo);

                break;
            }

            case '7':{
                Principal.gerarImagemGrafo(grafo.getNome(), grafo.formatarGrafoEmString(grafo));
                break;
            }

            case '8':{
                break;
            }
            case '0': {
                break;
            }
        }
    }

    public void navTelaInformacaoGrafoOrientado(Tela tela, Grafo grafo) {
        tela.telaInformacaoGrafoOrientado();
        Scanner scanner = new Scanner(System.in);
        String escolhaUsuario = scanner.next();

        switch (escolhaUsuario) {
            case "1": {
                if (grafo != null)
                    System.out.println("\nO grafo informado possui ordem: " + grafo.getOrdem());

                navTelaInformacaoGrafoOrientado(tela, grafo);
                break;
            }

            case "2": {
                grafo.encontrarGrauVertice(scanner);
                navTelaInformacaoGrafoOrientado(tela, grafo);
                break;

            }

            case "3": {
                ArrayList<Vertice> vertices = grafo.getVertices();
                ArrayList<Vertice> verticesFonte = new ArrayList<Vertice>();

                for (Vertice vertice : vertices) {
                    if (vertice.getGrauRecepcao() == 0 && vertice.getGrauEmissao() > 0)
                        verticesFonte.add(vertice);
                }

                if (!verticesFonte.isEmpty()) {
                    System.out.println("\nOs vertices fontes do grafo são: ");
                    for (Vertice vertice : verticesFonte) {
                        System.out.println(vertice.getNome());
                    }
                    navTelaInformacaoGrafoOrientado(tela, grafo);

                } else {
                    System.out.println("\nO grafo selecionado não possui vertices fonte");
                    navTelaInformacaoGrafoOrientado(tela, grafo);
                }
                break;
            }

            case "4": {
                ArrayList<Vertice> vertices = grafo.getVertices();
                ArrayList<Vertice> verticesSumidouro = new ArrayList<Vertice>();

                for (Vertice vertice : vertices) {
                    if (vertice.getGrauEmissao() == 0 && vertice.getGrauRecepcao() > 0)
                        verticesSumidouro.add(vertice);
                }

                if (!verticesSumidouro.isEmpty()) {
                    System.out.println("\nOs vertices sumidouros do grafo são: ");
                    for (Vertice vertice : verticesSumidouro) {
                        System.out.println(vertice.getNome());
                    }
                    navTelaInformacaoGrafoOrientado(tela, grafo);

                } else {
                    System.out.println("\nO grafo selecionado não possui vertices sumidouros");
                    navTelaInformacaoGrafoOrientado(tela, grafo);
                }
                break;

            }

            case "5": {
                // chamar metodo fecho td
                
                Vertice vertice = grafo.encontrarVertice(scanner);
                System.out.println("\nO fecho transitivo direto do vertice: " + vertice.getNome() + " é dado por:");
                System.out.println("\n{" + ((GrafoOrientado) grafo).listarFechoTd(vertice) + "}\n");
                ((GrafoOrientado) grafo).limparArestasUtilizadas();
                navTelaInformacaoGrafoOrientado(tela, grafo);
                
                break;
            }

            case "6": {
                Vertice vertice = grafo.encontrarVertice(scanner);
                System.out.println("\nO fecho transitivo indireto do vertice: " + vertice.getNome() + " é dado por:");
                System.out.println("\n{" + ((GrafoOrientado) grafo).listarFechoTi(vertice) + "}\n");
                ((GrafoOrientado) grafo).limparArestasUtilizadas();
                navTelaInformacaoGrafoOrientado(tela, grafo);

                break;
            }

            case "7": {
                String grafoString = grafo.formatarGrafoEmString(grafo);
                grafo.salvarGrafo(grafo.getNome(), grafoString);
                break;
            }

            case "8": {
                // chamar metodo editar grafo
                navTelaEditarGrafo(tela, grafo);
                break;
            }

            case "9":{
                navTelaProblemas(tela, grafo);
                navTelaInformacaoGrafoOrientado(tela, grafo);
                break;
            }

            case "10":{
                Principal.gerarImagemGrafo(grafo.getNome(), grafo.formatarGrafoEmString(grafo));
                break;
            }

            case "r": {
                //cria um novo grafo reduzido a partir do grafo acessado
                ((GrafoOrientado) grafo).criarCopiaReduzida();
                break;
            }

            case "0": {
                break;
            }
        }
    }

    public void navTelaEditarGrafo(Tela tela, Grafo grafo) {
        tela.telaEditarGrafo();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);

        switch (escolhaUsuario) {
            case '1': {
                // adicionar vertice
                grafo.adicionarNovoVertice(scanner);
                break;
            }
            case '2': {
                // adicionar aresta
                 grafo.adicionarNovaAresta(scanner);
                break;
            }
            case '3': {
                //remover vertice
                Vertice vertice = grafo.encontrarVertice(scanner);
                grafo.removerVertice(vertice);
                break;
            }
            case '4': {
                // remover aresta
                Aresta aresta = grafo.encontrarAresta(scanner);
                grafo.removerAresta(aresta);
                break;
            }
            case '0': {
                break;
            }

        }

    }

    public void navTelaProblemas(Tela tela, Grafo grafo){
        tela.telaProblemas();
        Scanner scanner = new Scanner(System.in);
        char escolhaUsuario = scanner.next().charAt(0);

        switch (escolhaUsuario) {
            case '1': {
                Vertice verticeEscolhido = grafo.encontrarVertice(scanner);
                grafo.encontrarMenorCaminho(verticeEscolhido, grafo.gerarMatrizDeDistancia());
                
                break;
            }

            case '2':{
                ArrayList<Aresta> arestas = grafo.ordenarArestasTopologicamente();
                ArrayList<Vertice> vertices = grafo.getVertices();

                GrafoOrientado grafoTopologico = new GrafoOrientado(grafo.getNome()+ "Topologico");

                for(Vertice vertice: vertices)
                    grafoTopologico.adicionarVertice(vertice);

                for(Aresta aresta: arestas)
                    grafoTopologico.adicionarAresta(aresta);

                ColecaoDeGrafos.addGrafo(grafoTopologico);
                navTelaAbrirGrafo(tela);
                break;
            }

            case '3':{
                System.out.println("Digite o nome do vértice");
                String nomeVertice = scanner.next();

                if(grafo.depthFirstSearch(nomeVertice) != null){
                    System.out.println("\nVertice encontrado no grafo");
                } else{
                    System.out.println("\nVertice não encontrado no grafo");
                }

                break;

            }

            case '0':{
                break;
            }
        }
    }
}
