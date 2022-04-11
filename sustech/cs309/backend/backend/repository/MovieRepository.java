package backend.repository;

import backend.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Transactional(timeout = 10)
    Integer deleteByTitle(String title);

    @Transactional(timeout = 10)
    List<Movie> findBy();

    @Transactional(timeout = 10)
    Movie findByTitle(String title);

    @Transactional(timeout = 10)
    Boolean existsByTitle(String title);

    @Transactional(timeout = 10)
    Boolean existsByAutoId(Long autoId);

    @Transactional(timeout = 10)
    List<Movie> findByDateAndMovieHall(String date, String movieHall);
}
