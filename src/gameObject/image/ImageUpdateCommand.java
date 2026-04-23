package gameObject.image;

import gameObject.movement.Direction;

public record ImageUpdateCommand(String key, AnimationFlags flag, Direction direction) {
}
