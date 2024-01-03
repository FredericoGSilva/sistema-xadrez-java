package xadrez.pecas;

import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaPartida;

public class Peao extends PecaPartida {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		PosicaoTabuleiro p = new PosicaoTabuleiro(0, 0);
		
		if (getCor() == Cor.BRANCA) {
			// 1 casa para frente.
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			// primeiro movimento -> 2 casas para frente.
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			PosicaoTabuleiro p2 = new PosicaoTabuleiro(p.getLinha() - 1, p.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && 
					getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && 
					getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		
			// diagonal direita
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			// diagonal esquerda
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		else {
			// peão preto
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			PosicaoTabuleiro p2 = new PosicaoTabuleiro(p.getLinha() + 1, p.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && 
					getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && 
					getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return matriz;
	}

	
	
}
