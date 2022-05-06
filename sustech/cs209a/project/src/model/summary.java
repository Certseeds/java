package model;

/**
 * The type Summary.
 * This class mainly use during Serialization in the list file part.
 */
public class summary {
    /**
     * The Md 5.
     */
    String md5;
    /**
     * The Length.
     */
    int length;
    /**
     * The Preview.
     */
    String preview;

    /**
     * Instantiates a new Summary.
     *
     * @param md5     the md 5
     * @param length  the length
     * @param preview the preview
     */
    public summary(String md5, int length, String preview) {
        this.md5 = md5;
        this.length = length;
        this.preview = preview;
    }

    /**
     * Gets md 5.
     *
     * @return the md 5
     */
    public String getMd5() {
        return md5;
    }

    /**
     * Sets md 5.
     *
     * @param md5 the md 5
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * Gets length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets length.
     *
     * @param length the length
     */
    private void setLength(int length) {
        this.length = length;
    }

    /**
     * Gets preview.
     *
     * @return the preview
     */
    public String getPreview() {
        return preview;
    }

    /**
     * Sets preview.
     *
     * @param preview the preview
     */
    private void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "summary {" +
                "md5='" + this.getMd5() + '\'' +
                ", length=" + this.getLength() + '\'' +
                ", preview=" + this.getPreview() + '}';

    }
}
