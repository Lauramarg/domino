 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.regex.*;

public class Tablero {
    ArrayList<Ficha> ListFichas;
    int turno;
    ArrayList<Jugador> jugadores;
    int cerrado;
    private int p;
    
    public Tablero(int numJ) {
        this.jugadores=new ArrayList<>(numJ);
        JOptionPane.showMessageDialog(null, "Domino Unimag");
        this.addJugadores(numJ);
        this.generadorDeFichas();
        this.repartirFichas();
        this.turno=1;
        this.cerrado=0;
        p=0;
    }

    public void addJugadores(int num){
        if(num>=2){
            for (int i = 0; i < num; i++) {
               String nom=JOptionPane.showInputDialog("Digite Nombre de jugador "+(i+1));
               if(!this.verificarNombreJugador(nom)){
                   this.jugadores.add(new Jugador(nom, 28/2));
               }else{
                   JOptionPane.showMessageDialog(null, "Este nombre ya existe");
                   i=i-1;
               }
               if(i==2)
                   break;
            }
        }
    }
    
    private boolean verificarNombreJugador(String nombre){
     
        for(Jugador j: this.jugadores)
            if(j.getNombre().equals(nombre))
                return true;
        return false;
    }

    private void generadorDeFichas() {
        this.ListFichas=new ArrayList<>(28);
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                Ficha aux=new Ficha(i,j);
                this.ListFichas.add(aux);
            }
        }
        
    }

    private void repartirFichas(){
        
        for (int j = 0; j < 14; j++) {
            this.jugadores.get(0).addFicha(this.ListFichas.remove(getNumAleatorio()));

        }
        for (int j = 0; j < 14; j++) {
            this.jugadores.get(1).addFicha(this.ListFichas.remove(getNumAleatorio()));

        }
    }
    private int getNumAleatorio(){
        Random s=new Random();
        
        int n=this.ListFichas.size();
        int x=s.nextInt(n);
      /*  System.out.println(x);
        while(x<0){
            x=s.nextInt(n);
            System.out.println(x);
        }*/
        return x;
    }
    
    public void imprimirJ(){
        for (int i = 0; i < this.jugadores.size(); i++) {
            System.out.println(this.jugadores.get(i).getNombre()+"---------");
            this.jugadores.get(i).ImprimirF();
        }
    }
    public void run(){
        this.ListFichas=new ArrayList<>();
        this.PonerFInicial();
        while(!this.ganador(this.turno)){
            
            this.mostrarTablero();
            this.JugarTurno();
            if(cantPlayJ(turno))
                this.p=0;
            else
                this.p+=1;
            if(p==2){
                ganadorJuegocerrado();
                break;
            }
            if(this.ganador(turno)){
                JOptionPane.showMessageDialog(null, "GANADOR "+this.jugadores.get(turno).getNombre());
                break;
            }
            this.cambiarTurno();
        }
    }
    private boolean ganador(int x){
        if(this.jugadores.get(x).getListFichas().size()==0){
            return true;
        }
        return false;
    }
    private void cambiarTurno(){
        if(this.turno==0)
            this.turno=1;
        else
            this.turno=0;
    }

    private void PonerFInicial() {
        for (int i = 0; i < this.jugadores.size(); i++) {
            for (int j = 0; j < this.jugadores.get(i).getListFichas().size(); j++) {
                if(this.jugadores.get(i).getListFichas().get(j).getPinta1()==6 && this.jugadores.get(i).getListFichas().get(j).getPinta2()==6){
                    this.jugadores.get(i).getListFichas().remove(j);
                    Ficha s=new Ficha(6, 6);
                    s.setL(0);
                    this.ListFichas.add(s);
                }
            }
        }
    }
    private int getOp(String msj){
        //int o=0;
        String a=JOptionPane.showInputDialog(msj);
        if(a.isEmpty()){
            a=JOptionPane.showInputDialog(msj);
        }

           try{ return Integer.parseInt(a);}
           catch(Exception e){
               return 20;
           }

    }

    private void JugarTurno() {
        this.mostrarFichasJ(this.turno);
        
        boolean sw=false;
        
        int x=getOp(" Digite que ficha desea jugar si no puede jugar ninguna digite 0 ")-1;
        while(!sw){
            if(x<-1 || x>this.jugadores.get(this.turno).getListFichas().size()){
                
                x=getOp("(error) eleccion invalida. Digite que ficha desea jugar si no puede jugar ninguna digite 0 ")-1;
            }else{
                if(x!=-1 && (x+1)!=15){
                    if(this.validarF(this.jugadores.get(this.turno).getListFichas().get(x))){
                        this.jugadores.get(this.turno).getListFichas().remove(x);
                        sw=true;
                    }else{
                        System.out.println("x=="+x);
                        x=getOp("eleccion invalida. Digite que ficha desea jugar si no puede jugar ninguna digite 0 ")-1;
                    }
                }else{
                    if(x==-1){
                        JOptionPane.showMessageDialog(null, "jugador ha pasado");
                        sw=true;
                    }          
                }
            }
        }
    }
    

    private void mostrarFichasJ(int parseInt) {
        System.out.println("El jugador en Turno es :"+this.jugadores.get(turno).getNombre()+":");
        this.jugadores.get(this.turno).ImprimirF();
        System.out.println(" ");
        System.out.println("_______________________________________________________________________________________________________________");
    }

    private boolean validarF(Ficha f){
        Ficha izq=this.ListFichas.get(0);
        Ficha der=this.ListFichas.get(this.ListFichas.size()-1);
        boolean sw1=validarFs(izq,f);
        boolean sw2=validarFs(der,f);
        if(sw1 && sw2){
            int op =Integer.parseInt(JOptionPane.showInputDialog("(1)izq 0 der(2)"));
            
            
            if(this.ListFichas.size()==1 && op !=1){
                this.ListFichas.get(0).setL(2);
            }
            if(this.ListFichas.size()==1 && op ==1){
                this.ListFichas.get(0).setL(1);
            }
            
            
            if(op==1)
                if(izq.getL()==1){
                    if(izq.getPinta2()==f.getPinta1()){
                        f.setL(1);
                        if(this.ListFichas.size()==1){
                            this.ListFichas.get(0).setL(1);
                        }
                        this.ListFichas.add(0,f);
                        return true;
                    }
                    if(izq.getPinta2()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                }else{
                    if(izq.getPinta1()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                    if(izq.getPinta1()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                }
            else{
                if(der.getL()==1){
                    if(der.getPinta2()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(f);
                        return true;
                    }
                    if(der.getPinta2()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(f);
                        return true;
                    }
                }else{
                    if(der.getPinta1()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(f);
                        return true;
                    }
                    if(der.getPinta1()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(f);
                        return true;
                    }
                }
            }
                
        }else{
            if(sw1){
                if(izq.getL()==1){
                    if(izq.getPinta2()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                    if(izq.getPinta2()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                }else{
                    if(izq.getPinta1()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                    if(izq.getPinta1()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(0,f);
                        return true;
                    }
                }
            }else{
                if(der.getL()==1){
                    if(der.getPinta2()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(f);
                        return true;
                    }
                    if(der.getPinta2()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(f);
                        return true;
                    }
                }else{
                    if(der.getPinta1()==f.getPinta1()){
                        f.setL(1);
                        this.ListFichas.add(f);
                        return true;
                    }
                    if(der.getPinta1()==f.getPinta2()){
                        f.setL(2);
                        this.ListFichas.add(f);
                        return true;
                    }
                }
            }
        }
        return false;
        
    }
    
    
    private boolean validarFs(Ficha f1,Ficha f2){
        if(f1.getL()==1)
            return f1.getPinta2()==f2.getPinta1() || f1.getPinta2()==f2.getPinta2();
        else
            return f1.getPinta1()==f2.getPinta1() || f1.getPinta1()==f2.getPinta2();
    }
    
    private void mostrarTablero(){
        System.out.println(" ");
        System.out.println("Tablero ");
        int m=1;
        ArrayList<Ficha> aux=this.ListFichas;
        for (int i = 0; i < this.ListFichas.size(); i++) {
            if(ListFichas.get(i).getL()==1){
                System.out.print("["+this.ListFichas.get(i).getPinta2()+"|"+this.ListFichas.get(i).getPinta1()+"]"+ListFichas.get(i).getL()+" ");
            }else{
                System.out.print("["+this.ListFichas.get(i).getPinta1()+"|"+this.ListFichas.get(i).getPinta2()+"]"+ListFichas.get(i).getL()+" ");
            }  
            
        }
        System.out.println("");
    }
    

    private boolean juegoCerrado() {
        return cantPlayJ(turno);
    }
    

    private void ganadorJuegocerrado() {
        int x=0,y=0;
        for (int i = 0; i < this.jugadores.get(0).getListFichas().size(); i++) {
            x+=this.jugadores.get(0).getListFichas().get(i).getPinta1()+this.jugadores.get(0).getListFichas().get(i).getPinta2();
        }
        for (int i = 0; i < this.jugadores.get(1).getListFichas().size(); i++) {
            y+=this.jugadores.get(1).getListFichas().get(i).getPinta1()+this.jugadores.get(1).getListFichas().get(i).getPinta2();
        }
        if(x>y)
            JOptionPane.showMessageDialog(null, "juego cerrado el ganador es "+ this.jugadores.get(1).getNombre()+" Numero de pintas "+y+" a "+x);
        if(x<y)
            JOptionPane.showMessageDialog(null, "juego cerrado el ganador es "+ this.jugadores.get(0).getNombre()+" Numero de pintas "+x+" a "+y);
        else
            JOptionPane.showMessageDialog(null, "juego cerrado empate entre jugadores ");
    }

    private boolean cantPlayJ(int turno) {
        boolean sw=false;
        if(this.jugadores.get(turno).getListFichas().size()>0){
            for (int i = 0; i < this.jugadores.get(turno).getListFichas().size(); i++) {
                if(validarFs(this.ListFichas.get(0),this.jugadores.get(turno).getListFichas().get(i))){
                    sw=true;
                    break;
                }
                if(validarFs(this.ListFichas.get(this.ListFichas.size()-1),this.jugadores.get(turno).getListFichas().get(i))){
                    sw=true;
                    break;
                }
            }
        }
        return sw;
        
    }   
}
