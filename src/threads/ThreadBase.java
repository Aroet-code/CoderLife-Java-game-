package threads;

public abstract class ThreadBase extends Thread{
    private boolean enabled = false;

    public ThreadBase(boolean enabled){
        this.enabled = enabled;
    }

    public void enable(){
        this.enabled = true;
    }

    public void disable(){
        this.enabled = false;
    }

    public boolean isEnabled(){
        return this.enabled;
    }
}
