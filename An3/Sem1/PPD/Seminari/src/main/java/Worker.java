public class Worker extends Thread {

    private int i, p;
    private int[] a, b, c;

    public Worker(int i, int p, int a[], int b[], int c[]) {
        this.i = i;
        this.p = p;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        //System.out.println("Hello thr" + this.getName() + ";" + i);
//        int start, end;
//        start = i * (a.length / p);
//        if (i == p - 1) {
//            end = a.length;
//        } else {
//            end = (i + 1) * (a.length / p);
//        }
//        for (int j = start; j < end; j++) {
//            c[j] = a[j] + b[j];
//        }
        for (int j = i; j < a.length; j = j + p) {
//            c[j] = a[j] + b[j];
            c[j] = (int) (Math.sqrt(Math.pow(a[i], 4)) + Math.pow(b[i], 4));
        }
//        for (int j = 0; j < a.length; j++) {
//            if (j % p == i) {
//                c[j] = a[j] + b[j];
//            }
//        }
    }
}
