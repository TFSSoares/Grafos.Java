package principal;
import br.com.davesmartins.api.Graph;

import java.io.IOException;

public class Principal {

    public static void gerarImagemGrafo(String nome,String grafoString) {
        // System.setProperty("guru.nidi.graphviz.engine.DefaultExecutorPath", "C:\\Program Files\\Graphviz\\bin\\dot.exe");
        try {
            Graph.createStringDotToPng(grafoString, "Imagens/"+ nome + ".png");
        } catch (IOException e) {
            System.out.println("Erro ao gerar a imagem");
        }

    }

//    public static void main(String[] args) {
//        gerarImagemGrafo ("teste", "digraph a3c{\n" +
//                "A -> B [label= 1.0];\n" +
//                "A -> C [label= 2.0];\n" +
//                "B -> C [label= 1.0];\n" +
//                "}");
//    }
}
