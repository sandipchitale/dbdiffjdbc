package dev.sandipchitale.dbdiffjdbc.postgres2.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.nio.charset.StandardCharsets;

@Table(name = "names")
public record NamesToDiff2WithCtId(@Id Long id, String name, int age, Object ctid) {
    // Used to calculate digest
    public byte[] toBytes() {
//        return String.format("%d|%s|%d", id, name, age).getBytes(StandardCharsets.UTF_8);
        return String.format("%s|%d", name, age).getBytes(StandardCharsets.UTF_8);
    }
}