package ui;

public interface UIAnimatable {
    int animate(UIElementState nextState, UIController uiController, boolean reversed);
    int move(boolean reversed, UIController uiController);
    int getTargetX();
    int getTargetY();
    int getStartX();
    int getStartY();
    String getName();
//    Должен возвращать текущую фазу анимации. 0 - Если анимация ещё не была начата. -1 - анимация окончилась успешно. -2 - анимация была прервана
}
