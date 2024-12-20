package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.UserMapper;
import pro.sky.recommendations.model.User;

import java.util.Optional;
import java.util.UUID;

import static pro.sky.recommendations.repository.constant.SQLQuery.FIND_USER_BY_ID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper mapper;

    public Optional<User> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, mapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
