/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banka;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 *
 * @author Milos
 */

public class Klijent {
    
    private final Client client = ClientBuilder.newClient();
    private final String scheme = "http:";
    private final String hostname = "localhost";
    private final String port = "8080";
    private final String appPath = "Server/banka_api";
    private final String uri = scheme + "//" + hostname + ":" + port + "/" + appPath + "/";
    
    public void kreirajMesto(String postanskiBroj, String naziv){
        Response response = client.target(uri).path("mesto/kreirajMesto/")
                            .queryParam("postanskiBroj", postanskiBroj)
                            .queryParam("naziv", naziv)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void kreirajFilijaluUMestu(String naziv, String adresa, String postanskiBroj){
        Response response = client.target(uri).path("filijala/kreirajFilijalu")
                            .queryParam("naziv", naziv)
                            .queryParam("adresa", adresa)
                            .queryParam("postanskiBroj", postanskiBroj)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void kreirajKomitenta(String naziv, String adresa, String postanskiBroj){
        Response response = client.target(uri).path("komitent/kreirajKomitenta")
                            .queryParam("naziv", naziv)
                            .queryParam("adresa", adresa)
                            .queryParam("postanskiBroj", postanskiBroj)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void promeniSedisteKomitenta(int id, String postanskiBroj){
        Response response = client.target(uri).path("komitent/promeniSediste")
                            .queryParam("id", id)
                            .queryParam("postanskiBroj", postanskiBroj)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void otvoriRacun(int id, String postanskiBroj, double dozvoljeniMinus){
        Response response = client.target(uri).path("racun/otvoriRacun/")
                            .queryParam("id", id)
                            .queryParam("postanskiBroj", postanskiBroj)
                            .queryParam("dozvoljeniMinus", dozvoljeniMinus)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void zatvoriRacun(int id){
        Response response = client.target(uri).path("racun/zatvoriRacun/")
                            .queryParam("id", id)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void prenesiNovac(int idSaKogSePrenosi, int idNaKojiSePrenosi, double sumaPrenosa, String svrha){
        Response response = client.target(uri).path("racun/prenesiNovac/")
                            .queryParam("idSaKogSePrenosi", idSaKogSePrenosi)
                            .queryParam("idNaKojiSePrenosi", idNaKojiSePrenosi)
                            .queryParam("sumaPrenosa", sumaPrenosa)
                            .queryParam("svrha", svrha)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void uplatiNaRacun(int id, double suma, int idF, String svrha){
        Response response = client.target(uri).path("racun/uplatiNaRacun/")
                            .queryParam("id", id)
                            .queryParam("suma", suma)
                            .queryParam("idF", idF)
                            .queryParam("svrha", svrha)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    public void isplatiSaRacuna(int id, double suma, int idF, String svrha){
        Response response = client.target(uri).path("racun/isplatiSaRacuna/")
                            .queryParam("id", id)
                            .queryParam("suma", suma)
                            .queryParam("idF", idF)
                            .queryParam("svrha", svrha)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiSvaMesta(){
        Response response = client.target(uri).path("mesto/dohvatiSvaMesta/")
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiSveFilijale(){
        Response response = client.target(uri).path("filijala/dohvatiSveFilijale/")
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
     
    public void dohvatiSveKomitente(){
        Response response = client.target(uri).path("komitent/dohvatiSveKomitente/")
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiSveRacuneZaKomitenta(int id){
        Response response = client.target(uri).path("racun/dohvatiSveRacuneZaKomitenta/")
                            .queryParam("id", id)
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiSveTransakcijeZaRacun(int id){
        Response response = client.target(uri).path("racun/dohvatiSveTransakcijeZaRacun/")
                            .queryParam("id", id)
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiSvePodatkeIzRezervneKopije(){
        Response response = client.target(uri).path("backup/dohvatiSvePodatkeIzRezervneKopije/")
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void dohvatiRazlikuUPodacima(){
        Response response = client.target(uri).path("backup/dohvatiRazlikuUPodacima/")
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }
    
    public void backup(){
        Response response = client.target(uri).path("backup/backup/")
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }
}
