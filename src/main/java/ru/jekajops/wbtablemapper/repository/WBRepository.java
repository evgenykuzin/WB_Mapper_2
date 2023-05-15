package ru.jekajops.wbtablemapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jekajops.wbtablemapper.models.WB;

@Repository
public interface WBRepository
        extends JpaRepository<WB, Long>
{
    default <S extends WB> void saveAllSafe(Iterable<S> entities) {
        try {
            saveAll(entities);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
