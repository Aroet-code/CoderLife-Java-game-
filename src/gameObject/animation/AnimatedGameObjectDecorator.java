package gameObject.animation;

abstract class AnimatedGameObjectDecorator implements Animatable{
    private final AnimatableObject animatableObject;

    public AnimatedGameObjectDecorator(AnimatableObject animatableObject) {
        this.animatableObject = animatableObject;
    }

    public AnimatableObject getAnimatableObject() {
        return animatableObject;
    }
}
