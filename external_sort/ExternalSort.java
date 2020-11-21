import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ExternalSort {
    //nome do arquivo de saida do programa
    public String outPutFileName;
    //numero maximo de arquivos que serao utilizados para ordenar
    public static int numFiles = 2;
    //tamanho maximo de registros
    public int sizeFile;

    public int partition(long[] list, int p, int r) {
        int media = (p + r) / 2;
        long a = list[p];
        long b = list[media];
        long c = list[r];

        int index = 0;
        if (a < b) {
            if (b < c)
                index = media;
            else {
                if (a < c)
                    index = r;
                else
                    index = p;
            }
        } else {
            if (c < b)
                index = media;
            else {
                if (c < a)
                    index = r;
                else
                    index = p;
            }
        }

        swap(list, index, r);

        long x = list[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (list[j] <= x) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, r);
        return (i + 1);
    }

    public void swap(long[] list, int i, int j) {
        long a = list[i];
        long b = list[j];

        list[i] = b;
        list[j] = a;
    }

    public void quickSort(long[] list, int p, int r) {
        if (p < r) {
            int q = partition(list, p, r);
            quickSort(list, p, q - 1);
            quickSort(list, q + 1, r);
        }
    }

    public int getMin(long[] list) {
        int index = 0;
        for (int i = 1; i < numFiles; i++) {
            if (list[i] < list[i-1]) {
                index = i;
            }
        }
        return index;
    }

    public void setSizeFile(int size) {
        sizeFile = size;
        outPutFileName = "output_" + size;
    }

    public void readFile(String filename, List<FileWriter> files) throws IOException {
        outPutFileName += "_" + filename;
        FileReader fr = null;
        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException fe) {
            throw fe;
        }

        BufferedReader buffer = new BufferedReader(fr);

        String line = buffer.readLine();
        boolean stop = false;
        int i = 0;
        while (!stop && i < numFiles) {
            long[] array = new long[this.sizeFile];
            int j;
            //sera preenchido um array com o tamanho maximo definido para cada arquivo
            for (j = 0; j < this.sizeFile; j++) {
                array[j] = Long.parseLong(line);
                line = buffer.readLine();
                if (line == null) {
                    stop = true;
                    break;
                }
            }
            //ordenando array para escrever no arquivo
            quickSort(array, 0, j - 1);

            //escrevendo todo o array no arquivo e adicionando +1 no i para seguir com o proximo arquivo
            FileWriter file = files.get(i);
            for (int k = 0; k < j; k++)
                file.write(Long.toString(array[k]) + "\n");
            file.close();
            i++;
        }
        buffer.close();
        fr.close();
    }

    public void mergeFiles(List<BufferedReader> files) throws IOException {
        FileWriter finalFile = new FileWriter(this.outPutFileName);

        //preenchendo o array 'toWrite' com a primeira posicao de cada arquivo
        int emptyFiles = 0;
        long[] toWrite = new long[numFiles];
        for (int i = 0; i < numFiles; i++) {
            BufferedReader buffer = files.get(i);
            toWrite[i] = Long.parseLong(buffer.readLine());
        }

        //loop que pega sempre o valor minimo do array e escreve ele no arquivo final
        //apos escrever, substitui a posição do valor minimo com o proximo elemento do buffer correspondente 
        //a condicao de parada é quando os dois arquivos chegarem ao final
        while (emptyFiles < numFiles) {
            int j = getMin(toWrite);
            if (toWrite[j] != -1) {
                finalFile.write(Long.toString(toWrite[j]) + "\n");
                String line = files.get(j).readLine();
                toWrite[j] = (line != null) ? Long.parseLong(line) : -1;
            } else
                emptyFiles++;
        }

        finalFile.close();
    }

    public void createFiles(String fileInput) throws IOException {
        //criando os arquivos temporarios com nomes sequenciais e escrevendo os valores ordenados dentro de cada um 
        long start = System.nanoTime();
        ArrayList<FileWriter> outputs = new ArrayList<FileWriter>();
        for (int i = 0; i < numFiles; i++) {
            outputs.add(new FileWriter("file_" + i + ".txt"));
        }
        readFile(fileInput, outputs);        
        long end = System.nanoTime();
        long elapsedTime = end - start;
        System.out.println(outPutFileName + ": (nanossegundos) " + elapsedTime);

        //após a ordenação será feito o merge desses arquivos
        //dentro de 'mergeFiles' ja é passado o buffer de leitura
        start = System.nanoTime();
        List<BufferedReader> files = new ArrayList<BufferedReader>();
        for (int i = 0; i < numFiles; i++) {
            FileReader fr = new FileReader("file_" + i + ".txt");
            files.add(new BufferedReader(fr));
        }
        mergeFiles(files);                
        end = System.nanoTime();
        elapsedTime = end - start;
        System.out.println(outPutFileName + ": (nanossegundos) " + elapsedTime);

        //fechando arquivos
        for (int i = 0; i < numFiles; i++) {
            files.get(i).close();
        }
    }

    public static void main(String[] args) {
        ExternalSort sort = new ExternalSort();

        try {
            //definindo os dois casos de capacidade de memoria
            int[] sizes = new int[2];
            sizes[0] = 1000000;
            sizes[1] = 10000000;

            //definindo os dois arquivos a serem lidos
            String[] files = new String[2];
            files[0] = "arq15M.txt";
            files[1] = "arq40M.txt";

            //loop para iterar nos 4 casos possiveis
            for(int i = 0; i < sizes.length; i++) {
                for (int j = 0; j < files.length; j++) {
                    sort.setSizeFile(sizes[i]);
                    sort.createFiles(files[j]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}