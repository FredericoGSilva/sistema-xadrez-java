package xadrez.pecas;

import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaPartida;

public class Peao extends PecaPartida {

	private Partida partida;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
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
			
			// movimento especial: EnPassant -> brancas
			
			// peça adversária do lado direito. 
			if (posicao.getLinha() == 3) {
				PosicaoTabuleiro esquerda = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && existePecaOponente(esquerda) 
						&& getTabuleiro().peca(esquerda) == partida.getEnPassant()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				
				// peça adversária do lado esquerdo.
				PosicaoTabuleiro direita = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && existePecaOponente(direita) 
						&& getTabuleiro().peca(direita) == partida.getEnPassant()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
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
			
			// movimento especial: EnPassant -> pretas
			
			// peça adversária do lado esquerdo.
			if (posicao.getLinha() == 4) {
				PosicaoTabuleiro esquerda = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && existePecaOponente(esquerda) && 
						getTabuleiro().peca(esquerda) == partida.getEnPassant()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				
				// peça adversária do lado direito
				PosicaoTabuleiro direita = new PosicaoTabuleiro(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePeca(direita) && existePecaOponente(direita) && 
						getTabuleiro().peca(direita) == partida.getEnPassant()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
			
		}
		
		return matriz;
		
	}

	
	
}
