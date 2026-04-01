package controllers;

import threads.ThreadBase;

import java.util.HashMap;

public class GameThreadController {
    private HashMap<String, ThreadBase> threads = new HashMap<>();

    public void addThread(String key, ThreadBase thread){
        threads.put(key, thread);
    }

    public void enableThread(String key){
        if (threads.containsKey(key)) {
            ThreadBase thread = threads.get(key);
            thread.enable();
        } else {
            throw new NullPointerException("There is no thread with such name in GameThreadController.");
        }
    }

    public void disableThread(String key){
        if (threads.containsKey(key)) {
            ThreadBase thread = threads.get(key);
            thread.disable();
        } else {
            throw new NullPointerException("There is no thread with such name in GameThreadController.");
        }
    }

    public ThreadBase getThreadBase(String key) {
        if (threads.containsKey(key)){
            return threads.get(key);
        } else {
            throw new NullPointerException("There is no thread with such name in GameThreadController.");
        }
    }

    public void runThreads(){
        for (ThreadBase thread : threads.values()){
            thread.start();
        }
    }
}
