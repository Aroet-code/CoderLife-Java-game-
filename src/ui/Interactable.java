package ui;

import java.util.concurrent.Callable;

public interface Interactable {
    void onInteract() throws Exception;
    void setCommand(Callable<Integer> command);
    Callable<Integer> getCommand();
}
