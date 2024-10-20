package dev.sandipchitale.dbdiffjdbc.postgres2.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "names")
public record NamesToDiff2WithCtId(@Id Long id, String name, int age, String ctId) {
}