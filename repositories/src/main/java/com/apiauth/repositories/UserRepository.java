package com.apiauth.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apiauth.abstraction.IUserRepository;
import com.apiauth.concrete.Role;
import com.apiauth.concrete.User;
import com.apiauth.concrete.UserRole;
import com.apiauth.jpa.IRoleJpaRepository;
import com.apiauth.jpa.IUserJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Component
public class UserRepository implements IUserRepository {

    private final IUserJpaRepository repository;

    private final IRoleJpaRepository roleJpaRepository;

    private final EntityManager entityManager;

    private CriteriaBuilder builder;

	private Root<User> root;

    private CriteriaQuery<User> query;

    public UserRepository(IUserJpaRepository repository, EntityManager entityManager, IRoleJpaRepository roleJpaRepository) {
		this.repository = repository;
        this.roleJpaRepository = roleJpaRepository;
		this.entityManager = entityManager;
        builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(User.class);
        root = query.from(User.class);
	}

    @Transactional
	@Override
	public User create(User record) {
		return repository.save(record);
	}

	@Override
	public Page<User> takeAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Optional<User> getById(UUID id) {
		return repository.findById(id);
	}

    @Transactional
	@Override
	public void update(User record) {
        repository.save(record);
	}

    @Transactional
	@Override
	public void delete(UUID id) {
		repository.deleteById(id);
	}

	@Override
	public boolean existsUsername(String username) {
        return repository.exists((root, query, builder) -> {
            return  builder.equal(builder.lower(root.get("username")), username);
        });
	}

	@Override
	public boolean existsEmail(String email) {
        return repository.exists((root, query, builder) -> {
            return  builder.equal(builder.lower(root.get("email")), email);
        });
	}

	@Override
	public List<User> filterByAttributes(User example) {

        var emailParameter = builder.parameter(String.class);
        var usernameParameter = builder.parameter(String.class);
        var nameParameter = builder.parameter(String.class);

        
        Predicate likeEmail = builder.like(builder.lower(root.get("email")), emailParameter);
        Predicate likeUsername = builder.like(builder.lower(root.get("username")), usernameParameter);
        Predicate likeName = builder.like(builder.lower(root.get("name")), nameParameter);

        Predicate orCondition = builder.or(likeEmail, likeUsername, likeName);

		query.select(root).where(orCondition);

        var typedQuery = entityManager.createQuery(query);

        typedQuery.setParameter(emailParameter, "%" + example.getEmail() + "%");
        typedQuery.setParameter(usernameParameter, "%" + example.getUsername() + "%");
        typedQuery.setParameter(nameParameter, "%" + example.getName() + "%");

        return typedQuery.getResultList();
	}

    @Transactional
	@Override
	public void addRole(User user, String value) {
		var role = roleJpaRepository.findByName(value);

        if (role.isEmpty()) {
            role = Optional.of(Role.builder().name(value).build());
            roleJpaRepository.save(role.get());
        }
        var userRole = UserRole.builder()
            .user(user)
            .role(role.get())
            .build();

        if (user.getUserRoles() == null) {
            user.setUserRoles(new ArrayList<>());
        }

        user.getUserRoles().add(userRole);
        repository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
        Assert.notNull(username, "Cannot be null");
		query.select(root).where(builder.equal(builder.lower(root.get("username")), username.toLowerCase()));
        return Optional.ofNullable(entityManager.createQuery(query).getResultList())
            .filter(x -> !x.isEmpty())
            .map(x -> x.get(0));
	}


    
}
