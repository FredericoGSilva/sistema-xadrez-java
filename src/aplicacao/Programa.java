package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import tabuleiro.Tabuleiro;
import xadrez.Partida;
import xadrez.PecaPartida;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class Programa {
	public static void main(String[] args) {
		
		Partida partida = new Partida();
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			try {
				UI.limparTelas();
				UI.printTabuleiro(partida.getPecaPartida());
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(scan);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(scan);
				PecaPartida capturaPeca = partida.executarMovimentoXadrez(origem, destino);
			} catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			
		}

	}
}
