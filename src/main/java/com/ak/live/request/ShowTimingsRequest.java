package com.ak.live.request;

import lombok.Data;

import java.sql.Date;

@Data
public class ShowTimingsRequest {
    private Date date;
    private Integer theaterId;
    private Integer movieId;
}
