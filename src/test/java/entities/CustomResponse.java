package entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private int category_id;
    private String category_title;
    private int seller_id;

    public String company_name;
    public String seller_name;
    public String email;
    public String phone_number;
    public String address;

    List<CustomResponse> responses;
    private String message;



}
