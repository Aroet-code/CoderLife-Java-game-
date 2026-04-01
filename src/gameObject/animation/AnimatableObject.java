package gameObject.animation;

public class AnimatableObject implements Animatable{
    protected int frame = 0;
    @Override
    public int animate(int frames) {
        frame++;
        if (frame >= frames) {
            frame = -1;
        }
        return frame;
    }
}
