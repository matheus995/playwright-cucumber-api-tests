package br.com.mbarros.payloads;

import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;

@Getter
@Setter
public class User {

    private String name, job;

    public User() {
        Faker faker = new Faker();
        this.name = faker.name().fullName();
        this.job = faker.job().title();
    }
}
