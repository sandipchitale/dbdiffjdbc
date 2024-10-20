package dev.sandipchitale.dbdiffjdbc.postgres2.repositories;

import dev.sandipchitale.dbdiffjdbc.postgres2.entities.NamesToDiff2;
import dev.sandipchitale.dbdiffjdbc.postgres2.entities.NamesToDiff2WithCtId;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NamesToDiff2Repository extends CrudRepository<NamesToDiff2, Long> {
    @Query(value = "SELECT id,name,age,ctid FROM names")
    Iterable<NamesToDiff2WithCtId> findAllIncludeRowId();

    @Query(value = "SELECT ctid FROM names")
    Iterable<Object> findAllRowIds();

    @Query(value = "SELECT * FROM names WHERE ctid = ?::tid")
    Optional<NamesToDiff2> findByCtId(String ctid);
}
