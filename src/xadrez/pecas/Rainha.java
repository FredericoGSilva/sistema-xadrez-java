package xadrez.pecas;

import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaPartida;

public class Rainha extends PecaPartida {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "RA";
	}

	@Override
	public boolean[][] movimentosPossiveis() {

		// lógica de movimentos iguais a Torre e Bispo.

		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		PosicaoTabuleiro p = new PosicaoTabuleiro(0, 0);

		// acima da peça
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		// existe uma casa e essa casa possui peça adversária ?
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda - coluna
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// direita - coluna
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// baixo - linha
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonais

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		return matriz;
	}

}
