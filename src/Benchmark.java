import javax.xml.transform.Source;

public class Benchmark {
    public static void checkBenchmark(String subcommand, String value) {
        switch (subcommand) {
            case "cpu":
                cpu(value);
                break;
            default:
                System.out.println("Sorry, but I don't know the command " + subcommand);
                break;
        }
    }

    private static void cpu(String value) {
        long valueIntBefore = 0;

        try {
            valueIntBefore = Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }

        final long valueInt = valueIntBefore;

        // Anzahl der verfügbaren logischen Prozessoren herausfinden
        int threads = Runtime.getRuntime().availableProcessors();

        System.out.println("CPU threads detected: " + threads);
        System.out.println("Starting benchmark...");
        System.out.println();


        // =========================
        // SINGLE THREAD TEST
        // =========================

        long[] singleTimes = new long[10];

        for (int i = 0; i < 10; i++) {

            long summe = 0;

            long start = System.nanoTime();

            // Ein Thread arbeitet
            for (long j = 0; j < valueInt; j++) {
                summe += j;
            }

            long end = System.nanoTime();

            singleTimes[i] = end - start;

            System.out.println(
                    (i + 1) + ". single-thread test finished: "
                            + singleTimes[i] / 1_000_000.0 + " ms"
            );
        }


        // Durchschnitt Single Thread
        long singleTotal = 0;

        for (int i = 0; i < 10; i++) {
            singleTotal += singleTimes[i];
        }

        double singleAverage = singleTotal / 10_000_000.0;


        // =========================
        // MULTI THREAD TEST
        // =========================

        long[] multiTimes = new long[10];

        for (int i = 0; i < 10; i++) {

            Thread[] workers = new Thread[threads];

            long start = System.nanoTime();

            // Für jeden logischen Prozessor einen Thread erstellen
            for (int t = 0; t < threads; t++) {

                workers[t] = new Thread(() -> {

                    long summe = 0;

                    for (long j = 0; j < valueInt / threads; j++) {
                        summe += j;
                    }
                });

                workers[t].start();
            }

            // Warten, bis alle Threads fertig sind
            for (int t = 0; t < threads; t++) {

                try {
                    workers[t].join();
                }
                catch (InterruptedException e) {
                    System.out.println(e);
                    return;
                }
            }

            long end = System.nanoTime();

            multiTimes[i] = end - start;

            System.out.println(
                    (i + 1) + ". multi-thread test finished: "
                            + multiTimes[i] / 1_000_000.0 + " ms"
            );
        }


        // Durchschnitt Multi Thread
        long multiTotal = 0;

        for (int i = 0; i < 10; i++) {
            multiTotal += multiTimes[i];
        }

        double multiAverage = multiTotal / 10_000_000.0;

        System.out.println();
        System.out.println("==============================================");
        System.out.println("                BENCHMARK RESULT");
        System.out.println("==============================================");

        System.out.printf("%-20s %-15s %-15s%n", "Test", "This PC", "Home PC");

        System.out.println("----------------------------------------------");

        System.out.printf("%-20s %-15s %-15s%n", "Single Thread", String.format("%.2f ms", singleAverage), "224.0 ms");

        System.out.printf("%-20s %-15s %-15s%n", "All " + threads + " Threads", String.format("%.2f ms", multiAverage), "131.0 ms");

        System.out.println("----------------------------------------------");
    }
}
