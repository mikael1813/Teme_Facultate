import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class MyThread extends Thread {
    List<List<Integer>> matrix;
    List<List<Integer>> kernel;
    int N;
    int M;
    int X;
    int Y;
    int nr;
    String name;

    public static CyclicBarrier newBarrier;

    public MyThread(List<List<Integer>> matrix, List<List<Integer>> kernel, int n, int m, int x, int y, int nr_threads) {
        //this.name = name;
        this.matrix = matrix;
        this.kernel = kernel;
        N = n;
        M = m;
        X = x;
        Y = y;
        nr = kernel.size() / 2;
        newBarrier = new CyclicBarrier(nr_threads);
    }

    @Override
    public void run() {
        //if (N < newBarrier.getParties() * (nr + 2) && M < newBarrier.getParties() * (nr + 2)) {
        if (matrix.size() <= 10 && matrix.get(0).size() <= 10) {
            List<List<Integer>> finalMatrix = new ArrayList<>();
            for (int i = nr + N; i < M + nr; i++) {
                List<Integer> list = new ArrayList<>();
                for (int j = nr + X; j < Y + nr; j++) {
                    int sum = 0;
                    for (int x = i - nr; x < i + nr + 1; x++) {
                        for (int y = j - nr; y < j + nr + 1; y++) {
                            sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                        }
                    }
                    list.add(sum);
                }
                finalMatrix.add(list);

            }

            try {
                //System.out.println("Astept urmatorul thread: " + N + " " + M); //pt 8,16 trb decomentat sout
                newBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            //System.out.println("Done " + N + " " + M);
            for (int i = N + nr; i < M + nr; i++) {
                matrix.set(i, finalMatrix.get(i - N - nr));
//                for (int j = nr + X; j < Y + nr; j++) {
//                    matrix.get(i - nr - N).set(j - nr, finalMatrix.get(i).get(j - nr));
//                }
            }

        } else {

            List<List<Integer>> frontiera_sus = new ArrayList<>();
            List<List<Integer>> frontiera_jos = new ArrayList<>();

            if (matrix.size() >= matrix.get(0).size()) {
                for (int i = nr + N; i < nr + N + nr; i++) {
                    List<Integer> list = new ArrayList<>();

                    for (int j = nr + X; j < Y + nr; j++) {
                        int sum = 0;
                        for (int x = i - nr; x < i + nr + 1; x++) {
                            for (int y = j - nr; y < j + nr + 1; y++) {
                                sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                            }
                        }
                        list.add(sum);
                    }
                    frontiera_sus.add(list);
                }
                for (int i = M; i < M + nr; i++) {
                    List<Integer> list = new ArrayList<>();
                    for (int j = nr + X; j < Y + nr; j++) {
                        int sum = 0;
                        for (int x = i - nr; x < i + nr + 1; x++) {
                            for (int y = j - nr; y < j + nr + 1; y++) {
                                sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                            }
                        }
                        list.add(sum);
                    }
                    frontiera_jos.add(list);
                }
                List<List<Integer>> aux = new ArrayList<>();

                for (int i = nr + N + nr; i < M; i++) {
                    if (aux.size() == 1 + nr) {
                        matrix.set(i - 1 - nr, aux.get(0));
                        aux.remove(0);
                        //System.out.println();
                    }
                    List<Integer> list = new ArrayList<>();
                    try {
                        for (int j = nr + X; j < Y + nr; j++) {
                            int sum = 0;
                            for (int x = i - nr; x < i + nr + 1; x++) {
                                for (int y = j - nr; y < j + nr + 1; y++) {
                                    //System.out.println(x + " " + y + " " + String.valueOf(x - (i - nr)) + " " + String.valueOf(y - (j - nr)));
                                    //System.out.println(matrix.size());
                                    sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                                }
                            }
                            list.add(sum);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(i);
                    }
                    aux.add(list);
                }
                if (aux.size() == 0) {

                } else if (aux.size() == 1) {
                    matrix.set(M - 1 - nr, aux.get(0));
                } else
                    for (int i = 0; i < 1 + nr; i++) {
                        matrix.set(M - 1 - nr + i, aux.get(0));
                        aux.remove(0);
                    }
                //matrix.set(M - 2, aux.get(0));
                //matrix.set(M - 1, aux.get(1));

                try {
                    //System.out.println("Astept urmatorul thread: " + N + " " + M);
                    newBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //System.out.println("Done: " + N + " " + M);
                int j = 0;
                for (int i = nr + N; i < nr + N + nr; i++) {
                    matrix.set(i, frontiera_sus.get(j));
                    j++;
                }
                j = 0;
                for (int i = M; i < M + nr; i++) {
                    matrix.set(i, frontiera_jos.get(j));
                    j++;
                }
            } else {
                for (int j = nr + X; j < nr + X + nr; j++) {
                    List<Integer> list = new ArrayList<>();

                    for (int i = nr + N; i < M + nr; i++) {
                        int sum = 0;
                        for (int x = i - nr; x < i + nr + 1; x++) {
                            for (int y = j - nr; y < j + nr + 1; y++) {
                                sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                            }
                        }
                        list.add(sum);
                    }
                    frontiera_sus.add(list);
                }
                for (int j = Y; j < Y + nr; j++) {
                    List<Integer> list = new ArrayList<>();
                    for (int i = nr + N; i < M + nr; i++) {
                        int sum = 0;
                        for (int x = i - nr; x < i + nr + 1; x++) {
                            for (int y = j - nr; y < j + nr + 1; y++) {
                                sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                            }
                        }
                        list.add(sum);
                    }
                    frontiera_jos.add(list);
                }
                List<List<Integer>> aux = new ArrayList<>();

                for (int j = nr + X + nr; j < Y; j++) {
                    //System.out.println(aux);
                    if (aux.size() == 1 + nr) {
                        //matrix.set(i - 1 - nr, aux.get(0));
                        for (int i = nr + N; i < M + nr; i++) {
                            matrix.get(i).set(j - 1 - nr, aux.get(0).get(i - nr));
                        }
                        aux.remove(0);
                        //System.out.println();
                    }
                    List<Integer> list = new ArrayList<>();
                    try {
                        for (int i = nr + N; i < M + nr; i++) {
                            int sum = 0;
                            for (int x = i - nr; x < i + nr + 1; x++) {
                                for (int y = j - nr; y < j + nr + 1; y++) {
                                    //System.out.println(x + " " + y + " " + String.valueOf(x - (i - nr)) + " " + String.valueOf(y - (j - nr)));
                                    //System.out.println(matrix.size());
                                    sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                                }
                            }
                            list.add(sum);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(j);
                    }
                    aux.add(list);
                }
                for (int j = 0; j < 1 + nr; j++) {
                    for (int i = nr + N; i < nr + M; i++) {
                        matrix.get(i).set(Y - 1 - nr + j, aux.get(0).get(i - nr));
                    }

                    //matrix.set(M - 1 - nr + i, aux.get(0));
                    aux.remove(0);
                }
                //matrix.set(M - 2, aux.get(0));
                //matrix.set(M - 1, aux.get(1));

                try {
                    //System.out.println("Astept urmatorul thread: " + N + " " + M);
                    newBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //System.out.println("Done: " + N + " " + M);
                int x = 0;
                for (int j = nr + X; j < nr + X + nr; j++) {
                    for (int i = nr + N; i < nr + M; i++) {
                        matrix.get(i).set(j, frontiera_sus.get(x).get(i - nr));
                    }
                    //matrix.set(i, frontiera_sus.get(j));
                    x++;
                }
                x = 0;
                for (int j = Y; j < Y + nr; j++) {
                    for (int i = nr + N; i < nr + M; i++) {
                        matrix.get(i).set(j, frontiera_jos.get(x).get(i - nr));
                    }
                    //matrix.set(i, frontiera_jos.get(j));
                    x++;
                }

            }

        }
//        for (int i = nr + N; i < M + nr; i++) {
//            List<Integer> list = new ArrayList<>();
//            for (int j = nr + X; j < Y + nr; j++) {
//                int sum = 0;
//                for (int x = i - nr; x < i + nr + 1; x++) {
//                    for (int y = j - nr; y < j + nr + 1; y++) {
//                        sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
//                    }
//                }
//
//            }
//
//
//        }

    }
}

