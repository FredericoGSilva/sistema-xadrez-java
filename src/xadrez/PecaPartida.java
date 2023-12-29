package xadrez;

import tabuleiro.Peca;
import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;

public abstract class PecaPartida extends Peca {
	
	private Cor cor;

	public PecaPartida(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	protected boolean existePeçaOponente(PosicaoTabuleiro posicao) {
		PecaPartida peca = (PecaPartida) getTabuleiro().peca(posicao);
		return peca != null && peca.getCor() != cor;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.posicaoXadrez(posicao);
	}
	
	public Cor getCor() {
		return cor;
	}

}
