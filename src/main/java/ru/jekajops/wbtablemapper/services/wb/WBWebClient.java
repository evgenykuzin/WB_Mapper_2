package ru.jekajops.wbtablemapper.services.wb;//package ru.jekajops.ru.jekajops.wbtablemapper.services.wb;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//public class WBWebClient {
//    private final WebClient webClient;
//    private final ObjectMapper logMapper = ObjectMapperFactory.create();
//    private final String requestPath;
//
//    public WBWebClient(final WebClient.Builder webClientBuilder,
//                                  final PropertiesCreateContextHolder properties) {
//        webClient = webClientBuilder.baseUrl(properties.getUrl())
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//        requestPath = properties.getRequestPath();
//    }
//
//    public Object request(Object rq) {
//        try {
//            if (log.isTraceEnabled()) {
//                log.trace("rq:{}", logMapper.writeValueAsString(rq));
//            }
//            final Mono<CreateContextRs> response = webClient.post().uri(requestPath)
//                    .body(Mono.just(rq), CreateContextRq.class)
//                    .retrieve()
//                    // Фильтры ошибок по типу
//                    .onStatus(HttpStatus::is5xxServerError, this::proceedServerError)
//                    .onStatus(HttpStatus::is4xxClientError, this::proceedClientError)
//                    .bodyToMono(CreateContextRs.class);
//            CreateContextRs rs = response.block();
//            if (log.isTraceEnabled()) {
//                log.trace("rs:ready");
//                log.trace("rs:{}", logMapper.writeValueAsString(rs));
//            }
//            return rs;
//        } catch (JsonProcessingException e) {
//            log.error(e.getMessage(), e);
//            throw new CreateContextRuntimeException(e);
//        } catch (Throwable th) {
//            log.error(th.getMessage(), th);
//            throw new CreateContextRuntimeException(th);
//        }
//    }
//
//    private Mono<GroupRequisitesRuntimeException> proceedServerError(ClientResponse exceptionFunction) {
//        log.error("proceedServerError:{}", exceptionFunction.toString());
//        return Mono.error(new CreateContextRuntimeException("CreateContext error"));
//    }
//
//    private Mono<ServiceInfoRuntimeException> proceedClientError(ClientResponse exceptionFunction) {
//        log.error("proceedClientError:{}", exceptionFunction.toString());
//        return Mono.error(new CreateContextRuntimeException("CreateContext client error"));
//    }
//}
