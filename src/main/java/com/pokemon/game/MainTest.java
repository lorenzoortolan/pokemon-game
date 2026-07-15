package com.pokemon.game;

public class MainTest {
    public static void main(String[] args) {
        PokeApiClient client = new PokeApiClient();
        try {
            Pokemon pikachu = client.getPokemon("pikachu");
            System.out.println("Nome: " + pikachu.getName());
            System.out.println("Tipi: " + pikachu.getTypeList());
            System.out.println("Statistiche:");
            for (Pokemon.Stat s : pikachu.getStats()) {
                System.out.println("  " + s.getName() + " = " + s.getBaseStat());
            }
            System.out.println("Prime 5 mosse:");
            pikachu.getMoves().stream().limit(5).forEach(m -> System.out.println("  - " + m.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
