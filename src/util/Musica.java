/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author Anderson Freitas
 */
public class Musica implements Serializable{
    
    int estacao;
    String musica;

    public Musica(int estacao, String musica) {
        this.estacao = estacao;
        this.musica = musica;
    }

    public Musica() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getEstacao() {
        return estacao;
    }

    public void setEstacao(int estacao) {
        this.estacao = estacao;
    }

    public String getMusica() {
        return musica;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }
    
    
    
}
