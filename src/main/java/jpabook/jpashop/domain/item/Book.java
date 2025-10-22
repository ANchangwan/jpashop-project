package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item {
    private String author;
    private String isbn;



}
