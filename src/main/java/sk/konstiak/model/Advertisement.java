package sk.konstiak.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Advertisement {

    private String title;
    private String description;
    private String author;
    private String location;
    private String link;
    private String imageLink;
    private Double oldPrice;
    private Double price;

}
