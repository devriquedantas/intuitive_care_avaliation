package ansScrapper;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class AnsScrapper {
    String urlBase = "https://dadosabertos.ans.gov.br/FTP/PDA/";

    public List<String> iniciar() {
        System.out.println("iniciando...");
        System.out.println("URL BASE: " + urlBase);

        try {
            Document htmlbaixado = Jsoup.connect(urlBase).get();
            System.out.println("conexao realizada");
            System.out.println("titulo: " + htmlbaixado.title());

            Elements links = htmlbaixado.select("a[href]");
            System.out.println("links encontrados: " + links);

            for (Element link : links) {
                String valorhref = link.attr("href");
                if (valorhref.equals("demonstracoes_contabeis/")) {
                    System.out.println("Achei a pasta certa!");
                    urlBase = urlBase + valorhref;
                    System.out.println(urlBase);
                }

            }
            Document paginaanos = Jsoup.connect(urlBase).get();

            Elements linksanos = paginaanos.select("a[href]");

            for (Element link : linksanos) {

                String valorhref = link.attr("href");
                System.out.println(valorhref);

            }

            int maiorano = 0;

            for (Element link : linksanos) {
                String valorhref = link.attr("href");
                String anotexto = valorhref.replace("/", "");

                try {
                    int ano = Integer.parseInt(anotexto);

                    if (ano > maiorano) {
                        maiorano = ano;
                    }

                } catch (NumberFormatException e) {

                }
            }

            System.out.println("Ano mais recente encontrado: " + maiorano);

            for (Element link : linksanos) {
                String valorhref = link.attr("href");
                if (valorhref.equals(maiorano + "/")) {
                    System.out.println("entrando no ano mais recente");
                    urlBase = urlBase + valorhref;
                    System.out.println("nova url: " + urlBase);
                }

            }
            Document paginaZip = Jsoup.connect(urlBase).get();
            Elements linksZip = paginaZip.select("a[href]");

            List<String> listaZips = new ArrayList<>();

            for (Element link : linksZip) {
                String valorhref = link.attr("href");

                if (valorhref.endsWith(".zip")) {
                    listaZips.add(urlBase + valorhref);
                    System.out.println("ZIP encontrado: " + valorhref);
                }
            }

            return listaZips;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
