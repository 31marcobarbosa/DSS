/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homesplit.business.GestaoMoradores;

/**
 *
 * @author dinispeixoto
 */
public class MoradorInexistenteException extends Exception{
    
    public MoradorInexistenteException(String msg){
        super(msg);
    }
}
