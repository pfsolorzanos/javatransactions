package service_account.service_account.utils.dto.feign;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientFeignTO {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String address;
    private String phoneNumber;
    private String identificationId;
    private String password;
    private Boolean state;
}

