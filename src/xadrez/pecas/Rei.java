package xadrez.pecas;

import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaPartida;

public class Rei extends PecaPartida {

	// jogadas especiais
	private Partida partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean testeRoqueTorre(PosicaoTabuleiro posicao) {
		PecaPartida p = (PecaPartida) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimentos() == 0;
	}

	public boolean podeMover(PosicaoTabuleiro posicao) {
		PecaPartida p = (PecaPartida) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	// Por padrão as posições da matriz são falsas -> retornando matriz com todas as
	// posições falsas -> rei preso
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		PosicaoTabuleiro p = new PosicaoTabuleiro(0, 0);

		// acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// a esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// a direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Roque
		if (getContagemMovimentos() == 0 && !partida.getXeque()) {

			// roque pequeno
			PosicaoTabuleiro posicaoTorreRei = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() + 3);
			
			if (testeRoqueTorre(posicaoTorreRei)) {
				PosicaoTabuleiro pDireita1 = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() + 1);
				PosicaoTabuleiro pDireita2 = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(pDireita1) == null && getTabuleiro().peca(pDireita2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			
			// roque grande
			PosicaoTabuleiro posicaoTorreRainha = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 4);
			
			if (testeRoqueTorre(posicaoTorreRainha)) {
				PosicaoTabuleiro pDireita1 = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 1);
				PosicaoTabuleiro pDireita2 = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 2);
				PosicaoTabuleiro pDireita3 = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(pDireita1) == null && getTabuleiro().peca(pDireita2 ) == null && 
						getTabuleiro().peca(pDireita3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
				
			}
			
		}

		return matriz;
	}

}
