/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import banka.Klijent;
import java.util.Scanner;

/**
 *
 * @author Milos
 */
public class Menu {
    private Klijent klijent = new Klijent();
    private Scanner scanner = new Scanner(System.in);
    private boolean exit = false;
    
    private void print(){
        System.out.println("1. Kreiranje mesta");
        System.out.println("2. Kreiranje filijale u mestu");
        System.out.println("3. Kreiranje komitenta");
        System.out.println("4. Promena sedišta za zadatog komitenta");
        System.out.println("5. Otvaranje racuna");
        System.out.println("6. Zatvaranje racuna");
        System.out.println("7. Kreiranje transakcije koja je prenos sume sa jednog racuna na drugi racun");
        System.out.println("8. Kreiranje transakcije koja je uplata novca na racun");
        System.out.println("9. Kreiranje transakcije koja je isplata novca sa racuna");
        System.out.println("10. Dohvatanje svih mesta");
        System.out.println("11. Dohvatanje svih filijala");
        System.out.println("12. Dohvatanje svih komitenata");
        System.out.println("13. Dohvatanje svih racuna za komitenta");
        System.out.println("14. Dohvatanje svih transakcija za racun");
        System.out.println("15. Dohvatanje svih podataka iz rezervne kopije");
        System.out.println("16. Dohvatanje razlike u podacima u originalnim podacima i u rezervnoj kopiji");
        System.out.println("17. Rucni backup");
        System.out.println("0. Kraj");
        System.out.println();
    }
    
    private int processUserInput(){
        int choice = -1;
        
        while(choice < 0 || choice > 17){
            System.out.println("Unesite opciju: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 17) {
                    System.out.println("Unesite validnu opciju (0-17)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Unesite validnu opciju (0-17)");
            }
        }
        
        return choice;
    }
    
    private String getUserInputString(String question){
        System.out.println(question);
        return scanner.nextLine();
    }
    
