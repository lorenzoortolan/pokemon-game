package com.pokemon.game;

import com.google.gson.Gson;
import java.io.*;
import java.nio.file.*;

public class PokemonCache {
    private final Path cacheDir;   // percorso della cartella data/pokemon/
    private final Gson gson;

    // Costruttore
    public PokemonCache() {
        this.cacheDir = Paths.get("data", "pokemon");
        this.gson = new Gson();
        // Crea la cartella se non esiste
        try {
            Files.createDirectories(cacheDir); //crea tutte le directory intermedie se mancano es. data e dentro pokemon. se esiste, non fa nulla.
        } catch (IOException e) {
            System.err.println("Impossibile creare la cartella cache: " + e.getMessage());
        }
    }

    // Metodo per salvare un Pokemon su disco
    /*Prendiamo il nome del Pokémon (in minuscolo) e aggiungiamo .json.
        cacheDir.resolve(fileName) unisce il percorso della cartella con il nome file.
        gson.toJson(pokemon) converte l’oggetto Java in una stringa JSON (esattamente l’inverso di fromJson).
        Files.writeString scrive l’intera stringa sul file in un colpo solo. Se il file esiste già, lo sovrascrive. */
       public void save(Pokemon pokemon) throws IOException 
       {
            String fileName = pokemon.getName().toLowerCase() + ".json";
            Path filePath = cacheDir.resolve(fileName);  // es. data/pokemon/pikachu.json

            // Serializza l'oggetto Pokemon in una stringa JSON
            String json = gson.toJson(pokemon);

            // Scrive la stringa su file (sovrascrive se esiste)
            Files.writeString(filePath, json);
        }

        

    // Metodo per caricare un Pokemon da disco (restituisce null se non esiste)
    /*Controlla se il file esiste. Se no, restituisce null (segnala “cache miss”).
    Files.readString legge tutto il contenuto in una stringa.
    gson.fromJson(json, Pokemon.class) ricostruisce l’oggetto Java.
    Nota: le annotazioni @SerializedName funzionano anche in deserializzazione, quindi la struttura annidata viene ricostruita perfettamente. */
        public Pokemon load(String name) throws IOException
        {
            String fileName = name.toLowerCase() + ".json";
            Path filePath = cacheDir.resolve(fileName);

            if (!Files.exists(filePath)) {
                return null;  // file non trovato -> cache vuota
            }

            // Legge l'intero contenuto del file come stringa
            String json = Files.readString(filePath);
            // Converte la stringa JSON in oggetto Pokemon
            return gson.fromJson(json, Pokemon.class);
        }
}