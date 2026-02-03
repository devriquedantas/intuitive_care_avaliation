package processamento;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ValidarDados {

    public void validar(String arquivoEntrada, String arquivoSaida) {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(arquivoEntrada), StandardCharsets.UTF_8));
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(arquivoSaida), StandardCharsets.UTF_8))
        ) {

            String header = br.readLine();
            if (header == null) {
                return;
            }


            bw.write(header + ";StatusValidacao;MotivoErro");
            bw.newLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");


                if (dados.length < 5) {
                    continue;
                }

                String cnpj = dados[0].trim();
                String razaoSocial = dados[1].trim();
                String valorStr = dados[4].replace(",", ".");

                String motivoErro = "";
                boolean registroValido = true;


                if (razaoSocial.isEmpty()) {
                    registroValido = false;
                    motivoErro = motivoErro + "RAZAO_VAZIA ";
                }

                if (cnpj.length() != 14) {
                    registroValido = false;
                    motivoErro = motivoErro + "CNPJ_INVALIDO ";
                }


                try {
                    double valor = Double.parseDouble(valorStr);
                    if (valor <= 0) {
                        registroValido = false;
                        motivoErro = motivoErro + "VALOR_NEGATIVO_OU_ZERO ";
                    }
                } catch (NumberFormatException e) {
                    registroValido = false;
                    motivoErro = motivoErro + "FORMATO_VALOR_ERRADO ";
                }

                String status = "VALIDO";
                if (registroValido == false) {
                    status = "INVALIDO";
                }

                bw.write(linha + ";" + status + ";" + motivoErro.trim());
                bw.newLine();
            }

            System.out.println("Item 2.1: Validação concluída com sucesso.");

        } catch (IOException e) {
            System.out.println("Erro na validação: " + e.getMessage());
        }
    }
}