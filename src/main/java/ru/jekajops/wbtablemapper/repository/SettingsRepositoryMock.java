package ru.jekajops.wbtablemapper.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.jekajops.wbtablemapper.models.Setting;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SettingsRepositoryMock implements SettingsRepository {
    private final Set<Setting> storage;

    public SettingsRepositoryMock() {
        this.storage = new HashSet<>();
        init();
    }

    @Override
    public List<Setting> findByGroup(Setting.Group group) {
        return storage.stream()
                .filter(s->group.equals(s.getGroup()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Setting> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public List<Setting> findAll(Sort sort) {
        return new ArrayList<>(storage);
    }

    @Override
    public Page<Setting> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Setting> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Setting entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Setting> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Setting> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Setting> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e->storage.add(e));
        return null;
    }

    @Override
    public Optional<Setting> findById(Long aLong) {
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
    public <S extends Setting> S saveAndFlush(S entity) {
        storage.add(entity);
        return null;
    }

    @Override
    public <S extends Setting> List<S> saveAllAndFlush(Iterable<S> entities) {
        entities.forEach(e->storage.add(e));
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Setting> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Setting getOne(Long aLong) {
        return null;
    }

    @Override
    public Setting getById(Long aLong) {
        return null;
    }

    @Override
    public Setting getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Setting> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Setting> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Setting> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Setting> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Setting> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Setting> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Setting, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
