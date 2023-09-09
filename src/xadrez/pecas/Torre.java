package xadrez.pecas;

import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaPartida;

public class Torre extends PecaPartida {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {

		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		PosicaoTabuleiro posicaoAuxiliar = new PosicaoTabuleiro(0, 0);

		// acima da peça
		posicaoAuxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(posicaoAuxiliar) && !getTabuleiro().existePeca(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
			posicaoAuxiliar.setLinha(posicaoAuxiliar.getLinha() - 1);
		}
		// existe uma casa e essa casa possui peça adversária ?
		if (getTabuleiro().existePosicao(posicaoAuxiliar) && existePeçaOponente(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
		}

		// esquerda - coluna
		posicaoAuxiliar.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(posicaoAuxiliar) && !getTabuleiro().existePeca(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
			posicaoAuxiliar.setColuna(posicaoAuxiliar.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(posicaoAuxiliar) && existePeçaOponente(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
		}

		// direita - coluna
		posicaoAuxiliar.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(posicaoAuxiliar) && !getTabuleiro().existePeca(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
			posicaoAuxiliar.setColuna(posicaoAuxiliar.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(posicaoAuxiliar) && existePeçaOponente(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
		}

		// esquerda - coluna
		posicaoAuxiliar.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(posicaoAuxiliar) && !getTabuleiro().existePeca(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
			posicaoAuxiliar.setColuna(posicaoAuxiliar.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(posicaoAuxiliar) && existePeçaOponente(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
		}

		// baixo - linha
		posicaoAuxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(posicaoAuxiliar) && !getTabuleiro().existePeca(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
			posicaoAuxiliar.setLinha(posicaoAuxiliar.getLinha() + 1);
		}
		if (getTabuleiro().existePosicao(posicaoAuxiliar) && existePeçaOponente(posicaoAuxiliar)) {
			matriz[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
		}

		return matriz;
	}

}
