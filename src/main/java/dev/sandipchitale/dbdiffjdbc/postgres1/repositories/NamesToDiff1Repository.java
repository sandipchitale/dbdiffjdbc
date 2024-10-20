package dev.sandipchitale.dbdiffjdbc.postgres1.repositories;

import dev.sandipchitale.dbdiffjdbc.postgres1.entities.NamesToDiff1;
import dev.sandipchitale.dbdiffjdbc.postgres1.entities.NamesToDiff1WithCtId;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NamesToDiff1Repository extends CrudRepository<NamesToDiff1, Long> {
    @Query(value = "SELECT id,name,age,ctid FROM names")
    Iterable<NamesToDiff1WithCtId> findAllIncludeRowId();

    @Query(value = "SELECT ctid FROM names")
    Iterable<Object> findAllRowIds();

    @Query(value = "SELECT * FROM names WHERE ctid = ?::tid")
    Optional<NamesToDiff1> findByCtId(String ctid);

}
