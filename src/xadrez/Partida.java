package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	// Regras do jogo.
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaPartida enPassant;
	private PecaPartida pecaPromovida;
	
	// controle de peças
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
	}
	
	public PecaPartida getEnPassant() {
		return enPassant;
	}
	
	public PecaPartida getPecaPromovida() {
		return pecaPromovida;
	}
	
	public PecaPartida[][] getPecaPartida() {
		PecaPartida[][] matriz = new PecaPartida[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaPartida) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	// Ao escolher uma peça -> indicar para quais posições a peça pode mexer
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		PosicaoTabuleiro posicao = posicaoOrigem.posicaoMatriz();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaPartida executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		// converter posicaoOrigem e posicaoDestino p/ posição da matriz.
		PosicaoTabuleiro origemMatriz = posicaoOrigem.posicaoMatriz();
		PosicaoTabuleiro destinoMatriz = posicaoDestino.posicaoMatriz();
		validarPosicaoOrigem(origemMatriz);
		validarPosicaoDestino(origemMatriz, destinoMatriz);
		
		PecaPartida capturaPeca = (PecaPartida) movimentarPeca(origemMatriz, destinoMatriz);
		
		if (testXeque(jogadorAtual)) {
			desfazerMovimento(origemMatriz, destinoMatriz, capturaPeca);
			throw new XadrezExcecao("Você não pode se colocar em xeque.");
		}
		
		PecaPartida moverPeca = (PecaPartida) tabuleiro.peca(destinoMatriz);
		
		// Peça Promovida
		pecaPromovida = null;
		if (moverPeca instanceof Peao) {
			if ((moverPeca.getCor() == Cor.BRANCA && destinoMatriz.getLinha() == 0) || 
					(moverPeca.getCor() == Cor.PRETA && destinoMatriz.getLinha() == 7)) {
				pecaPromovida = (PecaPartida) tabuleiro.peca(destinoMatriz);
				pecaPromovida = substituirPecaPromovida("RA");
			}
		}
		
		xeque = (testXeque(oponente(jogadorAtual))) ? true : false;
		
		if (testXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
	
		// enPassant
		if (moverPeca instanceof Peao && (destinoMatriz.getLinha() == origemMatriz.getLinha() - 2 || 
				destinoMatriz.getLinha() == origemMatriz.getLinha() + 2)) {
			enPassant = moverPeca;
		} else {enPassant = null;}
		
		return (PecaPartida) capturaPeca;
	}
	
	public PecaPartida substituirPecaPromovida(String tipo) {
		if (pecaPromovida == null) {
			throw new IllegalStateException("Não há peça para ser promovida.");
		}
		if (!tipo.equals("C") && !tipo.equals("B") && !tipo.equals("T") & !tipo.equals("RA")) {
			return pecaPromovida;
		}
		PosicaoTabuleiro posicaoPecaPromo = pecaPromovida.getPosicaoXadrez().posicaoMatriz();
		Peca p = tabuleiro.removerPeca(posicaoPecaPromo); 
		pecasNoTabuleiro.remove(p);
		PecaPartida novaPeca = novaPeca(tipo, pecaPromovida.getCor());
		tabuleiro.colocarPecaPosicao(novaPeca, posicaoPecaPromo);
		pecasNoTabuleiro.add(novaPeca);
		return novaPeca;
	}
	
	// método auxiliar -> 
	private PecaPartida novaPeca(String tipo, Cor cor) {
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("T")) return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
	}
	
	public Peca movimentarPeca(PosicaoTabuleiro posicaoOrigem, PosicaoTabuleiro posicaoDestino) {
		PecaPartida peca = (PecaPartida) tabuleiro.removerPeca(posicaoOrigem);
		peca.aumentaContagem();
		Peca pecaCapturada = tabuleiro.removerPeca(posicaoDestino);
		tabuleiro.colocarPecaPosicao(peca, posicaoDestino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		// Roque pequeno
		if (peca instanceof Rei && posicaoDestino.getColuna() == posicaoOrigem.getColuna() + 2) {
			PosicaoTabuleiro origemTorre = new PosicaoTabuleiro(posicaoOrigem.getLinha(), posicaoOrigem.getColuna() + 3);
			PosicaoTabuleiro destinoTorre = new PosicaoTabuleiro(posicaoOrigem.getLinha(), posicaoOrigem.getColuna() + 1);
			PecaPartida torre = (PecaPartida) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPecaPosicao(torre, destinoTorre);
			torre.aumentaContagem();
		}
		
		// Roque grande
		if (peca instanceof Rei && posicaoDestino.getColuna() == posicaoOrigem.getColuna() - 2) {
			PosicaoTabuleiro origemTorre = new PosicaoTabuleiro(posicaoOrigem.getLinha(), posicaoOrigem.getColuna() - 4);
			PosicaoTabuleiro destinoTorre = new PosicaoTabuleiro(posicaoOrigem.getLinha(), posicaoOrigem.getColuna() - 1);
			PecaPartida torre = (PecaPartida) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPecaPosicao(torre, destinoTorre);
			torre.aumentaContagem();
		}
		
		// EnPassant
		if (peca instanceof Peao) {
			if (posicaoOrigem.getColuna() != posicaoDestino.getColuna() && pecaCapturada == null) {
				PosicaoTabuleiro posicaoPeao;
				if (peca.getCor() == Cor.BRANCA) {
					// peão branco
					posicaoPeao = new PosicaoTabuleiro(posicaoDestino.getLinha() + 1, posicaoDestino.getColuna());
				} else {
					// peão preto
					posicaoPeao = new PosicaoTabuleiro(posicaoDestino.getLinha() - 1, posicaoDestino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	public void desfazerMovimento(PosicaoTabuleiro origem, PosicaoTabuleiro destino, Peca capturada) {
		PecaPartida peca = (PecaPartida) tabuleiro.removerPeca(destino);
		peca.diminuiContagem();
		tabuleiro.colocarPecaPosicao(peca, origem);
		if (capturada != null) {
			tabuleiro.colocarPecaPosicao(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasNoTabuleiro.add(capturada);
		}
		// Roque pequeno
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			PosicaoTabuleiro origemTorre = new PosicaoTabuleiro(origem.getLinha(), origem.getColuna() + 3);
			PosicaoTabuleiro destinoTorre = new PosicaoTabuleiro(origem.getLinha(), origem.getColuna() + 1);
			PecaPartida torre = (PecaPartida) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPecaPosicao(peca, origemTorre);
			torre.diminuiContagem();
		}
		// Roque pequeno
		if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			PosicaoTabuleiro origemTorre = new PosicaoTabuleiro(origem.getLinha(), origem.getColuna() - 4);
			PosicaoTabuleiro destinoTorre = new PosicaoTabuleiro(origem.getLinha(), origem.getColuna() - 1);
			PecaPartida torre = (PecaPartida) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPecaPosicao(peca, origemTorre);
			torre.diminuiContagem();
		}
		
		// EnPassant
		if (peca instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecasCapturadas == enPassant) {
				PecaPartida peao = (PecaPartida) tabuleiro.removerPeca(destino);
				PosicaoTabuleiro posicaoPeao;
				if (peca.getCor() == Cor.BRANCA) {
					posicaoPeao = new PosicaoTabuleiro(3, destino.getColuna());
				} else {
					posicaoPeao = new PosicaoTabuleiro(4, destino.getColuna());
				}
				
				tabuleiro.colocarPecaPosicao(peao, posicaoPeao);
			}
		}
		
	}
	
	public void validarPosicaoOrigem(PosicaoTabuleiro posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem.");
		}
		if (jogadorAtual != ((PecaPartida) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua.");
		}
		
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não existe movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarPosicaoDestino(PosicaoTabuleiro posicaoOrigem, PosicaoTabuleiro posicaoDestino) {
		if (!tabuleiro.peca(posicaoOrigem).possiveisMovimentos(posicaoDestino)) {
			throw new XadrezExcecao("A peça escolhida não pode se mover para a posição de destino."); 
		}
	}
	
	public void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.BRANCA ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaPartida rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(
				x -> ((PecaPartida) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaPartida) p;
			}
		}
		throw new IllegalStateException("Não existe rei na cor " + cor + " no tabuleiro.");
	}
	
	private boolean testXeque(Cor cor) {
		PosicaoTabuleiro posicaoRei = rei(cor).getPosicaoXadrez().posicaoMatriz();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> (
				(PecaPartida) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testXequeMate(Cor cor) {
		if (!testXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(
				x ->((PecaPartida) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						PosicaoTabuleiro origem = ((PecaPartida) p).getPosicaoXadrez().posicaoMatriz();
						PosicaoTabuleiro destino = new PosicaoTabuleiro(i, j);
						Peca capturaPeca = movimentarPeca(origem, destino);
						boolean testeXeque = testXeque(cor);
						desfazerMovimento(origem, destino, capturaPeca);
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaPartida peca) {
		tabuleiro.colocarPecaPosicao(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
		pecasNoTabuleiro.add(peca);
	}
	
	public void configuracaoInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));;
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));
		
		/*
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
		*/
		
		/*
		tabuleiro.colocarPecaPosicao(new Torre(tabuleiro, Cor.BRANCA), new PosicaoTabuleiro(2, 1));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.PRETA), new PosicaoTabuleiro(0, 4));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.BRANCA), new PosicaoTabuleiro(7, 4));
		*/
	}
	
}
