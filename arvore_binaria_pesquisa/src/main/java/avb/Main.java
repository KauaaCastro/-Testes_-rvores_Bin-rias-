package avb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Btree btree = new Btree();
        String clear = "\033\143";
        System.out.println(clear);

        System.out.println("Iniciando a leitura do arquivo...");

        List<Integer> number = new ArrayList<>();
        String archive = "teste01.txt";
        String endress = "C:\\Users\\Cricca\\Downloads\\estudos\\Estrutura de Dados\\Teste de desempenho\\testes\\"
                + archive;

        try (BufferedReader read = new BufferedReader(new FileReader(endress))) {
            String line;

            while ((line = read.readLine()) != null) {
                int newNumber = Integer.parseInt(line.trim());
                number.add(newNumber);
            }

            System.out.println("Preenchimento concluído! Iniciando montagem da arvore");
            Thread.sleep(2000);

            long startedTime = System.nanoTime();

            for (int numbers : number) {
                btree.Insert(numbers);
            }

            long finalTime = System.nanoTime();

            System.out.println(clear);
            System.out.println("Inserçao finalizada, exibindo as informaçoes obtidas");
            Thread.sleep(2000);

            long totalTime = finalTime - startedTime;

            double durationFinal = (double) totalTime / 1000000;

            System.out.println(clear);
            System.out.println("------------------------------------------------");
            System.out.println("Contagem final de tempo: " + durationFinal);
            System.out.println("------------------------------------------------");

        } catch (IOException e) {
            System.out.println(clear);
            System.out.println("Ocorreu um erro ao realizar a leitura");

            e.printStackTrace();
        }
    }
}