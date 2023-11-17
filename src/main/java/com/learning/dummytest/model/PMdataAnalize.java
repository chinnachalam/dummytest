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
@Table(name = "pmdata_analyze")*/
public class PMdataAnalize {
   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private Long id;
    private String type;
    private String coin;
    private String runTime;
    private String channel;
    private String dist;

}
