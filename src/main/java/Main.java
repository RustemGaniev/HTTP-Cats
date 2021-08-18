import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Main {

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        List<Cats> cats = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        stream(response.getAllHeaders()).forEach(System.out :: println);
        System.out.println("\n \n");

        cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cats>>(){});
        cats.stream()
                .filter(p -> p.getUpvotes() != null)
                .forEach(System.out::println);
    }


}
