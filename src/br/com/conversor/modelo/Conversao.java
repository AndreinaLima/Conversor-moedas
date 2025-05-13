package br.com.conversor.modelo;

public class Conversao {
    private String de;
    private String para;
    private double valor;
    private double resultado;

    public Conversao(String de, String para, double valor, double resultado) {
        this.de = de;
        this.para = para;
        this.valor = valor;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Conversão: " + valor + " [" + de + "] corresponde a ===> " + resultado + " [" + para + "]";
    }

}
