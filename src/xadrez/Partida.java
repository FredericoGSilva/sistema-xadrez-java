package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public class Partida {

	// Regras do jogo.
	
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	
	public PecaPartida[][] getPecaPartida() {
		PecaPartida[][] matriz = new PecaPartida[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaPartida) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
}
