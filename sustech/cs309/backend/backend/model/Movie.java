package backend.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author nanos
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "movie_table")
public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1148517201L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_Id", nullable = false)
    private Long autoId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "date_in", nullable = false)
    private String date;
    @Column(name = "start_time", nullable = false)
    private String startTime;
    @Column(name = "duration", nullable = false)
    private String duration;
    @Column(name = "movie_hall", nullable = false)
    private String movieHall;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "type", nullable = false)
    private String type;

    public Movie(Long autoId) {
        this.autoId = autoId;
    }

    public Movie(MovieDo movie) {
        this.title = movie.getTitle();
        this.date = movie.getDate();
        this.startTime = movie.getStartTime();
        this.duration = movie.getDuration();
        this.movieHall = movie.getMovieHall();
        this.price = movie.getPrice();
        this.type = movie.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){ return false;}
        final Movie movie = (Movie) o;
        return autoId != null && Objects.equals(autoId, movie.autoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(autoId, title, date, startTime, duration, movieHall, price, type);
    }
}