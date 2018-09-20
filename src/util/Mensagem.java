/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author Programador-03
 */
public class Mensagem implements Serializable{

    public Mensagem() {
    }

    /**
     * Comandos do cliente para o servidor
     * 1. Hello: uint8_t commandType = 0;      uint16_t udpPort;
     * 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
     * 
     * Respostas do servidor para o cliente
     * 1. Welcome:uint8_t replyType = 0; uint16_t numStations;
     * 2. Announce:uint8_t replyType = 1; uint8_t songnameSize; char songname[songnameSize];
     */
    private int commandType;
    private int updPort;
    private int stationNumber;
    private int replayType;
    private int numStation;

    public int getReplayType() {
        return replayType;
    }

    public void setReplayType(int replayType) {
        this.replayType = replayType;
    }

    public int getNumStation() {
        return numStation;
    }

    public void setNumStation(int numStation) {
        this.numStation = numStation;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }
    
    
    @Override
    public String toString() {
        return "Mensagem{" + '}';
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public int getUpdPort() {
        return updPort;
    }

    public void setUpdPort(int updPort) {
        this.updPort = updPort;
    }

}
