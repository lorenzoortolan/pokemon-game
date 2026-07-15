package com.pokemon.game;



public class Move
{
    private String name;
    private String type;
    private int power;
    private int accuracy;
    private String category;


    public Move()
    {
    }

    //Getter e setter
    public String getName(){return name;}
    public String getType(){return type;}
    public int getPower(){return power;}
    public int getAccuracy(){return accuracy;}
    public String getCategory(){return category;}
}