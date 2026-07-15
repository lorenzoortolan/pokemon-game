package com.pokemon.game;

import com.google.gson.Gson; //classe principale Gson che converte JSON in oggetti Java
import com.google.gson.JsonSyntaxException; //ecezione che Gson lancia se JSON non valido
import java.io.IOException; 
import java.net.URI; //per rappresentare l'indirizzo
import java.net.http.HttpClient; //tutte ste classi per fare richieste HTTP 
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration; //impostare timeout, quanto aspettare


public class PokeApiClient
{
    private final HttpClient httpClient; //dichiaro oggetto che sa come spedire richieste e ricevere risposte 
    private final Gson gson; //dichiaro il goat che sa trasformare stringhe Json in oggetti java
    private final PokemonCache cache;
    private static final String BASE_URL= "https://pokeapi.co/api/v2/pokemon/"; //pezzo fisso dell'indirizzo, il nome del pokemon va alla fine dopo /pokemon

    //costruttore
    public PokeApiClient()
    {
        this.httpClient = HttpClient.newBuilder() //creo costruttore per personalizzare il client
                .connectTimeout(Duration.ofSeconds(10))//se il server non risponde in 10s il programma smette di aspettare
                .build(); //produce l'oggetto HttpClient pronto all'uso
        this.gson = new Gson(); //crea istanza standard di Gson con config. di default
        this.cache = new PokemonCache(); //inizializzo la cache
    }


    //metodo per pullare il pokemon
    public Pokemon getPokemon(String name)throws IOException, InterruptedException
    {
         // 1. Prova a caricare dalla cache
        Pokemon cached = cache.load(name);
        if (cached != null) {
            System.out.println("[Cache] Trovato " + name + " in cache.");
            return cached;
        }

        // 2. Se non in cache, scarica da API
        System.out.println("[API] Scarico " + name + " da PokeAPI...");

        //Costruisco l'url
        String url = BASE_URL + name.toLowerCase();

        //Costruisco richeista http
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) //trasforma stringa in un oggetto URI
            .timeout(Duration.ofSeconds(10))
            .GET() //get legge i dati
            .build();
        //Spedisco richiesta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); //invia richeista e dice a java di restituire il corpo della risposta come stringa

        //Controllo il codice di stato dell'http
        if (response.statusCode()!=200)
        {
            throw new IOException("Errore HTTP: "+response.statusCode()+ " per "+name);
        }

        //Estraggo corpo risposta (Json) come stringa
        String json = response.body(); //la stringa JSON
        Pokemon pokemon = parsePokemon(json);


        //sALVA IN CACHE PER USI FUTURI
        cache.save(pokemon);
        System.out.println("[Cache] Salvato " + name);
        //Converto json nell'oggetto di tipo Pokemon (usando Gson)
        return pokemon;
    }

        private Pokemon parsePokemon(String json) throws IOException {
        try {
            return gson.fromJson(json, Pokemon.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("JSON non valido per il Pokémon", e);
        }
    }
}


