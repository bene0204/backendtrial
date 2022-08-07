package com.benem.findyourdreamjob.clients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @Length(max = 100)
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "^[a-z]+[0-9]*@[a-z]+\\.[a-z]+")
    private String email;

    @Column(name = "api_key")
    private String apiKey;
}
