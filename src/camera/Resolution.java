package camera;

public class Resolution<Width, Height, Size> {
    public final Width width;
    public final Height height;
    public final Integer size;
    private int scaleNumber;
    public Resolution(Width width, Height height, Integer size){
        this.width = width;
        this.height = height;
        this.size = size;
        this.scaleNumber = getScaleNumber();
    }

    public Width getWidth() {
        return width;
    }

    public Height getHeight() {
        return height;
    }

    public Integer getSize() {
        return size;
    }

    public int getScaleNumber(){
        switch (this.getSize()){
            case 0 -> scaleNumber = 0;
            case 1 -> scaleNumber = 5;
            case 2 -> scaleNumber = 10;
            case 3 -> scaleNumber = 20;
            case 4 -> scaleNumber = 40;
            case 5 -> scaleNumber = 80;
            case 6 -> scaleNumber = 120;
            case 7 -> scaleNumber = 160;
            case 8 -> scaleNumber = 240;
            case 9 -> scaleNumber = 480;
        }
        return scaleNumber;
    }
}
