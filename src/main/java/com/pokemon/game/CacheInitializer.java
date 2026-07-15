package com.pokemon.game;

public class CacheInitializer {
    public static void main(String[] args) {
        PokeApiClient client = new PokeApiClient();
        String[] pokemonToDownload = {
            "pikachu", "charmander", "bulbasaur", "squirtle", "eevee", "mew"
        };

        for (String name : pokemonToDownload) {
            try {
                Pokemon p = client.getPokemon(name);
                System.out.println(p.getName() + " pronto.");
            } catch (Exception e) {
                System.err.println("Errore per " + name + ": " + e.getMessage());
            }
        }
        System.out.println("Download completato.");
    }
}