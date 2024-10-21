package dev.sandipchitale.dbdiffjdbc;

import dev.sandipchitale.dbdiffjdbc.postgres1.entities.NamesToDiff1;
import dev.sandipchitale.dbdiffjdbc.postgres1.entities.NamesToDiff1WithCtId;
import dev.sandipchitale.dbdiffjdbc.postgres1.repositories.NamesToDiff1Repository;
import dev.sandipchitale.dbdiffjdbc.postgres2.entities.NamesToDiff2;
import dev.sandipchitale.dbdiffjdbc.postgres2.entities.NamesToDiff2WithCtId;
import dev.sandipchitale.dbdiffjdbc.postgres2.repositories.NamesToDiff2Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

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

            namesToDiff1 = new NamesToDiff1(null, "Chitale", 100);
            namesToDiff1Repository.save(namesToDiff1);

            namesToDiff1Repository.findAllIncludeRowId().forEach((NamesToDiff1WithCtId namesToDiff1WithCtId) -> {
                namesToDiff1Repository.findByCtId(String.valueOf(namesToDiff1WithCtId.ctid())).ifPresent(System.out::println);
            });

            NamesToDiff2 namesToDiff2 = new NamesToDiff2(null, "Jay", 28);
            namesToDiff2Repository.save(namesToDiff2);

            namesToDiff2 = new NamesToDiff2(null, "Neel", 23);
            namesToDiff2Repository.save(namesToDiff2);

            namesToDiff2 = new NamesToDiff2(null, "Chitale", 100);
            namesToDiff2Repository.save(namesToDiff2);

            namesToDiff2Repository.findAllIncludeRowId().forEach((NamesToDiff2WithCtId namesToDiff2WithCtId) -> {
                namesToDiff2Repository.findByCtId(String.valueOf(namesToDiff2WithCtId.ctid())).ifPresent(System.out::println);
            });

            Map<String, Object> namesToDiff1DigestsToRowIdMap = new TreeMap<>();
            Map<String, Object> namesToDiff2DigestsToRowIdMap = new TreeMap<>();

            StreamSupport.stream(namesToDiff1Repository.findAllIncludeRowId().spliterator(), false).forEach((NamesToDiff1WithCtId namesToDiff1WithCtId) -> {
                namesToDiff1DigestsToRowIdMap.put(DigestUtils.md5DigestAsHex(namesToDiff1WithCtId.toBytes()), namesToDiff1WithCtId.ctid());
            });

            StreamSupport.stream(namesToDiff2Repository.findAllIncludeRowId().spliterator(), false).forEach((NamesToDiff2WithCtId namesToDiff2WithCtId) -> {
                namesToDiff2DigestsToRowIdMap.put(DigestUtils.md5DigestAsHex(namesToDiff2WithCtId.toBytes()), namesToDiff2WithCtId.ctid());
            });

            Set<String> namesToDiff1DigestsSet = new TreeSet<>(namesToDiff1DigestsToRowIdMap.keySet());
            namesToDiff1DigestsSet.retainAll(namesToDiff2DigestsToRowIdMap.keySet());
            System.out.println("In both NamesToDiff1 and NamesToDiff2: ");

            namesToDiff1DigestsSet.forEach((String key) -> {
                Object ctId = namesToDiff1DigestsToRowIdMap.get(key);
                namesToDiff1Repository.findByCtId(String.valueOf(ctId)).ifPresent(System.out::println);
            });

            namesToDiff1DigestsSet = new TreeSet<>(namesToDiff1DigestsToRowIdMap.keySet());
            namesToDiff1DigestsSet.removeAll(namesToDiff2DigestsToRowIdMap.keySet());
            System.out.println("In NamesToDiff1 only: ");
            namesToDiff1DigestsSet.forEach((String key) -> {
                Object ctId = namesToDiff1DigestsToRowIdMap.get(key);
                namesToDiff1Repository.findByCtId(String.valueOf(ctId)).ifPresent(System.out::println);
            });

            Set<String> namesToDiff2DigestsSet = new TreeSet<>(namesToDiff2DigestsToRowIdMap.keySet());
            namesToDiff2DigestsSet.removeAll(namesToDiff1DigestsToRowIdMap.keySet());
            System.out.println("In NamesToDiff2 only: ");
            namesToDiff2DigestsSet.forEach((String key) -> {
                Object ctId = namesToDiff2DigestsToRowIdMap.get(key);
                namesToDiff2Repository.findByCtId(String.valueOf(ctId)).ifPresent(System.out::println);
            });
        };
    }
}
