package sk.konstiak.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
@Builder
public class SearchRequest extends ResourceSupport {

    private String searchString;

}
