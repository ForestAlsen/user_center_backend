package com.forestalsen.user_center.model.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class UserLoginRequest  implements Serializable {
   private static final long serialVersionUID = 1L;
    private String account;
    private String password;

}
