package ru.jekajops.wbtablemapper.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.jekajops.wbtablemapper.models.WB;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class WBREpositoryMock implements WBRepository {
    @Override
    public List<WB> findAll() {
        return null;
    }

    @Override
    public List<WB> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WB> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<WB> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(WB entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends WB> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends WB> S save(S entity) {
        return null;
    }

    @Override
    public <S extends WB> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WB> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends WB> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends WB> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<WB> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public WB getOne(Long aLong) {
        return null;
    }

    @Override
    public WB getById(Long aLong) {
        return null;
    }

    @Override
    public WB getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends WB> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WB> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WB> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WB> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WB> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WB> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WB, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
