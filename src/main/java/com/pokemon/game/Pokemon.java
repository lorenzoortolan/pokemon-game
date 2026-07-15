package com.pokemon.game;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    private String name;                       // "name" nel JSON
    private List<TypeWrapper> types;           // array di oggetti con "type"
    private List<Stat> stats;                  // array di statistiche
    private List<MoveSummary> moves;           // array di mosse (con nome e url)

    // Costruttore vuoto (obbligatorio per Gson)
    public Pokemon() {}

    // --- Getter ---
    public String getName() { return name; }

    // Restituisce i tipi come lista di stringhe (es. ["electric"])
    public List<String> getTypeList() {
        return types.stream()
                .map(tw -> tw.type.name)
                .toList();
    }

    public List<Stat> getStats() { return stats; }
    public List<MoveSummary> getMoves() { return moves; }

    // ------------------- Classi interne per mappare il JSON -------------------

    // 1. Mappatura per "types": [ { "type": { "name": "electric" } } ]
    public static class TypeWrapper {
        private TypeDetail type;
    }

    public static class TypeDetail {
        private String name;
    }

    // 2. Mappatura per "stats": [ { "base_stat": 35, "stat": { "name": "hp" } } ]
    public static class Stat {
        @SerializedName("base_stat")
        private int baseStat;

        private StatDetail stat;

        public static class StatDetail {
            private String name;
        }

        public int getBaseStat() { return baseStat; }
        public String getName() { return stat.name; }
    }

    // 3. Mappatura per "moves": [ { "move": { "name": "thunder-shock", "url": "..." } } ]
    public static class MoveSummary {
        private MoveDetail move;

        public static class MoveDetail {
            private String name;
            private String url;
        }

        public String getName() { return move.name; }
        public String getUrl() { return move.url; }
    }
}