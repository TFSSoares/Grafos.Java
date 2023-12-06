
public class Aresta {

    private float peso;
    private Vertice emissor;
    private Vertice receptor;
    private String codigo;

    public Aresta(float peso, Vertice emissor, Vertice receptor, String codigo) {
        this.peso = peso;
        this.emissor = emissor;
        this.receptor = receptor;
        this.codigo = codigo;
    }

    public Vertice getEmissor() {
        return emissor;
    }

    public Vertice getReceptor() {
        return receptor;
    }

    public float getPeso(){
        return peso;
    }

    public String getCode(){
        return this.codigo;
    }

    public boolean arestaExiste(Vertice emissor, Vertice receptor) {
        if (emissor.getNome().equals(this.emissor.getNome()) &&
                receptor.getNome().equals(this.receptor.getNome())) {
            return true;
        } else if (emissor.getNome().equals(this.receptor.getNome()) &&
                receptor.getNome().equals(this.emissor.getNome())) {
            return true;
        }

        return false;
    }

    public boolean verificaExistenciaDeVertice(Vertice vertice) {
        if (this.emissor.getNome().equals(vertice.getNome()) || this.receptor.getNome().equals(vertice.getNome())) {
            return true;
        }

        return false;

    }

    public boolean arestaConectaVertice(Vertice vertice) {
        return emissor.equals(vertice) || receptor.equals(vertice);
    }

}
