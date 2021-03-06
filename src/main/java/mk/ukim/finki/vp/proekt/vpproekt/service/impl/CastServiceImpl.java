package mk.ukim.finki.vp.proekt.vpproekt.service.impl;

import mk.ukim.finki.vp.proekt.vpproekt.model.Cast;
import mk.ukim.finki.vp.proekt.vpproekt.model.Movie;
import mk.ukim.finki.vp.proekt.vpproekt.model.dto.CastDto;
import mk.ukim.finki.vp.proekt.vpproekt.model.exceptions.CastNotFoundException;
import mk.ukim.finki.vp.proekt.vpproekt.model.exceptions.MovieNotFoundException;
import mk.ukim.finki.vp.proekt.vpproekt.repository.jpa.CastRepository;
import mk.ukim.finki.vp.proekt.vpproekt.repository.jpa.MovieRepository;
import mk.ukim.finki.vp.proekt.vpproekt.service.CastService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CastServiceImpl implements CastService {

    private final CastRepository castRepository;
    private final MovieRepository movieRepository;

    public CastServiceImpl(CastRepository castRepository, MovieRepository movieRepository) {
        this.castRepository = castRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Cast> findById(Long id) {
        return castRepository.findById(id);
    }

    @Override
    public List<Cast> findAll() {
        return castRepository.findAll();
    }

    @Override
    public Optional<Cast> findByName(String name) {
        return castRepository.findByName(name);
    }

    @Override
    public Optional<Cast> save(String name, String surname) {
        this.castRepository.deleteByName(name);
        Cast cast = new Cast(name, surname);
        this.castRepository.save(cast);
        return Optional.of(cast);
    }

    @Override
    public Optional<Cast> save(CastDto castDto) {
        this.castRepository.deleteByName(castDto.getName());
        Cast cast = new Cast(castDto.getName(), castDto.getSurname());
        this.castRepository.save(cast);
        return Optional.of(cast);
    }

    @Override
    @Transactional
    public Optional<Cast> edit(Long id, String name, String surname) {
        Cast cast = this.castRepository.findById(id)
                .orElseThrow(() -> new CastNotFoundException(id));

        cast.setName(name);
        cast.setSurname(surname);
        this.castRepository.save(cast);
        return Optional.of(cast);
    }

    @Override
    public Optional<Cast> edit(Long id, CastDto castDto) {
        Cast cast = this.castRepository.findById(id)
                .orElseThrow(() -> new CastNotFoundException(id));

        cast.setName(castDto.getName());
        cast.setSurname(cast.getSurname());
        this.castRepository.save(cast);
        return Optional.of(cast);
    }

    @Override
    public void deleteById(Long id) {
        this.castRepository.deleteById(id);
    }
}
