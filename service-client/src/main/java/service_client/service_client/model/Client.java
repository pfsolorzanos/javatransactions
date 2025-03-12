package service_client.service_client.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client extends Person {
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean state;
}
