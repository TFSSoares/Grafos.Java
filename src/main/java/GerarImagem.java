import br.com.davesmartins.api.Graph;

import java.io.IOException;

public abstract class GerarImagem {

    public static void gerarImagemGrafo(String nome,String grafoString) {
        System.setProperty("guru.nidi.graphviz.engine.DefaultExecutorPath", "C:\\Program Files\\Graphviz\\bin\\dot.exe");
        try {
            Graph.createStringDotToPng(grafoString, "graph/GrafoA.png");
        } catch (IOException e) {
            System.out.println("Erro ao gerar a imagem");
        }

    }
}

