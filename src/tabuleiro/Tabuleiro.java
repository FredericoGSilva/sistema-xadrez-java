package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao
			("Erro criando tabuleiro: é necessário que haja 1 linha e 1 coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	
	//retorna matriz pecas na linha e coluna.
	public Peca peca(int linha, int coluna) {
		if (!existePosicao(linha, coluna)) {
			throw new TabuleiroExcecao("Posição não está no quadro.");
		}
		return pecas[linha][coluna];
	}
	
	// sobrecarga -> retornar a peça pela posição.
	public Peca peca(PosicaoTabuleiro posicao) {
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição não está no quadro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	// colocar peça na posição.
	public void colocarPecaPosicao(Peca peca, PosicaoTabuleiro posicao) {
		if (existePeca(posicao)) {
			throw new TabuleiroExcecao("Já existe uma peça na posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	// método auxiliar
	public boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas; 
	}
	
	// Antes de com uma posição e uma peça -> verificar se elas existem.
	public boolean existePosicao(PosicaoTabuleiro posicao) {
		return existePosicao(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean existePeca(PosicaoTabuleiro posicao) {
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição não está no quadro.");
		}
		return peca(posicao) != null;
	}
	
	// remover uma peça de acordo cm a posição do tabuleiro.
	public Peca removerPeca(PosicaoTabuleiro posicao) {
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição não está no quadro.");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
}
