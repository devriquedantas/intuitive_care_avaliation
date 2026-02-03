package download;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloadzip {
    public void baixarArquivo(String urlArquivo, String nomeArquivo) {

        System.out.println("Baixando: " + nomeArquivo);

        try {

            File pasta = new File("zipbaixado");
            if (!pasta.exists()) {
                pasta.mkdir();
            }

            BufferedInputStream in =
                    new BufferedInputStream(new URL(urlArquivo).openStream());

            FileOutputStream out =
                    new FileOutputStream("zipbaixado/" + nomeArquivo);

            byte[] buffer = new byte[1024];
            int bytesLidos;

            while ((bytesLidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLidos);
            }

            in.close();
            out.close();

            System.out.println("Download concluído em: zipbaixado/" + nomeArquivo);


            extrairZip("zipbaixado/" + nomeArquivo, "extraidos");

        } catch (IOException e) {
            System.out.println("Erro ao baixar arquivo: " + e.getMessage());
        }
    }

    public void extrairZip(String caminhoZip, String destino) {
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(caminhoZip));
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                Path novoArquivo = Paths.get(destino, entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(novoArquivo);
                } else {
                    Files.createDirectories(novoArquivo.getParent());
                    Files.copy(zis, novoArquivo, StandardCopyOption.REPLACE_EXISTING);
                }

                zis.closeEntry();
            }

            zis.close();
            System.out.println("Arquivos extraídos em: " + destino);

        } catch (IOException e) {
            System.out.println("Erro ao extrair ZIP: " + e.getMessage());
        }
    }
}
