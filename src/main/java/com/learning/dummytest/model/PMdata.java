package com.learning.dummytest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*@Entity
@Table(name = "pmdata")*/
public class PMdata {
/*    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private Long id;
    private String type;
    private String coin;
    private String runTime;
    private String channel;
    private String dist;
    private String result;

}
