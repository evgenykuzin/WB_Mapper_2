package ru.jekajops.wbtablemapper.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import ru.jekajops.wbtablemapper.models.NuSeller;

import java.util.List;

@Repository
public interface NuSellerRepository extends JpaRepository<NuSeller, Integer>
{
    default List<NuSeller> findByDate(String date) {
        return findBy(Example.of(NuSeller.builder().payoutDate(date).build()),
                FluentQuery.FetchableFluentQuery::all);
    }

    default <S extends NuSeller> void saveAllSafe(Iterable<S> entities) {
        try {
            saveAll(entities);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
