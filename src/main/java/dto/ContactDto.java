package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder

public class ContactDto {

    int id;
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;
}
