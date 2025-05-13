package br.com.conversor.modelo;

public class DadosConversao {
    private String base_code;
    private String target_code;
    private double conversion_result;

    public String getBaseCode() {
        return base_code;
    }

    public String getTargetCode() {
        return target_code;
    }

    public double getConversionResult() {
        return conversion_result;
    }

    @Override
    public String toString() {
        return base_code + " => " + target_code + " = " + conversion_result;
    }
}
