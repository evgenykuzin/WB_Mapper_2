package ru.jekajops.wbtablemapper.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "stng_name")
    String name;
    @Column(name = "stng_value")
    String value;
    @Column(name = "stng_group")
    Group group;

    public static enum Group {
        COST_PRICE,
        ADVERTISEMENT
    }
}
