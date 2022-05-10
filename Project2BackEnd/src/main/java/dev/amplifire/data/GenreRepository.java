package dev.amplifire.data;

import dev.amplifire.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Integer> {
    public Genre findByName(String genreName);
}
