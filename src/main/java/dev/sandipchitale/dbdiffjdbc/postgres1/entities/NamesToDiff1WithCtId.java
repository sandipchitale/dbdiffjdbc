package dev.sandipchitale.dbdiffjdbc.postgres1.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.nio.charset.StandardCharsets;

@Table(name = "names")
public record NamesToDiff1WithCtId(@Id Long id, String name, int age, Object ctid) {
    // Used to calculate digest
    public byte[] toBytes() {
        return String.format("%d|%s|%d", id, name, age).getBytes(StandardCharsets.UTF_8);
    }
}