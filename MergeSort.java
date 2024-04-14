import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static void main(String[] args) {
        String inputFile = "D:/Programacao/Java/exercicios/src/dados100_mil.txt"; // Arquivo de entrada
        String outputFile = "output.txt"; // Arquivo de saída

        // Chamada do método para ordenar o arquivo de entrada e escrever no arquivo de saída
        try {
            long startTime = System.currentTimeMillis();
            mergeSortFile(inputFile, outputFile);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Tempo de execução: " + formatTime(executionTime));
            System.out.println("Arquivo ordenado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    public static void mergeSortFile(String inputFile, String outputFile) throws IOException {
        List<String> lines = readLinesFromFile(inputFile); // Lê as linhas do arquivo de entrada
        mergeSort(lines, 0, lines.size() - 1); // Ordena as linhas usando o Merge Sort
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

    // Método principal do Merge Sort
    public static void mergeSort(List<String> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid); // Ordena a metade esquerda
            mergeSort(list, mid + 1, right); // Ordena a metade direita
            merge(list, left, mid, right); // Combina as duas metades ordenadas
        }
    }

    // Método para combinar duas metades ordenadas
    public static void merge(List<String> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Arrays temporários
        String[] L = new String[n1];
        String[] R = new String[n2];

        // Copia os dados para os arrays temporários L[] e R[]
        for (int i = 0; i < n1; ++i) {
            L[i] = list.get(left + i);
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = list.get(mid + 1 + j);
        }

        // Combina os arrays temporários de volta ao array original
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                list.set(k, L[i]);
                i++;
            } else {
                list.set(k, R[j]);
                j++;
            }
            k++;
        }

        // Copia os elementos restantes de L[] se houver algum
        while (i < n1) {
            list.set(k, L[i]);
            i++;
            k++;
        }

        // Copia os elementos restantes de R[] se houver algum
        while (j < n2) {
            list.set(k, R[j]);
            j++;
            k++;
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
