package cat.itacademy.mascota_virtual_back.domain;

import cat.itacademy.mascota_virtual_back.domain.enums.ColorMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EstatMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.TipusMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EntornMascota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mascota {

    private String id;
    private String nom;
    private TipusMascota tipus;
    private ColorMascota color;
    private EntornMascota entorn;
    private int energia;
    private int anim;
    private EstatMascota estat;
    private String propietariId;

    private Random random = new Random();

    public void alimentar() {
        this.energia += getRandomValue(10, 15);
        this.anim += getRandomValue(5, 10);
        validarLimits();
        actualitzarEstat();
    }

    public void jugar() {
        this.energia -= getRandomValue(10, 15);
        this.anim += getRandomValue(10, 15);
        validarLimits();
        actualitzarEstat();
    }

    public void donarMims() {
        this.energia -= getRandomValue(0, 5);
        this.anim += getRandomValue(5, 10);
        validarLimits();
        actualitzarEstat();
    }

    public void canviarEntorn(EntornMascota nouEntorn) {
        this.entorn = nouEntorn;
        if (nouEntorn == EntornMascota.PIPICAN) {
            this.anim -= 30;
            this.energia -= getRandomValue(3, 7);
        } else if (nouEntorn == EntornMascota.CASA){
            this.anim += getRandomValue(-5, 5);
            this.energia -= getRandomValue(3, 7);
        } else {
            this.anim += getRandomValue(10, 15);
            this.energia -= getRandomValue(10, 15);
        }
        validarLimits();
        actualitzarEstat();
    }

    private int getRandomValue(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private void validarLimits() {
        this.energia = Math.max(0, Math.min(this.energia, 100));
        this.anim = Math.max(0, Math.min(this.anim, 100));
    }

    private void actualitzarEstat() {
        if (energia < 25) {
            estat = EstatMascota.FAMELIC;
        } else if (anim < 25) {
            estat = EstatMascota.TRIST;
        } else if (anim < 50) {
            estat = EstatMascota.ENSOPIT;
        } else if (energia > 75 && anim > 75) {
            estat = EstatMascota.ATOPE;
        } else {
            estat = EstatMascota.BE;
        }
    }

}


