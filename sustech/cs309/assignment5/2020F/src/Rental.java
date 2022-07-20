class Rental {
    private final Movie _movie;
    private final int _daysRented;

    public Rental(Movie movie, DataRange range) {
        _movie = movie;
        _daysRented = (int) ((range.end().getTime() - range.start().getTime()) / (1000 * 60 * 60 * 24));
    }

    public Movie getMovie() {
        return this._movie;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public String getTitle() {
        return _movie.getTitle();
    }

    public int getPriceCode() {
        return _movie.getPriceCode();
    }

    double getCharge() {
        return this._movie.getCharge(this._daysRented);
    }

    public int getFrequentRenterPoints() {
        return 1 + _movie.getFrequentRenterPoints(getDaysRented());
        // add bonus for a two day new release rental
    }
}
