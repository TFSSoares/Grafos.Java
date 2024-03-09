package principal;
public class Vertice {
    private String nome;
    private String codigo;
    private int grau;
    private int grauEmissao;
    private int grauRecepcao;

    public Vertice(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCode() {
        return this.codigo;
    }

    public int getGrauEmissao() {
        return this.grauEmissao;
    }

    public int getGrauRecepcao() {
        return this.grauRecepcao;
    }

    public int getGrau() {
        return this.grau;
    }

    public void addGrau(int valor) {
        this.grau += valor;
    }

    public void subGrau(int valor) {
        this.grau -= valor;
    }

    public void addGrauEmissao(int valor) {
        this.grauEmissao += valor;
    }

    public void addGrauRecepcao(int valor) {
        this.grauRecepcao += valor;
    }

    public void subGrauEmissao(int valor) {
        this.grauEmissao -= valor;
    }

    public void subGrauRecepcao(int valor) {
        this.grauRecepcao -= valor;
    }

}
