package homesplit;

import homesplit.business.HomeSplit;
import homesplit.presentation.FrameIniciarSessao;

/**
 *
 * @author Grupo 35
 */

public class Main {
    
    /**
     * Função Main da aplicação. 
     * @param args Argumentos da main.
     */
    public static void main(String[] args) {
        HomeSplit homesplit = new HomeSplit();
        FrameIniciarSessao f = new FrameIniciarSessao(homesplit);
        f.setVisible(true);
    }
}
