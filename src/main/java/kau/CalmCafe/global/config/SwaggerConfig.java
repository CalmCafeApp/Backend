package kau.CalmCafe.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "calm-cafe local 서버입니다."),
                @Server(url = "http://calm-cafe.sangsin.site", description = "calm-cafe http 서버입니다."),
                @Server(url = "https://calm-cafe.sangsin.site", description = "calm-cafe https 서버입니다.")
        }
)


public class SwaggerConfig {
}
