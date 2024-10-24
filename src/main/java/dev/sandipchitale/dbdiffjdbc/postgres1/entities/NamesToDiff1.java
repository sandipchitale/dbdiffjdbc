package dev.sandipchitale.dbdiffjdbc.postgres1.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "names")
public record NamesToDiff1(@Id Long id, String name, int age) {
}