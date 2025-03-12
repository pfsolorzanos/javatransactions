package service_account.service_account.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service_account.service_account.utils.dto.feign.ClientFeignTO;

import java.util.UUID;

@FeignClient(name = "service-client", url = "${client.service-data}/api/clientes")
public interface ClientFeignClient {

    @GetMapping("/{id}")
    ClientFeignTO getClientById(@PathVariable("id") UUID id);
}