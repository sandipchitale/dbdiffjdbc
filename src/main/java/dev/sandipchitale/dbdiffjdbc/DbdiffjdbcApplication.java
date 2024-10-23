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

import java.util.*;
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

            System.out.println("namesToDiff1DigestsToRowIdMap = " + namesToDiff1DigestsToRowIdMap);
            System.out.println("namesToDiff2DigestsToRowIdMap = " + namesToDiff2DigestsToRowIdMap);

            Iterator<Map.Entry<String, Object>> namesToDiff1DigestsToRowIdMapEntrySetIterator = namesToDiff1DigestsToRowIdMap.entrySet().iterator();
            Iterator<Map.Entry<String, Object>> namesToDiff2DigestsToRowIdMapEntrySetIterator = namesToDiff2DigestsToRowIdMap.entrySet().iterator();
            if (namesToDiff1DigestsToRowIdMapEntrySetIterator.hasNext() && namesToDiff2DigestsToRowIdMapEntrySetIterator.hasNext()) {
                Map.Entry<String, Object> n1 = namesToDiff1DigestsToRowIdMapEntrySetIterator.next();
                Map.Entry<String, Object> n2 = namesToDiff2DigestsToRowIdMapEntrySetIterator.next();
                LOOP: while (true) {
                    String key1 = n1.getKey();
                    String key2 = n2.getKey();
                    switch (key1.compareTo(key2)) {
                        case -1:
                            System.out.println("In namesToDiff1: " + key1);
                            if (namesToDiff1DigestsToRowIdMapEntrySetIterator.hasNext()) {
                                n1 = namesToDiff1DigestsToRowIdMapEntrySetIterator.next();
                            } else {
                                System.out.println("In namesToDiff2: " + key2);
                                break LOOP;
                            }
                            break;
                        case 0:
                            System.out.println("In both: " + key1);
                            if (namesToDiff1DigestsToRowIdMapEntrySetIterator.hasNext() && namesToDiff2DigestsToRowIdMapEntrySetIterator.hasNext()) {
                                n1 = namesToDiff1DigestsToRowIdMapEntrySetIterator.next();
                                n2 = namesToDiff2DigestsToRowIdMapEntrySetIterator.next();
                                continue LOOP;
                            } else if (namesToDiff1DigestsToRowIdMapEntrySetIterator.hasNext()) {
                                System.out.println("In namesToDiff2: " + namesToDiff2DigestsToRowIdMapEntrySetIterator.next().getKey());
                                break LOOP;
                            } else if (namesToDiff2DigestsToRowIdMapEntrySetIterator.hasNext()) {
                                System.out.println("In namesToDiff1: " + namesToDiff1DigestsToRowIdMapEntrySetIterator.next().getKey());
                                break LOOP;
                            }
                            break;
                        case 1:
                            System.out.println("In namesToDiff2: " + key2);
                            if (namesToDiff2DigestsToRowIdMapEntrySetIterator.hasNext()) {
                                n2 = namesToDiff2DigestsToRowIdMapEntrySetIterator.next();
                            } else {
                                System.out.println("In namesToDiff1: " + key1);
                                break LOOP;
                            }
                            break;
                    }
                }

                while (namesToDiff1DigestsToRowIdMapEntrySetIterator.hasNext()) {
                    System.out.println("In namesToDiff1: " + namesToDiff1DigestsToRowIdMapEntrySetIterator.next().getKey());
                }

                while (namesToDiff2DigestsToRowIdMapEntrySetIterator.hasNext()) {
                    System.out.println("In namesToDiff2: " + namesToDiff2DigestsToRowIdMapEntrySetIterator.next().getKey());
                }
            }
        };
    }
}
