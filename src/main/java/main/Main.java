package main;

import ansScrapper.AnsScrapper;
import download.Downloadzip;
import processamento.*;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AnsScrapper scrapper = new AnsScrapper();
        List<String> zips = scrapper.iniciar();

        Downloadzip download = new Downloadzip();

        int contador = 1;
        for (String url : zips) {
            download.baixarArquivo(url, "trimestre_" + contador + ".zip");
            contador++;
        }

        ListarArquivos listarArquivos = new ListarArquivos();
        listarArquivos.listar();

        FiltrarDados filtro = new FiltrarDados();
        List<File> arquivosValidos = filtro.filtrar();

        ConsolidarDespesas consolidador = new ConsolidarDespesas();
        consolidador.consolidar(
                arquivosValidos,
                "consolidado_despesas.csv"
        );

        ValidarDados validador = new ValidarDados();
        validador.validar(
                "consolidado_despesas.csv",
                "consolidado_validado.csv"
        );



    }
}
