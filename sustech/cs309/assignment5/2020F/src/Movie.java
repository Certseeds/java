public class Movie {
    public static final int NEW_RELEASE = 1;
    public static final int REGULAR = 0;
    public static final int CHILDRENS = 2;
    private final String _title;
    private Price _price;

    public Movie(String title, int priceCode) {
        this._title = title;
        setPriceCode(priceCode);
    }

    public String getTitle() {
        return _title;
    }

    public int getPriceCode() {
        return _price.getPriceCode();
    }

    public void setPriceCode(int arg) {
        _price = switch (arg) {
            case REGULAR -> new RegularPrice();
            case CHILDRENS -> new ChildrensPrice();
            case NEW_RELEASE -> new NewReleasePrice();
            default -> throw new IllegalArgumentException("Incorrect Price Code");
        };
    }

    /**
     * @deprecated Use {@link Price#getCharge(int)} instead
     */
    double getCharge(int daysRented) {
        return _price.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(int number) {
        // add bonus for a two day new release rental
       /* if ((getPriceCode() == NEW_RELEASE) && number > 1) {
            return 1;
        } else {
            return 0;
        }*/
        return _price.getFrequentRenterPoints(number);
    }
}
