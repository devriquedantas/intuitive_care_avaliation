package processamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FiltrarDados {

    private static final String PASTA_EXTRAIDOS = "extraidos";

    public List<File> filtrar() {
        List<File> arquivosValidos = new ArrayList<>();

        File pasta = new File(PASTA_EXTRAIDOS);

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Pasta 'extraidos' não encontrada ou inválida.");
            return arquivosValidos;
        }

        buscarArquivosRecursivo(pasta, arquivosValidos);
        return arquivosValidos;
    }

    private void buscarArquivosRecursivo(File pasta, List<File> arquivosValidos) {
        File[] arquivos = pasta.listFiles();

        if (arquivos == null) return;

        for (File arquivo : arquivos) {

            if (arquivo.isDirectory()) {
                buscarArquivosRecursivo(arquivo, arquivosValidos);
            }
            else if (arquivo.getName().toLowerCase().endsWith(".csv")) {
                if (verificarArquivo(arquivo)) {
                    arquivosValidos.add(arquivo);
                    System.out.println("Arquivo válido encontrado: " + arquivo.getAbsolutePath());
                }
            }
        }
    }

    private boolean verificarArquivo(File arquivo) {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(arquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = br.readLine()) != null) {

                String linhaLower = linha.toLowerCase();

                if (linhaLower.contains("despesas") && linhaLower.contains("sinistros")) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + arquivo.getName());
        }

        return false;
    }
}
