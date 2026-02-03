package processamento;

import java.io.File;

public class ListarArquivos {
    public void listar() {
        File pasta = new File("extraidos");

        if (pasta.exists()) {
            File[] arquivos = pasta.listFiles();
            System.out.println("arquivos encontrados: ");

            for (File arquivo : arquivos) {
                System.out.println(arquivo.getName());
            }
        } else {
            System.out.println("pasta nao existe");
        }

    }
}
