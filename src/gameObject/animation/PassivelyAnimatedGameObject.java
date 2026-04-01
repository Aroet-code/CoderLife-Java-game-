package gameObject.animation;

public class PassivelyAnimatedGameObject extends AnimatedGameObjectDecorator{
    public PassivelyAnimatedGameObject(AnimatableObject animatableObject) {
        super(animatableObject);
    }

    @Override
    public int animate(int frames) {
        int animationResult = this.getAnimatableObject().animate(frames);
        if (animationResult == -1){
            return -3;
        }
        return -2;
    }
}
