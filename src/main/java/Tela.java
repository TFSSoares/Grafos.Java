
public class Tela {
    public void telaPrincipal() {
        System.out.println("\n********** Bem Vindo(a) Ao Grafo Grafo! **********");
        System.out.println("\n");
        System.out.println("1- Novo Grafo");
        System.out.println("2- Abrir Grafo");
        System.out.println("0- Sair");
        System.out.println("\n");
        System.out.println("**************************************************");
    }

    public void telaAbrirGrafo(){
        System.out.println("\n1- Escolher grafo existente");
        System.out.println("2- Carregar grafo");
        System.out.println("0- voltar");
        System.out.println("\n");
    }
    public void telaNovoGrafo() {
        System.out.println("\n1- Criar Grafo");
        System.out.println("0- voltar");
        System.out.println("\n");
    }

    public void telaCriarGrafo() {
        System.out.println("\n1- Novo grafo orientado");
        System.out.println("2- Novo grafo nao orientado");
        System.out.println("0- Voltar");
        System.out.println("\n");
    }

    public void telaInformacaoGrafoNaoOrientado() {
        System.out.println("\n1- Encontrar ordem do grafo");
        System.out.println("2- Encontrar grau de um vertice");
        System.out.println("3- Salvar grafo");
        System.out.println("4- Editar grafo");
        System.out.println("5- Problemas");
        System.out.println("6- Arvore geradora");
        System.out.println("7- Gerar imagem");
        System.out.println("0- Voltar");
        System.out.println("\n");
    }

     public void telaInformacaoGrafoOrientado() {
        System.out.println("\n1- Encontrar ordem do grafo");
        System.out.println("2- Encontrar grau de um vertice");
        System.out.println("3- Listar vertices fonte");
        System.out.println("4- Listar vertices sumidouro");
        System.out.println("5- Encontrar fecho transitivo direto");
        System.out.println("6- Encontrar fecho transitivo inverso");
        System.out.println("7- Salvar grafo");
        System.out.println("8- Editar grafo");
        System.out.println("9- Problemas");
        System.out.println("10- Gerar imagem");
        System.out.println("0- Voltar");
        System.out.println("\n");
    }

    public void telaEditarGrafo() {
        System.out.println("\n1- Adicionar vertice");
        System.out.println("2- Adicionar aresta");
        System.out.println("3- Remover vertice");
        System.out.println("4- Remover aresta");
        System.out.println("0- Voltar");
    }

    public void telaProblemas(){
        System.out.println("\n1- Caminho mínimo");
        System.out.println("0- Voltar");
    }

}
