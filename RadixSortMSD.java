import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadixSortMSD {

    // Tamanho máximo da string a ser ordenada
    private static final int MAX_LENGTH = 100;

    public static void main(String[] args) {
        String inputFile = "D:/Programacao/Java/exercicios/src/dados100_mil.txt"; // Arquivo de entrada
        String outputFile = "output.txt"; // Arquivo de saída

        // Chamada do método para ordenar o arquivo de entrada e escrever no arquivo de saída
        try {
            long startTime = System.currentTimeMillis();
            radixSortFile(inputFile, outputFile);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Tempo de execução: " + formatTime(executionTime));
            System.out.println("Arquivo ordenado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    public static void radixSortFile(String inputFile, String outputFile) throws IOException {
        List<String> lines = readLinesFromFile(inputFile); // Lê as linhas do arquivo de entrada
        radixSort(lines, 0, lines.size() - 1, 0); // Ordena as linhas usando o Radix Sort
        writeLinesToFile(lines, outputFile); // Escreve as linhas ordenadas no arquivo de saída
    }

    // Método para ler as linhas de um arquivo
    public static List<String> readLinesFromFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    // Método para escrever as linhas em um arquivo
    public static void writeLinesToFile(List<String> lines, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Método principal do Radix Sort
    public static void radixSort(List<String> list, int left, int right, int position) {
        if (left < right && position < MAX_LENGTH) {
            int[] count = new int[256]; // Um contador para cada caractere ASCII
            List<String> temp = new ArrayList<>();

            // Contagem da frequência de cada caractere na posição atual
            for (int i = left; i <= right; i++) {
                if (list.get(i).length() <= position) {
                    count[0]++; // Se a string for menor do que a posição atual, considera como caractere nulo (0)
                } else {
                    count[list.get(i).charAt(position) + 1]++;
                }
            }

            // Conversão do contador para um índice de posição
            for (int i = 1; i < 256; i++) {
                count[i] += count[i - 1];
            }

            // Preenchimento do array temporário com os elementos ordenados
            for (int i = left; i <= right; i++) {
                int index = (list.get(i).length() <= position) ? 0 : list.get(i).charAt(position) + 1;
                temp.add(count[index], list.get(i));
                count[index]++;
            }

            // Cópia dos elementos ordenados para a lista original
            for (int i = left; i <= right; i++) {
                list.set(i, temp.get(i - left));
            }

            // Recursão para ordenar os próximos dígitos
            int currentPos = left;
            for (int i = 0; i < 256; i++) {
                int nextPos = currentPos + count[i];
                radixSort(list, currentPos, nextPos - 1, position + 1);
                currentPos = nextPos;
            }
        }
    }

    // Método para formatar o tempo em HH:MM:SS:ms
    public static String formatTime(long millis) {
        long hours = millis / 3600000;
        millis = millis % 3600000;
        long minutes = millis / 60000;
        millis = millis % 60000;
        long seconds = millis / 1000;
        millis = millis % 1000;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
    }
}

