/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homesplit.business.GestaoDespesas;

/**
 *
 * @author dinispeixoto
 */
public class DespesaInvalidaException extends Exception{
    public DespesaInvalidaException(String msg){
        super(msg);
    }
}
