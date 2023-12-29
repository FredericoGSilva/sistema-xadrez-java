package xadrez;

import tabuleiro.PosicaoTabuleiro;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao
			("Erro instanciando PosicaoXadrez. Valores validos são de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	
	protected PosicaoTabuleiro posicaoMatriz() {
		return new PosicaoTabuleiro(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez posicaoXadrez(PosicaoTabuleiro posicao) {
		return new PosicaoXadrez
				((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

}