    private void processUserChoice(int choice){
        String naziv;
        String postanskiBroj;
        String adresa;
        String id;
        String dozvoljeniMinusString;
        String sumaString;
        String idFilijale;
        String svrha;
        switch(choice){
            case 0:
                exit = true;
                break;
            case 1:
                postanskiBroj = getUserInputString("Postanski broj: ");
                naziv = getUserInputString("Naziv: ");
                if (postanskiBroj.equals(":q") || naziv.equals(":q")){
                    System.out.println("Akcija obustavljena");
                    break;
                }
                klijent.kreirajMesto(postanskiBroj, naziv);
                break;

            case 2:
                naziv = getUserInputString("Naziv: ");
                adresa = getUserInputString("Adresa: ");
                postanskiBroj = getUserInputString("Postanski broj mesta: ");
                if (postanskiBroj.equals(":q") || naziv.equals(":q") || adresa.equals(":q")){
                    System.out.println("Akcija obustavljena");
                    break;
                }
                klijent.kreirajFilijaluUMestu(naziv, adresa, postanskiBroj);
                break;
            case 3:
                naziv = getUserInputString("Naziv: ");
                adresa = getUserInputString("Adresa: ");
                postanskiBroj = getUserInputString("Postanski broj mesta: ");
                if (postanskiBroj.equals(":q") || naziv.equals(":q") || adresa.equals(":q")){
                    System.out.println("Akcija obustavljena");
                    break;
                }
                klijent.kreirajKomitenta(naziv, adresa, postanskiBroj);
                break;
            case 4:
                try {
                    id = getUserInputString("Id komitenta: ");
                    int idK = Integer.parseInt(id);
                    postanskiBroj = getUserInputString("Postanski broj novog mesta: ");
                    if (postanskiBroj.equals(":q")){
                        System.out.println("Akcija obustavljena");
                        break;
                    }
                    
                    klijent.promeniSedisteKomitenta(idK, postanskiBroj);
                    break;
                } catch(Exception e){
                    System.out.println("Niste uneli validan id");
                    break;
                }
            case 5:
                try{
                    int idK;
                    try {
                        id = getUserInputString("Id komitenta: ");
                        idK = Integer.parseInt(id);
                    } catch (Exception e){
                        System.out.println("Niste uneli validan id komitenta");
                        break;
                    }
                    postanskiBroj = getUserInputString("Postanski broj mesta: ");
                    dozvoljeniMinusString = getUserInputString("Dozvoljeni minus");
                    double dozvoljeniMinus = Double.parseDouble(dozvoljeniMinusString);
                    if (id.equals(":q") || postanskiBroj.equals(":q")){
                        System.out.println("Akcija obustavljena");
                        break;
                    }
                    
                    klijent.otvoriRacun(idK, postanskiBroj, dozvoljeniMinus);
                    break;
                } catch (Exception e){
                    System.out.println("Niste uneli validan dozvoljeni minus");
                    break;
                }
            case 6:
                try{
                    id = getUserInputString("Id racuna: ");
                    int idR = Integer.parseInt(id);
                    
                    klijent.zatvoriRacun(idR);
                    break;
                } catch (Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
            case 7:
                int idSaKogSePrenosi;
                int idNaKojiSePrenosi;
                id = getUserInputString("Id racuna sa kog se prenosi novac: ");
                
                try {
                    idSaKogSePrenosi = Integer.parseInt(id);
                } catch(Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
                
                id = getUserInputString("Id racuna na koji se prenosi novac: ");
                try {
                    idNaKojiSePrenosi = Integer.parseInt(id);
                } catch(Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
                
                sumaString = getUserInputString("Suma novca: ");
                double sumaPrenosa;
                try {
                    sumaPrenosa = Double.parseDouble(sumaString);
                    if (sumaPrenosa <= 0) throw new Exception();
                } catch(Exception e){
                    System.out.println("Niste uneli validnu sumu");
                    break;
                }
                
                svrha = getUserInputString("Svrha prenosa: ");
                
                klijent.prenesiNovac(idSaKogSePrenosi, idNaKojiSePrenosi, sumaPrenosa, svrha);
                break;
            case 8:
                try{
                    id = getUserInputString("Id racuna: ");
                    int idR = Integer.parseInt(id);
                    sumaString = getUserInputString("Suma novca: ");
                    double suma;
                    try {
                        suma = Double.parseDouble(sumaString);
                        if (suma <= 0) throw new Exception();
                    } catch(Exception e){
                        System.out.println("Niste uneli validnu sumu");
                        break;
                    }
                    
                    idFilijale = getUserInputString("Id filijale: ");
                    int idF;
                    try {
                        idF = Integer.parseInt(idFilijale);
                    } catch(Exception e){
                        System.out.println("Niste uneli validan id filijale");
                        break;
                    }
                    
                    svrha = getUserInputString("Svrha uplate: ");
                    
                    klijent.uplatiNaRacun(idR, suma, idF, svrha);
                    break;
                } catch (Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
            case 9:
                try{
                    id = getUserInputString("Id racuna: ");
                    int idR = Integer.parseInt(id);
                    sumaString = getUserInputString("Suma novca: ");
                    double suma;
                    try {
                        suma = Double.parseDouble(sumaString);
                        if (suma <= 0) throw new Exception();
                    } catch(Exception e){
                        System.out.println("Niste uneli validnu sumu");
                        break;
                    }
                    
                    idFilijale = getUserInputString("Id filijale: ");
                    int idF;
                    try {
                        idF = Integer.parseInt(idFilijale);
                    } catch(Exception e){
                        System.out.println("Niste uneli validan id filijale");
                        break;
                    }
                    
                    svrha = getUserInputString("Svrha isplate: ");
                    
                    klijent.isplatiSaRacuna(idR, suma, idF, svrha);
                    break;
                } catch (Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
            case 10:
                klijent.dohvatiSvaMesta();
                break;
            case 11:
                klijent.dohvatiSveFilijale();
                break;
            case 12:
                klijent.dohvatiSveKomitente();
                break;
            case 13:
                int idKomitenta;
                id = getUserInputString("Id komitenta: ");
                if (id.equals(":q")){
                    System.out.println("Akcija obustavljena");
                    break;
                }
                try{
                    idKomitenta = Integer.parseInt(id);
                } catch(Exception e){
                    System.out.println("Niste uneli validan id komitenta");
                    break;
                }
                
                klijent.dohvatiSveRacuneZaKomitenta(idKomitenta);
                break;
            case 14:
                int idRacuna;
                id = getUserInputString("Id racuna: ");
                if (id.equals(":q")){
                    System.out.println("Akcija obustavljena");
                    break;
                }
                try{
                    idRacuna = Integer.parseInt(id);
                } catch(Exception e){
                    System.out.println("Niste uneli validan id racuna");
                    break;
                }
                klijent.dohvatiSveTransakcijeZaRacun(idRacuna);
                break;
            case 15:
                klijent.dohvatiSvePodatkeIzRezervneKopije();
                break;
            case 16:
                klijent.dohvatiRazlikuUPodacima();
                break;
            case 17:
                klijent.backup();
                break;
            default:
                System.out.println("Nevalidna opcija");
                break;
        }
    }
    
    public void run(){
        while(!exit){
            print();
            int choice = processUserInput();
            processUserChoice(choice);
        }
    }
    
    public static void main(String args[]){
        Menu menu = new Menu();
        menu.run();
    }
}
