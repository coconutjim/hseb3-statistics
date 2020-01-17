import java.util.Random;

/**
 * Created by Lev on 10.12.2014.
 */
public class Zadanie1 {

    private static final int STANDARD_NORMAL_TYPE = 0; // для нормального стандартного распределения
    private static final int CUSTOM_TYPE = 1; // для распределения третьего варианта

    public static void main(String[] args) {


        System.out.println(portionOfTrueIntervals(1000, 10, STANDARD_NORMAL_TYPE));
        System.out.println(portionOfTrueIntervals(1000, 10, CUSTOM_TYPE));

        System.out.println(portionOfTrueIntervals(1000, 400, STANDARD_NORMAL_TYPE));
        System.out.println(portionOfTrueIntervals(1000, 400, CUSTOM_TYPE));

    }

    /***
     * Рассчитывает долю верных доверительных интервалов для математического ожидания
     * @param n количество испытаний
     * @param count количество значений в выборке
     * @param type тип распределения (стандратное нормальное или для 3-го варианта)
     * @return доля верных интервалов
     */
    private static double portionOfTrueIntervals(int n, int count, int type) {
        if (type != STANDARD_NORMAL_TYPE && type != CUSTOM_TYPE) {
            throw new IllegalArgumentException("Неверный тип исследования!");
        }
        double portion = 0;
        double values[];
        for (int i = 0; i < n; ++ i) {
            if (type == STANDARD_NORMAL_TYPE) {
                values = standardNormalDistribution(count);
            }
            else  {
                values = customDistribution(count);
                /*for (double value : values) {
                    System.out.println(value);
                }
                System.out.println(expected);*/
            }
            double expected = 0; // для нормального стандратного распределения 0, для распределения 3-го
                                 // варианта -3 * 0.5 + 3 * 0.5 = 0
            ConfidenceInterval interval = countInterval(values);
            //System.out.println(interval.toString());
            if (interval.occurrence(expected)) {
                ++ portion;
            }
        }
        portion /= n;
        return portion;
    }

    /***
     * Генерирует заданное количество стандартного значений нормального распределения
     * @param n количество значений
     * @return значения нормального распределения
     */
    private static double[] standardNormalDistribution(int n) {
        double[] values = new double[n];
        Random random = new Random();
        for (int i = 0; i < n; ++ i) {
            values[i] = random.nextGaussian();
        }
        return values;
    }

    /***
     * Генерирует заданное количество значений распределения 3-го варианта
     * @param n количество значений
     * @return значения  распределения
     */
    private static double[] customDistribution(int n) {
        double[] values = new double[n];
        Random random = new Random();
        for (int i = 0; i < n; ++ i) {
            double number = random.nextDouble();
            values[i] = (number > 0.5)? -3 : 3;
        }
        return values;
    }

    /***
     * Рассчитывает выборочное среднее для значения распределения
     * @param values значения
     * @return выборочное среднее
     */
    private static double countAverage(double[] values) {
        double average = 0;
        int length = values.length;
        for (double value : values) {
            average += value;
        }
        average /= length;
        return average;
    }

    /***
     * Рассчитывает выборочное стандартное отклонение
     * @param values значения
     * @return выборочное страндартное отклонение
     */
    private static double countDeviation(double[] values) {
        double average = countAverage(values);
        double deviation = 0;
        for (double value : values) {
            deviation += Math.pow((value - average), 2);
        }
        deviation /= values.length;
        deviation = Math.sqrt(deviation);
        return deviation;
    }

    /***
     * Расчитывает 90%-ный доверительный интервал для математического ожидания
     * Работает только на выборках размером 10 и 400 (за неимением других табличных значений)
     * @param values значения
     * @return доверительный интервал
     */
    private static ConfidenceInterval countInterval(double[] values) {
        int length = values.length;
        double t; // табличное значение
        if (length == 10) {
            t = 1.833112933;
        }
        else {
            if (length == 400) {
                t = 1.648681534;
            }
            else {
                throw new IllegalArgumentException("Нет значения из таблицы для выборки размером "
                        + length  + "!");
            }
        }
        double average = countAverage(values);
        double deviation = countDeviation(values);

        double expression = (t * deviation) / Math.sqrt(length);
        return new ConfidenceInterval(average - expression, average + expression);
    }

    private static class ConfidenceInterval {

        /** Нижняя граница интервала */
        private double bottomValue;

        /** Верхняя граница интервала */
        private double topValue;

        /***
         * Конструктор класса
         * @param bottomValue нижняя граница интервала
         * @param topValue верхняя граница интервала
         */
        public ConfidenceInterval(double bottomValue, double topValue) {
            if (topValue < bottomValue) {
                throw new IllegalArgumentException("Верхняя граница меньше нижней границы!");
            }
            this.bottomValue = bottomValue;
            this.topValue = topValue;
        }

        /***
         * Проверяет, входит ли значение в доверительный интервал
         * @param value значение
         * @return вхождение в интервал
         */
        public boolean occurrence(double value) {
            return value >= bottomValue && value <= topValue;
        }

    }


}
