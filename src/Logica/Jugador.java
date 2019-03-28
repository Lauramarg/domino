/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;


public class Jugador {
    String nombre;
    ArrayList<Ficha> listFichas;

    public Jugador(String nombre, int nF) {
        this.nombre=nombre;
        this.listFichas=new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Ficha> getListFichas() {
        return listFichas;
    }
    
    public void addFicha(Ficha f){
        this.listFichas.add(f);
    }

    public Ficha getF(int i){
        return this.listFichas.get(i);
    }
    public Ficha ponerF(int i){
        return this.listFichas.remove(i);
    }

    void ImprimirF() {
        for (int i = 0; i <this.listFichas.size(); i++) {
            System.out.print((i+1)+"["+this.listFichas.get(i).pinta1+"|"+this.listFichas.get(i).pinta2+"]  ");
        }
    }
}
