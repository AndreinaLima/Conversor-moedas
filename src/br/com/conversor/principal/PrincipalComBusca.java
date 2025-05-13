package br.com.conversor.principal;

import br.com.conversor.modelo.Conversao;
import br.com.conversor.modelo.DadosConversao;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String de = "";
        String para = "";
        String continuar = "";
        List<Conversao> historico = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();

        String menu = """
                *******************************
                Seja bem-vindo(a) ao Conversor de Moeda =]
                
                Escolha uma opção válida:
                1 - Dólar => Peso argentino
                2 - Peso argentino => Dólar
                3 - Dólar => Real brasileiro
                4 - Real brasileiro => Dólar
                5 - Dólar => Peso colombiano
                6 - Peso colombiano => Dólar
                7 - Sair
                *******************************
                """;

        while (true) {
            System.out.println(menu);
            int opcao = Integer.parseInt(scanner.nextLine());

            if (opcao == 1) {
                de = "USD";
                para = "ARS";
            } else if (opcao == 2) {
                de = "ARS";
                para = "USD";
            } else if (opcao == 3) {
                de = "USD";
                para = "BRL";
            } else if (opcao == 4) {
                de = "BRL";
                para = "USD";
            } else if (opcao == 5) {
                de = "USD";
                para = "COP";
            } else if (opcao == 6) {
                de = "COP";
                para = "USD";
            } else if (opcao == 7) {
                System.out.println("Saindo...");
                FileWriter escrita = new FileWriter("conversoes.json");
                escrita.write(gson.toJson(historico));
                escrita.close();
                System.out.println("Histórico salvo em 'conversoes.json'. Programa encerrado!");
                return;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            System.out.println("\nDigite o valor a ser convertido: ");
            double valor = Double.parseDouble(scanner.nextLine());

            String endereco = "https://v6.exchangerate-api.com/v6/7581acc8b3d2f6b11b96ccda/pair/"
                    + de + "/" + para + "/" + valor;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                DadosConversao dados = gson.fromJson(response.body(), DadosConversao.class);
                Conversao conversao = new Conversao(de, para, valor, dados.getConversionResult());
                System.out.println(conversao);

                historico.add(conversao);
            } catch (Exception e) {
                System.out.println("Erro ao fazer a conversão: " + e.getMessage());
            }

            System.out.println("\nDeseja fazer outra conversão? (sair para encerrar)");
            continuar = scanner.nextLine();
            if (continuar.equalsIgnoreCase("sair")) {
                break;
            }
        }

        FileWriter escrita = new FileWriter("conversoes.json");
        escrita.write(gson.toJson(historico));
        escrita.close();

        System.out.println("Histórico salvo em 'conversoes.json'. Programa encerrado!");
    }
}
