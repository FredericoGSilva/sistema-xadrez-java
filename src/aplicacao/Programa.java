package aplicacao;

import tabuleiro.Tabuleiro;
import xadrez.Partida;

public class Programa {
	public static void main(String[] args) {
		
		Partida partida = new Partida();
		
		UI.printTabuleiro(partida.getPecaPartida());

	}
}
