package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends IdGenerator {

        private String name;

        private Long officeId;

        @Override
        public String toString() {
                return "ClientDTO{" +
                        "name='" + name + '\'' +
                        ", officeID=" + officeId +
                        "} " + super.toString();
        }
}
