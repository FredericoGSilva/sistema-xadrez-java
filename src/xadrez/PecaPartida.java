package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public abstract class PecaPartida extends Peca {
	
	private Cor cor;

	public PecaPartida(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	public Cor getCor() {
		return cor;
	}

}
