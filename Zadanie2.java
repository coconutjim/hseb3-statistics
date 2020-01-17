import java.util.Arrays;
import java.util.Random;

/**
 * Created by Lev on 10.12.2014.
 */
public class Zadanie2 {

    public static void main(String[] args) {

        int[] selectionSortOperations = new int[100];
        int[] insertionSortOperations = new int[100];

        for (int i = 0; i < 100; ++ i) {
            double[] array = generateArrayForTask2(20);
            double[] arrayCopy = Arrays.copyOf(array, 20);
            selectionSortOperations[i] = selectionSort(array);
            insertionSortOperations[i] = insertionSort(arrayCopy);
        }

        for (int value : selectionSortOperations) {
            System.out.println(value);
        }

        System.out.println("--------------------");

        for (int value : insertionSortOperations) {
            System.out.println(value);
        }
    }

    /***
     * Сортировка выбором, возвращает количество операций
     * @param array входной массив
     * @return количество операций
     */
    private static int selectionSort(double[] array) {
        int operationCount = 0;
        int length = array.length; operationCount += 2;
        operationCount += 1; // изначальное присваивание в главном цикле
        for (int i = 0; i < length - 1; ++ i) {
            operationCount += 2; // сравнение в главном цикле
            int min = i; operationCount += 1;
            operationCount += 2; // изначальное присваивание во вложенном цикле
            for (int j = i + 1; j < length; ++ j) {
                operationCount += 1; // сравнение во вложенном цикле
                operationCount += 3; // сравнение тут
                if (array[j] < array[min]) {
                    min = j; operationCount += 1;
                }
                operationCount += 2; // инкрементирование счетчика вложенного цикла
            }
            operationCount += 1; // сравнение во вложенном цикле
            double temp = array[i]; operationCount += 2;
            array[i] = array[min]; operationCount += 3;
            array[min] = temp; operationCount += 2;
            operationCount += 2; // инкрементирование счетчика главного цикла
        }
        operationCount += 2; // сравнение в главном цикле
        return operationCount;
    }

    /***
     * Сортировка вставками, возвращает количество операций
     * @param array входной массив
     * @return количество операций
     */
    public static int insertionSort(double[] array) {
        int operationCount = 0;
        int length = array.length; operationCount += 2;
        operationCount += 1; // изначальное присваивание в главном цикле
        for (int i = 1; i < length; ++ i){
            operationCount += 1; // сравнение в главном цикле

            double current = array[i];  operationCount += 2;
            int prevIndex = i - 1;    operationCount += 2;
            while(prevIndex >= 0 && array[prevIndex] > current){
                operationCount += 4; // проверка выполнения вложенного цикла
                array[prevIndex + 1] = array[prevIndex];  operationCount += 4;
                array[prevIndex] = current; operationCount += 2;
                -- prevIndex; operationCount += 2;
            }
            operationCount += 4; // проверка выполнения вложенного цикла
            operationCount += 2; // инкрементирование счетчика главного цикла
        }
        operationCount += 1; // сравнение в главном цикле
        return operationCount;
    }

    /***
     * Генерирует массив рановмерно распределенных чисел от 0 до 1
     * @param n длинна массива
     * @return массив
     */
    private static double[] generateArrayForTask2(int n) {
        double[] array = new double[n];
        Random random = new Random();
        for (int i = 0; i < n; ++ i) {
            array[i] = random.nextDouble();
        }
        return array;
    }
}
