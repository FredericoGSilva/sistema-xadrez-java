package xadrez;

import tabuleiro.PosicaoTabuleiro;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			new XadrezExcecao
			("Erro instanciando PosicaoXadrez. Valores validos são de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public PosicaoTabuleiro posicaoMatriz() {
		return new PosicaoTabuleiro(8 - linha, coluna - 'a');
	}
	
	public PosicaoXadrez posicaoXadrez(PosicaoTabuleiro posicao) {
		return new PosicaoXadrez((char)('a' - coluna), 8 - linha);
	}
	
	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

}
