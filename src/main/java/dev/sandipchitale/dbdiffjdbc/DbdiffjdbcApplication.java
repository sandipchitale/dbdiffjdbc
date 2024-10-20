package dev.sandipchitale.dbdiffjdbc;

import dev.sandipchitale.dbdiffjdbc.postgres1.entities.NamesToDiff1;
import dev.sandipchitale.dbdiffjdbc.postgres1.repositories.NamesToDiff1Repository;
import dev.sandipchitale.dbdiffjdbc.postgres2.entities.NamesToDiff2;
import dev.sandipchitale.dbdiffjdbc.postgres2.repositories.NamesToDiff2Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbdiffjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbdiffjdbcApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(NamesToDiff1Repository namesToDiff1Repository, NamesToDiff2Repository namesToDiff2Repository) {
        return (String... args) -> {
            NamesToDiff1 namesToDiff1 = new NamesToDiff1(null, "Sandip", 60);
            namesToDiff1Repository.save(namesToDiff1);

            namesToDiff1 = new NamesToDiff1(null, "Priti", 54);
            namesToDiff1Repository.save(namesToDiff1);

//            namesToDiff1Repository.findAll().forEach(System.out::println);
            namesToDiff1Repository.findAllIncludeRowId().forEach(System.out::println);

            NamesToDiff2 namesToDiff2 = new NamesToDiff2(null, "Jay", 28);
            namesToDiff2Repository.save(namesToDiff2);

            namesToDiff2 = new NamesToDiff2(null, "Neel", 23);
            namesToDiff2Repository.save(namesToDiff2);

//            namesToDiff2Repository.findAll().forEach(System.out::println);
            namesToDiff2Repository.findAllIncludeRowId().forEach(System.out::println);
        };
    }
}
