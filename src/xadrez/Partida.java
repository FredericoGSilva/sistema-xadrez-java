package xadrez;

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
	
	private void colocarNovaPeca(char coluna, int linha, PecaPartida peca) {
		tabuleiro.colocarPecaPosicao(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
	}
	
	public void configuracaoInicial() {
		colocarNovaPeca('b', 6, new Torre(tabuleiro, Cor.Branca));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.Preta));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.Branca));
		/*
		tabuleiro.colocarPecaPosicao(new Torre(tabuleiro, Cor.Branca), new PosicaoTabuleiro(2, 1));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.Preta), new PosicaoTabuleiro(0, 4));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.Branca), new PosicaoTabuleiro(7, 4));
		*/
	}
	
}
