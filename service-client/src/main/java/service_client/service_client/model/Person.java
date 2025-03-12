package service_client.service_client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class Person implements Serializable {
    public Person() {}
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected Integer age;
    @Column(nullable = false)
    protected String gender;
    @Column(nullable = false)
    protected String address;
    @Column(nullable = false)
    protected String phoneNumber;
    @Column(nullable = false)
    protected String identificationId;
}
