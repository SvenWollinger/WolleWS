package io.wollinger.wollews.requests;

public class AcceptParameter {
    private final String type;
    private final float quality; //Defaults to 1.0 if not given

    public AcceptParameter(String type, float quality) {
        this.type = type;
        this.quality = quality;
    }

    public AcceptParameter(String type) {
        this(type, 1.0F);
    }

    public String getType() {
        return type;
    }

    public float getQuality() {
        return quality;
    }
}
