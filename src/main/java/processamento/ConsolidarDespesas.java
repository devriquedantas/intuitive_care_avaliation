package processamento;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConsolidarDespesas {

    public void consolidar(List<File> arquivos, String arquivoSaida) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(arquivoSaida), StandardCharsets.UTF_8))) {

            writer.write("CNPJ;RazaoSocial;Trimestre;Ano;ValorDespesas");
            writer.newLine();

            for (File arquivo : arquivos) {
                processarArquivo(arquivo, writer);
            }

            System.out.println("CSV consolidado gerado com sucesso.");

        } catch (IOException e) {
            System.out.println("Erro ao consolidar: " + e.getMessage());
        }
    }

    private void processarArquivo(File arquivo, BufferedWriter writer) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(arquivo), StandardCharsets.UTF_8))) {

            String headerLine = br.readLine();
            if (headerLine == null) {
                return;
            }

            String[] colunas = headerLine.replace("\"", "").split(";");


            int idxValor = encontrarColuna(colunas, "vl_saldo_final");
            int idxCnpj = encontrarColuna(colunas, "cnpj");
            int idxRazao = encontrarColuna(colunas, "razao_social", "nm_razao_social");
            int idxRegAns = encontrarColuna(colunas, "reg_ans", "cd_operadora");

            if (idxValor == -1) {
                System.out.println("Arquivo ignorado (coluna de valor ausente): " + arquivo.getName());
                return;
            }

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.replace("\"", "").split(";");

                if (dados.length <= idxValor) {
                    continue;
                }


                String cnpj = "";
                if (idxCnpj != -1) {
                    cnpj = dados[idxCnpj].trim();
                }

                String razaoSocial = "N/A";
                if (idxRazao != -1) {
                    razaoSocial = dados[idxRazao].trim();
                }


                String identificador = cnpj;
                if (identificador.isEmpty()) {
                    if (idxRegAns != -1) {
                        identificador = dados[idxRegAns].trim();
                    }
                }

                String valorStr = dados[idxValor].trim();


                if (identificador.isEmpty() || valorStr.isEmpty()) {
                    continue;
                }

                String ano = extrairAno(arquivo.getName());
                String trimestre = extrairTrimestre(arquivo.getName());


                writer.write(identificador + ";" + razaoSocial + ";" + trimestre + ";" + ano + ";" + valorStr);
                writer.newLine();
            }

            System.out.println("Arquivo processado: " + arquivo.getName());

        } catch (IOException e) {
            System.out.println("Erro no processamento técnico: " + e.getMessage());
        }
    }

    private int encontrarColuna(String[] colunas, String... termos) {
        for (int i = 0; i < colunas.length; i++) {
            String nomeColuna = colunas[i].toLowerCase();
            for (String termo : termos) {
                if (nomeColuna.contains(termo.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private String extrairAno(String nomeArquivo) {

        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\d{4}");
        java.util.regex.Matcher m = p.matcher(nomeArquivo);
        if (m.find()) {
            return m.group();
        }
        return "Desconhecido";
    }

    private String extrairTrimestre(String nomeArquivo) {

        if (nomeArquivo.length() > 0) {
            return nomeArquivo.substring(0, 1);
        }
        return "0";
    }
}