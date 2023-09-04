package tabuleiro;

public abstract class Peca {
	
	protected PosicaoTabuleiro posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro; 
		posicao = null;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean possiveisMovimentos(PosicaoTabuleiro posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	// pelo menos 1 movimento possível -> peça está ou não cercada por outras peças.
	public boolean existeAlgumMovimentoPossivel() {
		boolean[][] matriz = movimentosPossiveis();
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
}
