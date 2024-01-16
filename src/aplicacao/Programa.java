package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import tabuleiro.Tabuleiro;
import xadrez.Partida;
import xadrez.PecaPartida;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class Programa {
	public static void main(String[] args) {
		
		Partida partida = new Partida();
		List<PecaPartida> capturadas = new ArrayList<>();
		
		Scanner scan = new Scanner(System.in);
		
		while (!partida.getXequeMate()) {
			try {
				UI.limparTela();
				//UI.printTabuleiro(partida.getPecaPartida());
				UI.imprimirPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(scan);
				
				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getPecaPartida(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(scan);
				PecaPartida capturaPeca = partida.executarMovimentoXadrez(origem, destino);
				
				if (capturaPeca != null) {
					capturadas.add(capturaPeca);
				}
				
				// peão foi promovido
				if (partida.getPecaPromovida() != null) {
					System.out.println("Insira peça para promoção (C/B/T/RA): ");
					String tipo = scan.nextLine();
					partida.substituirPecaPromovida(tipo);
				}
				
			} catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			
		}
		
		UI.limparTela();
		UI.imprimirPartida(partida, capturadas);

	}
}
