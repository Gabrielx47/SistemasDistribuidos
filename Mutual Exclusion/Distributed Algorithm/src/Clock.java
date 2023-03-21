public class Clock extends Thread {
    private int hour;

    Clock(int hour) {
        this.hour = hour;
    }

    @Override
    public void run() throws UnsupportedOperationException {
        while (true) {
            ++hour;
        }
    }


    public int currentTime() {
        return hour;
    }
}
