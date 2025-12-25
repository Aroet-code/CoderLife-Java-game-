package gameObject.particles;

import java.util.ArrayList;

public class ParticleSystem {
    private ArrayList<Particle> particles;

    public void updateParticles() {
        for (Particle particle : particles){
//            particle.updateImage();
        }
    }

    public void addParticle(Particle particle){
        particles.add(particle);
    }

    public void removeParticle(Particle particle){
        if (particles.contains(particle)){
            particles.remove(particle);
        }
    }
}
