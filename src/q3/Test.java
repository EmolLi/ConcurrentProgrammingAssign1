package q3;

/**
 * Created by emol on 2/6/18.
 */
public class Test {
    public static int INTA;
    public volatile static int INTB;
    public static int INTC;
    public volatile static int INTD;
    public static int INTE;
    public static int x = 2;
    public static Object lock;
    public static void main(String[] args){
        if (args.length < 1) {
            System.err.println("Usage: java Test [1-5]");
        }
        int option = Integer.parseInt(args[0]);
        lock = new Object();
        long startTime;
        long diff;
        long avgDiff;

        switch (option){
            case 1:
                    INTA = 0;
                    startTime = System.currentTimeMillis();
                    for (int i = 0; i < Integer.MAX_VALUE / x; i++) {
                        INTA++;
                    }
                    diff = System.currentTimeMillis() - startTime;
                    System.out.println(diff);

                break;

            case 2:
                    INTB = 0;
                    startTime = System.currentTimeMillis();
                    for (int i = 0; i < Integer.MAX_VALUE / x; i++) {
                        INTB++;
                    }
                    diff = System.currentTimeMillis() - startTime;
                    System.out.println(diff);
                break;

            case 3:
                    INTC = 0;
                    startTime = System.currentTimeMillis();
                    synchronized (lock) {
                        for (int i = 0; i < Integer.MAX_VALUE / x; i++) {
                            INTC++;
                        }
                    }
                    diff = System.currentTimeMillis() - startTime;
                    System.out.println(diff);
                break;

            case 4:
                    INTD = 0;
                    Thread T1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int goal = Integer.MAX_VALUE / x;
                        synchronized (lock) {
                            while (INTD < goal) {
                                INTD++;
                            }
                        }
                    }
                    });
                    Thread T2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int goal = Integer.MAX_VALUE / x;
                            synchronized (lock) {
                                while (INTD < goal) {
                                    INTD++;
                                }
                            }
                        }
                    });
                    startTime = System.currentTimeMillis();
                    T1.start();
                    T2.start();

                    try {
                        T1.join();
                        T2.join();
                    }catch (InterruptedException e){}

                    diff = System.currentTimeMillis() - startTime;
                    System.out.println(diff);


                break;

            case 5:
                    INTE = 0;
                    Thread T3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int goal = Integer.MAX_VALUE / x;
                                while (INTE < goal) {
                                    INTE++;
                                }
                        }

                    });
                    Thread T4 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int goal = Integer.MAX_VALUE / x;
                                while (INTE < goal) {
                                    INTE++;
                                }
                        }
                    });
                    startTime = System.currentTimeMillis();
                    T3.start();
                    T4.start();

                    try {
                        T3.join();
                        T4.join();
                    }catch (InterruptedException e){}

                    diff = System.currentTimeMillis() - startTime;
                    System.out.println(diff);

                break;
        }



    }
}
