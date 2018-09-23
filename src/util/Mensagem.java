package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Programador-03
 */
public class Mensagem implements Serializable {

    public Mensagem() {
    }

    /**
     * Comandos do cliente para o servidor 1. Hello: uint8_t commandType = 0;
     * uint16_t udpPort; 2. SetStation: uint8_t commandType = 1; uint16_t
     * stationNumber;
     *
     * Respostas do servidor para o cliente 1. Welcome: uint8_t replyType = 0;
     * uint16_t numStations; 2. Announce:uint8_t replyType = 1; uint8_t
     * songnameSize; char songname[songnameSize]; 3. InvalidCommand:uint8_t
     * replyType = 2; uint8_t replyStringSize; char
     * replyString[replyStringSize];
     */
    private char commandType;
    private int updPort;
    private int stationNumber;
    private char replayType;
    private int numStation;

    Map<String, String> estacoes = new HashMap<>();

    public Map<String, String> getEstacoes() {
        return estacoes;
    }

    public void setEstacoes(Map<String, String> estacoes) {
        this.estacoes = estacoes;
    }

    public int getReplayType() {
        return replayType;
    }

    public void setReplayType(char replayType) {
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

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(char commandType) {
        this.commandType = commandType;
    }

    public int getUpdPort() {
        return updPort;
    }

    public void setUpdPort(int updPort) {
        this.updPort = updPort;
    }

}
