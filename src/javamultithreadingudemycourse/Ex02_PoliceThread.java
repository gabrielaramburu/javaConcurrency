package javamultithreadingudemycourse;

import java.util.*;
public class Ex02_PoliceThread {
    static final int KEY_RANGE = 10;
    static final int PAUSE_THIEF = 2000;
    static final int PAUSE_POLICE = 10_000;
    SecurityBox box;
    Thief[] thieves;
    Police police;

    Ex02_PoliceThread () {
        box = new SecurityBox(5);
        thieves = new Thief[3];
        thieves[0] = new Thief("T1");
        thieves[1] = new Thief("T2");
        thieves[2] = new Thief("T3");

        police = new Police();
    }

    public static void main(String[] args) {
        Ex02_PoliceThread policeGame = new Ex02_PoliceThread();
        for(Thief t: policeGame.thieves) t.start();

        policeGame.police.start();
    }

    static void pause(Thread t, int mil) {
        try {
            t.sleep(mil);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
    class Police extends  Thread {
        @Override
        public void run()  {
            System.out.println("Police: Going to the robbery place.");
            pause(this, PAUSE_POLICE);
            System.out.println("Police has arrived");
            for(Thief t: thieves){
                if (t.isAlive()) {
                    System.out.println(t.getName() + " Bang Bang Bang!!!!");
                    t.putUnderArrest();
                    //it take some time to capture a robber
                    pause(this, 3000);
                } else System.out.println(t.getName() + " has gone.");
            }
        }
    }

    class Thief extends Thread {
        boolean captured;

        Thief(String name) {
            super(name);
        }
        @Override
        public void run() {
            Random ran = new Random();
            while (true) {
                if (captured) break;
                if (box.isOpen()) break;
                int keyValue = ran.nextInt(KEY_RANGE);
                System.out.println(this.getName() + "-> Trying to open the box with key " + keyValue);

                if (box.open(keyValue)) {
                    System.out.println(this.getName() + " I have the money, byeee!!");
                    break;
                }
                pause(this, PAUSE_THIEF);
            }
            if (!captured) System.out.println(this.getName() + " has escaped.");
            else System.out.println("*** " + this.getName() + " has been arrested");
        }

        void putUnderArrest(){
            this.captured = true;
        }
    }

    class SecurityBox {
        private int accessCode;
        private boolean isOpen;

        SecurityBox(int val) {
            this.accessCode = val;
        }

        synchronized boolean open(int accessCode) {
            if (accessCode == this.accessCode) {
                System.out.println("The box is open.");
                this.isOpen = true;
                return true;
            } else {
                System.out.println("Wrong combination.");
                return false;
            }
        }

        boolean isOpen() {
            return isOpen;
        }
    }
}
