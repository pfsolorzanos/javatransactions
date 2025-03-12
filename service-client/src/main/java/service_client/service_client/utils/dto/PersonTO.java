package service_client.service_client.utils.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonTO {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String address;
    private String phoneNumber;
    private String identificationId;
}
