package org.tkit.quarkus.it.panache.reactive;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UserDAO implements PanacheRepositoryBase<User, String> {

}
