package com.github.drearmoute.es.sdk.mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//text, keyword, date, long, double, boolean or ip.
public class Type {
    
    private String type;

}
