package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	// Regras do jogo.
	
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
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
	
	public void configuracaoInicial() {
		tabuleiro.colocarPecaPosicao(new Torre(tabuleiro, Cor.Branca), new Posicao(2, 1));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.Preta), new Posicao(0, 4));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.Branca), new Posicao(7, 4));
	}
	
}
